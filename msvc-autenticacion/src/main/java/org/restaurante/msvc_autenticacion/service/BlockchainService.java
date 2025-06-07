package org.restaurante.msvc_autenticacion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.restaurante.msvc_autenticacion.dto.Blockchain.*;
import org.restaurante.msvc_autenticacion.model.*;
import org.restaurante.msvc_autenticacion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlockchainService {

    private final HttpGraphQlClient graphQlClient;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    private CuentaMesaRepository cuentaMesaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Value("${blockchain.url:http://localhost:3001/graphql}")
    private String blockchainUrl;

    public BlockchainService(
            @Value("${blockchain.url:http://localhost:3001/graphql}") String blockchainUrl
    ) {
        WebClient webClient = WebClient.builder()
                .baseUrl(blockchainUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.graphQlClient = HttpGraphQlClient.builder(webClient).build();
        log.info("BlockchainService inicializado con URL: {}", blockchainUrl);
    }


    public Mono<BlockchainResponse> registrarVenta(Venta venta){
        CuentaMesa cuentaMesa =venta.getCuentaMesa();
        Mesa mesa= cuentaMesa.getMesa();
        Cliente cliente=cuentaMesa.getCliente();

        List<Pedido> pedidos = pedidoRepository.findByCuentaMesaCuentaMesaId(cuentaMesa.getCuentaMesaId());

        GenerateHashInputDTO inputDTO= construirHashInput(venta,cuentaMesa,mesa,cliente,pedidos);

         String mutation = """
            mutation GenerateHash($input: GenerateHashInput!) {
              generateHash(input: $input) {
                success
                message
                error
                hash {
                  id
                  hash
                  ventaId
                  tenantId
                  createdAt
                  status
                }
                databaseId
              }
            }
        """;

         return graphQlClient.document(mutation)
                 .variable("input", inputDTO)
                 .retrieve("generateHash")
                 .toEntity(BlockchainResponse.class)
                 .doOnSuccess(response ->{
                     if (response.isSuccess()) {
                         log.info("Venta {} registrada en blockchain con hash {}",
                                 venta.getVentaId(), response.getHash().getHash());
                     } else {
                         log.error("Error registrando venta en blockchain: {}",
                                 response.getError());
                     }
                 })
                 .doOnError(e -> log.error("Error comunicándose con blockchain: {}", e.getMessage()));


    }


    private GenerateHashInputDTO construirHashInput(
            Venta venta, CuentaMesa cuentaMesa, Mesa mesa, Cliente cliente, List<Pedido> pedidos
    ) {
        GenerateHashInputDTO input = new GenerateHashInputDTO();


        input.setVentaId(venta.getVentaId());
        input.setTenantId(venta.getTenant().getTenantId());


        DatosVentaDTO datosVenta = new DatosVentaDTO();
        datosVenta.setVentaId(venta.getVentaId());
        datosVenta.setTenantId(venta.getTenant().getTenantId());
        datosVenta.setFechaVenta(venta.getCreatedAt().toString());
        datosVenta.setTotal(venta.getTotal().doubleValue());
        datosVenta.setEstado(venta.getEstado());
        datosVenta.setCuentaMesaId(cuentaMesa.getCuentaMesaId());


        MesaBlockchainDTO mesaDTO = new MesaBlockchainDTO();
        mesaDTO.setNumero(mesa.getNumero());
        mesaDTO.setCapacidad(mesa.getCapacidad());
        datosVenta.setMesa(mesaDTO);


        if (cliente != null) {
           ClienteBlockchainDTO clienteDTO = new ClienteBlockchainDTO();
            clienteDTO.setClienteId(cliente.getClienteId());
            clienteDTO.setNombre(cliente.getNombre());
            clienteDTO.setEmail(cliente.getEmail());
            datosVenta.setCliente(clienteDTO);
        }


        List<ProductoBlockchainDTO> productos = pedidos.stream()
                .flatMap(pedido -> pedidoDetalleRepository.findByPedidoPedidoId(pedido.getPedidoId()).stream())
                .map(detalle -> {
                    ProductoBlockchainDTO producto = new ProductoBlockchainDTO();
                    producto.setProductoId(detalle.getProducto().getProductoId());
                    producto.setNombre(detalle.getProducto().getNombre());
                    producto.setCantidad(detalle.getCantidad());
                    producto.setPrecio(detalle.getProducto().getPrecio());
                    producto.setSubtotal(detalle.getSubtotal().doubleValue());
                    return producto;
                })
                .collect(Collectors.toList());

        datosVenta.setProductos(productos);
        input.setDatosVenta(datosVenta);

        return input;
    }


    public BlockchainVerificationResponse verificarVenta(Long ventaId, Long tenantId) {
        try {
            // Consulta GraphQL simple
            String query = String.format(
                    "{\"query\":\"query{verificarIntegridadVenta(ventaId:\\\"%s\\\",tenantId:\\\"%s\\\"){integra,mensaje}}\"}",
                    ventaId, tenantId
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(query, headers);

            // Enviar consulta (cambia el nombre de la variable a httpResponse)
            var httpResponse = restTemplate.postForObject(
                    blockchainUrl,
                    entity,
                    Map.class
            );

            // Extraer resultado
            Map data = (Map)((Map)httpResponse.get("data")).get("verificarIntegridadVenta");

            // Ahora usamos otro nombre para esta variable
            BlockchainVerificationResponse verificationResponse = new BlockchainVerificationResponse();
            verificationResponse.setValid((Boolean)data.get("integra"));
            verificationResponse.setMessage((String)data.get("mensaje"));
            verificationResponse.setTimestamp(new Date());
            return verificationResponse;

        } catch (Exception e) {
            BlockchainVerificationResponse errorResponse = new BlockchainVerificationResponse();
            errorResponse.setValid(false);
            errorResponse.setMessage("Error: " + e.getMessage());
            errorResponse.setTimestamp(new Date());
            return errorResponse;
        }
    }

}

package org.restaurante.msvc_autenticacion.service;

import org.restaurante.msvc_autenticacion.dto.Producto.ProductoDTO;
import org.restaurante.msvc_autenticacion.dto.Producto.ProductoInput;
import org.restaurante.msvc_autenticacion.mapper.CategoriaMapper;
import org.restaurante.msvc_autenticacion.mapper.ProductoMapper;
import org.restaurante.msvc_autenticacion.model.Categoria;
import org.restaurante.msvc_autenticacion.model.Producto;
import org.restaurante.msvc_autenticacion.model.Tenant;
import org.restaurante.msvc_autenticacion.model.UnidadMedida;
import org.restaurante.msvc_autenticacion.repository.CategoriaRepository;
import org.restaurante.msvc_autenticacion.repository.ProductoRepository;
import org.restaurante.msvc_autenticacion.repository.TenantRepository;
import org.restaurante.msvc_autenticacion.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;
    @Autowired
    private ProductoMapper productoMapper;

    public ProductoDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return productoMapper.toDto(producto);
    }

    public List<ProductoDTO> findByTenantId(Long tenantId) {
        return productoRepository.findByTenantTenantId(tenantId).stream()
                .map(productoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductoDTO create(ProductoInput input){
        Producto producto = new Producto();
        return saveOrUpdateProducto(producto, input);
    }

    private ProductoDTO saveOrUpdateProducto(Producto producto, ProductoInput input) {
        producto.setNombre(input.getNombre());
        producto.setNombreCorto(input.getNombreCorto());
        producto.setDescripcion(input.getDescripcion());
        producto.setPrecio(input.getPrecio());

        Tenant tenant = tenantRepository.findById(input.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado con id: " + input.getTenantId()));

        Categoria categoria = categoriaRepository.findById(input.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + input.getCategoriaId()));

        UnidadMedida unidadMedida = unidadMedidaRepository.findById(input.getUnidadMedidaId())
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada con id: " + input.getUnidadMedidaId()));

        producto.setCategoria(categoria);
        producto.setUnidadMedida(unidadMedida);
        producto.setTenant(tenant);

        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDto(savedProducto);




    }


}

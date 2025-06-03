package org.restaurante.msvc_autenticacion.mapper;

import org.restaurante.msvc_autenticacion.dto.Producto.ProductoDTO;
import org.restaurante.msvc_autenticacion.dto.Producto.ProductoSimpleDTO;
import org.restaurante.msvc_autenticacion.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private UnidadMedidaMapper unidadMedidaMapper;

    public ProductoDTO toDto(Producto producto){
        if (producto == null) {
            return null;
        }

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setProductoId(producto.getProductoId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setNombreCorto(producto.getNombreCorto());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());

        if (producto.getCategoria() != null) {
            productoDTO.setCategoria(categoriaMapper.toSimpleDto(producto.getCategoria()));
        }

        if (producto.getUnidadMedida() != null) {
            productoDTO.setUnidadMedida(unidadMedidaMapper.toSimpleDto(producto.getUnidadMedida()));
        }

        if (producto.getTenant() != null) {
            productoDTO.setTenant(tenantMapper.toSimpleDto(producto.getTenant()));
        }

        return productoDTO;
    }

    public ProductoSimpleDTO toSimpleDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        return new ProductoSimpleDTO(
                producto.getProductoId(),
                producto.getNombre(),
                producto.getNombreCorto(),
                producto.getDescripcion(),
                producto.getPrecio()
        );
    }


}

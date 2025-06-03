package org.restaurante.msvc_autenticacion.controller;

import org.restaurante.msvc_autenticacion.dto.Producto.ProductoDTO;
import org.restaurante.msvc_autenticacion.dto.Producto.ProductoInput;
import org.restaurante.msvc_autenticacion.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @QueryMapping
    public ProductoDTO productoById(@Argument Long id) {
        return productoService.findById(id);
    }

    @QueryMapping
    public List<ProductoDTO> productosByTenantId(@Argument Long tenantId) {
        return productoService.findByTenantId(tenantId);
    }

    @MutationMapping
    public ProductoDTO createProducto(@Argument ProductoInput input) {
        return productoService.create(input);
    }

}

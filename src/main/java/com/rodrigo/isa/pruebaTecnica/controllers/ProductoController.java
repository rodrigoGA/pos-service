package com.rodrigo.isa.pruebaTecnica.controllers;

import com.rodrigo.isa.pruebaTecnica.data.entities.Producto;
import com.rodrigo.isa.pruebaTecnica.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;

   
    @Operation(summary = "Muestra el detalle de un producto")
    @GetMapping("/productos/{id}")
    public Producto one(@PathVariable Long id) {
        return productoService.findById(id);
    }
    
    @Operation(summary = "Lista los productos")
    @GetMapping("/productos")
    public Page<Producto> all(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "orderby", required = false) String orderby
    ) {
        return productoService.buscar(page, size, orderby);
    }

    @Operation(summary = "Crea un producto nuevo y lo inicializa con stock 10")
    @PostMapping("/productos")
    public Producto crearProducto(
            @RequestBody Producto body
    ) {
        return productoService.actualizarProducto(body);
    }
    
    @Operation(summary = "Actualiza un producto existente y su stock")
    @PutMapping("/productos")
    public Producto actualizarProducto(
            @RequestBody Producto body
    ) {
        return productoService.actualizarProducto(body);
    }
    
}

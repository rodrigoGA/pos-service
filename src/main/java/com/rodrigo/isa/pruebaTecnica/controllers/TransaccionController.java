package com.rodrigo.isa.pruebaTecnica.controllers;

import com.rodrigo.isa.pruebaTecnica.data.dtos.CreateTransaccionLineaDTO;
import com.rodrigo.isa.pruebaTecnica.data.entities.Transaccion;
import com.rodrigo.isa.pruebaTecnica.services.LogService;
import com.rodrigo.isa.pruebaTecnica.services.TransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private LogService logService;
   
    @Operation(summary = "Muestra el detalle de una transaccion")
    @GetMapping("/transacciones/{id}")
    public Transaccion one(@PathVariable Long id) {
        return transaccionService.findById(id);
    }
    
    
    @Operation(summary = "Consulta las transacciones realizadas")
    @GetMapping("/transacciones")
    public Page<Transaccion> all(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "orderby", required = false) String orderby
    ) {
        return transaccionService.buscarTransacciones(page, size, orderby);
    }

    
    @Operation(summary = "Crea una nueva venta")
    @PostMapping("/transacciones")
    public Transaccion crearTransaccion(
            @RequestBody List<CreateTransaccionLineaDTO> body
    ) {
        try{
            Transaccion res = transaccionService.crearTransacccion(body);
            logService.logData(body, true);
            return res;
        }catch(Throwable t){
            logService.logData(body, false);
            //se vuelve a tirar la exepcion y es manejada por el ExcpetionAdvice
            throw t;
        }
        
    }
    
}

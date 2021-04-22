/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.services;

import com.rodrigo.isa.pruebaTecnica.data.dtos.CreateTransaccionLineaDTO;
import com.rodrigo.isa.pruebaTecnica.data.entities.Linea;
import com.rodrigo.isa.pruebaTecnica.data.entities.Numerador;
import com.rodrigo.isa.pruebaTecnica.data.entities.Stock;
import com.rodrigo.isa.pruebaTecnica.data.entities.Transaccion;
import com.rodrigo.isa.pruebaTecnica.exceptions.BussinessException;
import com.rodrigo.isa.pruebaTecnica.exceptions.BussinessMessage;
import com.rodrigo.isa.pruebaTecnica.exceptions.ExceptionConstants;
import com.rodrigo.isa.pruebaTecnica.exceptions.ObjetoNoEncontradoException;
import com.rodrigo.isa.pruebaTecnica.data.repositories.NumeradorRespository;
import com.rodrigo.isa.pruebaTecnica.data.repositories.StockRespository;
import com.rodrigo.isa.pruebaTecnica.data.repositories.TransaccionRespository;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rodrigo
 */
@Slf4j
@Service
@Transactional
public class TransaccionService implements Serializable {

    @Autowired
    TransaccionRespository transaccionRespository;

    @Autowired
    NumeradorRespository numeradorRepository;

    @Autowired
    StockRespository stockRespository;

    public static Transaccion initTransaccion(Transaccion t) {
        if (t == null) {
            return t;
        }
        t.getLienas().isEmpty();
        return t;
    }

    public Transaccion findById(Long id) {
        Transaccion t = transaccionRespository.findById(id)
                .orElseThrow(() -> new ObjetoNoEncontradoException("Transaccion", id));

        return initTransaccion(t);
    }

    public Page<Transaccion> buscarTransacciones(Integer page, Integer size, String orderby) {

        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 20;
        }
        if (orderby == null) {
            orderby = "numero";
        }

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(orderby));

        Page<Transaccion> res = transaccionRespository.searchTransacccion(pageRequest);
        for (Transaccion t : res.getContent()) {
            initTransaccion(t);
        }
        return res;
    }

    public Transaccion crearTransacccion(List<CreateTransaccionLineaDTO> lineasAcrear) {
        //se hace validacion
        if (lineasAcrear == null || lineasAcrear.isEmpty()) {
            throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "Datos vacios"));
        }
        for (CreateTransaccionLineaDTO item : lineasAcrear) {
            if (item.getTotal() == null || item.getTotal() < 0) {
                throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "El total de un producto no puede ser menor a 0"));
            }
            if (item.getProductoId()== null) {
                throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "Linea no asociada a un producto"));
            }
        }

        //se hace lock a nivel de base para tener control sobre el inventario, y el numerador de transacciones
        Numerador numerador = numeradorRepository
                .findByCodigo("NUMERADOR_TRANSACCION")
                .orElseThrow(() -> new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_NUMERADOR, "Falta iniciar el numerador de transaccion")));

        
        Transaccion t = new Transaccion();
        t.setFecha(new Date());
        t.setLienas(new LinkedList());
        
        for (CreateTransaccionLineaDTO item : lineasAcrear) {
            List<Stock> resultStock = stockRespository.findStockByProduct(item.getProductoId());
            if (resultStock.size() != 1) {
                throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_STOCK, "Stock no definido "));
            }

            Stock stock = resultStock.get(0);
            stock.setCantidad(stock.getCantidad() - item.getTotal());
            if (stock.getCantidad() < 0) {
                throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_STOCK, "Stock insuficiente para el producto " + stock.getProducto().getCodigo()));
            }

            Linea linea = new Linea();
            linea.setProducto(stock.getProducto());
            linea.setTotal(item.getTotal());
            linea.setTransaccion(t);
            t.getLienas().add(linea);
        }

        
        t.setNumero(numerador.getSiguienteNumero());
        numerador.setSiguienteNumero(numerador.getSiguienteNumero() + 1);
        
        return transaccionRespository.save(t);
    }


}

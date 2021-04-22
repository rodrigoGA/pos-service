/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.services;


import com.rodrigo.isa.pruebaTecnica.data.entities.Producto;
import com.rodrigo.isa.pruebaTecnica.data.entities.Stock;
import com.rodrigo.isa.pruebaTecnica.exceptions.BussinessException;
import com.rodrigo.isa.pruebaTecnica.exceptions.BussinessMessage;
import com.rodrigo.isa.pruebaTecnica.exceptions.ExceptionConstants;
import com.rodrigo.isa.pruebaTecnica.exceptions.ObjetoNoEncontradoException;
import com.rodrigo.isa.pruebaTecnica.data.repositories.ProductoRespository;
import com.rodrigo.isa.pruebaTecnica.data.repositories.StockRespository;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class ProductoService implements Serializable {

    @Autowired 
    ProductoRespository productoRespository;
    
    @Autowired 
    StockRespository stockRespository;

   
    public Producto findById(Long id) {
        Producto p = productoRespository.findById(id)
            .orElseThrow(() -> new ObjetoNoEncontradoException("Producto", id));

        return p;
    }
    
    

    public Page<Producto> buscar(Integer page, Integer size, String orderby) {

        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 20;
        }
        if (orderby == null) {
            orderby = "id";
        }

        Pageable pageRequest = PageRequest.of(page, size , Sort.by(orderby));
        return productoRespository.buscar(pageRequest);
    }
    
    
    /**
     * Crea un producto y lo inicializa con stock 10
     * 
     * @param p
     * @return 
     */
    public Producto crearProducto(Producto p) {        
        validateProductFields(p);  
        if (p.getId() != null ){
            throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "Operacion permitida solo  para crear productos"));
        }       
        
        p = productoRespository.save(p);
        
        //se setea el stock por default
        Stock s = new Stock();
        s.setCantidad(10);
        s.setProducto(p);
        stockRespository.save(s);
        
        return p;   
    }
    
    
    /**
     * Actualiza un producto y su stock 
     * 
     * @param p
     * @return 
     */
    public Producto actualizarProducto(Producto p) {        
        validateProductFields(p);      
        if (p.getId() == null ){
            throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "Operacion permitida solo  para crear editar productos"));
        }
                
        if (p.getStock() == null || p.getStock().getCantidad() == null || p.getStock().getCantidad() < 0){
           throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "Stock inconsistente"));
        }
        List<Stock> stockList = stockRespository.findStockByProduct(p.getId());
        if (stockList.size()!=1){
            throw new BussinessException(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "Stock inconsistente"));
        }

        Stock s = stockList.get(0);
        s.setCantidad(p.getStock().getCantidad());

                
        
        return productoRespository.save(p);   
    }
    
    
    public static void validateProductFields(Producto p){
        List<BussinessMessage> errors = new LinkedList();
        
        if (p.getPrecioUnitario() == null || p.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0){
            errors.add(new BussinessMessage(ExceptionConstants.ERROR_CAMPO, "Precio unitario incorrecto"));
        }
        
        if (!errors.isEmpty()){
            throw new BussinessException(errors);
        }
               
    }
    
    
    
    
    
    

}

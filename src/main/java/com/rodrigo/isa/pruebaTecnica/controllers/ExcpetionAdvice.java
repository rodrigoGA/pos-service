/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.controllers;


import com.rodrigo.isa.pruebaTecnica.exceptions.BussinessException;
import com.rodrigo.isa.pruebaTecnica.exceptions.BussinessMessage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author rodrigo
 */
@Slf4j
@ControllerAdvice 
public class ExcpetionAdvice {

    
    @ResponseBody
    @ExceptionHandler(BussinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<BussinessMessage> bussinesExcptionAdviceFoundHandler(BussinessException ex) {
        return ex.getBussinessMessages();
    }
    
    
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String errorInsesperadoHandler(Throwable ex) {
        log.error("ERROR_NO_CONTROLADO", ex);
        //TODO: cambiar responder el mensaje del error por un codigo m[as gen'erico
        return ex.getMessage();
    }
    
   
    
}

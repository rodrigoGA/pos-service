/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigo.isa.pruebaTecnica.data.dtos.CreateTransaccionLineaDTO;
import com.rodrigo.isa.pruebaTecnica.data.entities.TransaccionesLog;
import com.rodrigo.isa.pruebaTecnica.data.repositories.TransaccionLogRespository;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rodrigo
 */
@Slf4j
@Service
@Transactional
public class LogService implements Serializable {

    @Autowired
    TransaccionLogRespository transaccionesLogRepo;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * Se encarga de guardar el registro de cada llamda en una nueva transaccion
     * 
     * @param body transaccion solicitada
     * @param result true si la transaccion se ejecuto correctamente
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logData(List<CreateTransaccionLineaDTO> body, boolean result) {
        try {
            TransaccionesLog logT = new TransaccionesLog();
            logT.setFecha(new Date());
            logT.setOk(result);
            logT.setBody(mapper.writeValueAsString(body));

            transaccionesLogRepo.save(logT);
        } catch (Throwable e) {
            log.error("Error al guardar log");
        }
    }

}

package com.rodrigo.isa.pruebaTecnica.data.repositories;

import com.rodrigo.isa.pruebaTecnica.data.entities.TransaccionesLog;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author usuario
 */
public interface TransaccionLogRespository extends CrudRepository<TransaccionesLog, Long> {
    
    
}

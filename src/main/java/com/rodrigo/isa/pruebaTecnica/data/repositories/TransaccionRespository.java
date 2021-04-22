package com.rodrigo.isa.pruebaTecnica.data.repositories;

import com.rodrigo.isa.pruebaTecnica.data.entities.Transaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author usuario
 */
public interface TransaccionRespository extends CrudRepository<Transaccion, Long> {

    @Query("SELECT t FROM Transaccion t " )
    Page<Transaccion> searchTransacccion(
            Pageable pageRequest
    );
    
    
    
}

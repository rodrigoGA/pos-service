package com.rodrigo.isa.pruebaTecnica.data.repositories;

import com.rodrigo.isa.pruebaTecnica.data.entities.Numerador;
import com.rodrigo.isa.pruebaTecnica.data.entities.Transaccion;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author usuario
 */
public interface NumeradorRespository extends JpaRepository<Numerador, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<Numerador> findByCodigo(String Codigo);
    
    
}

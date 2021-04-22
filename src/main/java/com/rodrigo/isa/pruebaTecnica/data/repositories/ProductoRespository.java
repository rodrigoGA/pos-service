package com.rodrigo.isa.pruebaTecnica.data.repositories;

import com.rodrigo.isa.pruebaTecnica.data.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author usuario
 */
public interface ProductoRespository extends CrudRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p ")
    Page<Producto> buscar(
            Pageable pageRequest
    );

}

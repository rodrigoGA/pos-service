package com.rodrigo.isa.pruebaTecnica.data.repositories;

import com.rodrigo.isa.pruebaTecnica.data.entities.Stock;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author usuario
 */
public interface StockRespository extends CrudRepository<Stock, Long> {

    
    @Query("SELECT s FROM Stock s WHERE s.producto.id = :productId ")
    public List<Stock> findStockByProduct(Long productId);

    @Query("SELECT p FROM Stock p ")
    public Page<Stock> buscar(
            Pageable pageRequest
    );
}

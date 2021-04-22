package com.rodrigo.isa.pruebaTecnica;

import com.rodrigo.isa.pruebaTecnica.data.dtos.CreateTransaccionLineaDTO;
import com.rodrigo.isa.pruebaTecnica.data.entities.Producto;
import com.rodrigo.isa.pruebaTecnica.data.entities.Transaccion;
import com.rodrigo.isa.pruebaTecnica.services.ProductoService;
import com.rodrigo.isa.pruebaTecnica.services.TransaccionService;
import com.sun.xml.bind.v2.runtime.reflect.ListTransducedAccessorImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class PruebaTecnicaApplicationTests {

    @Autowired 
    TransaccionService transaccionService;
    
    
    @Autowired 
    ProductoService productoService;
            
    @Test
    void test1Compra() throws InterruptedException {
        List<CreateTransaccionLineaDTO> l = new LinkedList();
        
        CreateTransaccionLineaDTO element1 = new CreateTransaccionLineaDTO();
        element1.setProductoId(1L);
        element1.setTotal(1);
        l.add(element1);
        
        
        transaccionService.crearTransacccion(l);
        
        Producto p = productoService.findById(1L);
        assertEquals(p.getStock().getCantidad(), 9);
    }
    
    
    
    @Test
    void testConcurrencia() throws InterruptedException {
        List<CreateTransaccionLineaDTO> l = new LinkedList();
        
        CreateTransaccionLineaDTO element2 = new CreateTransaccionLineaDTO();
        element2.setProductoId(2L);
        element2.setTotal(1);
        l.add(element2);
        
        CreateTransaccionLineaDTO element3 = new CreateTransaccionLineaDTO();
        element3.setProductoId(3L);
        element3.setTotal(1);
        l.add(element3);
        
                
        
        int numberOfThreads = 10;

        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                transaccionService.crearTransacccion(l);
                latch.countDown();
            });
        }
        latch.await();


        Producto p2 = productoService.findById(2L);
        Producto p3 = productoService.findById(3L);
        

        assertEquals(p2.getStock().getCantidad(), 0);
        assertEquals(p3.getStock().getCantidad(), 0);

    }

}

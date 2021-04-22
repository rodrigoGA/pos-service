/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 * @author rodrigo
 */
@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name = "productos")
public class Producto implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name="codigo")
    private String codigo;
        
    @Column(name="descripcion")
    private String descripcion;
  
    @Column(name="precio_unitario", precision=14, scale=2)
    private BigDecimal precioUnitario;
            
            
    @OneToOne(mappedBy = "producto")
    private Stock stock;
}

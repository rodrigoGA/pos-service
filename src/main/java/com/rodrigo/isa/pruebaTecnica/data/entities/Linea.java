/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;


/**
 *
 * @author rodrigo
 */
@Data
@Entity
@Table(name = "lineas")
public class Linea implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @JsonIgnore  
    @ManyToOne
    @JoinColumn(name = "transaccion_numero", nullable = false, foreignKey = @ForeignKey(name = "fk_lieas_transacciones"))
    private Transaccion transaccion;
    
    @Column(name="total")
    private Integer total;
        
        
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lienas_productos"))
    private Producto producto;
    
    
}

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 * @author rodrigo
 */
@EqualsAndHashCode(of="id")
@Data
@Entity
@Table(name = "stocks")
public class Stock implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
       
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "producto_id", nullable = false, unique=true, foreignKey = @ForeignKey(name = "fk_stocks_productos"))
    private Producto producto;
    
    @Column(name="cantidad")
    private Integer cantidad;
    


}

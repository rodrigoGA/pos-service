/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.data.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


/**
 *
 * @author rodrigo
 */
@Data
@Entity
@Table(name = "numeradores")
public class Numerador implements Serializable{

    @Id
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name="siguiente_numero")
    private Long siguienteNumero;
        
    
    
}

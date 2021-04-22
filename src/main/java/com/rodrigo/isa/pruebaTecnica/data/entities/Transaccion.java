/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.data.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;


/**
 *
 * @author rodrigo
 */
@Data
@Entity
@Table(name = "transaciones")
public class Transaccion implements Serializable{

    @Id
    @Column(name = "numero")
    private Long numero;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;
    
        
    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.ALL,  orphanRemoval = true )
    private List<Linea> lienas;

    
    
    
}

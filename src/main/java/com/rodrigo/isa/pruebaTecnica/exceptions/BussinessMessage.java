/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.exceptions;

import lombok.Data;

/**
 *
 * @author rodrigo
 */
@Data
public class BussinessMessage  {
    private String codigo;
    private String mensaje;

    public BussinessMessage(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public BussinessMessage() {
    }    

    

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.data.dtos;

import lombok.Data;

/**
 *
 * @author rodrigo
 */

@Data
public class CreateTransaccionLineaDTO {    
    private Integer total;
    private Long productoId;
}

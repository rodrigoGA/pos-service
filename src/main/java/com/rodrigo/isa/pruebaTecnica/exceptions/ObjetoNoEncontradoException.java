/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rodrigo.isa.pruebaTecnica.exceptions;


/**
 *
 * @author rodrigo
 */
public class ObjetoNoEncontradoException extends BussinessException {

    public ObjetoNoEncontradoException(String clase, Long id) {
      super();
      BussinessMessage b = new BussinessMessage("OBJETO_NO_ENCONTRADO", clase + " con id=" + id + " no encontrado");
      this.getBussinessMessages().add(b);
    }

    public ObjetoNoEncontradoException(String clase, String id) {
      super();
      BussinessMessage b = new BussinessMessage("OBJETO_NO_ENCONTRADO", clase + " con id=" + id + " no encontrado");
      this.getBussinessMessages().add(b);
    }

}

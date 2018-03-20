/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.modelo;

import java.io.Serializable;

/**
 *
 * @author Kevin Onofre
 */
public class StatusWS implements Serializable{
    private String mensaje;
    private String status;

    public StatusWS(String mensaje, String status) {
        this.mensaje = mensaje;
        this.status = status;
    }
    
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getStatus() {
        return status;
    }

    public StatusWS() {
    }

    
}

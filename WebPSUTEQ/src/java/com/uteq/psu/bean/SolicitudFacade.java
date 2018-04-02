/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.bean;

import com.uteq.psu.modelo.Solicitud;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kevin Onofre
 */
@Stateless
public class SolicitudFacade extends AbstractFacade<Solicitud> {

    @PersistenceContext(unitName = "WebPSUTEQPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudFacade() {
        super(Solicitud.class);
    }
    
}

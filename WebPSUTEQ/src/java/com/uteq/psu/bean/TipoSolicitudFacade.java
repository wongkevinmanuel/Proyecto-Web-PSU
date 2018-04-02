/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.bean;

import com.uteq.psu.modelo.TipoSolicitud;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kevin Onofre
 */
@Stateless
public class TipoSolicitudFacade extends AbstractFacade<TipoSolicitud> {

    @PersistenceContext(unitName = "WebPSUTEQPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoSolicitudFacade() {
        super(TipoSolicitud.class);
    }
    
}

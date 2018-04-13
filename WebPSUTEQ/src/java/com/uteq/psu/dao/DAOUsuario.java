/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.dao;

import com.uteq.psu.bean.UsuarioFacade;
import com.uteq.psu.modelo.Usuario;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kevin Onofre
 */
public class DAOUsuario extends UsuarioFacade {

    public DAOUsuario() {
    }

    @Override
    public Usuario buscarUsuario(int cedula) {
        
        //query = "SELECT u FROM Usuario u WHERE u.cedula = :cedula"
        Usuario usuario=null;
        try {
                javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                javax.persistence.criteria.CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
                javax.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
                javax.persistence.criteria.ParameterExpression <Integer> p = 
                getEntityManager().getCriteriaBuilder().parameter(Integer.class);
                cq.select(rt).where(
                        getEntityManager().getCriteriaBuilder().gt(
                                rt.get("cedula"),
                                p));
                TypedQuery<Usuario> query = getEntityManager().createQuery(cq);
                query.setParameter(p, cedula);
                usuario = query.getSingleResult();
        } catch (PersistenceException a) {
                String error = a.toString();
        }
        if(usuario == null)
            return null;
        else
            return usuario;
    }
    
    
}

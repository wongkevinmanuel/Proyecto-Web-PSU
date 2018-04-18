package com.uteq.psu.bean;

import com.uteq.psu.modelo.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kevin Onofre
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "WebPSUTEQPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario buscarUsuarioCed(String cedula) {
        Usuario usuario=null;
        try {
                javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                javax.persistence.criteria.CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
                javax.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
                javax.persistence.criteria.ParameterExpression <String> p = 
                cb.parameter(String.class);
                cq.select(rt).where(
                        getEntityManager().getCriteriaBuilder().like(rt.get("cedula"),p));
                
                TypedQuery<Usuario> query = getEntityManager().createQuery(cq);
                query.setParameter(p, cedula);
                usuario = query.getSingleResult();
        } catch (PersistenceException a) {usuario=null;}
        if(usuario == null)
            return null;
        else
            return usuario;
        
    }
    
    
    
}

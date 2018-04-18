
package com.uteq.psu.bean;

import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.modelo.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kevin Onofre
 */
@Stateless
public class RegistroSolicitudFacade extends AbstractFacade<RegistroSolicitud> {

    @PersistenceContext(unitName = "WebPSUTEQPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroSolicitudFacade() {
        super(RegistroSolicitud.class);
    }

    public List<RegistroSolicitud> findRange(int[] range,Usuario idUser) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(RegistroSolicitud.class));
        javax.persistence.Query q = getEntityManager().createNamedQuery("RegistroSolicitud.encontrarRegistroSolixUsuario", RegistroSolicitud.class);
        q.setParameter("idUser", idUser);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    
}

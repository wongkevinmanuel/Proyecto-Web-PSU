/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.controlador;

import com.uteq.psu.controlador.exceptions.NonexistentEntityException;
import com.uteq.psu.controlador.exceptions.PreexistingEntityException;
import com.uteq.psu.controlador.exceptions.RollbackFailureException;
import com.uteq.psu.modelo.RegistroSolicitud;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kevin Onofre
 */
public class RegistroSolicitudJpaController implements Serializable {

    public RegistroSolicitudJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroSolicitud registroSolicitud) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Solicitud idSolicitud = registroSolicitud.getIdSolicitud();
            if (idSolicitud != null) {
                idSolicitud = em.getReference(idSolicitud.getClass(), idSolicitud.getIdSolicitud());
                registroSolicitud.setIdSolicitud(idSolicitud);
            }
            Usuario idUsuario = registroSolicitud.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                registroSolicitud.setIdUsuario(idUsuario);
            }
            em.persist(registroSolicitud);
            if (idSolicitud != null) {
                idSolicitud.getRegistroSolicitudList().add(registroSolicitud);
                idSolicitud = em.merge(idSolicitud);
            }
            if (idUsuario != null) {
                idUsuario.getRegistroSolicitudList().add(registroSolicitud);
                idUsuario = em.merge(idUsuario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegistroSolicitud(registroSolicitud.getIdRegistro()) != null) {
                throw new PreexistingEntityException("RegistroSolicitud " + registroSolicitud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroSolicitud registroSolicitud) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegistroSolicitud persistentRegistroSolicitud = em.find(RegistroSolicitud.class, registroSolicitud.getIdRegistro());
            Solicitud idSolicitudOld = persistentRegistroSolicitud.getIdSolicitud();
            Solicitud idSolicitudNew = registroSolicitud.getIdSolicitud();
            Usuario idUsuarioOld = persistentRegistroSolicitud.getIdUsuario();
            Usuario idUsuarioNew = registroSolicitud.getIdUsuario();
            if (idSolicitudNew != null) {
                idSolicitudNew = em.getReference(idSolicitudNew.getClass(), idSolicitudNew.getIdSolicitud());
                registroSolicitud.setIdSolicitud(idSolicitudNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                registroSolicitud.setIdUsuario(idUsuarioNew);
            }
            registroSolicitud = em.merge(registroSolicitud);
            if (idSolicitudOld != null && !idSolicitudOld.equals(idSolicitudNew)) {
                idSolicitudOld.getRegistroSolicitudList().remove(registroSolicitud);
                idSolicitudOld = em.merge(idSolicitudOld);
            }
            if (idSolicitudNew != null && !idSolicitudNew.equals(idSolicitudOld)) {
                idSolicitudNew.getRegistroSolicitudList().add(registroSolicitud);
                idSolicitudNew = em.merge(idSolicitudNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getRegistroSolicitudList().remove(registroSolicitud);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getRegistroSolicitudList().add(registroSolicitud);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registroSolicitud.getIdRegistro();
                if (findRegistroSolicitud(id) == null) {
                    throw new NonexistentEntityException("The registroSolicitud with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegistroSolicitud registroSolicitud;
            try {
                registroSolicitud = em.getReference(RegistroSolicitud.class, id);
                registroSolicitud.getIdRegistro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroSolicitud with id " + id + " no longer exists.", enfe);
            }
            Solicitud idSolicitud = registroSolicitud.getIdSolicitud();
            if (idSolicitud != null) {
                idSolicitud.getRegistroSolicitudList().remove(registroSolicitud);
                idSolicitud = em.merge(idSolicitud);
            }
            Usuario idUsuario = registroSolicitud.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getRegistroSolicitudList().remove(registroSolicitud);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(registroSolicitud);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroSolicitud> findRegistroSolicitudEntities() {
        return findRegistroSolicitudEntities(true, -1, -1);
    }

    public List<RegistroSolicitud> findRegistroSolicitudEntities(int maxResults, int firstResult) {
        return findRegistroSolicitudEntities(false, maxResults, firstResult);
    }

    private List<RegistroSolicitud> findRegistroSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroSolicitud.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RegistroSolicitud findRegistroSolicitud(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroSolicitud> rt = cq.from(RegistroSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.controlador;

import com.uteq.psu.controlador.exceptions.NonexistentEntityException;
import com.uteq.psu.controlador.exceptions.PreexistingEntityException;
import com.uteq.psu.controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.TipoSolicitud;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kevin Onofre
 */
public class TipoSolicitudJpaController implements Serializable {

    public TipoSolicitudJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoSolicitud tipoSolicitud) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tipoSolicitud.getSolicitudList() == null) {
            tipoSolicitud.setSolicitudList(new ArrayList<Solicitud>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Solicitud> attachedSolicitudList = new ArrayList<Solicitud>();
            for (Solicitud solicitudListSolicitudToAttach : tipoSolicitud.getSolicitudList()) {
                solicitudListSolicitudToAttach = em.getReference(solicitudListSolicitudToAttach.getClass(), solicitudListSolicitudToAttach.getIdSolicitud());
                attachedSolicitudList.add(solicitudListSolicitudToAttach);
            }
            tipoSolicitud.setSolicitudList(attachedSolicitudList);
            em.persist(tipoSolicitud);
            for (Solicitud solicitudListSolicitud : tipoSolicitud.getSolicitudList()) {
                TipoSolicitud oldIdTipoSolicitudOfSolicitudListSolicitud = solicitudListSolicitud.getIdTipoSolicitud();
                solicitudListSolicitud.setIdTipoSolicitud(tipoSolicitud);
                solicitudListSolicitud = em.merge(solicitudListSolicitud);
                if (oldIdTipoSolicitudOfSolicitudListSolicitud != null) {
                    oldIdTipoSolicitudOfSolicitudListSolicitud.getSolicitudList().remove(solicitudListSolicitud);
                    oldIdTipoSolicitudOfSolicitudListSolicitud = em.merge(oldIdTipoSolicitudOfSolicitudListSolicitud);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoSolicitud(tipoSolicitud.getIdTipoSolicitud()) != null) {
                throw new PreexistingEntityException("TipoSolicitud " + tipoSolicitud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoSolicitud tipoSolicitud) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoSolicitud persistentTipoSolicitud = em.find(TipoSolicitud.class, tipoSolicitud.getIdTipoSolicitud());
            List<Solicitud> solicitudListOld = persistentTipoSolicitud.getSolicitudList();
            List<Solicitud> solicitudListNew = tipoSolicitud.getSolicitudList();
            List<Solicitud> attachedSolicitudListNew = new ArrayList<Solicitud>();
            for (Solicitud solicitudListNewSolicitudToAttach : solicitudListNew) {
                solicitudListNewSolicitudToAttach = em.getReference(solicitudListNewSolicitudToAttach.getClass(), solicitudListNewSolicitudToAttach.getIdSolicitud());
                attachedSolicitudListNew.add(solicitudListNewSolicitudToAttach);
            }
            solicitudListNew = attachedSolicitudListNew;
            tipoSolicitud.setSolicitudList(solicitudListNew);
            tipoSolicitud = em.merge(tipoSolicitud);
            for (Solicitud solicitudListOldSolicitud : solicitudListOld) {
                if (!solicitudListNew.contains(solicitudListOldSolicitud)) {
                    solicitudListOldSolicitud.setIdTipoSolicitud(null);
                    solicitudListOldSolicitud = em.merge(solicitudListOldSolicitud);
                }
            }
            for (Solicitud solicitudListNewSolicitud : solicitudListNew) {
                if (!solicitudListOld.contains(solicitudListNewSolicitud)) {
                    TipoSolicitud oldIdTipoSolicitudOfSolicitudListNewSolicitud = solicitudListNewSolicitud.getIdTipoSolicitud();
                    solicitudListNewSolicitud.setIdTipoSolicitud(tipoSolicitud);
                    solicitudListNewSolicitud = em.merge(solicitudListNewSolicitud);
                    if (oldIdTipoSolicitudOfSolicitudListNewSolicitud != null && !oldIdTipoSolicitudOfSolicitudListNewSolicitud.equals(tipoSolicitud)) {
                        oldIdTipoSolicitudOfSolicitudListNewSolicitud.getSolicitudList().remove(solicitudListNewSolicitud);
                        oldIdTipoSolicitudOfSolicitudListNewSolicitud = em.merge(oldIdTipoSolicitudOfSolicitudListNewSolicitud);
                    }
                }
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
                Integer id = tipoSolicitud.getIdTipoSolicitud();
                if (findTipoSolicitud(id) == null) {
                    throw new NonexistentEntityException("The tipoSolicitud with id " + id + " no longer exists.");
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
            TipoSolicitud tipoSolicitud;
            try {
                tipoSolicitud = em.getReference(TipoSolicitud.class, id);
                tipoSolicitud.getIdTipoSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoSolicitud with id " + id + " no longer exists.", enfe);
            }
            List<Solicitud> solicitudList = tipoSolicitud.getSolicitudList();
            for (Solicitud solicitudListSolicitud : solicitudList) {
                solicitudListSolicitud.setIdTipoSolicitud(null);
                solicitudListSolicitud = em.merge(solicitudListSolicitud);
            }
            em.remove(tipoSolicitud);
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

    public List<TipoSolicitud> findTipoSolicitudEntities() {
        return findTipoSolicitudEntities(true, -1, -1);
    }

    public List<TipoSolicitud> findTipoSolicitudEntities(int maxResults, int firstResult) {
        return findTipoSolicitudEntities(false, maxResults, firstResult);
    }

    private List<TipoSolicitud> findTipoSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoSolicitud.class));
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

    public TipoSolicitud findTipoSolicitud(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoSolicitud> rt = cq.from(TipoSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

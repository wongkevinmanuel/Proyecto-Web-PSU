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
import com.uteq.psu.modelo.TipoSolicitud;
import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.modelo.Solicitud;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kevin Onofre
 */
public class SolicitudJpaController implements Serializable {

    public SolicitudJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitud solicitud) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (solicitud.getRegistroSolicitudList() == null) {
            solicitud.setRegistroSolicitudList(new ArrayList<RegistroSolicitud>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoSolicitud idTipoSolicitud = solicitud.getIdTipoSolicitud();
            if (idTipoSolicitud != null) {
                idTipoSolicitud = em.getReference(idTipoSolicitud.getClass(), idTipoSolicitud.getIdTipoSolicitud());
                solicitud.setIdTipoSolicitud(idTipoSolicitud);
            }
            List<RegistroSolicitud> attachedRegistroSolicitudList = new ArrayList<RegistroSolicitud>();
            for (RegistroSolicitud registroSolicitudListRegistroSolicitudToAttach : solicitud.getRegistroSolicitudList()) {
                registroSolicitudListRegistroSolicitudToAttach = em.getReference(registroSolicitudListRegistroSolicitudToAttach.getClass(), registroSolicitudListRegistroSolicitudToAttach.getIdRegistro());
                attachedRegistroSolicitudList.add(registroSolicitudListRegistroSolicitudToAttach);
            }
            solicitud.setRegistroSolicitudList(attachedRegistroSolicitudList);
            em.persist(solicitud);
            if (idTipoSolicitud != null) {
                idTipoSolicitud.getSolicitudList().add(solicitud);
                idTipoSolicitud = em.merge(idTipoSolicitud);
            }
            for (RegistroSolicitud registroSolicitudListRegistroSolicitud : solicitud.getRegistroSolicitudList()) {
                Solicitud oldIdSolicitudOfRegistroSolicitudListRegistroSolicitud = registroSolicitudListRegistroSolicitud.getIdSolicitud();
                registroSolicitudListRegistroSolicitud.setIdSolicitud(solicitud);
                registroSolicitudListRegistroSolicitud = em.merge(registroSolicitudListRegistroSolicitud);
                if (oldIdSolicitudOfRegistroSolicitudListRegistroSolicitud != null) {
                    oldIdSolicitudOfRegistroSolicitudListRegistroSolicitud.getRegistroSolicitudList().remove(registroSolicitudListRegistroSolicitud);
                    oldIdSolicitudOfRegistroSolicitudListRegistroSolicitud = em.merge(oldIdSolicitudOfRegistroSolicitudListRegistroSolicitud);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSolicitud(solicitud.getIdSolicitud()) != null) {
                throw new PreexistingEntityException("Solicitud " + solicitud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitud solicitud) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Solicitud persistentSolicitud = em.find(Solicitud.class, solicitud.getIdSolicitud());
            TipoSolicitud idTipoSolicitudOld = persistentSolicitud.getIdTipoSolicitud();
            TipoSolicitud idTipoSolicitudNew = solicitud.getIdTipoSolicitud();
            List<RegistroSolicitud> registroSolicitudListOld = persistentSolicitud.getRegistroSolicitudList();
            List<RegistroSolicitud> registroSolicitudListNew = solicitud.getRegistroSolicitudList();
            if (idTipoSolicitudNew != null) {
                idTipoSolicitudNew = em.getReference(idTipoSolicitudNew.getClass(), idTipoSolicitudNew.getIdTipoSolicitud());
                solicitud.setIdTipoSolicitud(idTipoSolicitudNew);
            }
            List<RegistroSolicitud> attachedRegistroSolicitudListNew = new ArrayList<RegistroSolicitud>();
            for (RegistroSolicitud registroSolicitudListNewRegistroSolicitudToAttach : registroSolicitudListNew) {
                registroSolicitudListNewRegistroSolicitudToAttach = em.getReference(registroSolicitudListNewRegistroSolicitudToAttach.getClass(), registroSolicitudListNewRegistroSolicitudToAttach.getIdRegistro());
                attachedRegistroSolicitudListNew.add(registroSolicitudListNewRegistroSolicitudToAttach);
            }
            registroSolicitudListNew = attachedRegistroSolicitudListNew;
            solicitud.setRegistroSolicitudList(registroSolicitudListNew);
            solicitud = em.merge(solicitud);
            if (idTipoSolicitudOld != null && !idTipoSolicitudOld.equals(idTipoSolicitudNew)) {
                idTipoSolicitudOld.getSolicitudList().remove(solicitud);
                idTipoSolicitudOld = em.merge(idTipoSolicitudOld);
            }
            if (idTipoSolicitudNew != null && !idTipoSolicitudNew.equals(idTipoSolicitudOld)) {
                idTipoSolicitudNew.getSolicitudList().add(solicitud);
                idTipoSolicitudNew = em.merge(idTipoSolicitudNew);
            }
            for (RegistroSolicitud registroSolicitudListOldRegistroSolicitud : registroSolicitudListOld) {
                if (!registroSolicitudListNew.contains(registroSolicitudListOldRegistroSolicitud)) {
                    registroSolicitudListOldRegistroSolicitud.setIdSolicitud(null);
                    registroSolicitudListOldRegistroSolicitud = em.merge(registroSolicitudListOldRegistroSolicitud);
                }
            }
            for (RegistroSolicitud registroSolicitudListNewRegistroSolicitud : registroSolicitudListNew) {
                if (!registroSolicitudListOld.contains(registroSolicitudListNewRegistroSolicitud)) {
                    Solicitud oldIdSolicitudOfRegistroSolicitudListNewRegistroSolicitud = registroSolicitudListNewRegistroSolicitud.getIdSolicitud();
                    registroSolicitudListNewRegistroSolicitud.setIdSolicitud(solicitud);
                    registroSolicitudListNewRegistroSolicitud = em.merge(registroSolicitudListNewRegistroSolicitud);
                    if (oldIdSolicitudOfRegistroSolicitudListNewRegistroSolicitud != null && !oldIdSolicitudOfRegistroSolicitudListNewRegistroSolicitud.equals(solicitud)) {
                        oldIdSolicitudOfRegistroSolicitudListNewRegistroSolicitud.getRegistroSolicitudList().remove(registroSolicitudListNewRegistroSolicitud);
                        oldIdSolicitudOfRegistroSolicitudListNewRegistroSolicitud = em.merge(oldIdSolicitudOfRegistroSolicitudListNewRegistroSolicitud);
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
                Integer id = solicitud.getIdSolicitud();
                if (findSolicitud(id) == null) {
                    throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.");
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
            Solicitud solicitud;
            try {
                solicitud = em.getReference(Solicitud.class, id);
                solicitud.getIdSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitud with id " + id + " no longer exists.", enfe);
            }
            TipoSolicitud idTipoSolicitud = solicitud.getIdTipoSolicitud();
            if (idTipoSolicitud != null) {
                idTipoSolicitud.getSolicitudList().remove(solicitud);
                idTipoSolicitud = em.merge(idTipoSolicitud);
            }
            List<RegistroSolicitud> registroSolicitudList = solicitud.getRegistroSolicitudList();
            for (RegistroSolicitud registroSolicitudListRegistroSolicitud : registroSolicitudList) {
                registroSolicitudListRegistroSolicitud.setIdSolicitud(null);
                registroSolicitudListRegistroSolicitud = em.merge(registroSolicitudListRegistroSolicitud);
            }
            em.remove(solicitud);
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

    public List<Solicitud> findSolicitudEntities() {
        return findSolicitudEntities(true, -1, -1);
    }

    public List<Solicitud> findSolicitudEntities(int maxResults, int firstResult) {
        return findSolicitudEntities(false, maxResults, firstResult);
    }

    private List<Solicitud> findSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitud.class));
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

    public Solicitud findSolicitud(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitud> rt = cq.from(Solicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

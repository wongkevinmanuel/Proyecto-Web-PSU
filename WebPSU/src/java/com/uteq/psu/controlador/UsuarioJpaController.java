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
import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kevin Onofre
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuario.getRegistroSolicitudList() == null) {
            usuario.setRegistroSolicitudList(new ArrayList<RegistroSolicitud>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<RegistroSolicitud> attachedRegistroSolicitudList = new ArrayList<RegistroSolicitud>();
            for (RegistroSolicitud registroSolicitudListRegistroSolicitudToAttach : usuario.getRegistroSolicitudList()) {
                registroSolicitudListRegistroSolicitudToAttach = em.getReference(registroSolicitudListRegistroSolicitudToAttach.getClass(), registroSolicitudListRegistroSolicitudToAttach.getIdRegistro());
                attachedRegistroSolicitudList.add(registroSolicitudListRegistroSolicitudToAttach);
            }
            usuario.setRegistroSolicitudList(attachedRegistroSolicitudList);
            em.persist(usuario);
            for (RegistroSolicitud registroSolicitudListRegistroSolicitud : usuario.getRegistroSolicitudList()) {
                Usuario oldIdUsuarioOfRegistroSolicitudListRegistroSolicitud = registroSolicitudListRegistroSolicitud.getIdUsuario();
                registroSolicitudListRegistroSolicitud.setIdUsuario(usuario);
                registroSolicitudListRegistroSolicitud = em.merge(registroSolicitudListRegistroSolicitud);
                if (oldIdUsuarioOfRegistroSolicitudListRegistroSolicitud != null) {
                    oldIdUsuarioOfRegistroSolicitudListRegistroSolicitud.getRegistroSolicitudList().remove(registroSolicitudListRegistroSolicitud);
                    oldIdUsuarioOfRegistroSolicitudListRegistroSolicitud = em.merge(oldIdUsuarioOfRegistroSolicitudListRegistroSolicitud);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            List<RegistroSolicitud> registroSolicitudListOld = persistentUsuario.getRegistroSolicitudList();
            List<RegistroSolicitud> registroSolicitudListNew = usuario.getRegistroSolicitudList();
            List<RegistroSolicitud> attachedRegistroSolicitudListNew = new ArrayList<RegistroSolicitud>();
            for (RegistroSolicitud registroSolicitudListNewRegistroSolicitudToAttach : registroSolicitudListNew) {
                registroSolicitudListNewRegistroSolicitudToAttach = em.getReference(registroSolicitudListNewRegistroSolicitudToAttach.getClass(), registroSolicitudListNewRegistroSolicitudToAttach.getIdRegistro());
                attachedRegistroSolicitudListNew.add(registroSolicitudListNewRegistroSolicitudToAttach);
            }
            registroSolicitudListNew = attachedRegistroSolicitudListNew;
            usuario.setRegistroSolicitudList(registroSolicitudListNew);
            usuario = em.merge(usuario);
            for (RegistroSolicitud registroSolicitudListOldRegistroSolicitud : registroSolicitudListOld) {
                if (!registroSolicitudListNew.contains(registroSolicitudListOldRegistroSolicitud)) {
                    registroSolicitudListOldRegistroSolicitud.setIdUsuario(null);
                    registroSolicitudListOldRegistroSolicitud = em.merge(registroSolicitudListOldRegistroSolicitud);
                }
            }
            for (RegistroSolicitud registroSolicitudListNewRegistroSolicitud : registroSolicitudListNew) {
                if (!registroSolicitudListOld.contains(registroSolicitudListNewRegistroSolicitud)) {
                    Usuario oldIdUsuarioOfRegistroSolicitudListNewRegistroSolicitud = registroSolicitudListNewRegistroSolicitud.getIdUsuario();
                    registroSolicitudListNewRegistroSolicitud.setIdUsuario(usuario);
                    registroSolicitudListNewRegistroSolicitud = em.merge(registroSolicitudListNewRegistroSolicitud);
                    if (oldIdUsuarioOfRegistroSolicitudListNewRegistroSolicitud != null && !oldIdUsuarioOfRegistroSolicitudListNewRegistroSolicitud.equals(usuario)) {
                        oldIdUsuarioOfRegistroSolicitudListNewRegistroSolicitud.getRegistroSolicitudList().remove(registroSolicitudListNewRegistroSolicitud);
                        oldIdUsuarioOfRegistroSolicitudListNewRegistroSolicitud = em.merge(oldIdUsuarioOfRegistroSolicitudListNewRegistroSolicitud);
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
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<RegistroSolicitud> registroSolicitudList = usuario.getRegistroSolicitudList();
            for (RegistroSolicitud registroSolicitudListRegistroSolicitud : registroSolicitudList) {
                registroSolicitudListRegistroSolicitud.setIdUsuario(null);
                registroSolicitudListRegistroSolicitud = em.merge(registroSolicitudListRegistroSolicitud);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

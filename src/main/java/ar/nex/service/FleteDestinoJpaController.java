/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.nex.service;

import ar.nex.entity.FleteDestino;
import ar.nex.service.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Renzo
 */
public class FleteDestinoJpaController implements Serializable {

    public FleteDestinoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FleteDestino fleteDestino) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(fleteDestino);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FleteDestino fleteDestino) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            fleteDestino = em.merge(fleteDestino);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = fleteDestino.getIdDestino();
                if (findFleteDestino(id) == null) {
                    throw new NonexistentEntityException("The fleteDestino with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FleteDestino fleteDestino;
            try {
                fleteDestino = em.getReference(FleteDestino.class, id);
                fleteDestino.getIdDestino();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fleteDestino with id " + id + " no longer exists.", enfe);
            }
            em.remove(fleteDestino);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FleteDestino> findFleteDestinoEntities() {
        return findFleteDestinoEntities(true, -1, -1);
    }

    public List<FleteDestino> findFleteDestinoEntities(int maxResults, int firstResult) {
        return findFleteDestinoEntities(false, maxResults, firstResult);
    }

    private List<FleteDestino> findFleteDestinoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FleteDestino.class));
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

    public FleteDestino findFleteDestino(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FleteDestino.class, id);
        } finally {
            em.close();
        }
    }

    public int getFleteDestinoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FleteDestino> rt = cq.from(FleteDestino.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
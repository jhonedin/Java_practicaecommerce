/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Califica;
import modelo.Usuario;
import modelo.Producto;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Jhon
 */
public class CalificaJpaController implements Serializable {

    public CalificaJpaController(){
        this.emf = Persistence.createEntityManagerFactory("tienda_ecommercePU");
    }  
        
    public CalificaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Califica califica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioID = califica.getUsuarioID();
            if (usuarioID != null) {
                usuarioID = em.getReference(usuarioID.getClass(), usuarioID.getUsuarioID());
                califica.setUsuarioID(usuarioID);
            }
            Producto productoID = califica.getProductoID();
            if (productoID != null) {
                productoID = em.getReference(productoID.getClass(), productoID.getProductoID());
                califica.setProductoID(productoID);
            }
            em.persist(califica);
            if (usuarioID != null) {
                usuarioID.getCalificaList().add(califica);
                usuarioID = em.merge(usuarioID);
            }
            if (productoID != null) {
                productoID.getCalificaList().add(califica);
                productoID = em.merge(productoID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Califica califica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Califica persistentCalifica = em.find(Califica.class, califica.getCalificaID());
            Usuario usuarioIDOld = persistentCalifica.getUsuarioID();
            Usuario usuarioIDNew = califica.getUsuarioID();
            Producto productoIDOld = persistentCalifica.getProductoID();
            Producto productoIDNew = califica.getProductoID();
            if (usuarioIDNew != null) {
                usuarioIDNew = em.getReference(usuarioIDNew.getClass(), usuarioIDNew.getUsuarioID());
                califica.setUsuarioID(usuarioIDNew);
            }
            if (productoIDNew != null) {
                productoIDNew = em.getReference(productoIDNew.getClass(), productoIDNew.getProductoID());
                califica.setProductoID(productoIDNew);
            }
            califica = em.merge(califica);
            if (usuarioIDOld != null && !usuarioIDOld.equals(usuarioIDNew)) {
                usuarioIDOld.getCalificaList().remove(califica);
                usuarioIDOld = em.merge(usuarioIDOld);
            }
            if (usuarioIDNew != null && !usuarioIDNew.equals(usuarioIDOld)) {
                usuarioIDNew.getCalificaList().add(califica);
                usuarioIDNew = em.merge(usuarioIDNew);
            }
            if (productoIDOld != null && !productoIDOld.equals(productoIDNew)) {
                productoIDOld.getCalificaList().remove(califica);
                productoIDOld = em.merge(productoIDOld);
            }
            if (productoIDNew != null && !productoIDNew.equals(productoIDOld)) {
                productoIDNew.getCalificaList().add(califica);
                productoIDNew = em.merge(productoIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = califica.getCalificaID();
                if (findCalifica(id) == null) {
                    throw new NonexistentEntityException("The califica with id " + id + " no longer exists.");
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
            Califica califica;
            try {
                califica = em.getReference(Califica.class, id);
                califica.getCalificaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The califica with id " + id + " no longer exists.", enfe);
            }
            Usuario usuarioID = califica.getUsuarioID();
            if (usuarioID != null) {
                usuarioID.getCalificaList().remove(califica);
                usuarioID = em.merge(usuarioID);
            }
            Producto productoID = califica.getProductoID();
            if (productoID != null) {
                productoID.getCalificaList().remove(califica);
                productoID = em.merge(productoID);
            }
            em.remove(califica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Califica> findCalificaEntities() {
        return findCalificaEntities(true, -1, -1);
    }

    public List<Califica> findCalificaEntities(int maxResults, int firstResult) {
        return findCalificaEntities(false, maxResults, firstResult);
    }

    private List<Califica> findCalificaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Califica.class));
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

    public Califica findCalifica(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Califica.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Califica> rt = cq.from(Califica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

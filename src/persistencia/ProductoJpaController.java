/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Compra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Consulta;
import modelo.Califica;
import modelo.Producto;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Jhon
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(){
        this.emf = Persistence.createEntityManagerFactory("tienda_ecommercePU");
    }  
    
    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getCompraList() == null) {
            producto.setCompraList(new ArrayList<Compra>());
        }
        if (producto.getConsultaList() == null) {
            producto.setConsultaList(new ArrayList<Consulta>());
        }
        if (producto.getCalificaList() == null) {
            producto.setCalificaList(new ArrayList<Califica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : producto.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getCompraID());
                attachedCompraList.add(compraListCompraToAttach);
            }
            producto.setCompraList(attachedCompraList);
            List<Consulta> attachedConsultaList = new ArrayList<Consulta>();
            for (Consulta consultaListConsultaToAttach : producto.getConsultaList()) {
                consultaListConsultaToAttach = em.getReference(consultaListConsultaToAttach.getClass(), consultaListConsultaToAttach.getConsultaID());
                attachedConsultaList.add(consultaListConsultaToAttach);
            }
            producto.setConsultaList(attachedConsultaList);
            List<Califica> attachedCalificaList = new ArrayList<Califica>();
            for (Califica calificaListCalificaToAttach : producto.getCalificaList()) {
                calificaListCalificaToAttach = em.getReference(calificaListCalificaToAttach.getClass(), calificaListCalificaToAttach.getCalificaID());
                attachedCalificaList.add(calificaListCalificaToAttach);
            }
            producto.setCalificaList(attachedCalificaList);
            em.persist(producto);
            for (Compra compraListCompra : producto.getCompraList()) {
                Producto oldProductoIDOfCompraListCompra = compraListCompra.getProductoID();
                compraListCompra.setProductoID(producto);
                compraListCompra = em.merge(compraListCompra);
                if (oldProductoIDOfCompraListCompra != null) {
                    oldProductoIDOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldProductoIDOfCompraListCompra = em.merge(oldProductoIDOfCompraListCompra);
                }
            }
            for (Consulta consultaListConsulta : producto.getConsultaList()) {
                Producto oldProductoIDOfConsultaListConsulta = consultaListConsulta.getProductoID();
                consultaListConsulta.setProductoID(producto);
                consultaListConsulta = em.merge(consultaListConsulta);
                if (oldProductoIDOfConsultaListConsulta != null) {
                    oldProductoIDOfConsultaListConsulta.getConsultaList().remove(consultaListConsulta);
                    oldProductoIDOfConsultaListConsulta = em.merge(oldProductoIDOfConsultaListConsulta);
                }
            }
            for (Califica calificaListCalifica : producto.getCalificaList()) {
                Producto oldProductoIDOfCalificaListCalifica = calificaListCalifica.getProductoID();
                calificaListCalifica.setProductoID(producto);
                calificaListCalifica = em.merge(calificaListCalifica);
                if (oldProductoIDOfCalificaListCalifica != null) {
                    oldProductoIDOfCalificaListCalifica.getCalificaList().remove(calificaListCalifica);
                    oldProductoIDOfCalificaListCalifica = em.merge(oldProductoIDOfCalificaListCalifica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getProductoID()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getProductoID());
            List<Compra> compraListOld = persistentProducto.getCompraList();
            List<Compra> compraListNew = producto.getCompraList();
            List<Consulta> consultaListOld = persistentProducto.getConsultaList();
            List<Consulta> consultaListNew = producto.getConsultaList();
            List<Califica> calificaListOld = persistentProducto.getCalificaList();
            List<Califica> calificaListNew = producto.getCalificaList();
            List<String> illegalOrphanMessages = null;
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its productoID field is not nullable.");
                }
            }
            for (Consulta consultaListOldConsulta : consultaListOld) {
                if (!consultaListNew.contains(consultaListOldConsulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consulta " + consultaListOldConsulta + " since its productoID field is not nullable.");
                }
            }
            for (Califica calificaListOldCalifica : calificaListOld) {
                if (!calificaListNew.contains(calificaListOldCalifica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Califica " + calificaListOldCalifica + " since its productoID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getCompraID());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            producto.setCompraList(compraListNew);
            List<Consulta> attachedConsultaListNew = new ArrayList<Consulta>();
            for (Consulta consultaListNewConsultaToAttach : consultaListNew) {
                consultaListNewConsultaToAttach = em.getReference(consultaListNewConsultaToAttach.getClass(), consultaListNewConsultaToAttach.getConsultaID());
                attachedConsultaListNew.add(consultaListNewConsultaToAttach);
            }
            consultaListNew = attachedConsultaListNew;
            producto.setConsultaList(consultaListNew);
            List<Califica> attachedCalificaListNew = new ArrayList<Califica>();
            for (Califica calificaListNewCalificaToAttach : calificaListNew) {
                calificaListNewCalificaToAttach = em.getReference(calificaListNewCalificaToAttach.getClass(), calificaListNewCalificaToAttach.getCalificaID());
                attachedCalificaListNew.add(calificaListNewCalificaToAttach);
            }
            calificaListNew = attachedCalificaListNew;
            producto.setCalificaList(calificaListNew);
            producto = em.merge(producto);
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Producto oldProductoIDOfCompraListNewCompra = compraListNewCompra.getProductoID();
                    compraListNewCompra.setProductoID(producto);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldProductoIDOfCompraListNewCompra != null && !oldProductoIDOfCompraListNewCompra.equals(producto)) {
                        oldProductoIDOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldProductoIDOfCompraListNewCompra = em.merge(oldProductoIDOfCompraListNewCompra);
                    }
                }
            }
            for (Consulta consultaListNewConsulta : consultaListNew) {
                if (!consultaListOld.contains(consultaListNewConsulta)) {
                    Producto oldProductoIDOfConsultaListNewConsulta = consultaListNewConsulta.getProductoID();
                    consultaListNewConsulta.setProductoID(producto);
                    consultaListNewConsulta = em.merge(consultaListNewConsulta);
                    if (oldProductoIDOfConsultaListNewConsulta != null && !oldProductoIDOfConsultaListNewConsulta.equals(producto)) {
                        oldProductoIDOfConsultaListNewConsulta.getConsultaList().remove(consultaListNewConsulta);
                        oldProductoIDOfConsultaListNewConsulta = em.merge(oldProductoIDOfConsultaListNewConsulta);
                    }
                }
            }
            for (Califica calificaListNewCalifica : calificaListNew) {
                if (!calificaListOld.contains(calificaListNewCalifica)) {
                    Producto oldProductoIDOfCalificaListNewCalifica = calificaListNewCalifica.getProductoID();
                    calificaListNewCalifica.setProductoID(producto);
                    calificaListNewCalifica = em.merge(calificaListNewCalifica);
                    if (oldProductoIDOfCalificaListNewCalifica != null && !oldProductoIDOfCalificaListNewCalifica.equals(producto)) {
                        oldProductoIDOfCalificaListNewCalifica.getCalificaList().remove(calificaListNewCalifica);
                        oldProductoIDOfCalificaListNewCalifica = em.merge(oldProductoIDOfCalificaListNewCalifica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getProductoID();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getProductoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraListOrphanCheck = producto.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable productoID field.");
            }
            List<Consulta> consultaListOrphanCheck = producto.getConsultaList();
            for (Consulta consultaListOrphanCheckConsulta : consultaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Consulta " + consultaListOrphanCheckConsulta + " in its consultaList field has a non-nullable productoID field.");
            }
            List<Califica> calificaListOrphanCheck = producto.getCalificaList();
            for (Califica calificaListOrphanCheckCalifica : calificaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Califica " + calificaListOrphanCheckCalifica + " in its calificaList field has a non-nullable productoID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

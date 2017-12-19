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
import modelo.Usuario;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Jhon
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(){
        this.emf = Persistence.createEntityManagerFactory("tienda_ecommercePU");
    }  
        
    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getCompraList() == null) {
            usuario.setCompraList(new ArrayList<Compra>());
        }
        if (usuario.getConsultaList() == null) {
            usuario.setConsultaList(new ArrayList<Consulta>());
        }
        if (usuario.getCalificaList() == null) {
            usuario.setCalificaList(new ArrayList<Califica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : usuario.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getCompraID());
                attachedCompraList.add(compraListCompraToAttach);
            }
            usuario.setCompraList(attachedCompraList);
            List<Consulta> attachedConsultaList = new ArrayList<Consulta>();
            for (Consulta consultaListConsultaToAttach : usuario.getConsultaList()) {
                consultaListConsultaToAttach = em.getReference(consultaListConsultaToAttach.getClass(), consultaListConsultaToAttach.getConsultaID());
                attachedConsultaList.add(consultaListConsultaToAttach);
            }
            usuario.setConsultaList(attachedConsultaList);
            List<Califica> attachedCalificaList = new ArrayList<Califica>();
            for (Califica calificaListCalificaToAttach : usuario.getCalificaList()) {
                calificaListCalificaToAttach = em.getReference(calificaListCalificaToAttach.getClass(), calificaListCalificaToAttach.getCalificaID());
                attachedCalificaList.add(calificaListCalificaToAttach);
            }
            usuario.setCalificaList(attachedCalificaList);
            em.persist(usuario);
            for (Compra compraListCompra : usuario.getCompraList()) {
                Usuario oldUsuarioIDOfCompraListCompra = compraListCompra.getUsuarioID();
                compraListCompra.setUsuarioID(usuario);
                compraListCompra = em.merge(compraListCompra);
                if (oldUsuarioIDOfCompraListCompra != null) {
                    oldUsuarioIDOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldUsuarioIDOfCompraListCompra = em.merge(oldUsuarioIDOfCompraListCompra);
                }
            }
            for (Consulta consultaListConsulta : usuario.getConsultaList()) {
                Usuario oldUsuarioIDOfConsultaListConsulta = consultaListConsulta.getUsuarioID();
                consultaListConsulta.setUsuarioID(usuario);
                consultaListConsulta = em.merge(consultaListConsulta);
                if (oldUsuarioIDOfConsultaListConsulta != null) {
                    oldUsuarioIDOfConsultaListConsulta.getConsultaList().remove(consultaListConsulta);
                    oldUsuarioIDOfConsultaListConsulta = em.merge(oldUsuarioIDOfConsultaListConsulta);
                }
            }
            for (Califica calificaListCalifica : usuario.getCalificaList()) {
                Usuario oldUsuarioIDOfCalificaListCalifica = calificaListCalifica.getUsuarioID();
                calificaListCalifica.setUsuarioID(usuario);
                calificaListCalifica = em.merge(calificaListCalifica);
                if (oldUsuarioIDOfCalificaListCalifica != null) {
                    oldUsuarioIDOfCalificaListCalifica.getCalificaList().remove(calificaListCalifica);
                    oldUsuarioIDOfCalificaListCalifica = em.merge(oldUsuarioIDOfCalificaListCalifica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuarioID()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuarioID());
            List<Compra> compraListOld = persistentUsuario.getCompraList();
            List<Compra> compraListNew = usuario.getCompraList();
            List<Consulta> consultaListOld = persistentUsuario.getConsultaList();
            List<Consulta> consultaListNew = usuario.getConsultaList();
            List<Califica> calificaListOld = persistentUsuario.getCalificaList();
            List<Califica> calificaListNew = usuario.getCalificaList();
            List<String> illegalOrphanMessages = null;
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its usuarioID field is not nullable.");
                }
            }
            for (Consulta consultaListOldConsulta : consultaListOld) {
                if (!consultaListNew.contains(consultaListOldConsulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consulta " + consultaListOldConsulta + " since its usuarioID field is not nullable.");
                }
            }
            for (Califica calificaListOldCalifica : calificaListOld) {
                if (!calificaListNew.contains(calificaListOldCalifica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Califica " + calificaListOldCalifica + " since its usuarioID field is not nullable.");
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
            usuario.setCompraList(compraListNew);
            List<Consulta> attachedConsultaListNew = new ArrayList<Consulta>();
            for (Consulta consultaListNewConsultaToAttach : consultaListNew) {
                consultaListNewConsultaToAttach = em.getReference(consultaListNewConsultaToAttach.getClass(), consultaListNewConsultaToAttach.getConsultaID());
                attachedConsultaListNew.add(consultaListNewConsultaToAttach);
            }
            consultaListNew = attachedConsultaListNew;
            usuario.setConsultaList(consultaListNew);
            List<Califica> attachedCalificaListNew = new ArrayList<Califica>();
            for (Califica calificaListNewCalificaToAttach : calificaListNew) {
                calificaListNewCalificaToAttach = em.getReference(calificaListNewCalificaToAttach.getClass(), calificaListNewCalificaToAttach.getCalificaID());
                attachedCalificaListNew.add(calificaListNewCalificaToAttach);
            }
            calificaListNew = attachedCalificaListNew;
            usuario.setCalificaList(calificaListNew);
            usuario = em.merge(usuario);
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Usuario oldUsuarioIDOfCompraListNewCompra = compraListNewCompra.getUsuarioID();
                    compraListNewCompra.setUsuarioID(usuario);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldUsuarioIDOfCompraListNewCompra != null && !oldUsuarioIDOfCompraListNewCompra.equals(usuario)) {
                        oldUsuarioIDOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldUsuarioIDOfCompraListNewCompra = em.merge(oldUsuarioIDOfCompraListNewCompra);
                    }
                }
            }
            for (Consulta consultaListNewConsulta : consultaListNew) {
                if (!consultaListOld.contains(consultaListNewConsulta)) {
                    Usuario oldUsuarioIDOfConsultaListNewConsulta = consultaListNewConsulta.getUsuarioID();
                    consultaListNewConsulta.setUsuarioID(usuario);
                    consultaListNewConsulta = em.merge(consultaListNewConsulta);
                    if (oldUsuarioIDOfConsultaListNewConsulta != null && !oldUsuarioIDOfConsultaListNewConsulta.equals(usuario)) {
                        oldUsuarioIDOfConsultaListNewConsulta.getConsultaList().remove(consultaListNewConsulta);
                        oldUsuarioIDOfConsultaListNewConsulta = em.merge(oldUsuarioIDOfConsultaListNewConsulta);
                    }
                }
            }
            for (Califica calificaListNewCalifica : calificaListNew) {
                if (!calificaListOld.contains(calificaListNewCalifica)) {
                    Usuario oldUsuarioIDOfCalificaListNewCalifica = calificaListNewCalifica.getUsuarioID();
                    calificaListNewCalifica.setUsuarioID(usuario);
                    calificaListNewCalifica = em.merge(calificaListNewCalifica);
                    if (oldUsuarioIDOfCalificaListNewCalifica != null && !oldUsuarioIDOfCalificaListNewCalifica.equals(usuario)) {
                        oldUsuarioIDOfCalificaListNewCalifica.getCalificaList().remove(calificaListNewCalifica);
                        oldUsuarioIDOfCalificaListNewCalifica = em.merge(oldUsuarioIDOfCalificaListNewCalifica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsuarioID();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuarioID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraListOrphanCheck = usuario.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable usuarioID field.");
            }
            List<Consulta> consultaListOrphanCheck = usuario.getConsultaList();
            for (Consulta consultaListOrphanCheckConsulta : consultaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Consulta " + consultaListOrphanCheckConsulta + " in its consultaList field has a non-nullable usuarioID field.");
            }
            List<Califica> calificaListOrphanCheck = usuario.getCalificaList();
            for (Califica calificaListOrphanCheckCalifica : calificaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Califica " + calificaListOrphanCheckCalifica + " in its calificaList field has a non-nullable usuarioID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
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

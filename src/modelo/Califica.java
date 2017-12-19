/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jhon
 */
@Entity
@Table(name = "califica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Califica.findAll", query = "SELECT c FROM Califica c")
    , @NamedQuery(name = "Califica.findByCalificaID", query = "SELECT c FROM Califica c WHERE c.calificaID = :calificaID")
    , @NamedQuery(name = "Califica.findByFechaConsultaCalifica", query = "SELECT c FROM Califica c WHERE c.fechaConsultaCalifica = :fechaConsultaCalifica")
    , @NamedQuery(name = "Califica.findByCalificacion", query = "SELECT c FROM Califica c WHERE c.calificacion = :calificacion")})
public class Califica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calificaID")
    private Long calificaID;
    @Basic(optional = false)
    @Column(name = "fechaConsultaCalifica")
    @Temporal(TemporalType.DATE)
    private Date fechaConsultaCalifica;
    @Basic(optional = false)
    @Column(name = "calificacion")
    private int calificacion;
    @JoinColumn(name = "usuarioID", referencedColumnName = "usuarioID")
    @ManyToOne(optional = false)
    private Usuario usuarioID;
    @JoinColumn(name = "productoID", referencedColumnName = "productoID")
    @ManyToOne(optional = false)
    private Producto productoID;

    public Califica() {
    }

    public Califica(Long calificaID) {
        this.calificaID = calificaID;
    }

    public Califica(Long calificaID, Date fechaConsultaCalifica, int calificacion) {
        this.calificaID = calificaID;
        this.fechaConsultaCalifica = fechaConsultaCalifica;
        this.calificacion = calificacion;
    }

    public Long getCalificaID() {
        return calificaID;
    }

    public void setCalificaID(Long calificaID) {
        this.calificaID = calificaID;
    }

    public Date getFechaConsultaCalifica() {
        return fechaConsultaCalifica;
    }

    public void setFechaConsultaCalifica(Date fechaConsultaCalifica) {
        this.fechaConsultaCalifica = fechaConsultaCalifica;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Usuario getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Usuario usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Producto getProductoID() {
        return productoID;
    }

    public void setProductoID(Producto productoID) {
        this.productoID = productoID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calificaID != null ? calificaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Califica)) {
            return false;
        }
        Califica other = (Califica) object;
        if ((this.calificaID == null && other.calificaID != null) || (this.calificaID != null && !this.calificaID.equals(other.calificaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Califica[ calificaID=" + calificaID + " ]";
    }
    
}

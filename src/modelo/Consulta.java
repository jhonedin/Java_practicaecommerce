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
@Table(name = "consulta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consulta.findAll", query = "SELECT c FROM Consulta c")
    , @NamedQuery(name = "Consulta.findByConsultaID", query = "SELECT c FROM Consulta c WHERE c.consultaID = :consultaID")
    , @NamedQuery(name = "Consulta.findByFechaConsulta", query = "SELECT c FROM Consulta c WHERE c.fechaConsulta = :fechaConsulta")})
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "consultaID")
    private Long consultaID;
    @Basic(optional = false)
    @Column(name = "fechaConsulta")
    @Temporal(TemporalType.DATE)
    private Date fechaConsulta;
    @JoinColumn(name = "usuarioID", referencedColumnName = "usuarioID")
    @ManyToOne(optional = false)
    private Usuario usuarioID;
    @JoinColumn(name = "productoID", referencedColumnName = "productoID")
    @ManyToOne(optional = false)
    private Producto productoID;

    public Consulta() {
    }

    public Consulta(Long consultaID) {
        this.consultaID = consultaID;
    }

    public Consulta(Long consultaID, Date fechaConsulta) {
        this.consultaID = consultaID;
        this.fechaConsulta = fechaConsulta;
    }

    public Long getConsultaID() {
        return consultaID;
    }

    public void setConsultaID(Long consultaID) {
        this.consultaID = consultaID;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
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
        hash += (consultaID != null ? consultaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.consultaID == null && other.consultaID != null) || (this.consultaID != null && !this.consultaID.equals(other.consultaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Consulta[ consultaID=" + consultaID + " ]";
    }
    
}

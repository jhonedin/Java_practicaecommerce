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
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
    , @NamedQuery(name = "Compra.findByCompraID", query = "SELECT c FROM Compra c WHERE c.compraID = :compraID")
    , @NamedQuery(name = "Compra.findByCantidadCompra", query = "SELECT c FROM Compra c WHERE c.cantidadCompra = :cantidadCompra")
    , @NamedQuery(name = "Compra.findByValorCompra", query = "SELECT c FROM Compra c WHERE c.valorCompra = :valorCompra")
    , @NamedQuery(name = "Compra.findByFechaCompra", query = "SELECT c FROM Compra c WHERE c.fechaCompra = :fechaCompra")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "compraID")
    private Long compraID;
    @Basic(optional = false)
    @Column(name = "cantidadCompra")
    private int cantidadCompra;
    @Basic(optional = false)
    @Column(name = "valorCompra")
    private int valorCompra;
    @Basic(optional = false)
    @Column(name = "fechaCompra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @JoinColumn(name = "usuarioID", referencedColumnName = "usuarioID")
    @ManyToOne(optional = false)
    private Usuario usuarioID;
    @JoinColumn(name = "productoID", referencedColumnName = "productoID")
    @ManyToOne(optional = false)
    private Producto productoID;

    public Compra() {
    }

    public Compra(Long compraID) {
        this.compraID = compraID;
    }

    public Compra(Long compraID, int cantidadCompra, int valorCompra, Date fechaCompra) {
        this.compraID = compraID;
        this.cantidadCompra = cantidadCompra;
        this.valorCompra = valorCompra;
        this.fechaCompra = fechaCompra;
    }

    public Long getCompraID() {
        return compraID;
    }

    public void setCompraID(Long compraID) {
        this.compraID = compraID;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public int getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(int valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
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
        hash += (compraID != null ? compraID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.compraID == null && other.compraID != null) || (this.compraID != null && !this.compraID.equals(other.compraID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Compra[ compraID=" + compraID + " ]";
    }
    
}

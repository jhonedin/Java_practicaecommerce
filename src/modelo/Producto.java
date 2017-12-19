/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jhon
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByProductoID", query = "SELECT p FROM Producto p WHERE p.productoID = :productoID")
    , @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto")
    , @NamedQuery(name = "Producto.findByPrecioProducto", query = "SELECT p FROM Producto p WHERE p.precioProducto = :precioProducto")
    , @NamedQuery(name = "Producto.findByDescripcionProducto", query = "SELECT p FROM Producto p WHERE p.descripcionProducto = :descripcionProducto")
    , @NamedQuery(name = "Producto.findByCategoriaProducto", query = "SELECT p FROM Producto p WHERE p.categoriaProducto = :categoriaProducto")
    , @NamedQuery(name = "Producto.findBySubcategoriaProducto", query = "SELECT p FROM Producto p WHERE p.subcategoriaProducto = :subcategoriaProducto")
    , @NamedQuery(name = "Producto.findByCantidadDisponible", query = "SELECT p FROM Producto p WHERE p.cantidadDisponible = :cantidadDisponible")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "productoID")
    private Integer productoID;
    @Basic(optional = false)
    @Column(name = "nombreProducto")
    private String nombreProducto;
    @Basic(optional = false)
    @Column(name = "precioProducto")
    private int precioProducto;
    @Basic(optional = false)
    @Column(name = "descripcionProducto")
    private String descripcionProducto;
    @Basic(optional = false)
    @Column(name = "categoriaProducto")
    private String categoriaProducto;
    @Basic(optional = false)
    @Column(name = "subcategoriaProducto")
    private String subcategoriaProducto;
    @Basic(optional = false)
    @Column(name = "cantidadDisponible")
    private int cantidadDisponible;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoID")
    private List<Compra> compraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoID")
    private List<Consulta> consultaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoID")
    private List<Califica> calificaList;

    public Producto() {
    }

    public Producto(Integer productoID) {
        this.productoID = productoID;
    }

    public Producto(Integer productoID, String nombreProducto, int precioProducto, String descripcionProducto, String categoriaProducto, String subcategoriaProducto, int cantidadDisponible) {
        this.productoID = productoID;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.descripcionProducto = descripcionProducto;
        this.categoriaProducto = categoriaProducto;
        this.subcategoriaProducto = subcategoriaProducto;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Integer getProductoID() {
        return productoID;
    }

    public void setProductoID(Integer productoID) {
        this.productoID = productoID;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public String getSubcategoriaProducto() {
        return subcategoriaProducto;
    }

    public void setSubcategoriaProducto(String subcategoriaProducto) {
        this.subcategoriaProducto = subcategoriaProducto;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @XmlTransient
    public List<Consulta> getConsultaList() {
        return consultaList;
    }

    public void setConsultaList(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    @XmlTransient
    public List<Califica> getCalificaList() {
        return calificaList;
    }

    public void setCalificaList(List<Califica> calificaList) {
        this.calificaList = calificaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoID != null ? productoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.productoID == null && other.productoID != null) || (this.productoID != null && !this.productoID.equals(other.productoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Producto[ productoID=" + productoID + " ]";
    }
    
}

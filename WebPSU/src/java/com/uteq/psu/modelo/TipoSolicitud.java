/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin Onofre
 */
@Entity
@Table(name = "tipo_solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoSolicitud.findAll", query = "SELECT t FROM TipoSolicitud t")
    , @NamedQuery(name = "TipoSolicitud.findByIdTipoSolicitud", query = "SELECT t FROM TipoSolicitud t WHERE t.idTipoSolicitud = :idTipoSolicitud")
    , @NamedQuery(name = "TipoSolicitud.findByNombreTiSo", query = "SELECT t FROM TipoSolicitud t WHERE t.nombreTiSo = :nombreTiSo")
    , @NamedQuery(name = "TipoSolicitud.findByDescripcionTiSo", query = "SELECT t FROM TipoSolicitud t WHERE t.descripcionTiSo = :descripcionTiSo")})
public class TipoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_solicitud")
    private Integer idTipoSolicitud;
    @Size(max = 50)
    @Column(name = "nombre_ti_so")
    private String nombreTiSo;
    @Size(max = 50)
    @Column(name = "descripcion_ti_so")
    private String descripcionTiSo;
    @OneToMany(mappedBy = "idTipoSolicitud")
    private List<Solicitud> solicitudList = new ArrayList<>();;

    public TipoSolicitud() {
        
    }

    public TipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public Integer getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getNombreTiSo() {
        return nombreTiSo;
    }

    public void setNombreTiSo(String nombreTiSo) {
        this.nombreTiSo = nombreTiSo;
    }

    public String getDescripcionTiSo() {
        return descripcionTiSo;
    }

    public void setDescripcionTiSo(String descripcionTiSo) {
        this.descripcionTiSo = descripcionTiSo;
    }

    @XmlTransient
    public List<Solicitud> getSolicitudList() {
        return solicitudList;
    }

    public void setSolicitudList(List<Solicitud> solicitudList) {
        this.solicitudList = solicitudList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSolicitud != null ? idTipoSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSolicitud)) {
            return false;
        }
        TipoSolicitud other = (TipoSolicitud) object;
        if ((this.idTipoSolicitud == null && other.idTipoSolicitud != null) || (this.idTipoSolicitud != null && !this.idTipoSolicitud.equals(other.idTipoSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uteq.psu.modelo.TipoSolicitud[ idTipoSolicitud=" + idTipoSolicitud + " ]";
    }
    
}

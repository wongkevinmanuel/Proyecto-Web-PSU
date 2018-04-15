
package com.uteq.psu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kevin Onofre
 */
@Entity
@Table(name = "registro_solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroSolicitud.findAll", query = "SELECT r FROM RegistroSolicitud r")
    , @NamedQuery(name = "RegistroSolicitud.findByIdRegistro", query = "SELECT r FROM RegistroSolicitud r WHERE r.idRegistro = :idRegistro")
    , @NamedQuery(name = "RegistroSolicitud.findByNombreRe", query = "SELECT r FROM RegistroSolicitud r WHERE r.nombreRe = :nombreRe")
    , @NamedQuery(name = "RegistroSolicitud.findByFechaRe", query = "SELECT r FROM RegistroSolicitud r WHERE r.fechaRe = :fechaRe")
    , @NamedQuery(name = "RegistroSolicitud.findBySolicitudReg", query = "SELECT r FROM RegistroSolicitud r WHERE r.solicitudReg = :solicitudReg")
    , @NamedQuery(name = "RegistroSolicitud.encontrarUltimoRegistro", query ="SELECT r FROM RegistroSolicitud r ORDER BY r.idRegistro DESC")})
public class RegistroSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Size(max = 50)
    @Column(name = "nombre_re")
    private String nombreRe;
    @Column(name = "fecha_re")
    @Temporal(TemporalType.DATE)
    private Date fechaRe;
    @Size(max = 2147483647)
    @Column(name = "solicitud_reg")
    private String solicitudReg;
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud")
    @ManyToOne(fetch = FetchType.EAGER)
    private Solicitud idSolicitud;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario idUsuario;

    public RegistroSolicitud() {
    }

    public RegistroSolicitud(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getNombreRe() {
        return nombreRe;
    }

    public void setNombreRe(String nombreRe) {
        this.nombreRe = nombreRe;
    }

    public Date getFechaRe() {
        return fechaRe;
    }

    public void setFechaRe(Date fechaRe) {
        this.fechaRe = fechaRe;
    }

    public String getSolicitudReg() {
        return solicitudReg;
    }

    public void setSolicitudReg(String solicitudReg) {
        this.solicitudReg = solicitudReg;
    }

    public Solicitud getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Solicitud idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistro != null ? idRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroSolicitud)) {
            return false;
        }
        RegistroSolicitud other = (RegistroSolicitud) object;
        if ((this.idRegistro == null && other.idRegistro != null) || (this.idRegistro != null && !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uteq.psu.modelo.RegistroSolicitud[ idRegistro=" + idRegistro + " ]";
    }
    
}

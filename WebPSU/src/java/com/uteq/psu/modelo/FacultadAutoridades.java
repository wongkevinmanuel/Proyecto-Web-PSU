package com.uteq.psu.modelo;

/**
 *
 * @author Kevin Onofre
 */
public class FacultadAutoridades {


    private String tituloAutoridad;
    private String nombresAutoridad;
    private String cargo;
    private String tipo;

    public FacultadAutoridades() {
    }

    public FacultadAutoridades(String tituloAutoridad, String nombresAutoridad, String cargo,String tipo) {
        this.tituloAutoridad = tituloAutoridad;
        this.nombresAutoridad = nombresAutoridad;
        this.cargo = cargo;
        this.tipo = tipo;
    }
    
    
    
    
    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * @return the tituloAutoridad
     */
    public String getTituloAutoridad() {
        return tituloAutoridad;
    }

    /**
     * @param tituloAutoridad the tituloAutoridad to set
     */
    public void setTituloAutoridad(String tituloAutoridad) {
        this.tituloAutoridad = tituloAutoridad;
    }

    /**
     * @return the nombresAutoridad
     */
    public String getNombresAutoridad() {
        return nombresAutoridad;
    }

    /**
     * @param nombresAutoridad the nombresAutoridad to set
     */
    public void setNombresAutoridad(String nombresAutoridad) {
        this.nombresAutoridad = nombresAutoridad;
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
}

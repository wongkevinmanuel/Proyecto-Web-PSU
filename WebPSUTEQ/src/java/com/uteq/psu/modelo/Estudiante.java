/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.modelo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kevin Onofre
 */
public class Estudiante {

    
    
    private String status;
    private int estCodigo;
    private String estNombres;
    private String estApellido_paterno;
    private String estApellido_materno;
    private String estCedula;
    private String estCorreo;
    private String estCelular;
    private String estSexo;
    private String estSemestre;
    private String estParalelo;
    private String estPeriodoLectivo;
    private String estFactulad;
    private String estCarrera;
    private ArrayList<FacultadAutoridades> AutoridadesFacultad;
    //Utilizada para ordenar los nombres de las variables y los datos
    private ArrayList<DatoValor> informacionEstudiante;
    
    
    public Estudiante(int estCodigo, String estNombres, String estApellido_paterno, String estApellido_materno, String estCedula, String estCorreo, String estCelular, String estSexo, String estSemestre, String estParalelo, String estPeriodoLectivo, String estFactulad, String estCarrera, ArrayList<FacultadAutoridades> AutoridadesFacultad) {
        this.estCodigo = estCodigo;
        this.estNombres = estNombres;
        this.estApellido_paterno = estApellido_paterno;
        this.estApellido_materno = estApellido_materno;
        this.estCedula = estCedula;
        this.estCorreo = estCorreo;
        this.estCelular = estCelular;
        this.estSexo = estSexo;
        this.estSemestre = estSemestre;
        this.estParalelo = estParalelo;
        this.estPeriodoLectivo = estPeriodoLectivo;
        this.estFactulad = estFactulad;
        this.estCarrera = estCarrera;
        this.AutoridadesFacultad = AutoridadesFacultad;
    }
    public Estudiante() {
        AutoridadesFacultad = new ArrayList<>();
    }
    public void ordenarInformacionUsuario() {
        //ArrayList<DatoValor> variablesContenido=new ArrayList<>();
        this.setInformacionEstudiante(new ArrayList<>());
        getInformacionEstudiante().add(new DatoValor("estudiantenombre", estNombres.toUpperCase() + " " + estApellido_paterno.toUpperCase() +" " +estApellido_materno.toUpperCase() ));
        getInformacionEstudiante().add(new DatoValor("estudiantecedula", estCedula));
        getInformacionEstudiante().add(new DatoValor("estudiantesemestre",estSemestre));
        getInformacionEstudiante().add(new DatoValor("estudianteparalelo", estParalelo));
        getInformacionEstudiante().add(new DatoValor("estudiantecarrera", estCarrera));
        getInformacionEstudiante().add(new DatoValor("estudianteperiodolectivo", estPeriodoLectivo));
        getInformacionEstudiante().add(new DatoValor("facultad",estFactulad ));
        getInformacionEstudiante().add(new DatoValor("estudiantetelefono", estCelular));
        getInformacionEstudiante().add(new DatoValor("estudianteemail", estCorreo));
        //Autoridades
        for (Iterator<FacultadAutoridades> iterator = AutoridadesFacultad.iterator(); iterator.hasNext();) {
            FacultadAutoridades next = iterator.next();
            switch(next.getTipo()){
                case "facultaddecano":
                        {
                            getInformacionEstudiante().add(new DatoValor("facultaddecano",next.getNombresAutoridad() ));
                            break;
                        }
                case "facultadsubdecano":
                        {
                            getInformacionEstudiante().add(new DatoValor("facultadsuddecano",next.getNombresAutoridad() ));
                            break;
                        }
                case "facultadcoordinador":
                        {
                            getInformacionEstudiante().add(new DatoValor("facultadcoordinador",next.getNombresAutoridad() ));
                            break;  
                        }}
       }
    }   
       
    
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the estCodigo
     */
    public int getEstCodigo() {
        return estCodigo;
    }

    /**
     * @param estCodigo the estCodigo to set
     */
    public void setEstCodigo(int estCodigo) {
        this.estCodigo = estCodigo;
    }

    /**
     * @return the estNombres
     */
    public String getEstNombres() {
        return estNombres;
    }

    /**
     * @param estNombres the estNombres to set
     */
    public void setEstNombres(String estNombres) {
        this.estNombres = estNombres;
    }

    /**
     * @return the estApellido_paterno
     */
    public String getEstApellido_paterno() {
        return estApellido_paterno;
    }

    /**
     * @param estApellido_paterno the estApellido_paterno to set
     */
    public void setEstApellido_paterno(String estApellido_paterno) {
        this.estApellido_paterno = estApellido_paterno;
    }

    /**
     * @return the estApellido_materno
     */
    public String getEstApellido_materno() {
        return estApellido_materno;
    }

    /**
     * @param estApellido_materno the estApellido_materno to set
     */
    public void setEstApellido_materno(String estApellido_materno) {
        this.estApellido_materno = estApellido_materno;
    }

    /**
     * @return the estCedula
     */
    public String getEstCedula() {
        return estCedula;
    }

    /**
     * @param estCedula the estCedula to set
     */
    public void setEstCedula(String estCedula) {
        this.estCedula = estCedula;
    }

    /**
     * @return the estCorreo
     */
    public String getEstCorreo() {
        return estCorreo;
    }

    /**
     * @param estCorreo the estCorreo to set
     */
    public void setEstCorreo(String estCorreo) {
        this.estCorreo = estCorreo;
    }

    /**
     * @return the estCelular
     */
    public String getEstCelular() {
        return estCelular;
    }

    /**
     * @param estCelular the estCelular to set
     */
    public void setEstCelular(String estCelular) {
        this.estCelular = estCelular;
    }

    /**
     * @return the estSexo
     */
    public String getEstSexo() {
        return estSexo;
    }

    /**
     * @param estSexo the estSexo to set
     */
    public void setEstSexo(String estSexo) {
        this.estSexo = estSexo;
    }

    /**
     * @return the estSemestre
     */
    public String getEstSemestre() {
        return estSemestre;
    }

    /**
     * @param estSemestre the estSemestre to set
     */
    public void setEstSemestre(String estSemestre) {
        this.estSemestre = estSemestre;
    }

    /**
     * @return the estParalelo
     */
    public String getEstParalelo() {
        return estParalelo;
    }

    /**
     * @param estParalelo the estParalelo to set
     */
    public void setEstParalelo(String estParalelo) {
        this.estParalelo = estParalelo;
    }

    /**
     * @return the estPeriodoLectivo
     */
    public String getEstPeriodoLectivo() {
        return estPeriodoLectivo;
    }

    /**
     * @param estPeriodoLectivo the estPeriodoLectivo to set
     */
    public void setEstPeriodoLectivo(String estPeriodoLectivo) {
        this.estPeriodoLectivo = estPeriodoLectivo;
    }

    /**
     * @return the estFactulad
     */
    public String getEstFactulad() {
        return estFactulad;
    }

    /**
     * @param estFactulad the estFactulad to set
     */
    public void setEstFactulad(String estFactulad) {
        this.estFactulad = estFactulad;
    }

    /**
     * @return the estCarrera
     */
    public String getEstCarrera() {
        return estCarrera;
    }

    /**
     * @param estCarrera the estCarrera to set
     */
    public void setEstCarrera(String estCarrera) {
        this.estCarrera = estCarrera;
    }

    
    
    
    /**
     * @return the AutoridadesFacultad
     */
    public ArrayList<FacultadAutoridades> getAutoridadesFacultad() {
        return AutoridadesFacultad;
    }

    /**
     * @param AutoridadesFacultad the AutoridadesFacultad to set
     */
    public void setAutoridadesFacultad(ArrayList<FacultadAutoridades> AutoridadesFacultad) {
        this.AutoridadesFacultad = AutoridadesFacultad;
    }

    /**
     * @return the informacionEstudiante
     */
    public ArrayList<DatoValor> getInformacionEstudiante() {
        return informacionEstudiante;
    }

    /**
     * @param informacionEstudiante the informacionEstudiante to set
     */
    public void setInformacionEstudiante(ArrayList<DatoValor> informacionEstudiante) {
        this.informacionEstudiante = informacionEstudiante;
    }
    

    
}

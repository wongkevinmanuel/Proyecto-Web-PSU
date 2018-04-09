package com.uteq.psu.controler;

import com.uteq.psu.controler.util.JsfUtil;
import com.uteq.psu.modelo.ContentSolicitud;
import com.uteq.psu.modelo.DatoValor;
import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.VariableValor;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Kevin Onofre
 */
@Named("RegistroSolicontr")
@SessionScoped
public class RegistroSolicontr implements Serializable{

    /* Objetos para gestionar la persistencia*/
    private RegistroSolicitud currentRegisSoli;
    @EJB
    private com.uteq.psu.bean.RegistroSolicitudFacade ejbFacade;
    @EJB
    private com.uteq.psu.bean.SolicitudFacade ejbFacadesolicitud;
    
    /*Objetos para gestionar los datos al usuario */
    private ContentSolicitud contenidoSolicitud;
    private String [] selectedArchivoAdjunto;
    
    
    
    
    public RegistroSolicitud getSelected() {
        if(currentRegisSoli==null){
            currentRegisSoli = new RegistroSolicitud();
        }
        return currentRegisSoli;
    } 
    
    public RegistroSolicontr() {
    
    }
    
    public String obtenerSolicitud(int idSolicitud){
        
        if (idSolicitud != 0 ){
            try{
                //Obtiene la seleccion realiza el usuario en el menu 
                Solicitud solicitud = (Solicitud) ejbFacadesolicitud.find(idSolicitud);
                currentRegisSoli = new RegistroSolicitud();
                currentRegisSoli.setIdSolicitud(solicitud);
                //LLenar el conetido de la solicitud
                contenidoSolicitud = new ContentSolicitud();
                contenidoSolicitud.ordenarDatos(currentRegisSoli.getIdSolicitud().getDescripcion());
            }catch(Exception a){ JsfUtil.addErrorMessage(a, "Error en la solicitud" + a.toString());}
        }
        return "/pages/solicitud/Solmatter.xhtml";
    }
    
    /*Llena item con opciones a elegir para adjuntar en documentos */
    public SelectItem[] getItemsDisponiblesSelectMuchos(){
        return JsfUtil.getSelectItems(contenidoSolicitud.getAdjuntoOpcion(), false);
    }
    
    public void generarSolicitud(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedArchivoAdjunto.length == 0){
            context.addMessage(null , new FacesMessage( FacesMessage.SEVERITY_WARN ,"No ha adjuntado documento" , "Debe seleccionar los documentos que desea adjuntar"));
        }
        else{
            context.addMessage(null , new FacesMessage("Selecciono: " + selectedArchivoAdjunto[0] + " cantidad: "+ selectedArchivoAdjunto.length));
            //Generar Solcitud 
            //Optener los datos 

            //Guardar en la BD formateado en JSON

            //Generar el PDF de solicitud
        
        }
        
        
    }
    
    
    /* PROPIEDADES */
    /**
     * @return the ejbFacade
     */
    public com.uteq.psu.bean.RegistroSolicitudFacade getEjbFacade() {
        return ejbFacade;
    }
 
    /**
     * @return the selectedArchivoAdjunto
     */
    public String[] getSelectedArchivoAdjunto() {
        return selectedArchivoAdjunto;
    }

    /**
     * @param selectedArchivoAdjunto the selectedArchivoAdjunto to set
     */
    public void setSelectedArchivoAdjunto(String[] selectedArchivoAdjunto) {
        this.selectedArchivoAdjunto = selectedArchivoAdjunto;
    }
   
    /**
     * @return the contenidoSolicitud
     */
    public ContentSolicitud getContenidoSolicitud() {
        return contenidoSolicitud;
    }

    /**
     * @param contenidoSolicitud the contenidoSolicitud to set
     */
    public void setContenidoSolicitud(ContentSolicitud contenidoSolicitud) {
        this.contenidoSolicitud = contenidoSolicitud;
    }

}

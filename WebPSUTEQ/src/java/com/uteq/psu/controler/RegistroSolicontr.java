package com.uteq.psu.controler;

import com.uteq.psu.controler.util.JsfUtil;
import com.uteq.psu.controler.util.Pagina;
import com.uteq.psu.modelo.ContentSolicitud;
import com.uteq.psu.modelo.DatoValor;
import com.uteq.psu.modelo.Estudiante;
import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.VariableValor;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
            //Generar Solcitud 
            //Optener los datos del usuario
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
            Estudiante user = (Estudiante) session.getAttribute("usuario");
            //llenar la contenido de la solicitud con los datos del usuario
            user.ordenarInformacionUsuario();
            contenidoSolicitud.contSolicitudDatosEstud(user.getInformacionEstudiante());
            boolean solicitudLlenada = contenidoSolicitud.llenarContSolicitudDatosEstud();
            //Dato de la seleccion de archivos a adjuntos [] selectedArchivoAdjunto
            
            //Datos de Fecha
            Calendar fecha = Calendar.getInstance();
            String datoFecha = "Quevedo, " + fecha.get(Calendar.DATE) + " "+ fecha.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + "  del "+ fecha.get(Calendar.YEAR) ;
            //Ubicacion de la imagen
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String ubicacionLogo = (String) servletContext.getRealPath("/resources/image/logo_solicitud.png");
            //Generar Solicitud
            Pagina solicitudPDF = new Pagina();
            boolean generadoPDF = solicitudPDF.Crear_pdf(ubicacionLogo,currentRegisSoli.getIdSolicitud().getNombreSolicitud()
                                                        , datoFecha
                                                        , contenidoSolicitud.getDirigidaNombre().getContenido()+contenidoSolicitud.getDirigidaTitulo().getContenido()
                                                        ,  contenidoSolicitud.getContenido().getContenido()
                                                        , contenidoSolicitud.getAdjuntoTexto().getContenido()
                                                        , selectedArchivoAdjunto
                                                        , ""//"Nota se adjunta esto"
                                                        , new String[]{"Certificado medico"}//new String[]{"Certificado medico"}
                                                        , contenidoSolicitud.getAdicional().getContenido()
                                                        , contenidoSolicitud.getTituloSolicitante().getContenido());
            
            //contenidoSolicitud.getDirigidaNombre().getVariablesContenido().get(0).getNombreVariable().e
            //this.nombreUser = user.getEstNombres() + user.getEstApellido_paterno();
            //Guardar en la BD formateado en JSON
            //context.addMessage(null , new FacesMessage("Fecha: " + user.getEstCedula()));
            //Generar el PDF de solicitud
            
            //if (generadoPDF == true)
            if (true == true)
            context.addMessage(null , new FacesMessage("Solicitud generar "+ ubicacionLogo ));//context.addMessage(null , new FacesMessage("Fecha: " + datoFecha + " Conenido: " + contenidoSolicitud.getContenido().getContenido()));
            else
            context.addMessage(null , new FacesMessage( FacesMessage.SEVERITY_ERROR ,"Error en solicitud" , "No se ha podido generar el PDF de la solicitud"));
        
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

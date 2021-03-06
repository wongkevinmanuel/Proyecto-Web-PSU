package com.uteq.psu.controler;

import com.google.gson.Gson;
import com.uteq.psu.controler.util.JsfUtil;
import com.uteq.psu.controler.util.Pagina;
import com.uteq.psu.modelo.ContentSolicitud;
import com.uteq.psu.modelo.DatoValor;
import com.uteq.psu.modelo.Estudiante;
import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
    @EJB
    private com.uteq.psu.bean.UsuarioFacade ejbFacadeUsuario;
    
    /*Objetos para gestionar los datos al usuario */
    private ContentSolicitud contenidoSolicitud;
    private String [] selectedArchivoAdjunto;
    private StreamedContent file;
    
    /*Controlor de vista de fecha*/
    private boolean datosFecha=false;
    
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
                
                /*Verificar si la solicitud tiene que ingresar fechas */
                /*******************************************************************/
                //contenidoSolicitud.getContenido().getVariablesContenido();
                for (Iterator<DatoValor> iterator = contenidoSolicitud.getContenido().getVariablesContenido().iterator(); iterator.hasNext();) {
                    DatoValor next = iterator.next();
                    if (next.getNombreVariable().equals("faltadiainicio") ||
                        next.getNombreVariable().equals("faltadiafin")) {
                        datosFecha = true;
                    }else
                        datosFecha= false;
                }  
                /*******************************************************************/
                
            }catch(Exception a){ JsfUtil.addErrorMessage(a, "Error en la solicitud" + a.toString());}
        }
        return "/pages/solicitud/Solmatter.xhtml";
    }
    
    /*Llena item con opciones a elegir para adjuntar en documentos */
    public SelectItem[] getItemsDisponiblesSelectMuchos(){
        return JsfUtil.getSelectItems(contenidoSolicitud.getAdjuntoOpcion(), false);
    }
    
    public void generarSolicitud() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedArchivoAdjunto.length == 0){
            context.addMessage(null , new FacesMessage( FacesMessage.SEVERITY_WARN ,"No ha adjuntado documento" , "Debe seleccionar los documentos que desea adjuntar"));
        }
        else{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(true); 
            //************************************ Generar Solcitud 
            //Optener los datos del usuario
            Estudiante user = (Estudiante) session.getAttribute("usuario");
            int idusuario = (int) session.getAttribute("idUsuario");
            
            //llenar la contenido de la solicitud con los datos del usuario
            user.ordenarInformacionUsuario();
            contenidoSolicitud.contSolicitudDatosEstud(user.getInformacionEstudiante());
            boolean solicitudLlenada = contenidoSolicitud.llenarContSolicitudDatosEstud();
            
            /*Verificar si la solicitud tiene que ingresar fechas */
            //*************************************************************
            if (datosFecha){
            //form:fechainicio_input
            //form:fechafin_input
            Map<String,String> request = ec.getRequestParameterMap();
            String fechaInicio = request.get("form"+ NamingContainer.SEPARATOR_CHAR+"fechainicio_input");
            String fechaFin = request.get("form"+ NamingContainer.SEPARATOR_CHAR+"fechafin_input");
            ArrayList<DatoValor> fechas = new ArrayList<>();
            fechas.add(new DatoValor("faltadiainicio", fechaInicio));
            fechas.add(new DatoValor("faltadiafin", fechaFin));
            contenidoSolicitud.getContenido().setContenido(
                    contenidoSolicitud.buscarVariableReemplazar(
                            contenidoSolicitud.getContenido().getContenido()
                            ,fechas));
            }
            //*************************************************************
            /*@materiaatrasada@ @profesor@*/
            
            
            

            //Datos de Fecha
            Calendar fecha = Calendar.getInstance();
            String datoFecha = "Quevedo, " + fecha.get(Calendar.DATE) + " "+ fecha.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + "  del "+ fecha.get(Calendar.YEAR) ;
            //Ubicacion de la imagen
            ServletContext servletContext = (ServletContext) ec.getContext();
            String ubicacionLogo = (String) servletContext.getRealPath("/resources/image/logo_solicitud.png");
            //Generar Solicitud
            Pagina solicitudPDF = new Pagina();
            InputStream  archivoSolicitudPDF = solicitudPDF.Crear_pdf(ubicacionLogo
                                                        ,currentRegisSoli.getIdSolicitud().getNombreSolicitud()
                                                        , datoFecha
                                                        , contenidoSolicitud.getDirigidaNombre().getContenido()+contenidoSolicitud.getDirigidaTitulo().getContenido()
                                                        ,  contenidoSolicitud.getContenido().getContenido()
                                                        , contenidoSolicitud.getAdjuntoTexto().getContenido()
                                                        , selectedArchivoAdjunto
                                                        , ""//"Nota se adjunta esto"
                                                        , new String[]{""}//new String[]{"Certificado medico"}
                                                        , contenidoSolicitud.getAdicional().getContenido()
                                                        , contenidoSolicitud.getTituloSolicitante().getContenido());
            
            //Generar el PDF de solicitud
            if (archivoSolicitudPDF != null){
                String nombreArchivo = "Solicitud-" + currentRegisSoli.getIdSolicitud().getNombreSolicitud() + "-" + user.getEstApellido_paterno()+".pdf";
                file = new DefaultStreamedContent(archivoSolicitudPDF,"application/pdf",nombreArchivo.toUpperCase());
                //Guardar en la BD formateado en JSON
                //Buscar ultimo registro
                RegistroSolicitud a = ejbFacade.ultimoRegistro("RegistroSolicitud.encontrarUltimoRegistro");
                int id = ( a == null ? 1 : a.getIdRegistro()+1 );
                currentRegisSoli.setIdRegistro(id);
                currentRegisSoli.setFechaRe(new Date());
                currentRegisSoli.setNombreRe(nombreArchivo);
                currentRegisSoli.setIdUsuario(new Usuario(idusuario));
                currentRegisSoli.setSolicitudReg( convertiraJson(contenidoSolicitud, selectedArchivoAdjunto));
                    try{
                        ejbFacade.create(currentRegisSoli);
                        JsfUtil.addSuccessMessage("Se guardo la solicitud generada");
                    }catch(Exception e){
                        JsfUtil.addErrorMessage(e,"No se pudo guardar la solicitud");
                    }
                }else
                JsfUtil.addErrorMessage("No se ha podido generar el PDF de la solicitud");
        }
    }
    
    private String convertiraJson(ContentSolicitud data, String [] selecionMotivo){
        ContentSolicitud jsonCon = data;
        jsonCon.setAdjuntoOpcion( 
                new ArrayList<>(
                Arrays.asList(selecionMotivo)));
        return new Gson().toJson(jsonCon);
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

    
    /**
     * @return the file
     */
    public StreamedContent getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(StreamedContent file) {
        this.file = file;
    }

    
    
    /**
     * @return the datosFecha
     */
    public boolean isDatosFecha() {
        return datosFecha;
    }

    /**
     * @param datosFecha the datosFecha to set
     */
    public void setDatosFecha(boolean datosFecha) {
        this.datosFecha = datosFecha;
    }
}

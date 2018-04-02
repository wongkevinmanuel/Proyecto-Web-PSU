package com.uteq.psu.controler;

import com.uteq.psu.controler.util.JsfUtil;
import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.modelo.Solicitud;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Kevin Onofre
 */
@Named("RegistroSolicontr")
@SessionScoped
public class RegistroSolicontr implements Serializable{

    private RegistroSolicitud currentRegisSoli;
    @EJB
    private com.uteq.psu.bean.RegistroSolicitudFacade ejbFacade;
    @EJB
    private com.uteq.psu.bean.SolicitudFacade ejbFacadesolicitud;
    
    
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
                Solicitud solicitud = (Solicitud) ejbFacadesolicitud.find(idSolicitud);
                currentRegisSoli = new RegistroSolicitud();
                currentRegisSoli.setIdSolicitud(solicitud);} 
            catch(Exception a){ JsfUtil.addErrorMessage(a, "Error en la solicitud");}
        }
        return "/pages/solicitud/Solmatter.xhtml";
    }
    
    /* PROPIEDADES */
    /**
     * @return the ejbFacade
     */
    public com.uteq.psu.bean.RegistroSolicitudFacade getEjbFacade() {
        return ejbFacade;
    }
    
}

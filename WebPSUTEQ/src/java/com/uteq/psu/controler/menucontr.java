
package com.uteq.psu.controler;

import com.uteq.psu.modelo.Estudiante;
import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.TipoSolicitud;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kevin Onofre
 */

@Named("menucontr")
@SessionScoped
public class menucontr implements Serializable{
    
    private String nombreUser;
    
    
    private TipoSolicitud solicitudesMenu;
    private DataModel items= null;
    
    private Solicitud currentsolicitud;
    @EJB
    private com.uteq.psu.bean.TipoSolicitudFacade ejbFacade;
    
     /**
     * @return the ejbFacade
     */
    private com.uteq.psu.bean.TipoSolicitudFacade getEjbFacade() {
        return ejbFacade;
    }

    public Solicitud getSelected(){
        if (currentsolicitud == null){
            currentsolicitud = new Solicitud();
        }
        return currentsolicitud;
    }
    
    public menucontr() {}
    
    
    public String closeSession(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index";
    }
    
    public void chequearSession(){
        ExternalContext exContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) exContext.getSession(false);  
        Estudiante user = (session == null)? null: (Estudiante) session.getAttribute("usuario") ;
        if(user == null){  
            try{
            exContext.redirect(exContext.getRequestContextPath()+ "/faces/index.xhtml");
            }catch(IOException e){
            e.printStackTrace();
            }
        }
    }
    
    
    public DataModel getItems() {
        if(items == null){
            items =new ListDataModel(getEjbFacade().obtenerSolicitudes());
        }
        return items;
    }
    
    /**
     * @return the solicitudesMenu
     */
    public TipoSolicitud getSolicitudesMenu() {
        return solicitudesMenu;
    }

    /**
     * @param solicitudesMenu the solicitudesMenu to set
     */
    public void setSolicitudesMenu(TipoSolicitud solicitudesMenu) {
        this.solicitudesMenu = solicitudesMenu;
    }
    
    /**
     * @return the nombreUser
     */
    public String getNombreUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);  
        Estudiante user = (Estudiante) session.getAttribute("usuario");
        //((Estudiante) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("usuario")).getEstCodigo();
        nombreUser = user.getEstApellido_paterno();
        return nombreUser;
    }

    /**
     * @param nombreUser the nombreUser to set
     */
    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }
    
}


package com.uteq.psu.controler;

import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.TipoSolicitud;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

/**
 *
 * @author Kevin Onofre
 */

@Named("menucontr")
@SessionScoped
public class menucontr implements Serializable{
 
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
    
    
}


package com.uteq.psu.controlador;

import com.uteq.psu.modelo.Solicitud;
import com.uteq.psu.modelo.TipoSolicitud;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

/**
 *
 * @author Kevin Onofre
 */

@Named("menucontr")
@ManagedBean
@SessionScoped
public class menucontr implements Serializable{

    
    private TipoSolicitud solicitudesMenu;
    private com.uteq.psu.sessionbean.TipoSolicitudFacade ejbFacade;
    
    public menucontr() {}
    
    public DataModel datosMenu(){
        ArrayList<TipoSolicitud> al = new ArrayList<>();
        TipoSolicitud s1 = new TipoSolicitud();
        Solicitud soli1 = new Solicitud();
        soli1.setNombreSolicitud("Solicitud diferente");
        s1.setNombreTiSo("Solicitudes docentes");
        ArrayList<Solicitud> soll = new ArrayList<>();
        soll.add(soli1);
        soll.add(soli1);
        soll.add(soli1);
        s1.setSolicitudList(soll);
        
        TipoSolicitud s2 = new TipoSolicitud();
        s2.setNombreTiSo("Solicitudes PPP");
        s2.setSolicitudList(soll);
        TipoSolicitud s3 = new TipoSolicitud();
        s3.setNombreTiSo("Solicitudes VCC");
        s3.setSolicitudList(soll);
        
        al.add(s1);
        al.add(s2);
        al.add(s3);
        //return new ListDataModel(al);
        /* Acceder a bd con persistencia */
        
        DataModel items= null;
        
        return items;//new ListDataModel(items);
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

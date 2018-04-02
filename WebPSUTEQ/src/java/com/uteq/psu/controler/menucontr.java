
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
        return new ListDataModel(al);
        /* Acceder a bd con persistencia */
        //items= null;
        //items = new ListDataModel(getEjbFacade().obtenerSolicitudes());
        //return getItems();//new ListDataModel(items);
        /*
        <!--<ui:repeat value="#{menucontr.items}" var="item">
                                    <li class="root-level has-sub"> 
                                            <a href="#"><i class="entypo-vcard" aria-hidden="true"></i> Tipo #{item.nombreTiSo} <i class="icono derecha fa fa-chevron-down" aria-hidden="true"></i></a>
                                            <ul>
                                                <ui:repeat value="#{item.getSolicitudList()}" var="item2" >
                                                    <li><h:link outcome="/pages/solicitudK/Solmatter"  value="#{item2.nombreSolicitud}"/></li>
                                                </ui:repeat>
                                            </ul>
                                    </li>
                                    </ui:repeat>-->
        2
        <ui:repeat value="#{menucontr.items}" var="item">
                                    <li class="root-level has-sub"> 
                                            <a href="#"><i class="entypo-vcard" aria-hidden="true"></i> Tipo #{item.nombreTiSo} <i class="icono derecha fa fa-chevron-down" aria-hidden="true"></i></a>
                                            <ul>
                                                 <ui:repeat value="#{item.solicitudList}" var="item2" >
                                                    <li><h:link outcome="/pages/solicitudK/Solmatter"  value="#{item2.nombreSolicitud}"/></li>
                                                </ui:repeat>
                                            </ul>
                                    </li>
                                    </ui:repeat>
        
        */
    }
    public String elegirSolicitud(int idSolici){
       int idSolicitud = idSolici;
       //currentsolicitud = (Solicitud) getItems().getRowData();
       return "/pages/solicitud/Solmatter.xhtml";
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
    
    
}

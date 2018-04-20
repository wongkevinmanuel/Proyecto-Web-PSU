package com.uteq.psu.controler;

import com.google.gson.Gson;
import com.uteq.psu.modelo.RegistroSolicitud;
import com.uteq.psu.controler.util.JsfUtil;
import com.uteq.psu.controler.util.PaginationHelper;
import com.uteq.psu.bean.RegistroSolicitudFacade;
import com.uteq.psu.controler.util.Pagina;
import com.uteq.psu.modelo.ContentSolicitud;
import com.uteq.psu.modelo.Usuario;
import java.io.InputStream;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("registroSolicitudController")
@SessionScoped
public class RegistroSolicitudController implements Serializable {

    private RegistroSolicitud current;
    private DataModel items = null;
    @EJB
    private com.uteq.psu.bean.RegistroSolicitudFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private Usuario user;
    private StreamedContent file;
    
    
    public RegistroSolicitudController() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        user = new Usuario((int) session.getAttribute("idUsuario"));
    }

    public RegistroSolicitud getSelected() {
        if (current == null) {
            current = new RegistroSolicitud();
            selectedItemIndex = -1;
        }
        return current;
    }

    private RegistroSolicitudFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(5) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},user));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (RegistroSolicitud) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new RegistroSolicitud();
        selectedItemIndex = -1;
        return "Create";
    }
    
    public void descargar(){
        RegistroSolicitud registroSolicitud =  (RegistroSolicitud)getItems().getRowData();
        ContentSolicitud contenidoSolicitud = 
                (new Gson()).fromJson(registroSolicitud.getSolicitudReg(), ContentSolicitud.class);
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String ubicacionLogo = (String) servletContext.getRealPath("/resources/image/logo_solicitud.png");
           
        
        Pagina solicitud = new Pagina();
        InputStream inputStreamSolicitud = solicitud.Crear_pdf(
                ubicacionLogo,
               registroSolicitud.getIdSolicitud().getNombreSolicitud(), 
               registroSolicitud.getFechaRe().toString(),
                contenidoSolicitud.getDirigidaNombre().getContenido()+contenidoSolicitud.getDirigidaTitulo().getContenido(), 
                contenidoSolicitud.getContenido().getContenido(), 
                contenidoSolicitud.getAdjuntoTexto().getContenido(), 
                 contenidoSolicitud.getAdjuntoOpcion()
                         .toArray(new String[contenidoSolicitud.getAdjuntoOpcion().size()]),
                "",
                new String[]{""},
                contenidoSolicitud.getAdicional().getContenido(),
                contenidoSolicitud.getTituloSolicitante().getContenido());
        if (inputStreamSolicitud!= null) {
                String nombreArchivo =
                        "Solicitud-"+registroSolicitud.getIdSolicitud().getNombreSolicitud()+
                        ".pdf";
                file = new DefaultStreamedContent(inputStreamSolicitud,"application/pdf", nombreArchivo);
                
            } else {
            JsfUtil.addErrorMessage("No se ha podido descargar el registro.");
            }
         
    }
    
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegistroSolicitudCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (RegistroSolicitud) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegistroSolicitudUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (RegistroSolicitud) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegistroSolicitudDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1},user).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public RegistroSolicitud getRegistroSolicitud(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = RegistroSolicitud.class)
    public static class RegistroSolicitudControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RegistroSolicitudController controller = (RegistroSolicitudController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "registroSolicitudController");
            return controller.getRegistroSolicitud(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RegistroSolicitud) {
                RegistroSolicitud o = (RegistroSolicitud) object;
                return getStringKey(o.getIdRegistro());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RegistroSolicitud.class.getName());
            }
        }

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

}

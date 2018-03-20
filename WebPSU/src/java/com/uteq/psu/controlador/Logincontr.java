package com.uteq.psu.controlador;
 
import com.google.gson.Gson;
import com.uteq.psu.modelo.Estudiante;
import com.uteq.psu.modelo.StatusWS;
import com.uteq.psu.wsclient.ClientWsSicau;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.stream.JsonParser;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ClientErrorException;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Kevin Onofre
 */
@Named("Logincontr")
@ManagedBean
@SessionScoped
public class Logincontr implements Serializable{

    private String userSicau;
    private String claveSicau;
    
    public Logincontr() {
        
    }
    
    public String doLogin(){
        ClientWsSicau clienteSicau  = new ClientWsSicau();
        StatusWS statusWs = null;
        //Estudiante estudiante = null;
        try{
            String dataJson = clienteSicau.Login(userSicau, claveSicau,"dXRlcTp1dGVx");
            if (dataJson != null){
                try {
                     JSONObject status = new JSONObject(dataJson);   
                     statusWs = new StatusWS();
                     statusWs.setStatus(status.getString("status"));
                     if("1".equals(statusWs.getStatus())){
                     Estudiante estudiante = new Estudiante();
                     //estudiante = new Estudiante();
                     estudiante = new Gson().fromJson(dataJson, Estudiante.class);
                     //Guardar en session los datos del usuario para su posterior
                     //uso en la generacion de documentos y guardado en BD
                     FacesContext msg = FacesContext.getCurrentInstance();
                     HttpSession session = (HttpSession) msg.getExternalContext().getSession(true);
                     session.setAttribute("usuario", estudiante);
                     }
                     else if ("2".equals(statusWs.getStatus())){
                     statusWs = new Gson().fromJson(dataJson, StatusWS.class);
                     }
                } catch (JSONException errorInfo){ statusWs.setStatus("2"); statusWs.setMensaje("Error al tratar datos JSON");}
            }    
        }catch(ClientErrorException errorInfo){
           statusWs.setStatus("2"); 
           statusWs.setMensaje("Error al acceder al WS SICAU");
        }
        if("1".equals(statusWs.getStatus())){
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage("loginForm" , new FacesMessage("DATA USER SICAU: "+ estudiante.getAutoridadesFacultad().get(0).getNombresAutoridad()));
            return "template.xhtml";
        }
        else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginForm" , new FacesMessage(statusWs.getMensaje()));
            return null;
        }        
    } 
    /**
     * @return the userSicau
     */
    public String getUsersicau() {
        return userSicau;
    }

    /**
     * @return the claveSicau
     */
    public String getClavesicau() {
        return claveSicau;
    }
    
    /**
     * @param userSicau the userSicau to set
     */
    public void setUsersicau(String userSicau) {
        this.userSicau = userSicau;
    }

    /**
     * @param claveSicau the claveSicau to set
     */
    public void setClavesicau(String claveSicau) {
        this.claveSicau = claveSicau;
    }
}

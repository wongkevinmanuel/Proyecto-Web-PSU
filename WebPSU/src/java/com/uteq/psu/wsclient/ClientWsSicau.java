package com.uteq.psu.wsclient;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
//import 
//        org.glassfish.jersey.

/**
 * Jersey REST client generated for REST resource:sf [recursos]<br>
 * USAGE:
 * <pre>
 *        ClientWsSicau client = new ClientWsSicau();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Kevin Onofre
 */
public class ClientWsSicau {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/WebServiceUTEQ/webresources";

    public ClientWsSicau() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("recursos");
        
    }

    public ClientWsSicau(String username, String password) {
        this();
        setUsernamePassword(username, password);
    }

    public String Login(String usuario, String clave, String authDato) throws ClientErrorException {//      uteq:uteq
        WebTarget resource = webTarget;String dataAuthDato = "Basic "+authDato;                     //Basic dXRlcTp1dGVx
        resource = resource.path(java.text.MessageFormat.format("Login/{0},{1},{2}", new Object[]{usuario, clave,dataAuthDato}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    /*
    public <T> T imprimirMensaje(Class<T> responseType, String param) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("imprimirMensaje/{0}", new Object[]{param}));
        return resource.get(responseType);
    }*/

    public void close() {
        client.close();
    }

    public final void setUsernamePassword(String username, String password) {
        webTarget.register(new org.glassfish.jersey.client.filter.HttpBasicAuthFilter(username, password));
       
    }
    
}

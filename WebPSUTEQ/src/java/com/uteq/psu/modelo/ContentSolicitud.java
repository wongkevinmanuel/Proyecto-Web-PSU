package com.uteq.psu.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Kevin Onofre
 */
public class ContentSolicitud implements Serializable{
    /*
    Nombre de variables del Shema-Json
    */
    final String PROPERTIES = "properties";
    final String DIRIGIDANOMBRE = "dirigida_nombre";
    final String DIRIGIDATITULO = "dirigida_titulo";
    final String CONTENIDO = "contenido";
    final String ADJUNTOTEXT = "adjunto_text";
    final String ADJUNTOOPCION = "adjunto_opcion";
    final String ITEMS = "items";
    final String ENUM = "enum";
    final String ADICIONAL = "adicional";
    final String TITULOSOLICITANTE = "titulo_solicitante";
    
    
    private VariableValor dirigidaNombre;
    private VariableValor dirigidaTitulo;
    private VariableValor contenido;
    private VariableValor adjuntoTexto;
    //private VariableValor adjuntoOpcion;
    private ArrayList<String> adjuntoOpcion;
    private VariableValor adicional;
    private VariableValor tituloSolicitante;

    public ContentSolicitud() {
        
    }
    
    public ContentSolicitud(VariableValor dirigidaNombre, VariableValor dirigidaTitulo, VariableValor contenido, VariableValor adjuntoTexto, ArrayList<String> adjuntoOpcion, VariableValor adicional, VariableValor tituloSolicitante) {
        this.dirigidaNombre = dirigidaNombre;
        this.dirigidaTitulo = dirigidaTitulo;
        this.contenido = contenido;
        this.adjuntoTexto = adjuntoTexto;
        this.adjuntoOpcion = adjuntoOpcion;
        this.adicional = adicional;
        this.tituloSolicitante = tituloSolicitante;
    }
    
    public void ordenarDatos(String data){
        try {
            //Obtener los datos de cada objeto
            JSONObject dataJson = new JSONObject(data);
            JSONObject contenido = dataJson.getJSONObject(PROPERTIES);
            //Establecer el contenido de la variables 
            //DIRIGIDANOMBRE
            JSONArray dato = new JSONArray(contenido.getJSONObject(DIRIGIDANOMBRE).getString(ENUM));
            dirigidaNombre = new VariableValor(dato.getString(0));
            dirigidaNombre.setVariablesContenido(buscarNombreVariables(dirigidaNombre.getContenido()));
            //DIRIGIDATITULO
            JSONArray dato2 = new JSONArray(contenido.getJSONObject(DIRIGIDATITULO).getString(ENUM));
            dirigidaTitulo = new VariableValor(dato2.getString(0));
            dirigidaTitulo.setVariablesContenido(buscarNombreVariables(dirigidaTitulo.getContenido()));
            //CONTENIDO 
            JSONArray dato3 = new JSONArray(contenido.getJSONObject(CONTENIDO).getString(ENUM));
            this.contenido = new VariableValor(dato3.getString(0));
            this.contenido.setVariablesContenido(buscarNombreVariables(this.contenido.getContenido()));
            //ADJUNTOTEXT 
            // new JSONArray(contenido.getJSONObject(ADJUNTOTEXT).getString(ENUM)).getString(0);
            adjuntoTexto = new VariableValor(new JSONArray(contenido.getJSONObject(ADJUNTOTEXT).getString(ENUM)).getString(0));
            adjuntoTexto.setVariablesContenido(null);
            //ADJUNTOOPCION 
            JSONObject dato4 =  new JSONObject(contenido.getJSONObject(ADJUNTOOPCION).getString(ITEMS));
            JSONArray adjuntoOpcionJsonArray = null;
            adjuntoOpcionJsonArray = new JSONArray(dato4.getString(ENUM));
            adjuntoOpcion = new ArrayList<>();
            if(adjuntoOpcionJsonArray != null){
             for(int i =0; i < adjuntoOpcionJsonArray.length(); i++){
                  adjuntoOpcion.add(adjuntoOpcionJsonArray.get(i).toString());
                }   
            }
            //ADICIONAL 
            adicional = new VariableValor(new JSONArray(contenido.getJSONObject(ADICIONAL).getString(ENUM)).getString(0));
            adicional.setVariablesContenido(buscarNombreVariables(adicional.getContenido()));
            //TITULOSOLICITANTE
            tituloSolicitante = new VariableValor(new JSONArray(contenido.getJSONObject(TITULOSOLICITANTE).getString(ENUM)).getString(0));
            tituloSolicitante.setVariablesContenido(null);
            
        } catch (JSONException ex) {
            Logger.getLogger(ContentSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ArrayList<DatoValor> buscarNombreVariables(String data){
        ArrayList<DatoValor> nombVariableValor = new ArrayList<>();
        int inicio = 0, fin = 0;
        boolean bandera =true;
        while(bandera){
            inicio = data.indexOf("@");
            fin = data.indexOf("@",inicio+1);
            if(inicio == -1 || fin == -1 )
            {bandera=false;break;}
            else{
            nombVariableValor.add(new DatoValor(data.substring(inicio+1,fin),""));
            data = data.substring(fin+1,data.length());
            }
        }
        return nombVariableValor;
    }
    
    
    
    /**
     * @return the dirigidaNombre
     */
    public VariableValor getDirigidaNombre() {
        return dirigidaNombre;
    }

    /**
     * @param dirigidaNombre the dirigidaNombre to set
     */
    public void setDirigidaNombre(VariableValor dirigidaNombre) {
        this.dirigidaNombre = dirigidaNombre;
    }

    /**
     * @return the dirigidaTitulo
     */
    public VariableValor getDirigidaTitulo() {
        return dirigidaTitulo;
    }

    /**
     * @param dirigidaTitulo the dirigidaTitulo to set
     */
    public void setDirigidaTitulo(VariableValor dirigidaTitulo) {
        this.dirigidaTitulo = dirigidaTitulo;
    }

    /**
     * @return the contenido
     */
    public VariableValor getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(VariableValor contenido) {
        this.contenido = contenido;
    }

    /**
     * @return the adjuntoTexto
     */
    public VariableValor getAdjuntoTexto() {
        return adjuntoTexto;
    }

    /**
     * @param adjuntoTexto the adjuntoTexto to set
     */
    public void setAdjuntoTexto(VariableValor adjuntoTexto) {
        this.adjuntoTexto = adjuntoTexto;
    }

    /**
     * @return the adjuntoOpcion
     */
    public ArrayList<String> getAdjuntoOpcion() {
        return adjuntoOpcion;
    }

    /**
     * @param adjuntoOpcion the adjuntoOpcion to set
     */
    public void setAdjuntoOpcion(ArrayList<String> adjuntoOpcion) {
        this.adjuntoOpcion = adjuntoOpcion;
    }

    /**
     * @return the adicional
     */
    public VariableValor getAdicional() {
        return adicional;
    }

    /**
     * @param adicional the adicional to set
     */
    public void setAdicional(VariableValor adicional) {
        this.adicional = adicional;
    }

    /**
     * @return the tituloSolicitante
     */
    public VariableValor getTituloSolicitante() {
        return tituloSolicitante;
    }

    /**
     * @param tituloSolicitante the tituloSolicitante to set
     */
    public void setTituloSolicitante(VariableValor tituloSolicitante) {
        this.tituloSolicitante = tituloSolicitante;
    }
    
    public void obtenerVariables(){

    }
    
}
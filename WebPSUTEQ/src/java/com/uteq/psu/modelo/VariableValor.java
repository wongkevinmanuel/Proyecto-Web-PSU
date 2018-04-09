package com.uteq.psu.modelo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Kevin Onofre
 */
public class VariableValor implements  Serializable{
    private String contenido;
    private ArrayList<DatoValor> variablesContenido=new ArrayList<>();

    public VariableValor() {
    }

    public VariableValor(String contenido) {
        this.contenido = contenido;
    }
    
    /*
    Propiedades
    */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setVariablesContenido(ArrayList<DatoValor> variablesContenido) {
        this.variablesContenido = variablesContenido;
    }

    public String getContenido() {
        return contenido;
    }

    public ArrayList<DatoValor> getVariablesContenido() {
        return variablesContenido;
    }
    
    
}

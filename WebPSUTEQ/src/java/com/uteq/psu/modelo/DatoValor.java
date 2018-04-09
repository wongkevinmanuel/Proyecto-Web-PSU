package com.uteq.psu.modelo;

/**
 *
 * @author Kevin Onofre
 */
public class DatoValor {
    private String nombreVariable;
    private String valorVariable;

    public DatoValor() {
    
    }
    
    public DatoValor(String nombreVariable, String valorVariable) {
        this.nombreVariable = nombreVariable;
        this.valorVariable = valorVariable;
    }
    
    
    
    
    
    
    
    
    //Propiedades
    public void setNombreVariable(String nombreVariable) {
        this.nombreVariable = nombreVariable;
    }

    public void setValorVariable(String valorVariable) {
        this.valorVariable = valorVariable;
    }
    
    public String getNombreVariable() {
        return nombreVariable;
    }

    public String getValorVariable() {
        return valorVariable;
    }
    
}

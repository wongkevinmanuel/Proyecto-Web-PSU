/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uteq.psu.controler;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.outputlabel.OutputLabel;
/**
 *
 * @author Kevin Onofre
 */
@Named(value = "manegadorInterfaz")
@SessionScoped
public class ManegadorInterfaz implements Serializable {

    private UIComponent found;
    
    public ManegadorInterfaz() {
    }
    
    public void cargarVariables(){
        
        HtmlPanelGroup div = new HtmlPanelGroup();
        div.setLayout("block");
        
        //HtmlInputText entradaVariable  = new HtmlInputText();
        //entradaVariable.setValue("herrrr");
        //div.getChildren().add(entradaVariable);
        
        //Calendar a=new Calendar();
        //a.setId("fechaInicio");
        //div.getChildren().add(a);
        
        for (int i = 0; i <= 1 ; i++) {
            Calendar b = new Calendar();
            b.setId("fechainicio"+i);
            
            OutputLabel text = new OutputLabel();
            text.setId("text"+i);
            text.setValue("Fecha inicio "+ i + ":");
            
            div.getChildren().add(text);
            div.getChildren().add(b);
        }
        
        //doFind(FacesContext.getCurrentInstance(), "tiles");
        doFind(FacesContext.getCurrentInstance(), "form");
        found.getChildren().add(div);
    }
    
    private void doFind(FacesContext context, String clientId){
       FacesContext.getCurrentInstance().getViewRoot()
               .invokeOnComponent(context, 
                       clientId, 
                       new ContextCallback() {
           @Override
           public void invokeContextCallback(FacesContext context, UIComponent 
                   componet) {
               found = componet;
           }
       });
    
    }
    public void mensaje(){
         FacesContext context = FacesContext.getCurrentInstance();
         Map<String, String> request = context.getExternalContext().getRequestParameterMap();
         try {
                String data = request.get("form"+NamingContainer.SEPARATOR_CHAR+"fechainicio0_input");
                context.addMessage("form" , new FacesMessage("Valor de variable:"+data));             
        } catch (Exception e) {//form:fechainicio0_input
             context.addMessage("form" , new FacesMessage("Error:"+e));             
        }
         
    }
}

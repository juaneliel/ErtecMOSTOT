package converter;
 

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import mbean.mb_Cliente;
import mbean.mb_Funcionario;
import mbean.mb_Usuario;
import model.Funcionario;
 
@FacesConverter("ConverterFuncionario")
public class ConverterFuncionario implements Converter {

  @Inject
  private mb_Usuario service;
  
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	Funcionario salida=null;
        if(value != null && value.trim().length() > 0) {
            try { 
            	mb_Usuario service = (mb_Usuario) 
            	    fc.getExternalContext().getSessionMap().get("mb_Usuario"); 
                System.out.println("funcionario converter: "+service);
                if(service==null){
                  return salida;
                }
                ArrayList<Funcionario> auxL= service.getListaFuncionariosOBJ();
                System.out.println("converter func "+auxL);
                if(auxL==null){
                	return salida;
                }                
                for (Funcionario o : auxL) {
                        String id =  Integer.toString( o.getFuncionarioID());
                    if (value.equals(id)) {
                        salida = o;
                        break;
                    }
                }
                
                return salida;                 
                
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "No es un valor numerico."));
            }
        }
        return salida;
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Funcionario) object).getFuncionarioID());
        }
        else {
            return null;
        }
    }   
} 
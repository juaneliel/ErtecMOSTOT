package converter;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import mbean.mb_Articulo;
import mbean.mb_OT;
import mbean.mb_Usuario;
import model.Articulo;
 
@FacesConverter("ConverterArticulo")
public class ConverterArticulo implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	System.out.println("entro en converterarticulo primero");
    	Articulo salida=null;
        if(value != null && value.trim().length() > 0) {
            try {
            	System.out.println("entro en converterarticulo");
            	mb_Usuario service = (mb_Usuario) 
                		fc.getExternalContext().getSessionMap().get("mb_Usuario"); 
                if (service==null){
                  return null;
                }
                ArrayList<Articulo> auxL=service.getListaArticulosOBJ();  
                if(auxL==null){
                	System.err.println("lista de articulos es nula converterarticulo");
                	return salida;
                }         
                
                for (Articulo o : auxL) {
                        String id =  Integer.toString( o.getArticuloID());
                    if (value.equals(id)) {
                        salida = o;
                        break;
                    }
                }
            } catch(NumberFormatException e) {
                 
            	e.printStackTrace();
            }
        }
        System.out.println("convertido articulo "+salida);
        return salida;
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
    		System.out.println("convertido articulo getAsString");
        if(object != null) {
            return String.valueOf(((Articulo) object).getArticuloID());
        }
        else {
            return null;
        }
    }   
} 
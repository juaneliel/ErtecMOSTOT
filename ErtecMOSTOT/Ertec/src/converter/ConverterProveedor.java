package converter;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import mbean.mb_OT;
import model.Articulo;
import model.Proveedores;
 
@FacesConverter("ConverterProveedor")
public class ConverterProveedor implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	Proveedores salida=null;
        if(value != null && value.trim().length() > 0) {
            try {
                mb_OT service = (mb_OT) 
                		fc.getExternalContext().getSessionMap().get("mb_OT");
                if(service==null){
                  return salida;
                }
                ArrayList<Proveedores> auxL= service.getListaProveedoresOBJ();                
                for (Proveedores o : auxL) {
                        String id =  Integer.toString( o.getProveedorID());
                    if (value.equals(id)) {
                        salida = o;
                        break;
                    }
                }                
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        return salida;        
    }
    
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Proveedores) object).getProveedorID());
        }
        else {
            return null;
        }
    }   
} 
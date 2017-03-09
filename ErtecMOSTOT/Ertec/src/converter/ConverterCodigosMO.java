package converter;

import java.util.ArrayList;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter; 
import javax.faces.convert.FacesConverter; 
import mbean.mb_ManoObra; 
import model.CodigosMO;
 
@FacesConverter("ConverterCodigosMO")
public class ConverterCodigosMO implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	CodigosMO salida=null;
        if(value != null && value.trim().length() > 0) {
            try {
            	mb_ManoObra service = (mb_ManoObra) 
                		fc.getExternalContext().getSessionMap().get("mb_ManoObra");                 
                if(service==null){
                  return salida;
                }
                ArrayList<CodigosMO> auxL=service.getListaCodMO();   
                if(auxL==null){
                	return salida;
                } 
                for (CodigosMO o : auxL) {
                        String id =  Integer.toString( o.getCodigo());
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
        if(object != null) {
            return String.valueOf(((CodigosMO) object).getCodigo() );
        }
        else {
            return null;
        }
    }   
} 
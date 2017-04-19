package converter;


import java.util.ArrayList;
 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter; 
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import mbean.mb_Cliente;
import mbean.mb_Usuario;
import model.Cliente;
import model.UsuarioLogin;
import model.VentaContado;
 
@FacesConverter("ConverterVC")
public class ConverterVC implements Converter {
  
//  @Inject
//  private mb_Cliente service;
 
  @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	VentaContado salida=null;
        if(value != null && value.trim().length() > 0) {
            try {
            	mb_Usuario service = (mb_Usuario) 
                    fc.getExternalContext().getSessionMap().get("mb_Usuario"); 
                if(service==null){
                  return salida;
                }
                ArrayList<VentaContado> auxL=service.getListaVCOBJ();   
                if(auxL==null){
                	return salida;
                } 
                for (VentaContado o : auxL) {
                        String id =  Integer.toString( o.getId());
                    if (value.equals(id)) {
                        salida = o;
                        break;
                    }
                }
                if(salida!=null){
                  //System.out.println("recargar contratos del cliente en converter "+salida.getClienteID()+" "+salida.getNombre());
                }                
            } catch(NumberFormatException e) {                 
            	e.printStackTrace();
            }
        }        
        return salida;
    }
 
  @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            String salida=String.valueOf(((VentaContado) object).getId());
            //System.out.println("getasstring converte: "+salida);
            return salida;
        }
        else {
            return null;
        }
    }   
} 
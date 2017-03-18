package converter;


import java.util.ArrayList;
 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter; 
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import mbean.mb_Cliente; 
import model.Cliente;
import model.UsuarioLogin;
 
@FacesConverter("ConverterCliente")
public class ConverterCliente implements Converter {
  
//  @Inject
//  private mb_Cliente service;
 
  @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    	Cliente salida=null;
        if(value != null && value.trim().length() > 0) {
            try {
                mb_Cliente service = (mb_Cliente) 
                    fc.getExternalContext().getSessionMap().get("mb_Cliente"); 
                System.out.println("converter cliente fc"+fc);
                System.out.println("converter cliente external"+fc.getExternalContext());
                System.out.println("converter cliente map"+fc.getExternalContext().getSessionMap());
                System.out.println("converter cliente service"+service);
                if(service==null){
                  return salida;
                }
                
                
                ArrayList<Cliente> auxL=service.getListaClientesOBJ();   
                if(auxL==null){
                	return salida;
                } 
                for (Cliente o : auxL) {
                        String id =  Integer.toString( o.getClienteID());
                    if (value.equals(id)) {
                        salida = o;
                        break;
                    }
                }
                if(salida!=null){
                  System.out.println("recargar contratos del cliente en converter "+salida.getClienteID()+" "+salida.getNombre());
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
            String salida=String.valueOf(((Cliente) object).getClienteID());
            //System.out.println("getasstring converte: "+salida);
            return salida;
        }
        else {
            return null;
        }
    }   
} 
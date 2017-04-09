package converter;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mbean.mb_Cliente;
import mbean.mb_Usuario;
import model.Cliente;
import model.Contrato;

@FacesConverter("ConverterContrato")
public class ConverterContrato implements Converter {

  @Override
  public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    Contrato salida=null;
      if(value != null && value.trim().length() > 0) {
          try {
              mb_Usuario service = (mb_Usuario) 
                  fc.getExternalContext().getSessionMap().get("mb_Usuario"); 
              System.out.println("converter cliente fc"+fc);
              System.out.println("converter cliente external"+fc.getExternalContext());
              System.out.println("converter cliente map"+fc.getExternalContext().getSessionMap());
              System.out.println("converter cliente service"+service);
              if(service==null){
                return salida;
              }
              ArrayList<Contrato> auxL=service.getListaContratosOBJ();  
              if(auxL==null){
              	return salida;
              } 
              for (Contrato o : auxL) {
                      String id =  Integer.toString( o.getId());
                  if (value.equals(id)) {
                      salida = o;
                      break;
                  }
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
          String salida=String.valueOf(((Contrato) object).getId());
          //System.out.println("getasstring converte: "+salida);
          return salida;
      }
      else {
          return null;
      }
  }   
} 
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import model.Funcionario;
import model.DAO.DAO_Funcionario;

@Stateless
@LocalBean
public class TimerAutomatico {

	
  @Schedule(second = "0", minute = "0", hour = "16", dayOfWeek = "Mon-Sun", dayOfMonth = "*", month = "*", year = "*", info = "Temporizador")
  private void scheduledTimeout(Timer t) {  
    System.out.println("XXXXXminuto mas");
    buscarCarnetDeSalud();
    buscarCumpleaños();
  }
  
  
  private void buscarCumpleaños(){
    ArrayList<Funcionario> lfcumplen=DAO_Funcionario.getFuncionariosCumple(true);
    System.out.println("tamaño de cumpleañeros "+lfcumplen.size());
    if(lfcumplen.size()>0){      
      ArrayList<Funcionario> lfavisar=DAO_Funcionario.getFuncionariosCumple(false);
      System.out.println("tamaño de avisados "+lfavisar.size());
      for(Funcionario f : lfcumplen){     
        if(f.getEmail()!=null&&f.getEmail().contains("@")){
          String deseo="Feliz cumpleaños "+f.getNombre() +" te deseamos tus amigos del trabajo, que la pases muy lindo en tu dia, alegria :D"+"\n\n ERTEC S.A.";
          Mail.sendEmail(f.getEmail(), "Feliz cumple",deseo);
        }
        for (Funcionario r : lfavisar ){          
          if(r.getEmail()!=null&&r.getEmail().contains("@")){
            String aviso="Hoy es el cumpleaños de "+f.getNombre() +", recuerda enviarle saludos :D"+"\n\n ERTEC S.A.";
           // Mail.sendEmail(r.getEmail(), "Cumpleaños",aviso);
          }
        }
      }
    } 
  }
  
  private void buscarCarnetDeSalud(){
    ArrayList<Funcionario> lfcv=DAO_Funcionario.getFuncionariosCarneVencido();
    if(lfcv.size()>0){
      String mensaje= "Se detalla a continuacion los Carné de salud vencidos o proximos a vencer:\n\n";
      for(Funcionario f : lfcv){
        String fechavencimiento= new SimpleDateFormat("dd-MM-yyyy").format(f.getCarneSalud());      
        mensaje+="Nombre: "+f.getNombre()+" , Vencimiento: "+fechavencimiento+"\n";      
        if(f.getEmail()!=null&&f.getEmail().contains("@")){
          String aviso="Su Carné de salud se esta por vencer o ya vencio el dia: "+fechavencimiento+"\n\n ERTEC S.A.";
          Mail.sendEmail(f.getEmail(), "Carne Vencido",aviso);
        }
      }
      mensaje+="\n ERTEC S.A.";
      Mail.sendEmail("elieljuegos@gmail.com", "Listado de Carne Vencidos",mensaje);
      Mail.sendEmail("gperez@ertec.com.uy", "Listado de Carne Vencidos",mensaje);
      Mail.sendEmail("cbazzi@ertec.com.uy", "Listado de Carne Vencidos",mensaje);
    }    
  }
}

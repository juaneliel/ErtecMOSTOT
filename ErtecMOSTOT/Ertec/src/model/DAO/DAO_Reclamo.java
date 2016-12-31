package model.DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery; 
import model.Funcionario;
import model.Reclamo;
import util.JpaUtil;

 
//SELECT count(r.ClienteID), r.clienteID, c.Equipo,  nombreCliente, sum(red),AVG(red) FROM Reclamos r, Contratos c where r.contratoID = c.id and r.estado="visitado"  group by clienteID, nombreCliente,equipo having count(*) > 0




public class DAO_Reclamo {

  
  public boolean add (Reclamo o){
    EntityManager em=JpaUtil.getEntityManager(); 
    boolean salida=false;
    try{      
      em.getTransaction().begin();
      em.persist(o);
      em.getTransaction().commit(); 
      salida = true;
    }
    catch (Exception e){
      e.printStackTrace();
     }finally{ 
          if(em.isOpen() ){
          em.close();
        }   
    }
    return salida;
  }

  public ArrayList<DAO_infoService> getFiltrado(){
    ArrayList<DAO_infoService> salida = new ArrayList<DAO_infoService>();    
    //String consulta="SELECT count(r.ClienteID), r.clienteID, c.equipo,  nombreCliente, sum(red),AVG(red) FROM Reclamo r, Contratos c where r.contratoID = c.id and r.estado='visitado'  group by clienteID, nombreCliente,equipo having count(*) > 0";
    
    
    EntityManager em=JpaUtil.getEntityManager();
   
    
    String consulta = "SELECT count(r.clienteID), r.clienteID, r.nombreCliente, sum(r.red),AVG(r.red), c.equipo,c.tipo,c.contratoID FROM Reclamo r, Contrato c where r.contratoID = c.id and r.estado='visitado' group by r.clienteID, r.nombreCliente,c.equipo,c.tipo,c.contratoID having count(*) > 0";
    
 
    System.out.println(">>>consulta>"+ consulta);
     
    Query q = em.createQuery(consulta);
    
   
        
    List < Object[]>r = (List<Object[]>) q.getResultList();
    
    for (Object[] results : r){       
      String codigo="";
      DAO_infoService aux = new DAO_infoService();
      int i = 0;
      for (Object o : results) {
        String ostring = o.toString();
        if(i==0){
          aux.setVisitas(Long.parseLong(ostring));          
        }
        if(i==2){
          aux.setNombreCliente(ostring);
        }
        if(i==3){
          aux.setHorasRed(Long.parseLong(ostring));     
        }
        
        
        if(i==5){
          aux.setEquipo(ostring);     
        }
        if(i==6){
          codigo=ostring;
        }
        if(i==7){
          codigo+=ostring;
          aux.setCodigo(codigo);  
        }
        
        
        System.out.println(i+"ostring: "+ostring);
        ++i;    
      } 
      
      salida.add(aux);
      
    } 
    return salida;    
  }
  
  
  public boolean delete (Reclamo o){ 
    EntityManager em=JpaUtil.getEntityManager();
    boolean salida = false;
    try{      
      em.getTransaction().begin();
      em.remove(em.contains(o) ? o : em.merge(o)); 
      em.getTransaction().commit();
      salida = true;
    }
    catch (Exception e){
      e.printStackTrace();  
    }finally{ 
          if(em.isOpen() ){
          em.close();
        }   
    }
    return salida;
  }
  
  
  public ArrayList<Reclamo> getListaPendientes (){    
    ArrayList<Reclamo> salida = new ArrayList<Reclamo>();    
    String consulta="SELECT r FROM Reclamo r Where estado = 'pendiente'";
    EntityManager em=JpaUtil.getEntityManager();
    TypedQuery<Reclamo> consultaFuncionario= em.createQuery(consulta, Reclamo.class);
    try{
      System.out.println(">>>consulta>"+ consulta);
      salida =  (ArrayList<Reclamo>) consultaFuncionario.getResultList();
    }
    catch (Exception e){
      e.printStackTrace();
    }finally{ 
          if(em.isOpen() ){
          em.close();
        }   
    }
    return salida; 
  }
  
  
  public ArrayList<Reclamo> getLista (){
    EntityManager em=JpaUtil.getEntityManager();
    TypedQuery<Reclamo> consulta= em.createNamedQuery("Reclamo.findAll", Reclamo.class);
    ArrayList<Reclamo> salida = new ArrayList<Reclamo>();
    try{
      salida= (ArrayList<Reclamo>) consulta.getResultList();
      System.out.println("dao tamaño reclamos "+salida.size());
    }
    catch (Exception e){
      e.printStackTrace();      
    }finally{ 
          if(em.isOpen() ){
          em.close();
        }   
    }
    return salida;
  }
  
  //097980954
  
  public ArrayList<DAO_infoService> filtrarInformeVisitadosPorFechas(Date fechaIni, Date fechaFin,boolean buscarVisitado){
    
    String consulta = "SELECT count(r.clienteID), r.clienteID, r.nombreCliente, sum(r.red),AVG(r.red), c.equipo,c.tipo,c.contratoID "
        + "FROM Reclamo r, Contrato c where r.contratoID = c.id";          
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String fi = "";
    String ff="";
    if(fechaIni!=null){
      fi = formatter.format(fechaIni); 
    }
    
    if (fechaFin!=null){
      ff = formatter.format(fechaFin); 
    }
    else{
      ff = formatter.format(new Date()); 
    }
    
    String fechaCondicion="";
    
    if(!fi.equals("")){
      fechaCondicion = " and r.fechaVisita >= '" + fi + "' AND r.fechaVisita <= '" + ff + "'" ;
    }
    else{  
      fechaCondicion = " and r.fechaVisita <= '" + ff + "' " ;         
    }    
    consulta +=fechaCondicion;
    
    
    if(buscarVisitado){
      consulta+= " and r.estado='visitado' ";
    }
    else{
      consulta+= " and r.estado<>'visitado' ";
    }
    
    
    consulta += " group by r.clienteID, r.nombreCliente,c.equipo,c.tipo,c.contratoID having count(*) > 0";
    
    
    ArrayList<DAO_infoService> salida = new ArrayList<DAO_infoService>();    
    //String consulta="SELECT count(r.ClienteID), r.clienteID, c.equipo,  nombreCliente, sum(red),AVG(red) FROM Reclamo r, Contratos c where r.contratoID = c.id and r.estado='visitado'  group by clienteID, nombreCliente,equipo having count(*) > 0";
    
    
    EntityManager em=JpaUtil.getEntityManager();      
     
    System.out.println(">>>consulta>"+ consulta);     
    Query q = em.createQuery(consulta);            
    List < Object[]>r = (List<Object[]>) q.getResultList();
    
    for (Object[] results : r){       
      String codigo="";
      DAO_infoService aux = new DAO_infoService();
      int i = 0;
      for (Object o : results) {
        String ostring = o.toString();
        if(i==0){
          aux.setVisitas(Long.parseLong(ostring));          
        }
        if(i==2){
          aux.setNombreCliente(ostring);
        }
        if(i==3){
          aux.setHorasRed(Long.parseLong(ostring));     
        }
        
        
        if(i==5){
          aux.setEquipo(ostring);     
        }
        if(i==6){
          codigo=ostring;
        }
        if(i==7){
          codigo+=ostring;
          aux.setCodigo(codigo);  
        }
        
        
        System.out.println(i+"ostring: "+ostring);
        ++i;    
      }       
      salida.add(aux);    
    }    
    return salida;
  }
  
  public boolean update (Reclamo o){ 
    boolean salida=false;
    EntityManager em=JpaUtil.getEntityManager();
    try{
      Reclamo aux = em.find(Reclamo.class, o.getId());      
      em.getTransaction().begin();      
      if(o.getCliente()!=null){
        aux.setNombreCliente(o.getCliente().getNombre());
        aux.setClienteID(o.getCliente().getClienteID()); 
      } 
      if(o.getFuncionario()!=null){
        aux.setFuncionarioID(o.getFuncionario().getFuncionarioID()); 
      } 
      else{
        System.out.println("funcionario nulo en update reclamo");
      }
       
      //revisar se debe de hacer con objetos
      aux.setContratoID(o.getContratoID());
      
      
      
      aux.setAntel(o.getAntel());
      aux.setCodigo(o.getCodigo());
      aux.setConmutador(o.getConmutador());
      aux.setContacto(o.getContacto());
      aux.setEnergia(o.getEnergia());
      aux.setEstado(o.getEstado());
      aux.setFechaReclamado(o.getFechaReclamado());
      aux.setFechaVisita(o.getFechaVisita());
      aux.setHoraReclamado(o.getHoraReclamado());
      aux.setMyr(o.getMyr());
      aux.setMyrEstado(o.getMyrEstado());
      aux.setObservaciones(o.getObservaciones());
      aux.setOrdenreparacion(o.getOrdenreparacion());
      aux.setReclamo(o.getReclamo());
      aux.setRed(o.getRed());
      aux.setTelefonos(o.getTelefonos());
      aux.setUrgente(o.getUrgente()); 
      em.getTransaction().commit();
      salida=true;
    }
    catch (Exception e){
      e.printStackTrace();      
    }finally{ 
          if(em.isOpen() ){
          em.close();
        }   
    }
    return salida;  
  } 
  
}

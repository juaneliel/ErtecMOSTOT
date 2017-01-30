package model.DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Contrato;
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
  
  
  public ArrayList<Contrato> filtrarSinVisitar (Date fechaIni, Date fechaFin,boolean visitado){
    // encontrar los contratos cuyo tope de meses haya sido superado es decir no existe
    //una visita tal que r.fechavisita > (fecha actual -topemeses) 
    
  	//cambiar 13 por 0  
//    String consulta =  "SELECT * FROM  Contratos c where c.TopeMesesVisita <> 13 and "
//        + " NOT EXISTS  (SELECT * FROM Reclamos r WHERE r.contratoID = c.id and "; 
//    
    
    String consulta =  "SELECT * FROM  Contratos c where c.TopeMesesVisita <> 13 and "
        + " NOT EXISTS  (SELECT * FROM Reclamos r,Contratos c2 WHERE r.contratoID = c2.id "+
    		" and c2.contratoID = c.contratoID and "; 
    
    
    
    
    
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
    
    consulta += "r.fecha_visita >= DATE_SUB(CURDATE(), INTERVAL c.TopeMesesVisita MONTH)";
    
//    
//    String fechaCondicion="";
//    
//    if(!fi.equals("")){
//      fechaCondicion = " and r.fechaVisita >= '" + fi + "' AND r.fechaVisita <= '" + ff + "'" ;
//    }
//    else{  
//      fechaCondicion = " and r.fechaVisita <= '" + ff + "' " ;         
//    }    
//    consulta +=fechaCondicion;
    
    
    if(visitado){
      consulta+= " and r.estado='visitado' ";
    }
    else{
      consulta+= " and r.estado<>'visitado' ";
    }
    
    consulta += ")" ; 
    
    
    ArrayList<Contrato> salida = new ArrayList<Contrato>();    
    //String consulta="SELECT count(r.ClienteID), r.clienteID, c.equipo,  nombreCliente, sum(red),AVG(red) FROM Reclamo r, Contratos c where r.contratoID = c.id and r.estado='visitado'  group by clienteID, nombreCliente,equipo having count(*) > 0";
    

    EntityManager em=JpaUtil.getEntityManager();      
     
    try {
      System.out.println(">>>daoreclamo consulta filtrarSinVisitar>"+ consulta);     
      Query q = em.createNativeQuery(consulta,Contrato.class);            
      salida= (ArrayList<Contrato>) q.getResultList();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      System.out.println("mendoza");
      e.printStackTrace();
    }
    

    System.out.println("dao tamanio sinvisitas " +salida.size() );
    
    return salida;
    
    
    
  }
  
  
  
  public ArrayList<DAO_infoService> filtrarInformeVisitadosPorFechas(Date fechaIni, Date fechaFin,boolean buscarVisitado){
    
    String consulta =  "SELECT count(r.clienteID), r.clienteID, r.nombreCliente, sum(r.red),AVG(r.red+r.antel+r.conmutador),"
        + " c.equipo,c.tipo,c.contratoID,max( DATEDIFF(r.fechaVisita,r.fechaReclamado) ),sum(r.antel),sum(r.conmutador) "
        + "FROM Reclamo r, Contrato c where r.contratoID = c.id ";
 
        
                 
    
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
    
    try{
      EntityManager em=JpaUtil.getEntityManager();      
       
      System.out.println(">>>consulta>"+ consulta);     
      Query q = em.createQuery(consulta);            
      List < Object[]>r = (List<Object[]>) q.getResultList();
      
      for (Object[] results : r){ 
        DAO_infoService aux = new DAO_infoService();
        
        aux.setTiempoHoras(Double.parseDouble(results[4].toString()));
        aux.setVisitas(Long.parseLong(results[0].toString()));          
        aux.setNombreCliente(results[2].toString());
        aux.setHorasRed(Long.parseLong(results[3].toString()));  
        aux.setEquipo(results[5].toString());     
        aux.setCodigo(results[6].toString()+results[7].toString());    
        aux.setTiempoRespuesta(Long.parseLong(results[8].toString()));  
        aux.setHorasAntel(Long.parseLong(results[9].toString()));  
        aux.setHorasConmutador(Long.parseLong(results[10].toString()));  
         
        salida.add(aux);
        
      } 
    }
    catch(Exception e){
      e.printStackTrace();
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
























//
//
//
//
//public ArrayList<Contrato> filtrarSinVisitar (Date fechaIni, Date fechaFin,boolean visitado){
//  // buscar aquellos contratos con 0 visitas en el periodo de fechas dado   
//  
//  String consulta =  "SELECT c FROM  Contrato c where r.topeMesesVisita <> 13 and "
//      + " NOT EXISTS  (SELECT r FROM Reclamo r WHERE r.contratoID = c.id  "; 
//  
//  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//  String fi = "";
//  String ff="";
//  if(fechaIni!=null){
//    fi = formatter.format(fechaIni); 
//  }
//  
//  if (fechaFin!=null){
//    ff = formatter.format(fechaFin); 
//  }
//  else{
//    ff = formatter.format(new Date()); 
//  }
//  
//  String fechaCondicion="";
//  
//  if(!fi.equals("")){
//    fechaCondicion = " and r.fechaVisita >= '" + fi + "' AND r.fechaVisita <= '" + ff + "'" ;
//  }
//  else{  
//    fechaCondicion = " and r.fechaVisita <= '" + ff + "' " ;         
//  }    
//  consulta +=fechaCondicion;
//  
//  
//  if(visitado){
//    consulta+= " and r.estado='visitado' ";
//  }
//  else{
//    consulta+= " and r.estado<>'visitado' ";
//  }
//  
//  consulta += ")" ; 
//  
//  
//  ArrayList<Contrato> salida = new ArrayList<Contrato>();    
//  //String consulta="SELECT count(r.ClienteID), r.clienteID, c.equipo,  nombreCliente, sum(red),AVG(red) FROM Reclamo r, Contratos c where r.contratoID = c.id and r.estado='visitado'  group by clienteID, nombreCliente,equipo having count(*) > 0";
//  
//
//  EntityManager em=JpaUtil.getEntityManager();      
//   
//  try {
//    System.out.println(">>>consulta>"+ consulta);     
//    Query q = em.createQuery(consulta);            
//    salida= (ArrayList<Contrato>) q.getResultList();
//  } catch (Exception e) {
//    // TODO Auto-generated catch block
//    e.printStackTrace();
//  }
//  
//
//    
//  return salida;
//  
//  
//  
//}








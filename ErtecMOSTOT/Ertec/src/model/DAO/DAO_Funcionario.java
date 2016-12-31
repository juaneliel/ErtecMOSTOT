package model.DAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager; 
import javax.persistence.TypedQuery;

import model.Funcionario;
import util.JpaUtil;

public class DAO_Funcionario {
	
  private final static String filtrarInexistentes = " nombre <> 'NO EXISTE' and  funcionarioID <> 0 ORDER BY funcionarioID DESC";
	
	public boolean add(Funcionario f){
		EntityManager em=JpaUtil.getEntityManager(); 
		boolean salida=false;
		try{			
			em.getTransaction().begin();
			em.persist(f);
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

	
	
	public ArrayList<Funcionario> completarFuncionario(String buscar){
		ArrayList<Funcionario> salida= new ArrayList<Funcionario>();
		
		
		String consulta="";
		//se verifica si es numerico se busca por el id sino se busca por nombre
		try {
			int idFuncionario = Integer.parseInt(buscar);
			consulta="SELECT fun FROM Funcionario fun Where funcionarioID like '"+idFuncionario+"%' " + " and activo='Si' and "+ filtrarInexistentes;
			
		} catch (NumberFormatException nfe){
			consulta="SELECT fun FROM Funcionario fun Where nombre like  '%"+ buscar+"%' " + " and activo='Si' and "+ filtrarInexistentes;
		}
		
		System.out.println("Consulta: "+consulta);
		EntityManager em=JpaUtil.getEntityManager();
		TypedQuery<Funcionario> query= em.createQuery(consulta, Funcionario.class);
		try{
			System.out.println(">>>consulta completar funcionario: "+ consulta);
			salida =  (ArrayList<Funcionario>) query.getResultList();
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
	
	
	
	
	public boolean update(Funcionario f){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{	
			Funcionario aux = em.find(Funcionario.class, f.getFuncionarioID());			
			
			em.getTransaction().begin();
			 
	    aux.setNombre(f.getNombre());
	    aux.setEmail(f.getEmail());
	    aux.setTelefono(f.getTelefono());
	    aux.setActivo(f.getActivo());
	    aux.setAlias(f.getAlias());
	    aux.setArea(f.getArea());
	    aux.setCat(f.getCat());
	    aux.setDireccion(f.getDireccion()); 
			aux.setNacimiento(f.getNacimiento());
			aux.setCarneSalud(f.getCarneSalud());
			aux.setCedula(f.getCedula());
			aux.setVencimientoCedula(f.getVencimientoCedula());
			aux.setLibretaCat(f.getLibretaCat());
			aux.setVencimientoLibreta(f.getVencimientoLibreta());
			
			
			//em.flush();
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
	
	public boolean delete(Funcionario f){ 
		EntityManager em=JpaUtil.getEntityManager();
		boolean salida = false;
		try{			
			em.getTransaction().begin();
			em.remove(em.contains(f) ? f : em.merge(f));
			//em.flush();
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
	

	
	
	public ArrayList<Funcionario> getListaFuncionarios(){
	  return getListaFuncionariosActivos(true);
	}
	
	public ArrayList<Funcionario> getListaHistoricaFuncionarios(){
    return getListaFuncionariosActivos(false);
  }
	
	
	public ArrayList<Funcionario> getListaFuncionariosActivos(boolean activos){
	  ArrayList<Funcionario> salida= new ArrayList<Funcionario>();    
    String consulta="SELECT fun FROM Funcionario fun Where "; 
    if(activos){
      consulta+="activo='Si' and ";
    }
    consulta+=filtrarInexistentes;
    
    System.out.println(">>>consulta listafuncionariosactivos: >"+ consulta);
    EntityManager em=JpaUtil.getEntityManager();
    TypedQuery<Funcionario> query= em.createQuery(consulta, Funcionario.class);
    try{      
      salida =  (ArrayList<Funcionario>) query.getResultList();
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
	
	public static ArrayList<Funcionario> getFuncionariosCarneVencido(){
	  ArrayList<Funcionario> salida = new ArrayList<Funcionario>();
	  
	  Date fecha= new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha); // Configuramos la fecha que se recibe
    calendar.add(Calendar.DAY_OF_YEAR, -9);  
    fecha= calendar.getTime(); 
	  
    String fechaComparar= new SimpleDateFormat("yyyy-MM-dd").format(fecha);   
    
    
    
	  String consulta="SELECT fun FROM Funcionario fun Where '"+fechaComparar + "' > fun.carneSalud and fun.activo = 'Si' " + " and "+ filtrarInexistentes;

    EntityManager em=JpaUtil.getEntityManager();
    TypedQuery<Funcionario> consultaFuncionario= em.createQuery(consulta, Funcionario.class);
    try{
      System.out.println(">>>consulta funcionarioscarnevencidos>"+ consulta);
      salida =  (ArrayList<Funcionario>) consultaFuncionario.getResultList();
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
	
	public static ArrayList<Funcionario> getFuncionariosCumple(boolean buscarCumpleañeros){
    ArrayList<Funcionario> salida = new ArrayList<Funcionario>();
    
    Calendar calendar = Calendar.getInstance();
    int mes = calendar.get(Calendar.MONTH)+1;
    int dia = calendar.get(Calendar.DAY_OF_MONTH); 
    
    String condicion=  "MONTH(nacimiento) <> "+ mes +" OR DAY(nacimiento) <> " + dia;
    if(buscarCumpleañeros){
      condicion=  "MONTH(nacimiento) = "+ mes +" AND DAY(nacimiento) = " + dia;
    }
    
    String consulta="SELECT fun FROM Funcionario fun Where "+ condicion +" and fun.activo = 'Si' " + " and "+ filtrarInexistentes;

    EntityManager em=JpaUtil.getEntityManager();
    TypedQuery<Funcionario> consultaFuncionario= em.createQuery(consulta, Funcionario.class);
    try{
      System.out.println(">>>consulta funcionariocumple>"+ consulta);
      salida =  (ArrayList<Funcionario>) consultaFuncionario.getResultList();
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
	 
	
	public ArrayList<Funcionario> findFuncionario(Funcionario f){
	  
	  ArrayList<Funcionario> salida = new ArrayList<Funcionario>();
	  String consulta="SELECT fun FROM Funcionario fun Where ";
	  int contador=0;
	  
	  if (  f.getFuncionarioID()!=0){     
	    consulta+= " funcionarioID= '"+f.getFuncionarioID()+"' ";
	    ++contador;
	  }
	  
	  if (f.getNombre()!=null && !f.getNombre().equals("")){
	    if(contador>0){
	      consulta+=" AND ";
	    }
	    consulta+= " nombre like '%"+f.getNombre()+"%' ";
	    ++contador;
	  }
	  
	  if (f.getEmail()!=null && !f.getEmail().equals("")){
	    if(contador>0){
	      consulta+=" AND ";
	    }
	    consulta+= " email like '"+f.getEmail()+ "%' ";
	    ++contador;
	  }
	  if (f.getTelefono()!=null && !f.getTelefono().equals("")){
	    if(contador>0){
	      consulta+=" AND ";
	    }
	    consulta+= " telefono= "+f.getFuncionarioID();
	    ++contador;
	  } 
	  if (contador==0){
	    consulta = "SELECT fun FROM Funcionario fun";
	  }
	
	  System.out.println("Consulta: "+consulta);
	  EntityManager em=JpaUtil.getEntityManager();
	  TypedQuery<Funcionario> consultaFuncionario= em.createQuery(consulta, Funcionario.class);
	  try{
	    System.out.println(">>>consulta>"+ consulta);
	    salida =  (ArrayList<Funcionario>) consultaFuncionario.getResultList();
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









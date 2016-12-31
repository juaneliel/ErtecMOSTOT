package controller;

 
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Ot ;
import model.DAO.DAO_ManoObra;

 

public class otc {

	public static void main(String[] args) {
		 
try{
	Timestamp stamp = new Timestamp(System.currentTimeMillis());
	  Date date = new Date(stamp.getTime());
	  System.out.println(date);

	 
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String format = formatter.format(date);
	  System.out.println(format);
	  
	  
}
catch(Exception e){
	e.printStackTrace();
	
}
		
		 		
	}

}

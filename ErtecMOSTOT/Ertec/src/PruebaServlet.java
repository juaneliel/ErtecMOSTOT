

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import mbean.mb_Articulo;
import model.UsuarioLogin;
import util.ExportMovimientoPDF;
import util.ExportarFichaFuncionario;
import util.ExportarOTPDF;



@WebServlet("/exportarpdf")
public class PruebaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogin user;     

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PruebaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
      response.setContentType("application/pdf");
//     try {
//          //request.getParameter("otID");
//        
//          // step 1
//          Document document = new Document();
//          // step 2
//          PdfWriter.getInstance(document, response.getOutputStream());
//          // step 3
//          document.open();
//          // step 4
//          document.add(new Paragraph("Hello World"));
//          document.add(new Paragraph(new Date().toString()));
//          // step 5
//          document.close(); 
//      } catch (DocumentException de) {
//          throw new IOException(de.getMessage());
//      } 
      
      
    String id=request.getParameter("id");
    System.out.println(id);    
    String tipo=request.getParameter("tipo");

    
    String path = request.getRequestURI();
   // String split_path[] = path.split("/");
   // path = request.getRealPath(split_path[0]);
    String imagePath="\\resources\\images\\letterHead.jpg";
    //Image image = Image.getInstance(path+imagePath);
    System.out.println("path "+path+" "+id);
    
    
    if(user.estaLogueado()&& Integer.parseInt(id)>0){
      if (tipo.equals("ot")){
		  	ExportarOTPDF.ExportarPDF(Integer.parseInt(id),response.getOutputStream()); 
		  	System.out.println("user export pdf OT "+user.getNombre());
		  }
		  else{
		    if (tipo.equals("mov")){ 			    	
	    		ExportMovimientoPDF.ExportarPDF(Integer.parseInt(id),response.getOutputStream()); 
	      	System.out.println("user export pdf mov "+user.getNombre());
		    }
		    else{
			    if (tipo.equals("fun")){ 			    	
		    		ExportarFichaFuncionario.ExportarPDF(Integer.parseInt(id),response.getOutputStream()); 
		      	System.out.println("user export pdf fun "+user.getNombre());
			    }
			  }   	
		  }   	
    }
    else{
    	//ExportMovimientoPDF.ExportarPDF(Integer.parseInt(idOT),response.getOutputStream());     	
    }	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

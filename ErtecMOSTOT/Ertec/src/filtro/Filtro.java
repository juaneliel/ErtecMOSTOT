package filtro;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuarioLogin;



@WebFilter("*.xhtml")
public class Filtro implements Filter {

	@Inject
	private UsuarioLogin user;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		String url=request.getRequestURL().toString().toLowerCase();
		boolean esRecurso= url.indexOf("/javax.faces.resource/")!=-1;
		//boolean esLogin=url.endsWith("/login.xhtml");
		String paginaLogin=request.getContextPath() + "/login.xhtml";
		String paginaSinAcceso=request.getContextPath() + "/paginas/sinacceso.xhtml";
		boolean esLogin=url.endsWith(paginaLogin.toLowerCase());
		boolean logueado=user.estaLogueado();		
		
		//System.out.println("URL "+url + " "+paginaLogin);
		
		
		
		if( !logueado && !esLogin && !esRecurso  ){
			//user.setPaginaReingreso(url);
			response.sendRedirect(paginaLogin);
			//System.out.println("<<<<<loguear en dofileter");
		}
		else{
			//esta logueado se verifica si tiene acceso de ver la pagina
			if(!tieneAcceso(url,user)){
				response.sendRedirect(paginaSinAcceso); 
			}			
			//System.out.println(">>>>>>no redirigir dofilter");
			chain.doFilter(req, res);
		}		
	}

	private boolean tieneAcceso(String url,UsuarioLogin user){
		return user.accesoVerUrl(url); 
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

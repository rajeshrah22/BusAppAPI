package app.routes;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.exceptions.CacheInitializationException;
import app.service.CacheManagerService;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BaseServlet() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	System.out.println("baseinit");
    	ServletContext context = getServletContext();
    	
    	CacheManagerService cacheManagerService = (CacheManagerService) context.getAttribute("cacheManagerService");
    	
    	if (cacheManagerService == null) {
    		try {
				cacheManagerService = CacheManagerService.createCacheManagerService();
			} catch (CacheInitializationException e) {
				System.out.println(e);
			}
    	}
    	
    	
    	context.setAttribute("cacheManagerService", cacheManagerService);
    	System.out.println("CacheManagerService: " + context.getAttribute("cacheManagerService"));
    }
}

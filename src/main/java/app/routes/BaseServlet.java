package app.routes;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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

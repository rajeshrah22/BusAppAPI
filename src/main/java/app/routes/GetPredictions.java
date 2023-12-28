package app.routes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;	
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * Servlet implementation class GetPredictions
 */
@WebServlet("/GetPredictions")
public class GetPredictions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public GetPredictions() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}

package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

/**
 * Servlet implementation class GetRouteConfig
 */
@WebServlet("/GetRouteConfig")
public class GetRouteConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRouteConfig() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String agencyTag = request.getParameter("agencyTag");
		String routeTag = request.getParameter("routeTag");
		try {
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=routeConfig&a=" + agencyTag + "&r=" + routeTag);
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

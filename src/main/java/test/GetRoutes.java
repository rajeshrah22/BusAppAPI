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
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Servlet implementation class GetRoutes
 */
@WebServlet("/GetRoutes")
public class GetRoutes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRoutes() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String tag = request.getParameter("tag");
		
		try {
			//gets inputStream from URL
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=agencyList");
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			
			while (reader.hasNext()) {
				
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

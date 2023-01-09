package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

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
 * Servlet implementation class XmlFetchingParsing
 */
@WebServlet("/XmlFetchingParsing")
public class XmlFetchingParsing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArrayList<Agency> agencyList = new ArrayList<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XmlFetchingParsing() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			//gets inputStream from URL
			URL url = new URL("https://retro.umoiq.com/service/publicXMLFeed?command=agencyList");
			InputStream stream = url.openStream();
			
			//StaxParser Setup
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(stream);
			out.print("<Table>");
			out.print("<TR>");
				out.print("<TD>");
				out.print("Agency Tag");
				out.print("</TD>");
				out.print("<TD>");
				out.print("Title");
				out.print("</TD>");
				out.print("<TD>");
				out.print("regionTitle");
				out.print("</TD>");
				out.print("<TD>");
				out.print("Show Routes");
				out.print("</TD>");
			
			out.print("</TR>");
			while (reader.hasNext()) {
				
				XMLEvent nextEvent = reader.nextEvent();
				if (nextEvent.isStartElement()) {
					System.out.println("\n----------");
					out.print("<TR>");
					StartElement startElm = nextEvent.asStartElement();
					//if element is not an agency, then skip
					if (!startElm.getName().getLocalPart().equals("agency")) {
						continue;
					}
					
					String agencyTag = startElm.getAttributeByName(new QName("tag")).getValue();
					String title = startElm.getAttributeByName(new QName("title")).getValue();
					String regionTitle = startElm.getAttributeByName(new QName("regionTitle")).getValue();
					String shortTitleText = "";
					
					
					out.print("<TD>" + agencyTag + "</TD> <TD>" + title + "</TD><TD>" + regionTitle+"</TD>");
					Attribute shortTitle = startElm.getAttributeByName(new QName("shortTitle"));
					if (shortTitle != null) {
						shortTitleText = shortTitle.getValue();
							System.out.print("<br>shortTitle: " + shortTitleText);
					}
					out.print("<TD><input type=\"button\" value=\"ShowRoute\" onClick=\"showRoute('" + agencyTag +"')\"></TD>");
					out.print("</TR>");
					
					Agency agency = new Agency(agencyTag, regionTitle, title, shortTitleText);
					agencyList.add(agency);
				}
			}
			
			out.print("</Table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}

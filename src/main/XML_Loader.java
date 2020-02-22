package main;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class XML_Loader {
	public static boolean xmlread() throws ParserConfigurationException, SAXException {
	boolean proceed = false;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
	factory.setIgnoringComments(true); 
	factory.setIgnoringElementContentWhitespace(true); 
	DocumentBuilder builder = factory.newDocumentBuilder(); 
	File fichero = new File("C:\\DAM\\resources\\inicio.XML");
	try {
	Document doc = builder.parse(fichero); 
	Node raiz = doc.getFirstChild();
	doc.getDocumentElement().normalize();
	NodeList nodelist = doc.getElementsByTagName("USUARIOS");
	for(int i = 0; i < nodelist.getLength(); i++) {
		  Node node = nodelist.item(i);
		  if(node.getNodeType() == Node.ELEMENT_NODE) {
		    Element element = (Element) node;
		    UserEngine.DB_USUARIOS = element.getElementsByTagName("DIRECCION").item(0).getTextContent();
		    UserEngine.USUARIOS_USER  = element.getElementsByTagName("USUARIO").item(0).getTextContent();
		    UserEngine.USUARIOS_PASS = element.getElementsByTagName("PASS").item(0).getTextContent();
		    //escribir en el xml
		  }
		}
	nodelist = doc.getElementsByTagName("PELICULAS");
	for(int i = 0; i < nodelist.getLength(); i++) {
		Node node = nodelist.item(i);
		  if(node.getNodeType() == Node.ELEMENT_NODE) {
		    Element element = (Element) node;
		    FilmEngine.DB_PELICULAS = element.getElementsByTagName("DIRECCION").item(0).getTextContent();
		    FilmEngine.PELICULAS_USER = element.getElementsByTagName("USUARIO").item(0).getTextContent();
		    FilmEngine.PELICULAS_PASS = element.getElementsByTagName("PASS").item(0).getTextContent();
		    //escribir en el xml
			}
		  }
	System.out.println("Exito.");
	proceed = true;
	}
	catch(Exception e) {
	proceed = false;
		//error archivo inexistente
				System.out.println("No se encontró el archivo de configuración");
				//System.out.println("Se ha creado un archivo vacío en \"C:\\DAM\\.");
				System.out.println("Escriba una configuración válida y prueba de nuevo.");
	}
	return proceed;
}
}
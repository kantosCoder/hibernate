package main;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javassist.convert.Transformer;
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
			}
		  }
	System.out.println("Exito.");
	proceed = true;
	//escribo los xml con la informacion que cargará hibernate
	XMLWriter("src\\hibernate.cfg.xml",FilmEngine.DB_PELICULAS,FilmEngine.PELICULAS_USER,FilmEngine.PELICULAS_PASS);
	XMLWriter("src\\hibernate2nd.cfg.xml",UserEngine.DB_USUARIOS,UserEngine.USUARIOS_USER,UserEngine.USUARIOS_PASS);
	}
	catch(Exception e) {
	proceed = false;
		//error archivo inexistente
				System.out.println("No se encontró el archivo de configuración");
				System.out.println("Escriba una configuración válida y prueba de nuevo.");
	}
	return proceed;
}
	private static void XMLWriter(String path, String db, String user, String pass) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path);

			// Get the root element
			Node company = doc.getFirstChild();
			Node node = doc.getElementsByTagName("property").item(0);
			node.setTextContent("com.mysql.jdbc.Driver");
			node = doc.getElementsByTagName("property").item(1);
			node.setTextContent(db);
			node = doc.getElementsByTagName("property").item(2);
			node.setTextContent(user);
			node = doc.getElementsByTagName("property").item(3);
			node.setTextContent(pass);
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
			System.out.println("Cargados datos a "+path);

		   } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (TransformerException tfe) {
			tfe.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		}
			 
}
package cal.card.upload;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AppSettings {
    private static String apikey = null;
    private static String directory = null;
    
    public AppSettings() 
    {
        if(loadSettings("settings.xml")) 
            System.out.println("Settings Loaded Successfully");
        else 
        {
            System.out.println("Settings NOT FOUND. Creating a default XML file in the root of this application.");
            createSettings();
        }
    }
    
    public static boolean loadSettings(String filename) {
        try 
        {
            File fXmlFile = new File(filename);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            
            doc.getDocumentElement().normalize();
            
            apikey = doc.getDocumentElement().getElementsByTagName("apikey").item(0).getTextContent();
            directory = doc.getDocumentElement().getElementsByTagName("directory").item(0).getTextContent();
    
            return true; 
            
        } catch (Exception e) 
        {
        	System.out.println("Error reading settings file!");
        	System.out.println("Error is: " + e.getMessage());
            return false;
        }
        

    }
    
    public static void createSettings()
    {
        try 
        {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("settings");
			doc.appendChild(rootElement);
	 
			// firstname elements
			Element firstname = doc.createElement("directory");
			firstname.appendChild(doc.createTextNode("/Users/Shared"));
			rootElement.appendChild(firstname);
	 
			// lastname elements
			Element lastname = doc.createElement("apikey");
			lastname.appendChild(doc.createTextNode("dd6b9d2beb614611c5eb9f56c34b743d1d86f385"));
			rootElement.appendChild(lastname);
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("settings.xml"));
	 
			transformer.transform(source, result);
	 
	  } 
      catch (ParserConfigurationException pce) 
      {
    	  System.out.println("Error: Cannot Create Settings File");
          System.exit(0);
	  } 
      catch (TransformerException tfe) 
      {
    	  System.out.println("Error: Cannot Create Settings File");
          System.exit(0);
	  }
        
        loadSettings("settings.xml");
    }
    
    public String getAPIkey() 
    {
        return apikey;
    }
    
    public String getDirectoryLocation() 
    {
        return directory;
    }
    
}

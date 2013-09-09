package cal.card.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFile {
  public static String reportLocation = null;
    
  public static ArrayList load() {
        
        String filename = latestXMLReport(reportLocation);
        ArrayList parsedXML;
        
        if(filename == null) {
            System.out.println("The file is not an XML file");
            parsedXML = null;
            System.exit(0);
        } else {
            parsedXML = parseXMLFile(filename);
        }
        return parsedXML;
        

  }
  
  public static String latestXMLReport(String latestFile) {
      
      File dir = new File(latestFile);
      
      if(dir.listFiles() != null) {
        File[] files = dir.listFiles();

        Arrays.sort(files, new Comparator<File>() {
            @Override
              public int compare(File f1, File f2) {
                  return Long.valueOf(f2.lastModified()).compareTo(
                          f1.lastModified());
              }
          });
        
        String latestReport = files[0].toString();
      
        if(latestReport.endsWith(".xml") != false) {
            return latestReport;
        } else {
            /* NOT THE PROPER FILE TYPE */
            return null;
        }
        
      } else {
          /* NOT A VALID DIRECTORY */
          return null;
      }
      
  }
  
  public static ArrayList parseXMLFile(String latestFile) {
      System.out.println("Begin Parsing File " + latestFile);
     
      try { 
         
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        
        final ArrayList<String[]> students = new ArrayList<String[]>();
        
        DefaultHandler handler;
        handler = new DefaultHandler() {
          
          boolean cardnumber = false;
          boolean lastname = false;
          boolean firstname = false;
          boolean emailaddress = false;
          
          String[] student;

          @Override
          public void startElement(String uri, String localName,String qName, 
                 Attributes attributes) throws SAXException {
                 
                 if (qName.equalsIgnoreCase("RAWDATA")) {
                         student = new String[4];
                 }
              
                 if (qName.equalsIgnoreCase("CUSTOMERPRIMARYCARDNUMBER")) {
                         cardnumber = true;
                 }

                 if (qName.equalsIgnoreCase("LASTNAME")) {
                         lastname = true;
                 }

                 if (qName.equalsIgnoreCase("FIRSTNAME")) {
                         firstname = true;
                 }

                 if (qName.equalsIgnoreCase("EMAILADDRESS")) {
                         emailaddress = true;
                 }

          }

          @Override
          public void endElement(String uri, String localName,
                 String qName) throws SAXException {
                 
                 if (qName.equalsIgnoreCase("RAWDATA")) {
                    students.add(student); 
                 }
                
          }

          @Override
          public void characters(char ch[], int start, int length) throws SAXException {
                 
                 if (cardnumber) {
                    student[0] = new String(ch, start, length);
                    cardnumber = false;
                 }

                 if (lastname) {
                    student[1] = new String(ch, start, length);
                    lastname = false;
                 }

                 if (firstname) {
                    student[2] = new String(ch, start, length);
                    firstname = false;
                 }

                 if (emailaddress) {
                    student[3] = new String(ch, start, length);
                    emailaddress = false;
                 }

          }
        };
        
        saxParser.parse(latestFile, handler);
       
        
        return students;
                      
      } catch (Exception e) {
         return null;
      }

  }
  
  public static void setDirectory(String dir) {
      reportLocation = dir;
  }
  
  
}
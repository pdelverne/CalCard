/* 
 *  Designed, Coded, and Created by: Koury Lape
 * 
 *  Blackboard Cal Card Import to OrgSync
 *      1. Parse XML File
 *      2. Get OrgSync ID from XML File's Email Node using URLConnection
 *      3. Get Cal Card from XML and POST to OrgSync using API and OrgSync ID
 */
package cal.card.upload;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CalCardUpload {
            
	public static void main(String[] args) {
        //convert timestamp to user friendly format
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy 'at' h:mm:ss a");
               
        //set start time application loaded
        Timestamp startTime = getCurrentTimeStamp();
        System.out.println("Program Started: " + sdf.format(startTime));
        
        //retrieve settings and set the apikey and directory
        AppSettings settings;
        settings = new AppSettings();
        ReadXMLFile.setDirectory(settings.getDirectoryLocation());
        URLConnection.setAPIKey(settings.getAPIkey());

        //parse XML by getting the latest XML file in the directory stated
        ArrayList<String[]> parsedXML = ReadXMLFile.load();   
        
        //use email address from XML file to get OSID, then upload CardNumber
        OrgSyncAPI.upload(parsedXML);
        
        //get time program was finished
        Timestamp endTime = getCurrentTimeStamp();
        System.out.println("Program Ended: " + sdf.format(endTime));
        
        //get the total run time in seconds
        long totalRunTime = ((endTime.getTime() - startTime.getTime()) / 1000);     
        System.out.println("Program Ran for: " + totalRunTime + " seconds");
      
    }
    
    public static Timestamp getCurrentTimeStamp() {
        java.util.Date date= new java.util.Date();
        return new Timestamp(date.getTime());
    }


}
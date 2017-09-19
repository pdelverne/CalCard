package cal.card.upload;

import java.util.ArrayList;

public class OrgSyncAPI 
{
    
    public static int usersNotFound = 0;
    public static int successfullyAdded = 0;
    public static int duplicateCards = 0;
        

	public static void upload(ArrayList<String[]> list) 
    {
        if(list != null) 
        {
            for(int i = 0; i < list.size(); i++) 
            {    
                Object[] test = (Object[]) list.get(i);
                try
                {
                	String email_address = test[3].toString().replace("@calu.edu", "@california-university-of-pennsylvania");
                
	                String calcard_number = "0000000000000000";
	                
	                if(test[0] != null)
	                    calcard_number = test[0].toString().substring(6,22);                  
	                
	                System.out.println("Attempting User: " + email_address + " with card number: " + calcard_number);
	                
	                String osid = URLConnection.getOSID(email_address);
	                if(osid != null) 
	                {
	                    int updateStatus = URLConnection.updateCardNumber(osid, calcard_number);
	
	                    switch(updateStatus) 
	                    {
	                        case 200:
	                            successfullyAdded++;
	                            break;
	                        case 422:                            
	                            duplicateCards++;
	                            break;
	                    }   
	                    
	                } 
	                else 
	                    usersNotFound++;  
	            }
                catch(Exception e)
	            {
	            	System.err.println("Error while processing user: \nCalcard number: " + test[0] + "\nName: " + test[2] + " " + test[1] + "\nEmail: " + test[3]);
	            }
                
                	
            }
            
            System.out.println("Users Attempted to Update: " + list.size());
            System.out.println("Users Added Succesfully: " + successfullyAdded);
            System.out.println("Users Not Found: " + usersNotFound);
            System.out.println("Duplicate Card Numbers Found: " + duplicateCards);
        
        } 
        else 
        {
           System.out.println("No Student Data Found!");
           System.exit(0); 
        }

    }
        
}

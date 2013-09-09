package cal.card.upload;

import java.io.*;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class URLConnection {
    
    public static String key = null;
    
    public static String getOSID(String username) {
        try {            
            URL u = new URL("https://api.orgsync.com/api/v2/accounts/username/"+username+"?key="+key);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Accept", "application/json");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(60000);
            c.setReadTimeout(60000);
            c.connect();
            int status = c.getResponseCode();
            String response = c.getResponseMessage();
            
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    JSONObject json;
                    try {
                        json = (JSONObject)new JSONParser().parse(sb.toString());
                        return json.get("id").toString();
                    } catch (ParseException ex) {
                        return null;
                    }
            }
            
            c.disconnect();
            
        } catch (MalformedURLException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return null;
    }
    
    
    public static int updateCardNumber(String osid, String cardnumber) {
        try {
            URL url = new URL("https://api.orgsync.com/api/v2/identification_cards?account_id="+osid+"&number="+cardnumber+"&key="+key);           
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setDoOutput(false);
            c.setRequestMethod("POST");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestProperty("Content-Type", "application/json");
            
            int status = c.getResponseCode();          
            c.disconnect();
            
            return status;
    
        } catch (MalformedURLException ex) {
            return 0;
        } catch (IOException ex) {
            return 0;
        }
    }
    
    public static void setAPIKey(String apikey) {
        key = apikey;
    }
}

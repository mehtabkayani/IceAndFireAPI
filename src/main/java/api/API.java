package api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class API {
   static String API_URL = "https://anapioficeandfire.com/api/";



   public JSONObject getCharacter(int characterId){
       String urlString = API_URL + "characters/" + characterId;
       JSONObject jsonObject = new JSONObject(getRequest(urlString));

       return jsonObject;

   }
    public JSONObject getPovCharacter(String url){
        JSONObject jsonObject = new JSONObject(getRequest(url));
        return jsonObject;

    }


    public List<String> getSwornMembers(JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("swornMembers");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }


    public  List<JSONObject> getBantamBooks(){
        String urlString = API_URL + "books";
        JSONArray jsonArray = new JSONArray(getRequest(urlString));

        List<JSONObject> povBooks = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject book = jsonArray.getJSONObject(i);

            if (book.getString("publisher").equals("Bantam Books")) {
                povBooks.add(book);
            }
        }
        return povBooks;
    }

    public static String getRequest(String str){
        String result = "";
        try {
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            if(con.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String inputLine;
                StringBuffer content = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    content.append(inputLine);
                }
                br.close();

                result = content.toString();



            } else {
                System.out.println("Error");
                System.out.println("Server responded with: " + con.getResponseCode());
            }


        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return result;

    }


   public JSONObject get(String str){
       try {
           URL url = new URL(str);
           HttpURLConnection con = (HttpURLConnection) url.openConnection();
           con.setRequestMethod("GET");


           if(con.getResponseCode() == HttpURLConnection.HTTP_OK){

               InputStreamReader isr = new InputStreamReader(con.getInputStream());
               BufferedReader br = new BufferedReader(isr);
               String inputLine;
               StringBuffer content = new StringBuffer();

               while ((inputLine = br.readLine()) != null) {
                   content.append(inputLine);
               }
               br.close();

               JSONObject json = new JSONObject(content.toString());

               return json;


           } else {
               System.out.println("Error");
               System.out.println("Server responded with: " + con.getResponseCode());
           }


       }
       catch(Exception ex){
           System.out.println(ex.getMessage());
       }
       return null;

   }

}

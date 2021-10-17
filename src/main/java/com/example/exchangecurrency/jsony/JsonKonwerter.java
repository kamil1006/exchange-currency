package com.example.exchangecurrency.jsony;

import com.example.exchangecurrency.entity.MyCurrency;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class JsonKonwerter {

    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    public static String getJSON(String url) {
        HttpsURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();

            con.connect();


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();


        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
    //--------------------------------------------------------------------------
    public   List<MyCurrency> jsonToListCurrencies(String json){

        List<MyCurrency> myCurrencyList = new ArrayList<>();

        //JSONArray obj;
        JSONObject obj;

        try{

            //obj = new JSONArray(json);
            obj = new JSONObject(json);
            //System.out.println(obj);
            Iterator<String> keys = obj.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                if (obj.get(key) instanceof JSONObject) {
                    // do something with jsonObject here
                }
                // System.out.println("valuta: "+key+" nazwa:"+obj.get(key));
                myCurrencyList.add(new MyCurrency(key, obj.get(key).toString()));

            }

        }catch (Exception e){
            return null;
        }
        return myCurrencyList;
    }
    //--------------------------------------------------------------------------
    public Map<String, String> jsonToMap(String json2){

        Map<String,String> mapa = new HashMap<String,String>();
        //JSONArray obj2;
        JSONObject obj2;
        try {

            //obj = new JSONArray(json);
            obj2 = new JSONObject(json2);
            //System.out.println(obj2);
            Iterator<String> keys2 = obj2.keys();

            while (keys2.hasNext()) {
                String key = keys2.next();
                Object obj3 = obj2.get(key);
                if (obj3 instanceof JSONObject) {

                   // System.out.println(obj3);
                    Iterator<String> it3 = ((JSONObject) obj3).keys();

                    while (it3.hasNext()) {
                        Object key3 = it3.next();
                        String value3 = (String) ((JSONObject) obj3).get(key3.toString());
                        //System.out.println(key3 + " ======== " + value3);
                        mapa.put(key3.toString(),value3);
                    }


                }


            }
        }catch (JSONException e) {
                e.printStackTrace();
            }



    return mapa;
    }
        //--------------------------------------------------------------------------

}

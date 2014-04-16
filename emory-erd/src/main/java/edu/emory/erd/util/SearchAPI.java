package edu.emory.erd.util;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;
import java.io.FileInputStream;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 1. To show how to perform a search. 
 * For advanced filter, refer to https://developers.google.com/freebase/v1/search-overview
 * 
 * 2. Before run it, several dependencies should be added to .classpath if eclipse used.
 * E.g, https://developers.google.com/api-client-library/java/apis/freebase/v1 
 * 
 * 3. Need a Freebase Key for server applications. (For testing, you can use my key "AIzaSyB-AbGEhYVuYGlhDJ4_R99DBzqFx1-Zw6Q")
 * 
 * @author Xin Chen modified from from https://developers.google.com/freebase/v1/search-overview
 *
 */

public class SearchAPI {
  public static Properties properties = new Properties();
  public static void main(String[] args) {
    try {
      properties.load(new FileInputStream("FreebaseAPI.properties"));
      HttpTransport httpTransport = new NetHttpTransport();
      HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
      JSONParser parser = new JSONParser();
      GenericUrl url = new GenericUrl("https://www.googleapis.com/freebase/v1/search");
      url.put("query", "Cee Lo Green");
      url.put("filter", "(all type:/music/artist created:\"The Lady Killer\")");
      url.put("limit", "10");
      url.put("indent", "true");
      url.put("key", properties.get("API_KEY"));
      HttpRequest request = requestFactory.buildGetRequest(url);
      HttpResponse httpResponse = request.execute();
      JSONObject response = (JSONObject)parser.parse(httpResponse.parseAsString());
      JSONArray results = (JSONArray)response.get("result");
      for (Object result : results) {
        System.out.println(JsonPath.read(result,"$.name").toString());
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
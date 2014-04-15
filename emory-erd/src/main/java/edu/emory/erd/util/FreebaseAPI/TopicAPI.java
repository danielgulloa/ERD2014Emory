package utils;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;
import java.io.FileInputStream;
import java.util.Properties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TopicAPI {
  public static Properties properties = new Properties();
  public static void main(String[] args) {
    try {
      properties.load(new FileInputStream("FreebaseAPI.properties"));
      HttpTransport httpTransport = new NetHttpTransport();
      HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
      JSONParser parser = new JSONParser();
      String topicId = "/en/bob_dylan";
      GenericUrl url = new GenericUrl("https://www.googleapis.com/freebase/v1/topic" + topicId);
      url.put("key", properties.get("API_KEY"));
      HttpRequest request = requestFactory.buildGetRequest(url);
      HttpResponse httpResponse = request.execute();
      JSONObject topic = (JSONObject)parser.parse(httpResponse.parseAsString());
      System.out.println(JsonPath.read(topic,"$.property['/type/object/name'].values[0].value").toString());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
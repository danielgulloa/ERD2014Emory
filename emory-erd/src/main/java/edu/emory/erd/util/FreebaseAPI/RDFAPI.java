package utils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 * 
 * Not work currently because of RDF contains invalid character escapes.
 * 
 * Details refer to 
 * 1. https://bugs.freebase.com/browse/SITE-1504
 * 2. http://stackoverflow.com/questions/18033043/rdf-java-sample-of-freebase-not-working
 *  
 * @author root
 *
 */


public class RDFAPI {
  public static Properties properties = new Properties();
  public static void main(String[] args) {
    try {
      properties.load(new FileInputStream("FreebaseAPI.properties"));
      String serviceURL = "https://www.googleapis.com/freebase/v1/rdf";
      HttpClient httpclient = new DefaultHttpClient();
      String topicId = "/m/0kpv1_";//"/m/02h40lc";
      String url = serviceURL + topicId + "?key=" + properties.get("API_KEY");
      HttpGet request = new HttpGet(url);
      HttpResponse httpResponse = httpclient.execute(request);
      Model model = ModelFactory.createDefaultModel();
      model.read(httpResponse.getEntity().getContent(), null, "TURTLE");
      Resource topic = model.getResource("http://rdf.freebase.com/ns/" + topicId.substring(1).replace('/','.'));
      Property labelProperty = model.getProperty("http://www.w3.org/2000/01/rdf-schema#label");
      System.out.println(topic.getProperty(labelProperty).getString());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
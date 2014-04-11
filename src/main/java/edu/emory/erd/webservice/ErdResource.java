package edu.emory.erd.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Root resource (exposed at "erd" path)
 */
@Path("erd")
public class ErdResource {

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * return String that will be returned as a text/plain response.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String annotatePost(@FormParam("runID") String runId,
                           @FormParam("TextID") String textId,
                           @FormParam("Text") String text) {
        return "Got it!";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String annotateGet(@QueryParam("runID") String runId,
                              @QueryParam("TextID") String textId,
                              @QueryParam("Text") String text) {
        return "Got it!";
    }
}

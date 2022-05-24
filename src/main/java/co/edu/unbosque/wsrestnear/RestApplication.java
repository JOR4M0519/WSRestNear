package co.edu.unbosque.wsrestnear;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationPath("/api")
public class RestApplication extends Application {
    /*@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(){
        System.out.println("pasa?");
        return Response.ok()
                .entity("hellow")
                .build();
    }*/
}
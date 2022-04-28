package co.edu.unbosque.wsrestnear.resources;
import co.edu.unbosque.wsrestnear.dtos.Collection;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

@Path("/collections")
public class CollectionsIndexResources {

    //Responde como el método Get de la API de esta clase, obtiene las últimas seis colecciones agregadas a la plataforma
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimasCollections() throws IOException {
        UserService userServices = new UserService();
        List<Collection> collections = userServices.getUltimasCollections();
        return Response.ok().entity(collections).build();
    }

}

package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Collection;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("users/{username}/collections/up")
public class CollectionsArtistResources {

    @Context
    ServletContext context;

    //Responde como el método Post de la API de esta clase, recibe como parámetro el nombre del usuario, el nombre de la colección y la cantidad de NFTs en la colección, bajo estos parámetros crea una colección para dicho usuario
    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postCollection(@PathParam("username") String username, @FormParam("collection") String collection, @FormParam("quantity") String quantity) throws IOException {
        UserService userService = new UserService();
        userService.createCollection(username,collection,quantity);
        return Response.ok().entity(null).build();
    }

    //Responde como el método Get de la API de esta clase, recibe como parámetro el nombre del usuario para obtener las colecciones correspondientes a este
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollections(@PathParam("username") String username) throws IOException {
        UserService userService = new UserService();
        List<Collection> col= userService.getCollectionsPorArtista(username);
        return Response.ok().entity(col).build();
    }
}

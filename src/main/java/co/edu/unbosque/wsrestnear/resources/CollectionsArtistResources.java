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

@Path("/users/{username}/collections")
public class CollectionsArtistResources {

    @Context
    ServletContext context;

    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postCollection(@PathParam("username") String username, @FormParam("collection") String collection, @FormParam("quantity") String quantity) throws IOException {
        UserService userService = new UserService();
        userService.createCollection(username,collection,quantity);
        return Response.ok().entity(null).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollections(@PathParam("username") String username) throws IOException {
        UserService userService = new UserService();
        List<Collection> col= userService.getCollectionsPorArtista(username);
        return Response.ok().entity(col).build();
    }
}

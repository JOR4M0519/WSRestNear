package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Collection;
import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("users/{username}/collections")
public class CollectionsArtistResources {

    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollections(@PathParam("username") String username) throws IOException {
        UserService userService = new UserService();
        List<Collection> col= userService.getCollectionsPorArtista(username);
        return Response.ok().entity(col).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postCollection(@PathParam("username") String username, @FormParam("collection") String collection, @FormParam("quantity") String quantity) throws IOException {

        String contextPath =context.getRealPath("") + File.separator;
        try {
            Collection collection1 = new UserService().createCollection(username,collection,"0",contextPath);
            return Response.ok().entity(collection1).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }


    }

    @GET
    @Path("/{collection}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username, @PathParam("collection") String collection) {

        System.out.println(username+collection);
        try {
            UserService userService = new UserService();
            List<Collection> col= userService.getCollectionsPorArtista(username);

            Collection collectionList = col.stream()
                    .filter(u -> u.getCollection().equals(collection))
                    .findFirst()
                    .orElse(null);

            if (collectionList != null) {
                return Response.ok()
                        .entity(collectionList)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
}

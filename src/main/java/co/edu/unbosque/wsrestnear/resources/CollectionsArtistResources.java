package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Collection;
import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.CollectionServices;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/users/{username}/collections")
public class CollectionsArtistResources {

    @Context
    ServletContext context;


    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://35.225.50.237/near";
    static final String USER = "postgres";
    static final String PASS = "near123";


    //Responde como el método Get de la API de esta clase, recibe como parámetro el nombre del usuario para obtener las colecciones correspondientes a este
    @GET
    @Produces("application/json")
    public Response listUsers(@PathParam("username") String username) {

        // Objects for handling connection
        Connection conn = null;
        List<Collection> collections = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);
            collections = collectionServices.listCollections(username);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); //
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok().entity(collections).build();

    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newCollection(@PathParam("username") String username, @FormParam("collection") String collection) throws IOException {
        Connection conn = null;
        List<Collection> collections = null;
        Collection collection1 = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);

            collection1 = new Collection(
                    username,
                    collection
            );
            collectionServices.newCollection(collection1);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                .entity(collection1)
                .build();
    }

    @GET
    @Path("/{collection}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollection(@PathParam("username") String username, @PathParam("collection") String collection) {
        Connection conn = null;
        Collection collection1 = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);
            collection1 = collectionServices.getCollection(username, collection);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Handling errors from JDBC driver
        } finally {
            // Cleaning-up environment
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if (collection1 != null) {
            return Response.ok()
                    .entity(collection1)
                    .build();
        } else {
            return Response.status(404)
                    .entity(new ExceptionMessage(404, "User not found"))
                    .build();
        }
    }
}

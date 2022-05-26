package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.ArtServices;
import co.edu.unbosque.wsrestnear.services.LikeServices;
import co.edu.unbosque.wsrestnear.services.OwnershipServices;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/owners")
public class OwnershipResources {

    @Context
    ServletContext context;

    private String UPLOAD_DIRECTORY = "NFTS";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://199.223.235.245/near";
    static final String USER = "postgres";
    static final String PASS = "near123";

    //Responde como el método Get de la API de esta clase, recibe como parámetro el nombre del usuario para obtener las FCoins correspondientes a este
    @Path("/arts/{art}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnerArt(@PathParam("art") String art) throws IOException {

        Connection conn = null;
        User user = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            OwnershipServices ownershipServices = new OwnershipServices(conn);

            String emailOwner = ownershipServices.getOwnership(art);

            UserService userService = new UserService(conn);
            user = userService.getUser(emailOwner);

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

        return Response.ok().entity(user).build();
    }

    @Path("/{username}/arts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListArtsOwner(@PathParam("username") String username) throws IOException {

        Connection conn = null;
        List<Art> nfts = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            OwnershipServices ownershipServices = new OwnershipServices(conn);

            nfts = ownershipServices.getListArtsOwnership(username);

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
        List<Art> dataFiles = new ArrayList<Art>();

        for(int j=0;j<nfts.size();j++){
            dataFiles.add(nfts.get(j));
            dataFiles.get(j).setId(UPLOAD_DIRECTORY + File.separator + dataFiles.get(j).getId());
        }

        return Response.ok().entity(nfts).build();
    }
    //Responde como el método Post de la API de esta clase, recibe como parámetro el nombre del usuario y los FCoins para agregar los valores actualizados al usuario especificado

    @GET
    @Path("/{username}/arts/likes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listArtsLikes(@PathParam("username") String username) {
        Connection conn = null;
        List<Art> nfts = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            LikeServices likeServices = new LikeServices(conn);

            nfts = likeServices.getListArtsLikes(username);

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
        List<Art> dataFiles = new ArrayList<Art>();

        for(int j=0;j<nfts.size();j++){
            dataFiles.add(nfts.get(j));
            dataFiles.get(j).setId("NFTS"+ File.separator + dataFiles.get(j).getId());
        }

        return Response.ok().entity(dataFiles).build();
    }


    @Path("/{username}/arts/{art}")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public Response buyArt(@PathParam("username") String username, @PathParam("art") String art)
            throws IOException {
        Connection  conn = null;
        String result = "";
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("pasé1234");
            result = new OwnershipServices(conn).buyArt(username,art);

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
                .entity(result)
                .build();
    }



}

package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestnear.dtos.FCoins;
import co.edu.unbosque.wsrestnear.dtos.User;
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

@Path("users/{username}/fcoins")
public class FCoinsResource {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://35.225.50.237/near";
    static final String USER = "postgres";
    static final String PASS = "near123";

    //Responde como el método Get de la API de esta clase, recibe como parámetro el nombre del usuario para obtener las FCoins correspondientes a este
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFCoins(@PathParam("username") String username) throws IOException {

        Connection conn = null;
        User user = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            UserService uService = new UserService(conn);
            user = uService.getUser(username);
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
           if (user == null) {
               return Response.status(404).entity(new ExceptionMessage(404, "User not found")).build();
           }
            FCoins fCoinsUser = new FCoins(user.getUsername(),user.getFcoins());
               return Response.ok().entity(fCoinsUser).build();
    }

    //Responde como el método Post de la API de esta clase, recibe como parámetro el nombre del usuario y los FCoins para agregar los valores actualizados al usuario especificado

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postFCoins(@PathParam("username") String username, @FormParam("cantidad") long fcoins)
            throws IOException {
        Connection conn = null;
        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            user = usersService.getUser(username);
            user = usersService.updateUser(user,fcoins);

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
                .entity(user)
                .build();
    }


}

package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art;
import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestnear.dtos.Likes;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.ArtServices;
import co.edu.unbosque.wsrestnear.services.LikeServices;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context ;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.servlet.ServletContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
public class UsersResource {

    @Context
    ServletContext context;

    LikeServices likeServices;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://199.223.235.245/near";
    static final String USER = "postgres";
    static final String PASS = "near123";

    @GET
    @Produces("application/json")
    public Response listUsers() {

        // Objects for handling connection
        Connection conn = null;
        List<User> users = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            users = usersService.listUsers();

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
        return Response.ok().entity(users).build();

    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(
            @FormParam("username") String username,
            @FormParam("name") String name,
            @FormParam("lastname") String lastname,
            @FormParam("password") String password,
            @FormParam("role") String role
    ) {
        Connection conn = null;
        List<User> users = null;
        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);

            user = new User(
                    username,
                    name,
                    lastname,
                    role,
                    password,
                    0
            );
            usersService.newUser(user);

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

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response getUser (@PathParam("username") String username){

        Connection conn = null;
        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            user = usersService.getUser(username);

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
        if (user != null) {
                return Response.ok()
                        .entity(user)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }

    }

    @GET
    @Path("/{username}/arts/{art}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArt(@PathParam("username") String username,@PathParam("art") String image){
        Connection conn = null;

        Art art = null;
        try {
            Class.forName(JDBC_DRIVER);
            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            art = new ArtServices(conn).getArt(image);
            System.out.println(art.toString());
            conn.close();
        }catch (ClassNotFoundException | SQLException nullPointerException){
            return Response.ok()
                    .entity(String.valueOf(0))
                    .build();
        }

        return Response.ok()
                .entity(art)
                .build();
    }

    @GET
    @Path("/{username}/arts/{art}/likes/like")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listLikes(@PathParam("username") String username,@PathParam("art") String art) {
        Connection conn = null;

        int userLikedArt = 0;
        try {
            Class.forName(JDBC_DRIVER);
            // Opening database connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            likeServices = new LikeServices(conn);

            userLikedArt = likeServices.likeArtUser(new Likes(username,art));
            conn.close();
        }catch (ClassNotFoundException | SQLException nullPointerException){
            return Response.ok()
                    .entity(String.valueOf(0))
                    .build();
        }

        return Response.ok()
                .entity(userLikedArt)
                .build();
    }


    @GET
    @Path("/arts/{art}/likes")
    @Produces(MediaType.TEXT_PLAIN)
    public Response likesNFT(@PathParam("art") String image) {

        Connection conn = null;

            int likes = 0;
            try {
                Class.forName(JDBC_DRIVER);
                // Opening database connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                likeServices = new LikeServices(conn);

                likes = likeServices.likesArt(image);
                conn.close();
            }catch (ClassNotFoundException | SQLException nullPointerException){

                return Response.ok()
                        .entity(String.valueOf(0))
                        .build();
            }

            return Response.ok()
                    .entity(likes)
                    .build();
    }


    @POST
    @Path("/{username}/arts/{art}/likes/like")
    @Produces(MediaType.TEXT_PLAIN)
    public Response operateList(@PathParam("username") String username,@PathParam("art") String art) {

        Connection conn = null;

        int likes = 0;
        try {
            Class.forName(JDBC_DRIVER);
            // Opening database connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            likeServices = new LikeServices(conn);

            likes = likeServices.likeArtUser(new Likes(username,art));
            System.out.println("likes: "+ likes);
            if(likes == 0){
                likeServices.addLike(new Likes(username,art));
            }else{
                likeServices.removeLike(new Likes(username,art));
            }
            conn.close();
            return Response.ok()
                    .entity("Se cargo exitpsamente")
                    .build();
        } catch (SQLException | ClassNotFoundException nullPointerException) {
            return Response.serverError().build();
        }
    }



}
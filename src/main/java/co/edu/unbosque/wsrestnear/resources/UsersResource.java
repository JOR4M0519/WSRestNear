package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art_NFT;
import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestnear.dtos.Likes;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context ;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/users")
public class UsersResource {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://35.225.50.237/near";
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
    //Responde como el método Get de la API de esta clase, obtiene la lista de los usuarios que se encuentran registrados en la página


//    //Responde como el método Get de la API de esta clase agregándole el nombre de usuario, obtiene todas las especificaciones de únicamente el usuario solicitado a través de la API
//    @GET
//    @Path("/{username}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response get(@PathParam("username") String username, @QueryParam("role") String role) {
//
//        try {
//            List<User> users = new UserService().getUsers();
//
//            User user = users.stream()
//                    .filter(u -> u.getUsername().equals(username) && u.getRole().equals(role))
//                    .findFirst()
//                    .orElse(null);
//
//            if (user != null) {
//                return Response.ok()
//                        .entity(user)
//                        .build();
//            } else {
//                return Response.status(404)
//                        .entity(new ExceptionMessage(404, "User not found"))
//                        .build();
//            }
//        } catch (IOException e) {
//            return Response.serverError().build();
//        }
//    }
//    @GET
//    @Path("/{username}/arts/{art}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response listLikes(@PathParam("username") String username,@PathParam("art") String art) {
//        try {
//            Likes likeDetailUser =  new UserService().getLikes().stream()
//                    .filter(likes -> likes.getEmail().equals(username) && likes.getPictureName().equals(art)).findFirst().orElse(null);
//
//            if(likeDetailUser ==null){
//                return Response.ok()
//                        .entity(new Likes("","","",0))
//                        .build();
//            }
//            return Response.ok()
//                    .entity(likeDetailUser)
//                    .build();
//        } catch (IOException e) {
//            return Response.serverError().build();
//        }
//    }
//
//    @GET
//    @Path("/arts/{art}/likes")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response likesNFT(@PathParam("art") String art) {
//        try {
//            List<Likes> listLikeNFT = null;
//            try {
//                listLikeNFT = new UserService().getLikes().stream()
//                        .filter(likes -> likes.getPictureName().equals(art) && (likes.getLiker() == 1)).collect(Collectors.toList());
//
//            }catch (NullPointerException nullPointerException){
//
//                return Response.ok()
//                        .entity(String.valueOf(0))
//                        .build();
//            }
//
//            return Response.ok()
//                    .entity(listLikeNFT.size())
//                    .build();
//        } catch (IOException e) {
//            return Response.serverError().build();
//        }
//    }
//
//    @POST
//    @Path("/{username}/arts/{art}/{idLike}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response operateList(@PathParam("username") String username,@PathParam("art") String art,@PathParam("idLike") String idLike) {
//        UserService userService = new UserService();
//        String contextPath =context.getRealPath("") + File.separator;
//        try {
//            List<Likes> likesList = userService.getLikes();
//            Likes likeDetailUser =  userService.getLikes().stream()
//                    .filter(likes -> likes.getEmail().equals(username) && likes.getPictureName().equals(art)).findFirst().orElse(null);
//
//            Art_NFT art_nft = userService.getNft().stream().filter(nft -> nft.getId().equals(art)).findFirst().orElse(null);
//
//            if(likeDetailUser ==null){
//                userService.addLike(username,art_nft.getAuthor(),art,Integer.parseInt(idLike),contextPath);
//            }else{
//                boolean exit = false;
//                for (int i=0;i<likesList.size() && !exit;i++){
//                    if(likesList.get(i).getEmail().equals(likeDetailUser.getEmail()) && likesList.get(i).getPictureName().equals(likeDetailUser.getPictureName())){
//                        likesList.remove(i);
//                        userService.updateLike(likesList,contextPath);
//                        exit=true;
//                    }
//                }
//            }
//
//            return Response.ok()
//                    .entity("Se cargo exitpsamente")
//                    .build();
//        } catch (IOException e) {
//            return Response.serverError().build();
//        }
//    }
//


}
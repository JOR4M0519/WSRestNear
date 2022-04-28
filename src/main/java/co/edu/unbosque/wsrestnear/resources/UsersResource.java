package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
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
import java.util.List;

@Path("/users")
public class UsersResource {

    @Context
    ServletContext context;

    //Responde como el método Get de la API de esta clase, obtiene la lista de los usuarios que se encuentran registrados en la página
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {

        try {
            List<User> users = new UserService().getUsers();
            return Response.ok()
                    .entity(users)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }


//Responde como el método Post de la API de esta clase, obtienes como parámetros los datos necesarios para crear un usuario y con estos registra dicho usuario en el csv
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response create(User user) {
//        String contextPath =context.getRealPath("") + File.separator;
//
//        try {
//            user = new UserService().createUser(user.getUsername(),user.getName(),user.getLastname(), user.getPassword(),user.getRole(),"0", contextPath);
//
//            return Response.created(UriBuilder.fromResource(UsersResource.class).path(user.getUsername()).build())
//                    .entity(user)
//                    .build();
//        } catch (IOException e) {
//            return Response.serverError().build();
//        }
//    }

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
        String contextPath =context.getRealPath("") + File.separator;

        try {
            User user = new UserService().createUser(username, name, lastname, password, role, "0",contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                    .entity(user)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    //Responde como el método Get de la API de esta clase agregándole el nombre de usuario, obtiene todas las especificaciones de únicamente el usuario solicitado a través de la API
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username, @QueryParam("role") String role) {

        try {
            List<User> users = new UserService().getUsers();

            User user = users.stream()
                    .filter(u -> u.getUsername().equals(username) && u.getRole().equals(role))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                return Response.ok()
                        .entity(user)
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
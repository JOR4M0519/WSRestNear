package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestnear.dtos.FCoins;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

@Path("users/{username}/fcoins")
public class AmountFCoinsResource {

    @Context
    ServletContext context;

    //Responde como el método Get de la API de esta clase, recibe como parámetro el nombre del usuario para obtener las FCoins correspondientes a este
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFCoins(@PathParam("username") String username, @QueryParam("role") String role) throws IOException {

       if (role.equals("Comprador")) {
           UserService uService = new UserService();
           FCoins user = uService.amountMoney(username);
           if (user == null) {
               return Response.status(404).entity(new ExceptionMessage(404, "User not found")).build();
           }
               return Response.ok().entity(user).build();

       }else {
           return Response.status(404).entity(new ExceptionMessage(404, "User not found")).build();
       }

    }

    //Responde como el método Post de la API de esta clase, recibe como parámetro el nombre del usuario y los FCoins para agregar los valores actualizados al usuario especificado
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postFCoins(@PathParam("username") String username, @FormParam("cantidad") String fcoins) throws IOException {

        String contextPath =context.getRealPath("") + File.separator;
        UserService uService =  new UserService();
        FCoins user= uService.createMoney(username,fcoins, contextPath);
        return Response.ok().entity(user).build();
    }

}

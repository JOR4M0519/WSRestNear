package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestnear.dtos.FCoins;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

@Path("users/{username}/FCoins")
public class AmountFCoinsResource {

    @Context
    ServletContext context;

    //Responde como el método Get de la API de esta clase, recibe como parámetro el nombre del usuario para obtener las FCoins correspondientes a este
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFCoins(@PathParam("username") String username) throws IOException {

        UserService uService =  new UserService();
        FCoins user = uService.amountMoney(username);
        if(user==null){
            return Response.status(484).entity(new ExceptionMessage(404,"User not found")).build();
        }
        return Response.ok().entity(user).build();
    }

    //Responde como el método Post de la API de esta clase, recibe como parámetro el nombre del usuario y los FCoins para agregar los valores actualizados al usuario especificado
    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postFCoins(@PathParam("username") String username, @FormParam("FCoins") String FCoins) throws IOException {
        UserService uService =  new UserService();
        FCoins user= uService.createMoney(username,FCoins);
        return Response.ok().entity(user).build();
    }

}

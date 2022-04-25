package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.FCoins;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Path("/FCoins")
public class AmountFCoinsResource {

    @Context
    ServletContext context;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws IOException {

        UserService uService =  new UserService();
        Optional<List<FCoins>> FCoins = uService.getFCoins();

        return Response.ok().entity(FCoins).build();

    }

}

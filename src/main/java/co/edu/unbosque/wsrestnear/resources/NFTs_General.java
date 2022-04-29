package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art_NFT;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Path("/arts")
public class NFTs_General {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";
    private UserService uService;

    //Retorna los últimos seis NFTs en un JSON de un usuario y una colección en específico
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generalListFiles() {

        uService = new UserService();

        List<Art_NFT> nfts = null;
        try {
            nfts = uService.getNft().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Art_NFT> dataFiles = new ArrayList<Art_NFT>();

        Collections.reverse(nfts);
        for(int j=0;j<6 && j<nfts.size();j++){
            dataFiles.add(nfts.get(j));
            dataFiles.get(j).setId(UPLOAD_DIRECTORY + File.separator + dataFiles.get(j).getId());
        }

        // Adding the data to response, parsing it to json using Gson library
        return Response.ok().entity(dataFiles).build();
    }
}

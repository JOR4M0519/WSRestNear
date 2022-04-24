package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.NFT_Picture;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Path("/ArtNFT")
public class NFT_FileResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";
    private UserService uService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generalListFiles(){

        uService = new UserService();

        List<NFT_Picture> nfts = null;
        try {
            nfts = uService.getNft().get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Pase por aqui");
        // Getting an instance of the upload path
        String uploadPath = context.getRealPath("")+File.separator+UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);

        // Listing file names in path
        int i =0;
        List<NFT_Picture> files = new ArrayList<NFT_Picture>();

        File[] listFiles = uploadDir.listFiles();
        Collections.reverse(Arrays.asList(listFiles));

        for (File file : listFiles) {
            NFT_Picture nft = null;
            nft = nfts.stream().filter(nft_picture -> (file.getName()).equals(nft_picture.getId())).findFirst().orElse(null);
            if(nft != null){
                nft.setId(UPLOAD_DIRECTORY + File.separator + file.getName());
                if(i<6){files.add(nft); i++;}
            }
        }

        // Adding the data to response, parsing it to json using Gson library
        return Response.ok().entity(files).build();
    }

    /*@GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response personalListFiles(@PathParam("email") String email){

        uService = new UserService();

        List<NFT_Picture> nfts = null;
        try {
            nfts = uService.getNft().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Getting an instance of the upload path
        String uploadPath = context.getRealPath("")+File.separator+UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);

        // Listing file names in path
        //id,extension,title,author,price,likes,email_owner
        List<NFT_Picture> files = new ArrayList<NFT_Picture>();
        for (File file : uploadDir.listFiles()) {
            NFT_Picture nft = null;
            String finalEmail = email;
            nft = nfts.stream().filter(nft_picture -> (file.getName()).equals(nft_picture.getId()) && finalEmail.equals(nft_picture.getEmail_owner())).findFirst().orElse(null);
            if(nft != null){
                nft.setId(UPLOAD_DIRECTORY + File.separator + file.getName());
                files.add(nft);
            }
        }
        // Adding the data to response, parsing it to json using Gson library
        return Response.ok().entity(files).build();
    }*/

}

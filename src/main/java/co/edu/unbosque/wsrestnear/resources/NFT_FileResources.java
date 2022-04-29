package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art_NFT;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.util.*;

@Path("/users/{username}/collections/{collection}/arts")
public class NFT_FileResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";
    private UserService uService;


    //Retorna los NFTs en un JSON de un usuario y una colección en específico
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response personalListFiles(@PathParam("username") String username,@PathParam("collection") String collectionName) {

        uService = new UserService();

        Optional<List<Art_NFT>> art_nftList = null;


        try {
            List<Art_NFT> nfts = new ArrayList<Art_NFT>();

            art_nftList = uService.getNft();

            for(Art_NFT nft: art_nftList.get()){

                if(nft.getEmail_owner().equals(username) && nft.getCollection().equals(collectionName)){
                    nft.setId(UPLOAD_DIRECTORY + File.separator + nft.getId());
                    nfts.add(nft);
                }
            }
            return Response.ok().entity(nfts).build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();

        }
    }

    //Crea NFTs de un usuario y una colección en específico
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadNFT(MultipartFormDataInput inputData) {

        uService = new UserService();

        try {

            String emailAuthor = inputData.getFormDataPart("author", String.class, null);
            String collection = inputData.getFormDataPart("collection", String.class, null);
            String title = inputData.getFormDataPart("title", String.class, null);
            String price = inputData.getFormDataPart("price", String.class, null);


            //Found Data author
            List<User> users = new UserService().getUsers();
            User userFounded = users.stream().filter(user -> emailAuthor.equals(user.getUsername()))
                    .findFirst().orElse(null);
            String author = userFounded.getName() + " " + userFounded.getLastname();

            Map<String, List<InputPart>> formParts = inputData.getFormDataMap();
            List<InputPart> inputParts = formParts.get("customFile");

            for (InputPart inputPart : inputParts) {
                try {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    String randomString = uService.generateRandomString();
                    String fileName = randomString+"&"+title;


                    // Handling the body of the part with an InputStream
                    InputStream istream = inputPart.getBody(InputStream.class,null);

                    saveFile(istream, fileName, context);

                    uService.createNFT(fileName, collection, title, author, price, emailAuthor, context.getRealPath("") +File.separator);
                } catch (IOException e) {
                    return Response.serverError().build();
                }
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
        return Response.status(201)
                .entity("NFT successfully uploaded")
                .build();
    }

    //Guarda el archivo subido a una ruta específica en el servidor
    private void saveFile(InputStream uploadedInputStream, String fileName, ServletContext context) {
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            // Complementing servlet path with the relative path on the server
            String uploadPath = context.getRealPath("") +File.separator+ UPLOAD_DIRECTORY+File.separator;

            // Creating the upload folder, if not exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Persisting the file by output stream
            OutputStream outpuStream = new FileOutputStream(uploadPath + fileName);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

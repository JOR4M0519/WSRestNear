package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art_NFT;
import co.edu.unbosque.wsrestnear.dtos.Collection;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.util.*;

@Path("/users/{username}/collections/{collection}")
public class NFT_FileResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";
    private UserService uService;


    @Path("/arts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response personalListFiles(@PathParam("username") String username,@PathParam("collection") String collectionName) {

        uService = new UserService();

        Optional<List<Art_NFT>> art_nftList = null;
        List<Art_NFT> nfts = new ArrayList<Art_NFT>();

        try {
            art_nftList = uService.getNft();
            for(Art_NFT nft: art_nftList.get()){
                if(nft.getEmail_owner().equals(username) && nft.getCollection().equals(collectionName)){
                    nfts.add(nft);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Getting an instance of the upload path
        String uploadPath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);

        // Listing file names in path

        List<Art_NFT> files = new ArrayList<Art_NFT>();

        File[] listFiles = uploadDir.listFiles();
        Collections.reverse(Arrays.asList(listFiles));

        for (File file : listFiles) {
            Art_NFT nft = null;
            String finalEmail = "";
            nft = nfts.stream().filter(artNft_ -> (file.getName()).equals(artNft_.getId()) && finalEmail.equals(artNft_.getEmail_owner())).findFirst().orElse(null);
            if (nft != null) {
                nft.setId(UPLOAD_DIRECTORY + File.separator + file.getName());
                files.add(nft);
            }
        }

        return Response.ok().entity(files).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadNFT(
            @FormParam("author") String emailAuthor,
            @FormParam("titulo") String tittle,
            @FormParam("precio") String price,
            MultipartFormDataInput inputData
            ) {

        uService = new UserService();
        try {
            //Found Data author
            List<User> users = new UserService().getUsers();
            User userFounded = users.stream().filter(user -> emailAuthor.equals(user.getUsername()))
                    .findFirst().orElse(null);
            String author = userFounded.getName() + " " + userFounded.getLastname();

            String uploadPath = context.getRealPath("") + File.separator + "NFTS";

            /*request.setAttribute("name", userFounded.getName());
            request.setAttribute("role", userFounded.getRole());
            request.setAttribute("username", userFounded.getUsername());
            */

            Map<String, List<InputPart>> formParts = inputData.getFormDataMap();
            List<InputPart> inputParts = formParts.get("file");

            for (InputPart inputPart : inputParts) {
                try {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    MultivaluedMap<String, String> headers = inputPart.getHeaders();
                    String randomString = uService.generateRandomString();
                    String extension = parseFileName(headers).split(".")[1];
                    String fileName = randomString+"&"+parseFileName(headers);


                    // Handling the body of the part with an InputStream
                    InputStream istream = inputPart.getBody(InputStream.class,null);

                    saveFile(istream, fileName, context);
                    System.out.println(fileName);
                    uService.createNFT(fileName, extension, tittle, author, price, emailAuthor, context.getRealPath("") + File.separator);
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

    private String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                String fileName = tmp[1].trim().replaceAll("\"","");
                return fileName;
            }
        }
        return "unknown";
    }

    // Save uploaded file to a defined location on the server
    private void saveFile(InputStream uploadedInputStream, String fileName, ServletContext context) {
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            // Complementing servlet path with the relative path on the server
            String uploadPath = context.getRealPath("") + UPLOAD_DIRECTORY;

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

package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.NFT_Picture;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.util.*;

@Path("/ArtNFT")
public class NFT_FileResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";
    private UserService uService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generalListFiles() {

        uService = new UserService();

        List<NFT_Picture> nfts = null;
        try {
            nfts = uService.getNft().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Getting an instance of the upload path
        String uploadPath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);

        // Listing file names in path
        int i = 0;
        List<NFT_Picture> files = new ArrayList<NFT_Picture>();

        File[] listFiles = uploadDir.listFiles();
        Collections.reverse(Arrays.asList(listFiles));

        for (File file : listFiles) {
            NFT_Picture nft = null;
            nft = nfts.stream().filter(nft_picture -> (file.getName()).equals(nft_picture.getId())).findFirst().orElse(null);
            if (nft != null) {
                nft.setId(UPLOAD_DIRECTORY + File.separator + file.getName());
                if (i < 6) {
                    files.add(nft);
                    i++;
                }
            }
        }

        // Adding the data to response, parsing it to json using Gson library
        return Response.ok().entity(files).build();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response personalListFiles(@PathParam("email") String email) {

        uService = new UserService();

        List<NFT_Picture> nfts = null;
        try {
            nfts = uService.getNft().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Getting an instance of the upload path
        String uploadPath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);

        // Listing file names in path
        //id,extension,title,author,price,likes,email_owner
        List<NFT_Picture> files = new ArrayList<NFT_Picture>();
        for (File file : uploadDir.listFiles()) {
            NFT_Picture nft = null;
            String finalEmail = email;
            nft = nfts.stream().filter(nft_picture -> (file.getName()).equals(nft_picture.getId()) && finalEmail.equals(nft_picture.getEmail_owner())).findFirst().orElse(null);
            if (nft != null) {
                nft.setId(UPLOAD_DIRECTORY + File.separator + file.getName());
                files.add(nft);
            }
        }
        // Adding the data to response, parsing it to json using Gson library
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

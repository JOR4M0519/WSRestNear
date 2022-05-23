package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art_NFT;
import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.services.ArtServices;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@Path("/users/{username}/collections/{collection}/arts")
public class ArtUserResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://35.225.50.237/near";
    static final String USER = "postgres";
    static final String PASS = "near123";
    //private UserService uService;
    private ArtServices artServices;

    //Retorna los NFTs en un JSON de un usuario y una colección en específico
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response personalListFiles(@PathParam("username") String username,@PathParam("collection") String collectionName) {

        Connection conn = null;

        List<Art_NFT> art_nftList = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            artServices = new ArtServices(conn);
            art_nftList = artServices.listArts();

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

            List<Art_NFT> nfts = new ArrayList<Art_NFT>();

            for(Art_NFT nft: art_nftList){

                if(nft.getEmail().equals(username) && nft.getCollection().equals(collectionName)){
                    nft.setId(UPLOAD_DIRECTORY + File.separator + nft.getId());
                    nfts.add(nft);
                }
            }
            return Response.ok().entity(nfts).build();


    }

    //Crea NFTs de un usuario y una colección en específico
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadNFT(MultipartFormDataInput inputData) {

        Connection conn = null;


        try {

            String emailAuthor = inputData.getFormDataPart("author", String.class, null);
            String collection = inputData.getFormDataPart("collection", String.class, null);
            String title = inputData.getFormDataPart("title", String.class, null);
            String price = inputData.getFormDataPart("price", String.class, null);


            Class.forName(JDBC_DRIVER);
            // Opening database connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            artServices = new ArtServices(conn);
            UserService userService = new UserService(conn);

            User user = userService.getUser(emailAuthor);
            String author = user.getName() + " " + user.getLastname();

            conn.close();

            Map<String, List<InputPart>> formParts = inputData.getFormDataMap();
            List<InputPart> inputParts = formParts.get("customFile");

            for (InputPart inputPart : inputParts) {
                try {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    MultivaluedMap<String, String> headers = inputPart.getHeaders();
                    String randomString = generateRandomString();
                    String fileName = randomString+"."+parseFileName(headers).split("\\.")[1];


                    // Handling the body of the part with an InputStream
                    InputStream istream = inputPart.getBody(InputStream.class,null);

                    saveFile(istream, fileName, context);

                    artServices.newArt(new Art_NFT(fileName,collection,title,author,price,emailAuthor));
                } catch (IOException e) {
                    return Response.serverError().build();
                }
            }
        } catch (IOException e) {
            return Response.serverError().build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Response.status(201)
                .entity("NFT successfully uploaded")
                .build();
    }

    //Retorna el nombre del archivo del header del multipartFormDataInput
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

    public String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}

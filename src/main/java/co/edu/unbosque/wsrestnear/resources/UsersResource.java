package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.*;
import co.edu.unbosque.wsrestnear.services.ArtServices;
import co.edu.unbosque.wsrestnear.services.LikeServices;
import co.edu.unbosque.wsrestnear.services.OwnershipServices;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.servlet.ServletContext;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Path("/users")
public class UsersResource {

    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "profileImages";
    LikeServices likeServices;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://199.223.235.245/near";
    static final String USER = "postgres";
    static final String PASS = "near123";

    @GET
    @Produces("application/json")
    public Response listUsers() {

        // Objects for handling connection
        Connection conn = null;
        List<User> users = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            users = usersService.listUsers();

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); //
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok().entity(users).build();

    }

    @GET
    @Path("/data")
    @Produces (MediaType.TEXT_PLAIN)
    public Response listPersonalDataArtist() {

        // Objects for handling connection
        Connection conn = null;
        JSONArray artistList = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);

            artistList = usersService.listPersonalDataUsers();

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); //
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok().entity(artistList).build();

    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForm(MultipartFormDataInput inputData) {
        String username = "";
        Connection conn = null;
        User user = null;

        try {
            username = inputData.getFormDataPart("username", String.class, null);
            String name = inputData.getFormDataPart("name", String.class, null);
            String lastname = inputData.getFormDataPart("lastname", String.class, null);
            String password = inputData.getFormDataPart("password", String.class, null);
            String role = inputData.getFormDataPart("role", String.class, null);

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService userService = new UserService(conn);


            Map<String, List<InputPart>> formParts = inputData.getFormDataMap();
            List<InputPart> inputParts = formParts.get("formFile");

            for (InputPart inputPart : inputParts) {
                try {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    MultivaluedMap<String, String> headers = inputPart.getHeaders();
                    String randomString = generateRandomString();
                    String profileImage = "Profile&"+randomString + "." + parseFileName(headers).split("\\.")[1];

                    // Handling the body of the part with an InputStream
                    InputStream istream = inputPart.getBody(InputStream.class, null);

                    saveFile(istream, profileImage, context);
                    user = new User(username,name,lastname,role,password,profileImage,"");
                    userService.newUser(user);
                    conn.close();

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
        return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                .entity(user)
                .build();
    }

    @PUT
    @Path("/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePhoto(MultipartFormDataInput inputData) {
        String fileName = "";

        try {
            fileName = inputData.getFormDataPart("fileName", String.class, null);


        Map<String, List<InputPart>> formParts = inputData.getFormDataMap();
            List<InputPart> inputParts = formParts.get("formFile");

            for (InputPart inputPart : inputParts) {
                try {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    MultivaluedMap<String, String> headers = inputPart.getHeaders();

                    // Handling the body of the part with an InputStream
                    InputStream istream = inputPart.getBody(InputStream.class, null);

                    saveFile(istream, fileName, context);


                } catch (IOException e) {
                    return Response.serverError().build();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return Response.ok()
                    .entity("New Photo")
                    .build();
        }

    @PUT
    @Path("/password")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(User user)
            throws IOException {
        Connection conn = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            user = usersService.updateUserPassword(user);
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok()
                .entity(user)
                .build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserData(User user)
            throws IOException {
        Connection conn = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            user = usersService.updateUser(user);
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok()
                .entity(user)
                .build();
    }


   //Metodo viejo sin guardar imagen
    /*
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
        Connection conn = null;

        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);

            user = new User(
                    username,
                    name,
                    lastname,
                    role,
                    password,
                    0
            );
            usersService.newUser(user);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                .entity(user)
                .build();
    }*/

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response getUser (@PathParam("username") String username){

        Connection conn = null;
        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            user = usersService.getUser(username);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if (user != null) {
                return Response.ok()
                        .entity(user)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }

    }

    @GET
    @Path("/{username}/likes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTotalLikesByUser(@PathParam("username") String username) {

        Connection conn = null;
        List<Quantity> likesTotalList = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            LikeServices lServices = new LikeServices(conn);
            likesTotalList = lServices.listTotalLikesByUser(username);

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

        return Response.ok().entity(likesTotalList).build();
    }
    @GET
    @Path("/{username}/collections/{collection}/arts/{art}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArt(@PathParam("username") String username,@PathParam("art") String image){
        Connection conn = null;

        Art art = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            art = new ArtServices(conn).getArt(image);
            conn.close();
        }catch (ClassNotFoundException | SQLException nullPointerException){
            return Response.ok()
                    .entity(String.valueOf(0))
                    .build();
        }

        return Response.ok()
                .entity(art)
                .build();
    }




    @GET
    @Path("/{username}/arts/{art}/likes/like")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listLikes(@PathParam("username") String username,@PathParam("art") String art) {
        Connection conn = null;

        int userLikedArt = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            likeServices = new LikeServices(conn);

            userLikedArt = likeServices.likeArtUser(new Likes(username,art));
            conn.close();
        }catch (ClassNotFoundException | SQLException nullPointerException){
            return Response.ok()
                    .entity(String.valueOf(0))
                    .build();
        }

        return Response.ok()
                .entity(userLikedArt)
                .build();
    }


    @GET
    @Path("/arts/{art}/likes")
    @Produces(MediaType.TEXT_PLAIN)
    public Response likesNFT(@PathParam("art") String image) {

        Connection conn = null;

            int likes = 0;
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                likeServices = new LikeServices(conn);

                likes = likeServices.likesArt(image);
                conn.close();
            }catch (ClassNotFoundException | SQLException nullPointerException){

                return Response.ok()
                        .entity(String.valueOf(0))
                        .build();
            }

            return Response.ok()
                    .entity(likes)
                    .build();
    }
    @GET
    @Path("/arts/{art}/likes/like")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listLikesNFT(@PathParam("art") String image) {

        Connection conn = null;

        int likes = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            likeServices = new LikeServices(conn);

            likes = likeServices.likesArt(image);
            conn.close();
        }catch (ClassNotFoundException | SQLException nullPointerException){

            return Response.ok()
                    .entity(String.valueOf(0))
                    .build();
        }

        return Response.ok()
                .entity(likes)
                .build();
    }


    @POST
    @Path("/{username}/arts/{art}/likes/like")
    @Produces(MediaType.TEXT_PLAIN)
    public Response operateList(@PathParam("username") String username,@PathParam("art") String art) {

        Connection conn = null;

        int likes = 0;
        try {
            Class.forName(JDBC_DRIVER);
            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            likeServices = new LikeServices(conn);

            likes = likeServices.likeArtUser(new Likes(username,art));
            if(likes == 0){
                likeServices.addLike(new Likes(username,art));
            }else{
                likeServices.removeLike(new Likes(username,art));
            }
            conn.close();
            return Response.ok()
                    .entity("Se cargo exitosamente")
                    .build();
        } catch (SQLException | ClassNotFoundException nullPointerException) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{username}/edit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserDescription(@PathParam("username") String username, String description)
            throws IOException {
        Connection conn = null;
        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            user = usersService.getUser(username);

            user = usersService.updateUserDescription(user, description);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
                .entity(user)
                .build();
    }

    //Retorna el nombre del archivo del header del multipartFormDataInput
    private String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                String fileName = tmp[1].trim().replaceAll("\"", "");
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
            String uploadPath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator;

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

    public static String generateRandomString() {
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
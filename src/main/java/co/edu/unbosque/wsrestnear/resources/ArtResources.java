package co.edu.unbosque.wsrestnear.resources;

import co.edu.unbosque.wsrestnear.dtos.Art_NFT;
import co.edu.unbosque.wsrestnear.services.ArtServices;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/arts")
public class ArtResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "NFTS";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://35.225.50.237/near";
    static final String USER = "postgres";
    static final String PASS = "near123";

    //Retorna los últimos seis NFTs en un JSON de un usuario y una colección en específico
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generalListFiles() {

        Connection conn = null;
        List<Art_NFT> nfts = null;

        try {

            Class.forName(JDBC_DRIVER);

            // Opening database connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            ArtServices artServices = new ArtServices(conn);
            nfts = artServices.listArts();
            System.out.println(nfts.toString());

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

        List<Art_NFT> dataFiles = new ArrayList<Art_NFT>();

        Collections.reverse(nfts);
        for(int j=0;j<6 && j<nfts.size();j++){
            System.out.println("pase?");
            dataFiles.add(nfts.get(j));
            dataFiles.get(j).setId(UPLOAD_DIRECTORY + File.separator + dataFiles.get(j).getId());
        }

        // Adding the data to response, parsing it to json using Gson library
        return Response.ok().entity(dataFiles).build();
    }
}

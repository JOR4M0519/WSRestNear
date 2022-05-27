package co.edu.unbosque.wsrestnear.resources;
import co.edu.unbosque.wsrestnear.dtos.Collection;
import co.edu.unbosque.wsrestnear.services.CollectionServices;
import co.edu.unbosque.wsrestnear.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Path("/collections")
public class CollectionsIndexResources {

    @Context
    ServletContext context;


    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://199.223.235.245/near";
    static final String USER = "postgres";
    static final String PASS = "near123";

    //Responde como el método Get de la API de esta clase, obtiene las últimas seis colecciones agregadas a la plataforma
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimasCollections() throws IOException {
        // Objects for handling connection
        Connection conn = null;
        List<Collection> collections = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionServices collectionServices = new CollectionServices(conn);
            collections = collectionServices.listCollections();
            Collections.reverse(collections);
            if(collections.size()>3){
                int i=3;
                    while (collections.size()>3) {
                        collections.remove(i);
                    }
            }

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
        return Response.ok().entity(collections).build();
    }

}

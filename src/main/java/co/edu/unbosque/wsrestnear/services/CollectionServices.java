package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.Collection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionServices {

    private Connection conn;

    public CollectionServices(){

    }

    public CollectionServices(Connection conn) {
        this.conn = conn;
    }

    public List<Collection> listCollections(String username) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        // Data structure to map results from database
        List<Collection> collections = new ArrayList<Collection>();

        try {
            // Executing a SQL query
            stmt = this.conn.prepareStatement("SELECT * FROM collection WHERE user_id = ?");
            stmt.setString(1, username);


            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String title = rs.getString("title");


                // Creating a new UserApp class instance and adding it to the array list
                collections.add(new Collection(user_id,title));
                System.out.println(new Collection(user_id,title));
            }


            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {

            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return collections;
    }
    public Collection newCollection(Collection collection) {

        PreparedStatement stmt = null;

        if (collection != null) {

            try {


                stmt = this.conn.prepareStatement("INSERT INTO Collection (user_id, title)\n" +
                            "VALUES (?,?)");


                stmt.setString(1, collection.getUsername());
                stmt.setString(2, collection.getCollection());



                stmt.executeUpdate();
                stmt.close();
            } catch(SQLException se){
                se.printStackTrace();
            } finally{
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return collection;
        }
        else {
            return null;
        }
    }
    public Collection getCollection(String username, String collection) {

        PreparedStatement stmt = null;

        Collection collection1 = null;

        try {

            stmt = this.conn.prepareStatement("SELECT * FROM collection WHERE user_id = ? and title =?");
            stmt.setString(1, username);
            stmt.setString(2, collection);
            ResultSet rs = stmt.executeQuery();


            rs.next();

            collection1 = new Collection (
                    rs.getString("user_id"),
                    rs.getString("title")
            );

            rs.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return collection1;
    }

}

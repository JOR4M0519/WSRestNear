package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.Art;
import co.edu.unbosque.wsrestnear.dtos.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnershipServices {

    private Connection conn;

    public OwnershipServices(Connection conn) {this.conn = conn;}

    public List<Art> getListArtsOwnership(String email) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;
        List<Art> artList = new ArrayList<>();
        // Data structure to map results from database
        try {
            String sql = "SELECT\n" +
                    "    a.image,\n" +
                    "    a.title,\n" +
                    "    price,\n" +
                    "    c.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname,\n" +
                    "\ta.forsale\n" +
                    "FROM ownership o\n" +
                    "         JOIN art a\n" +
                    "              ON o.\"image\" = a.\"image\"\n" +
                    "         JOIN collection c\n" +
                    "              ON c.\"collection_id\" = a.\"collection_id\"\n" +
                    "         JOIN userapp u\n" +
                    "              ON c.\"user_id\" = u.\"user_id\"\n" +
                    "                  AND o.\"user_id\" = ?;";

            stmt = this.conn.prepareStatement(sql);

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(2);
                int price = rs.getInt(3);
                String collection = rs.getString(5);
                String author = rs.getString(6) + " " + rs.getString(7);
                boolean forSale = rs.getBoolean(8);

                artList.add(new Art(id, collection, title, author, price, email, forSale));
            }

            stmt.close();
        } catch(SQLException se){
            se.printStackTrace(); // Handling errors from database
        } finally{
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return artList;
    }

    public String getOwnership(String image) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;
        String email = "";
        // Data structure to map results from database
        try {

            stmt = this.conn.prepareStatement("SELECT user_id\n" +
                    "FROM ownership\n" +
                    "WHERE image = ?;");

            stmt.setString(1, image);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            email = rs.getString("user_id");

            stmt.close();
        } catch(SQLException se){
            se.printStackTrace(); // Handling errors from database
        } finally{
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return email;
    }

    public String creatArtOwner(String email, String image) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        // Data structure to map results from database
        try {

            stmt = this.conn.prepareStatement("insert into ownership (user_id, image) VALUES (?,?);");

            stmt.setString(1, email);
            stmt.setString(2, image);

            stmt.executeUpdate();
            stmt.close();
        } catch(SQLException se){
            se.printStackTrace(); // Handling errors from database
        } finally{
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return "Se adquirió exitosamente al crearse";
    }
    public String buyArt(String email, String image) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        // Data structure to map results from database
        try {
            System.out.println("arte: "+ image);
            stmt = this.conn.prepareStatement("UPDATE ownership \n" +
                    "SET user_id = ?\n" +
                    "WHERE user_id = ?\n" +
                    "AND image = ?;");


            stmt.setString(1, email);
            stmt.setString(2, getOwnership(image));
            stmt.setString(3, image);

            stmt.executeUpdate();
            stmt.close();
        } catch(SQLException se){
            se.printStackTrace(); // Handling errors from database
        } finally{
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return "Se compró exitosamente";
    }


}

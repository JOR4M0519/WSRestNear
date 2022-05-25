package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnershipServices {

    private Connection conn;

    public OwnershipServices(Connection conn) {this.conn = conn;}

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
//    public String buyArt(String email, String image) {
//        // Object for handling SQL statement
//        PreparedStatement stmt = null;
//
//        // Data structure to map results from database
//        try {
//            System.out.println("arte: "+ image);
//            stmt = this.conn.prepareStatement("UPDATE ownership \n" +
//                    "SET user_id = ?\n" +
//                    "WHERE user_id = ?\n" +
//                    "AND image = ?;");
//
//
//            stmt.setString(1, email);
//            stmt.setString(2, getOwnership(image));
//            stmt.setString(3, image);
//
//            stmt.executeUpdate();
//            stmt.close();
//        } catch(SQLException se){
//            se.printStackTrace(); // Handling errors from database
//        } finally{
//            // Cleaning-up environment
//            try {
//                if (stmt != null) stmt.close();
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }
//        return "Se compró exitosamente";
//    }

}

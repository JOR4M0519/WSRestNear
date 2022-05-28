package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.Art;
import co.edu.unbosque.wsrestnear.dtos.Likes;
import co.edu.unbosque.wsrestnear.dtos.Quantity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeServices {

    private Connection conn;

    public LikeServices(Connection conn) {
        this.conn = conn;
    }



    public int likesArt(String image) {

        PreparedStatement stmt = null;
        // Data structure to map results from database
        int likes = 0;
        try {
            stmt = conn.prepareStatement("SELECT \n" +
                    "\tCOUNT (*) AS likes\n" +
                    "\tFROM art a \n" +
                    "\t\tJOIN likeart l\n" +
                    "\t\t\tON a.image = l.image\n" +
                    "\t\t\tAND a.image = ?;");

            stmt.setString(1, image);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            likes = rs.getInt("likes");

            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return likes;
    }

    public int likeArtUser(Likes like) {

        PreparedStatement stmt = null;
        // Data structure to map results from database
        int likes = 0;
        try {
            stmt = conn.prepareStatement("SELECT\n" +
                    "    COUNT(*) AS likes\n" +
                    "FROM art a\n" +
                    "         JOIN likeart l\n" +
                    "              ON l.\"image\" = a.\"image\"\n" +
                    "         JOIN userapp u\n" +
                    "              ON l.\"user_id\" = u.\"user_id\"\n" +
                    "                     AND l.image = ? \n" +
                    "                     And u.user_id = ?;");

            stmt.setString(1, like.getArt_id());
            stmt.setString(2, like.getEmail());

            ResultSet rs = stmt.executeQuery();

            rs.next();

            // Reading data from result set row by row
            likes = rs.getInt("likes");

            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return likes;
    }

    public List<Art> getListArtsLikes(String email) {
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
                    "    u.lastname\n" +
                    "FROM likeart l\n" +
                    "         JOIN art a\n" +
                    "              ON l.\"image\" = a.\"image\"\n" +
                    "          JOIN collection c\n" +
                    "              ON c.\"collection_id\" = a.\"collection_id\"\n" +
                    "\t\t JOIN userapp u\n" +
                    "              ON c.\"user_id\" = u.\"user_id\"\n" +
                    "                  AND l.\"user_id\" = ?;";

            stmt = this.conn.prepareStatement(sql);

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(2);
                int price = rs.getInt(3);
                String collection = rs.getString(5);
                String author = rs.getString(6) + " " + rs.getString(7);

                artList.add(new Art(id, collection, title, author, price, email));
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

    public void addLike(Likes likes){

        PreparedStatement stmt = null;
        try {
            // Executing a SQL query
            stmt = this.conn.prepareStatement("INSERT INTO LikeArt (user_id, image)\n" +
                    "VALUES (?,?)");

            stmt.setString(1,likes.getEmail());
            stmt.setString(2, likes.getArt_id());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public void removeLike(Likes likes){
        PreparedStatement stmt = null;
        try {
            // Executing a SQL query

            stmt = this.conn.prepareStatement("DELETE from likeart \n" +
                    "WHERE user_id = ?\n" +
                    "\tAND image = ?;");

            stmt.setString(1,likes.getEmail());
            stmt.setString(2, likes.getArt_id());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public List<Quantity> listTotalLikesByUser(String username) {
        PreparedStatement stmt = null;

        List<Quantity> likeList = new ArrayList<Quantity>();

        try {
            String sql = "SELECT\n" +
                    "l.image\n" +
                    "FROM likeart l\n" +
                    "JOIN art a\n" +
                    "ON a.image = l.image\n" +
                    "AND l.user_id = ?;";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                likeList.add(new Quantity(rs.getString("image"),1));
            }

            rs.close();
            stmt.close();
        } catch (SQLException se) {

        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return likeList;
    }
}



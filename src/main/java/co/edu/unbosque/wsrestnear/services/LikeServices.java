package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.Likes;

import java.sql.*;

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
}


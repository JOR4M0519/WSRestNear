package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.Likes;

import java.sql.*;

public class LikeServices {

    private Connection conn;

    public LikeServices(Connection conn) {
        this.conn = conn;
    }

    public int likesArt(String art) {

        Statement stmt = null;
        // Data structure to map results from database
        int likes = 0;
        try {
            stmt = conn.createStatement();

            String sql = "SELECT AS likes\n" +
                    "\tCOUNT(*)\n" +
                    "FROM art a\n" +
                    "JOIN likeart l\n" +
                    "\tON a.\"art_id\" = l.\"art_id\"\n" +
                    "\tAND a.title = '" + art + "';";

            ResultSet rs = stmt.executeQuery(sql);

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

    public int likeArtUser(Likes like) {

        Statement stmt = null;
        // Data structure to map results from database
        int likes = 0;
        try {
            stmt = conn.createStatement();

            String sql = "SELECT\n" +
                    "\tCOUNT(*) AS likes\n" +
                    "FROM art a\n" +
                    "JOIN likeart l\n" +
                    "\tON l.\"art_id\" = a.\"art_id\"\n" +
                    "JOIN userapp u\n" +
                    "\tON l.\"user_id\" = u.\"user_id\"\n" +
                    "\tAND a.title = '"+ like.getArt_id() +"'\n" +
                    "\tAnd u.user_id = '"+ like.getEmail() +"';\n" +
                    "\t";

            ResultSet rs = stmt.executeQuery(sql);

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
        Statement stmt_Art_id = null;
        PreparedStatement stmt = null;
        try {
            stmt_Art_id = conn.createStatement();

            String sql = "SELECT \n" +
                    "\tart_id\n" +
                    "FROM art\n" +
                    "\tWHERE image = '"+ likes.getArt_id() +"';\n" +
                    "\t";
            ResultSet rs = stmt_Art_id.executeQuery(sql);
            String art_id = rs.getString("art_id");

            // Executing a SQL query
            stmt = this.conn.prepareStatement("INSERT INTO LikeArt (user_id, art_id)\n" +
                    "VALUES (?,?)");

            stmt.setString(1,likes.getEmail());
            stmt.setString(2,art_id);


            stmt.executeUpdate();
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

    }

    public void removeLike(Likes likes){
        Statement stmt_Art_id = null;
        PreparedStatement stmt = null;
        try {
            stmt_Art_id = conn.createStatement();

            String sql = "SELECT \n" +
                    "\tart_id\n" +
                    "FROM art\n" +
                    "\tWHERE image = '"+ likes.getArt_id() +"';\n" +
                    "\t";
            ResultSet rs = stmt_Art_id.executeQuery(sql);
            String art_id = rs.getString("art_id");

            // Executing a SQL query
            stmt = this.conn.prepareStatement("DELETE from likeart \n" +
                    "WHERE user_id = '"+likes.getEmail()+"'\n" +
                    "\tAND art_id = '"+ art_id +"';  ");

            stmt.executeUpdate();
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
    }
}


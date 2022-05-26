package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.Art;
import co.edu.unbosque.wsrestnear.dtos.Quantity;
import co.edu.unbosque.wsrestnear.dtos.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtServices {

    // Objects for handling connection
    private Connection conn;

    public ArtServices(Connection conn) {
        this.conn = conn;
    }

    public List<Art> listArts() {
        Statement stmt = null;

        List<Art> artList = new ArrayList<Art>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT\n" +
                    "    image,\n" +
                    "    a.title,\n" +
                    "    price,\n" +
                    "    c.user_id,\n" +
                    "    c.title,\n" +
                    "\tu.name,\n" +
                    "\tu.lastname\n" +
                    "FROM collection c\n" +
                    "    JOIN art a\n" +
                    "        ON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "\tJOIN userapp u\n" +
                    "        ON u.\"user_id\" = c.\"user_id\";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {


                String email = rs.getString(4);
                String id = rs.getString(1);
                String collection = rs.getString(5);
                int price = rs.getInt(3);
                String title = rs.getString(2);
                String author = rs.getString(6) + " " + rs.getString(7);

                artList.add(new Art(id, collection, title, author, price, email));
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
        return artList;
    }

    public List<Art> getListArtsByfilter(String data) {
        PreparedStatement stmt = null;

        List<Art> artList = new ArrayList<Art>();

        try {
            String sql = "SELECT\n" +
                    "    image,\n" +
                    "    a.title,\n" +
                    "    price,\n" +
                    "    c.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname\n" +
                    "FROM collection c\n" +
                    "         JOIN art a\n" +
                    "              ON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "         JOIN userapp u\n" +
                    "              ON u.\"user_id\" = c.\"user_id\"\n" +
                    "\t\t\t  AND a.title LIKE ?;";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, ("%"+data+"%"));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String email = rs.getString(4);
                String id = rs.getString(1);
                String collection = rs.getString(5);
                int price = rs.getInt(3);
                String title = rs.getString(2);
                String author = rs.getString(6) + " " + rs.getString(7);

                artList.add(new Art(id, collection, title, author, price, email));
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
        return artList;
    }

    public Art getArt(String image){

        PreparedStatement stmt = null;

        Art art = null;

        try {
            String sql = "SELECT\n" +
                    "    image,\n" +
                    "    a.title,\n" +
                    "    price,\n" +
                    "    c.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname\n" +
                    "FROM collection c\n" +
                    "         JOIN art a\n" +
                    "              ON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "         JOIN userapp u\n" +
                    "              ON u.\"user_id\" = c.\"user_id\"\n" +
                    "\t\tAND a.image = ?;";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, image);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            String email = rs.getString(4);
            String id = rs.getString(1);
            String collection = rs.getString(5);
            int price = rs.getInt(3);
            String title = rs.getString(2);
            String author = rs.getString(6) + " " + rs.getString(7);

            art = new Art(image,collection,title,author,price,email);
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
        return art;
    }


    public int getIdCollection (String email, String collection){

        PreparedStatement stmt = null;
        int collection_id = 0;

        try {
            stmt = this.conn.prepareStatement("SELECT a.collection_id FROM art a JOIN collection c ON a.\"collection_id\" = c.\"collection_id\" AND c.\"user_id\" = ? AND c.\"title\" = ?;");
            stmt.setString(1, email);
            stmt.setString(2, collection);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            collection_id = rs.getInt("collection_id");

        }catch (SQLException se) {
            se.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    return collection_id;
    }


    public List<Art> listMostLikedArts() {
        Statement stmt = null;

        List<Art> artList = new ArrayList<Art>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT\n" +
                    "\tl.image,\n" +
                    "    COUNT (*) AS likes\n" +
                    "FROM likeart l\n" +
                    "         JOIN art a\n" +
                    "              ON a.image = l.image\n" +
                    "GROUP BY l.image\n" +
                    "ORDER BY COUNT(*) DESC\n" +
                    "limit 3;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                artList.add(getArt(rs.getString("image")));
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
        return artList;
    }

    public void newArt(Art art) {

        PreparedStatement stmt = null;

       if (art != null){
           try {
               stmt = this.conn.prepareStatement("INSERT INTO Art (collection_id, image, title, price)\n" +
                       "VALUES (?,?,?,?)");

               stmt.setInt(1, getIdCollection(art.getEmail(), art.getCollection()));
               stmt.setString(2, art.getId());
               stmt.setString(3, art.getTitle());
               stmt.setInt(4, (int) art.getPrice());
               stmt.executeUpdate();
               stmt.close();

               new OwnershipServices(conn).creatArtOwner(art.getEmail(), art.getId());
           } catch(SQLException se){
               se.printStackTrace();
           } finally{
               try {
                   if (stmt != null) stmt.close();
               } catch (SQLException se) {
                   se.printStackTrace();
               }
           }
       }

    }
    public List<Quantity> listTotalLikes() {
        Statement stmt = null;

        List<Quantity> likeList = new ArrayList<Quantity>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT\n" +
                    "\tl.image,\n" +
                    "    COUNT (*) AS likes\n" +
                    "FROM likeart l\n" +
                    "         JOIN art a\n" +
                    "              ON a.image = l.image\n" +
                    "GROUP BY l.image\n;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                likeList.add(new Quantity(rs.getString("image"),rs.getInt("likes")));
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

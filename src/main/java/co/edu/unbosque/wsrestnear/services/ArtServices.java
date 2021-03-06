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
            String sql = "SELECT   \n" +
                    "   image,\n" +
                    "    a.title,\n" +
                    "    price,\n" +
                    "    c.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname,\n" +
                    "    a.art_id,\n" +
                    "\ta.forsale\n" +
                    "FROM collection c\n" +
                    "         JOIN art a\n" +
                    "              ON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "                   AND a.\"forsale\" = true \n" +
                    "         JOIN userapp u\n" +
                    "              ON u.\"user_id\" = c.\"user_id\";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {


                String email = rs.getString(4);
                String id = rs.getString(1);
                String collection = rs.getString(5);
                int price = rs.getInt(3);
                String title = rs.getString(2);
                String author = rs.getString(6) + " " + rs.getString(7);
                boolean forSale = rs.getBoolean(9);
                int counter = rs.getInt(8);

                artList.add(new Art(id, collection, title, author, price, email, counter, forSale));
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

    public List<Art> listArts2() {
        Statement stmt = null;

        List<Art> artList = new ArrayList<Art>();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT   \n" +
                    "   image,\n" +
                    "    a.title,\n" +
                    "    price,\n" +
                    "    c.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname,\n" +
                    "    a.art_id,\n" +
                    "\ta.forsale\n" +
                    "FROM collection c\n" +
                    "         JOIN art a\n" +
                    "              ON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "         JOIN userapp u\n" +
                    "              ON u.\"user_id\" = c.\"user_id\";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {


                String email = rs.getString(4);
                String id = rs.getString(1);
                String collection = rs.getString(5);
                int price = rs.getInt(3);
                String title = rs.getString(2);
                String author = rs.getString(6) + " " + rs.getString(7);
                boolean forSale = rs.getBoolean(9);
                int counter = rs.getInt(8);

                artList.add(new Art(id, collection, title, author, price, email, counter, forSale));
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
                    "    u.lastname,\n" +
                    "    a.art_id,\n" +
                    "\ta.forsale\n" +
                    "FROM collection c\n" +
                    "         JOIN art a\n" +
                    "              ON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "         JOIN userapp u\n" +
                    "              ON u.\"user_id\" = c.\"user_id\"\n" +
                    "                  AND a.title ILIKE ?;";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, ("%" + data + "%"));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String email = rs.getString(4);
                String id = rs.getString(1);
                String collection = rs.getString(5);
                int price = rs.getInt(3);
                String title = rs.getString(2);
                String author = rs.getString(6) + " " + rs.getString(7);
                boolean forSale = rs.getBoolean(9);
                int counter = rs.getInt(8);

                artList.add(new Art(id, collection, title, author, price, email, counter, forSale));
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

    public Art getArt(String image) {

        PreparedStatement stmt = null;

        Art art = null;

        try {
            String sql = "SELECT\n" +
                    "    image,\n" +
                    "    a.title,\n" +
                    "\tprice,\n" +
                    "\tc.user_id,\n" +
                    "    c.title,\n" +
                    "    u.name,\n" +
                    "    u.lastname,\n" +
                    "    a.art_id,\n" +
                    "\ta.forsale\n" +
                    "    \tFROM collection c\n" +
                    "        JOIN art a\n" +
                    "        \tON a.\"collection_id\" = c.\"collection_id\"\n" +
                    "        JOIN userapp u \n" +
                    "        \tON u.\"user_id\" = c.\"user_id\"\n" +
                    "\t\t\t\tAND a.image = ?;";
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
            boolean forSale = rs.getBoolean(9);
            int counter = rs.getInt(8);

            art = new Art(image, collection, title, author, price, email, counter, forSale);

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

    public Art changeForSale(Art art) {

        PreparedStatement stmt = null;
        Art updatedArt = null;

        if (art != null) {

            try {

                stmt = this.conn.prepareStatement("UPDATE art SET forsale = ? WHERE image = ?;");


                stmt.setBoolean(1, (!art.isForSale()));
                stmt.setString(2, art.getId());

                stmt.executeUpdate();

                stmt = this.conn.prepareStatement("SELECT * FROM art WHERE image = ?;");
                stmt.setString(1, art.getId());
                ResultSet rs = stmt.executeQuery();


                rs.next();

                updatedArt = getArt(art.getId());

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
            return updatedArt;
        } else {
            return null;
        }
    }


    public int getIdCollection(String email, String collection) {

        PreparedStatement stmt = null;
        int collection_id = 0;

        try {

            String sql = "SELECT \n" +
                    "\tcollection_id \n" +
                    "FROM collection  \n" +
                    "\t\tWHERE \"user_id\" = ?\n" +
                    "\t\tAND \"title\" = ?;";

            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, collection);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            collection_id = rs.getInt("collection_id");
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
                System.out.println(getArt(rs.getString("image").toString()));
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

        if (art != null) {
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
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
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

                likeList.add(new Quantity(rs.getString("image"), rs.getInt("likes")));
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

    public Art updateArt(Art art) {

        PreparedStatement stmt = null;
        Art updatedArt = null;

        if (art != null) {

            try {

                stmt = this.conn.prepareStatement("SELECT\n" +
                        "\tcollection_id\n" +
                        "FROM\n" +
                        "\tcollection\n" +
                        "WHERE \n" +
                        "\tuser_id = ?\n" +
                        "\tAND\n" +
                        "\ttitle = ?;");

                stmt.setString(1, art.getEmail());
                stmt.setString(2, art.getCollection());
                ResultSet rs = stmt.executeQuery();

                int collection_id =0;

                rs.next();

                collection_id = rs.getInt("collection_id");

                rs.close();

                stmt = this.conn.prepareStatement("UPDATE art\n" +
                        "\tSET collection_id=?, title=?, price=?\n" +
                        "\tWHERE image=?;");


                stmt.setInt(1, collection_id);
                stmt.setString(2, art.getTitle());
                stmt.setFloat(3,art.getPrice());
                stmt.setString(4, art.getId());

                stmt.executeUpdate();



                stmt.close();

                updatedArt = getArt(art.getId());

            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return updatedArt;
        } else {
            return null;
        }
    }
}

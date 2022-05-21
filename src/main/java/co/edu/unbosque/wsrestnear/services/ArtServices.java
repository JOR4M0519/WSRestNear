package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.Art_NFT;
import co.edu.unbosque.wsrestnear.dtos.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtServices {

    // Objects for handling connection
    private Connection conn;

    public ArtServices(Connection conn) {
        this.conn = conn;
    }

    public List<Art_NFT> listArts() {
        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        ArrayList<Art_NFT> artList = new ArrayList<Art_NFT>();

        try {
            // Executing a SQL query
            System.out.println("=> Listing users...");
            stmt = conn.createStatement();
            String sql = "SELECT\n" +
                    "\ta.art_id,\n" +
                    "\timage,\n" +
                    "\ta.title,\n" +
                    "\tprice,\n" +
                    "\tc.user_id,\n" +
                    "\tc.title\n" +
                    "FROM art a\n" +
                    "JOIN collection c\n" +
                    "\tON a.\"collection_id\" = c.\"collection_id\";\n";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name
                String email_owner = rs.getString("c.user_id");
                String sqlAuthor = "SELECT\n" +
                        "\tname,\n" +
                        "\tlastname\n" +
                        "FROM userapp\n" +
                        "WHERE user_id = '" + email_owner + "';\n";
                ResultSet rsAuthor = stmt.executeQuery(sqlAuthor);

                String id = rs.getString("image");
                String collection = rs.getString("c.title");
                String title = rs.getString("a.title");
                String author = rsAuthor.getString("name") + " " + rsAuthor.getString("lastname");
                String price = rs.getString("price");

                // Creating a new UserApp class instance and adding it to the array list
                artList.add(new Art_NFT(id, collection, title, author, price, null, email_owner));
            }

            // Printing results
            System.out.println("id,collection,title,author,price,likes,email_owner");
            for (Art_NFT nft : artList) {
                System.out.println(nft.toString());
            }

            // Printing total rows
            System.out.println("Total of users retrieved: " + artList.size() + "\n");

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
        return artList;
    }

    public User getUserData(String email) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sqlAuthor = "SELECT *\n" +
                    "FROM userapp\n" +
                    "WHERE user_id = '" + email + "';\n";
            ResultSet rs = stmt.executeQuery(sqlAuthor);

            String password = rs.getString("password");
            String name = rs.getString("name");
            String lastname = rs.getString("lastname");
            String role = rs.getString("role");
            String fcoins = rs.getString("fcoins");

            return new User(email,name,lastname,role,password,fcoins);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

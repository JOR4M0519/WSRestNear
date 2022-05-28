package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.*;


public class UserService {

    private Connection conn;

    public UserService(Connection conn) {
        this.conn = conn;
    }

    public List<User> listUsers() {
        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        List<User> users = new ArrayList<User>();

        try {
            // Executing a SQL query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM userapp";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name
                String username = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                String role = rs.getString("role");
                int fcoins = rs.getInt("fcoins");


                // Creating a new UserApp class instance and adding it to the array list
                users.add(new User(username,name, lastname, role, password, fcoins));
            }

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
        return users;
    }

    public List<Integer> getArtistLikesList(){
        Statement stmt = null;

        // Data structure to map results from database

        List<Integer> artistLikesList = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            // Executing a SQL query
            String sql = "SELECT\n" +
                    "\tu.user_id,\n" +
                    "\tCOUNT (l) AS likes\n" +
                    "FROM userapp u\n" +
                    "\tJOIN collection c\n" +
                    "\t\tON c.user_id = u.user_id\n" +
                    "\tJOIN art a\n" +
                    "\t\tON a.collection_id = c.collection_id\n" +
                    "\tJOIN likeart l\n" +
                    "\t\tON l.image = a.image\n" +
                    "\tGROUP BY u.user_id\n" +
                    ";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                artistLikesList.add(rs.getInt("likes"));
            }

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
        return artistLikesList;
    }

    public JSONArray listPersonalDataUsers() {
        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        List<String> artist = new ArrayList<>();


        JSONArray artistList = new JSONArray();
        try {
            // Executing a SQL query


            stmt = conn.createStatement();

            String sql = "SELECT\n" +
                    "    u.user_id,\n" +
                    "    u.name,\n" +
                    "    u.lastname,\n" +
                    "\tu.profileImage,\n" +
                    "\tu.description,\n" +
                    "    COUNT (a) AS arts,\n" +
                    "    COUNT (DISTINCT c) filter (where c.collection_id = a.collection_id) AS collections\n" +
                    "FROM userapp u\n" +
                    "         JOIN collection c\n" +
                    "              ON c.user_id = u.user_id\n" +
                    "         JOIN art a\n" +
                    "              ON a.collection_id = c.collection_id\n" +
                    "                  AND u.role = 'Artista'\n" +
                    "GROUP BY u.user_id;";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            int i=0;
            List<Integer> artistLikesList = getArtistLikesList();


            while (rs.next()) {
                // Extracting row values by column name
                String name = rs.getString("name")+" "+rs.getString("lastname");
                String profileImage = rs.getString("profileImage");
                String description = rs.getString("description");
                int collections = rs.getInt("collections");
                int arts = rs.getInt("arts");
                int likes = artistLikesList.get(i);


                JSONObject jsonListUser = new JSONObject();

                jsonListUser.put("name", name);
                jsonListUser.put("profileImage", profileImage);
                jsonListUser.put("description", description);
                jsonListUser.put("collections", collections);
                jsonListUser.put("arts", arts);
                jsonListUser.put("likes", likes);

                artistList.put(jsonListUser.toMap());
            i++;
            }

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
        return artistList;
    }

    public User getUser(String username) {

        PreparedStatement stmt = null;
        User user = null;

        try {

            stmt = this.conn.prepareStatement("SELECT * FROM userapp WHERE user_id = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();


            rs.next();

            user = new User(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("role"),
                    rs.getString("password"),
                    rs.getInt("fcoins")
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
        return user;
    }

    public User newUser(User user) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        // Data structure to map results from database
        if (user != null) {

            try {

                if (user.getRole().equals("Artista")) {
                    stmt = this.conn.prepareStatement("INSERT INTO UserApp (user_id, name, lastname, password, role, fcoins)\n" +
                            "VALUES (?,?,?,?,'Artista',0)");
                }

                else if (user.getRole().equals("Comprador")) {
                    stmt = this.conn.prepareStatement("INSERT INTO UserApp (user_id, name, lastname, password, role, fcoins)\n" +
                            "VALUES (?,?,?,?,'Comprador',0)");
                }
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getName());
                stmt.setString(3, user.getLastname());
                stmt.setString(4, user.getPassword());


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
            return user;
        }
        else {
            return null;
        }


    }
    public User updateUser(User user, long parameter) {

        PreparedStatement stmt = null;
        User updatedUser = null;

        if (user != null) {

            try {

                stmt = this.conn.prepareStatement("UPDATE UserApp SET fcoins = ? WHERE user_id = ?");


                stmt.setLong(1, (user.getFcoins() + parameter));
                stmt.setString(2, user.getUsername());

                stmt.executeUpdate();

                stmt = this.conn.prepareStatement("SELECT * FROM userapp WHERE user_id = ?");
                stmt.setString(1, user.getUsername());
                ResultSet rs = stmt.executeQuery();


                rs.next();

                updatedUser = new User(
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getInt("fcoins")
                );

                rs.close();
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
            return updatedUser;
        }
        else {
            return null;
        }


    }



}
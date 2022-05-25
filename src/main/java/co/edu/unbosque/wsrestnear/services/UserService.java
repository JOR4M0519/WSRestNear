package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.*;
import co.edu.unbosque.wsrestnear.dtos.Collection;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


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
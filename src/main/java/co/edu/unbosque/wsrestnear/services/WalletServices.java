package co.edu.unbosque.wsrestnear.services;

import co.edu.unbosque.wsrestnear.dtos.User;
import co.edu.unbosque.wsrestnear.dtos.WalletHistory;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class WalletServices {

    private Connection conn;

    public WalletServices(Connection conn) {
        this.conn = conn;
    }

    //get history invoices of an user
    public ArrayList<WalletHistory> getWalletHistoryUser(String username) {

        PreparedStatement stmt = null;
        ArrayList<WalletHistory> walletHistory = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement("SELECT * FROM wallet_history WHERE user_id = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();


            while(rs.next()) {

                walletHistory.add( new WalletHistory(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7)
                ));
            }
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
        return walletHistory;
    }

    //get fcoins total of an user
    public JSONObject getFcoinsUser(String username){

        PreparedStatement stmt = null;
        JSONObject fcoins = null;

        if (username != null) {
            try {

                String sql = "SELECT\n" +
                        "    u.user_id,\n" +
                        "    SUM (fcoins) AS fcoins\n" +
                        "FROM wallet_history w\n" +
                        "         JOIN userapp u \n" +
                        "              ON u.user_id = w.user_id\n" +
                        "                  AND w.user_id = ?\n" +
                        "GROUP BY u.user_id;";

                stmt = this.conn.prepareStatement(sql);

                stmt.setString(1, username);


                ResultSet rs = stmt.executeQuery();



                while(rs.next()) {
                    fcoins = new JSONObject();
                    fcoins.put("username", username);
                    fcoins.put("fcoins", rs.getString("fcoins"));
                }

                if(fcoins == null){
                    fcoins = new JSONObject();
                    fcoins.put("username", username);
                    fcoins.put("fcoins", 0);
                }

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
            return fcoins;
        }
        else {return null;}
    }

    //create invoice of an user
    public WalletHistory createInvoice(WalletHistory invoice) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;
        // Data structure to map results from database
        if (invoice != null) {

            try {

                if(invoice.getWalletType().equals("Recarga")) {
                    stmt = this.conn.prepareStatement("INSERT INTO wallet_history(user_id, wtype, fcoins, image, registeredat, origin_product)\n" +
                            "\tVALUES (?, 'Recarga', ?, null, ?, ?);");

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//2015-05-11 18:26:55
                    formatter.format(invoice.getRegisteredAt().getTime());
                    stmt.setTimestamp(3, new Timestamp(invoice.getRegisteredAt().getTime()));
                    stmt.setString(4, "Banco");

                } else if (invoice.getWalletType().equals("Venta")) {
                    stmt = this.conn.prepareStatement("INSERT INTO wallet_history(user_id, wtype, fcoins, image, registeredat, origin_product)\n" +
                            "\tVALUES (?, 'Venta', ?, ?, ?, ?);");

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//2015-05-11 18:26:55
                    formatter.format(invoice.getRegisteredAt().getTime());
                    stmt.setString(3, invoice.getArt());
                    stmt.setTimestamp(4, new Timestamp(invoice.getRegisteredAt().getTime()));
                    stmt.setString(5, invoice.getOrigin_product());

                }else if (invoice.getWalletType().equals("Compra")){
                    stmt = this.conn.prepareStatement("INSERT INTO wallet_history(user_id, wtype, fcoins, image, registeredat, origin_product)\n" +
                            "\tVALUES (?, 'Compra', ?, ?, ?, ?);");

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//2015-05-11 18:26:55
                    formatter.format(invoice.getRegisteredAt().getTime());
                    stmt.setString(3, invoice.getArt());
                    stmt.setTimestamp(4, new Timestamp(invoice.getRegisteredAt().getTime()));
                    stmt.setString(5, invoice.getOrigin_product());
                }

                stmt.setString(1, invoice.getUsername());
                stmt.setLong(2, invoice.getFcoins());


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
            return invoice;
        }
        else {
            return null;
        }
    }
}

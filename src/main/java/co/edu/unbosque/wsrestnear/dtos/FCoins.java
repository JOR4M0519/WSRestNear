package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;



public class FCoins {

    @CsvBindByName
    private String username;
    @CsvBindByName
    private String FCoins;
    public FCoins(){

    }

    public FCoins(String username, String FCoins) {
        this.username = username;
        this.FCoins = FCoins;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFCoins() {
        return FCoins;
    }

    public void setFCoins(String FCoins) {
        this.FCoins = FCoins;
    }

    @Override
    public String toString() {
        return "FCoins{" +
                "username='" + username + '\'' +
                ", FCoins='" + FCoins + '\'' +
                '}';
    }
}

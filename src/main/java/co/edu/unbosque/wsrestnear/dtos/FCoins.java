package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;



public class FCoins {

    @CsvBindByName
    private String username;
    @CsvBindByName
    private String fcoins;
    public FCoins(){

    }

    public FCoins(String username, String fcoins) {
        this.username = username;
        this.fcoins = fcoins;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFCoins() {
        return fcoins;
    }

    public void setFCoins(String fcoins) {
        this.fcoins = fcoins;
    }

    @Override
    public String toString() {
        return "FCoins{" +
                "username='" + username + '\'' +
                ", fcoins='" + fcoins + '\'' +
                '}';
    }
}

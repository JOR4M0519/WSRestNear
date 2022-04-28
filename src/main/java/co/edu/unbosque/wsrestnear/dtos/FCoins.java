package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class FCoins {

    @CsvBindByName
    private String username;
    @CsvBindByName
    private String fcoins;
    public FCoins(){

    }


  //Método constructor de la clase FCoins y inicializa las variables declaradas con las pasadas por parámetros
    public FCoins(String username, String fcoins) {
        this.username = username;
        this.fcoins = fcoins;
    }

    //Obtiene el valor correspondiente a la variable username
    public String getUsername() {
        return username;
    }

    //Asigna un valor a la variable username
    public void setUsername(String username) {
        this.username = username;
    }

    //Obtiene el valor correspondiente a la variable FCoins
    public String getFCoins() {
        return fcoins;
    }

  //Asigna un valor a la variable FCoins
    public void setFCoins(String fcoins) {
        this.fcoins = fcoins;
    }

    //Muestra, por medio de un String, los nombres y valores para todas las variables en esta clase
    @Override
    public String toString() {
        return "FCoins{" +
                "username='" + username + '\'' +
                ", fcoins='" + fcoins + '\'' +
                '}';
    }
}

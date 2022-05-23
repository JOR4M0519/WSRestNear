package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class FCoins {


    private String username;
    private long fcoins;
    public FCoins(){

    }


  //Método constructor de la clase FCoins y inicializa las variables declaradas con las pasadas por parámetros
    public FCoins(String username, long fcoins) {
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

    public long getFcoins() {
        return fcoins;
    }

    public void setFcoins(long fcoins) {
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

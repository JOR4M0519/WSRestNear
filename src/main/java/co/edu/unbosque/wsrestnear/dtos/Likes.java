package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Likes {

    @CsvBindByName
    private String email;
    @CsvBindByName
    private String art_id;


    public Likes() {
    }

    //Método constructor de la clase Likes y inicializa las variables declaradas con las pasadas por parámetros
    public Likes(String email, String art_id) {
        this.email = email;
        this.art_id = art_id;
    }

    //Obtiene el valor correspondiente a la variable email
    public String getEmail() {return email;}

    //Obtiene el valor correspondiente a la variable authorPictureEmail

    //Obtiene el valor correspondiente a la variable pictureName
    public String getArt_id() {return art_id;}

}

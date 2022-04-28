package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Likes {

    @CsvBindByName
    private String email;
    @CsvBindByName
    private String authorPictureEmail;
    @CsvBindByName
    private String pictureName;
    @CsvBindByName
    private int liker;

    //Método constructor de la clase Likes y inicializa las variables declaradas con las pasadas por parámetros
    public Likes(String email, String authorPictureEmail, String pictureName, int liker) {
        this.email = email;
        this.authorPictureEmail = authorPictureEmail;
        this.pictureName = pictureName;
        this.liker = liker;
    }

    //Obtiene el valor correspondiente a la variable email
    public String getEmail() {return email;}

    //Obtiene el valor correspondiente a la variable authorPictureEmail
    public String getAuthorPictureEmail() {return authorPictureEmail;}

    //Obtiene el valor correspondiente a la variable pictureName
    public String getPictureName() {return pictureName;}

    //Obtiene el valor correspondiente a la variable liker
    public int getLiker() {return liker;}
}

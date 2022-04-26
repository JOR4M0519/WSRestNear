package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Likes {
//email,authorPictureEmail,pictureName,liker
    @CsvBindByName
    private String email;
    @CsvBindByName
    private String authorPictureEmail;
    @CsvBindByName
    private String pictureName;
    @CsvBindByName
    private int liker;

    public Likes(String email, String authorPictureEmail, String pictureName, int liker) {
        this.email = email;
        this.authorPictureEmail = authorPictureEmail;
        this.pictureName = pictureName;
        this.liker = liker;
    }

    public String getEmail() {return email;}

    public String getAuthorPictureEmail() {return authorPictureEmail;}

    public String getPictureName() {return pictureName;}

    public int getLiker() {return liker;}
}

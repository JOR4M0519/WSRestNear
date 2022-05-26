package co.edu.unbosque.wsrestnear.dtos;

public class Quantity {

    private String idImage;
    private int likes;

    public Quantity(){

    }

    public Quantity(String idImage, int likes) {
        this.idImage = idImage;
        this.likes = likes;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "idImage='" + idImage + '\'' +
                ", likes=" + likes +
                '}';
    }
}

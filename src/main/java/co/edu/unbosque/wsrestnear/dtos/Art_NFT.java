package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Art_NFT {

    @CsvBindByName
    private String id;
    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String author;
    @CsvBindByName
    private String price;
    @CsvBindByName
    private String likes;
    @CsvBindByName
    private String email_owner;


    public Art_NFT(String id, String collection, String title, String author, String price, String likes, String email_owner) {
        this.id = id;
        this.collection = collection;
        this.title = title;
        this.author = author;
        this.price = price;
        this.likes = likes;
        this.email_owner = email_owner;
    }

    //Obtiene el valor correspondiente a la variable collection
    public String getCollection() {return collection;}

    //Asigna un valor a la variable collection
    public void setCollection(String collection) {this.collection = collection;}

    //Obtiene el valor correspondiente a la variable id
    public String getId() {return id;}

    //Asigna un valor a la variable id
    public void setId(String id) {this.id = id;}

    //Obtiene el valor correspondiente a la variable title
    public String getTitle() {return title;}

    //Asigna un valor a la variable title
    public void setTitle(String title) {this.title = title;}

    //Obtiene el valor correspondiente a la variable author
    public String getAuthor() {return author;}

    //Asigna un valor a la variable author
    public void setAuthor(String author) {this.author = author;}

    //Obtiene el valor correspondiente a la variable price
    public String getPrice() {return price;}

    //Asigna un valor a la variable price
    public void setPrice(String price) {this.price = price;}

    //Obtiene el valor correspondiente a la variable likes
    public String getLikes() {
        return likes;
    }

    //Asigna un valor a la variable likes
    public void setLikes(String likes) {
        this.likes = likes;
    }

    //Obtiene el valor correspondiente a la variable email_owner
    public String getEmail_owner() {return email_owner;}

    //Asigna un valor a la variable collection
    public void setEmail_owner(String email_owner) {this.email_owner = email_owner;}

}

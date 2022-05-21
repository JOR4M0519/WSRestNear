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
    private String email;


    public Art_NFT(String id, String collection, String title, String author, String price, String email) {
        this.id = id;
        this.collection = collection;
        this.title = title;
        this.author = author;
        this.price = price;
        this.email = email;
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
    public int getPrice() {return Integer.parseInt(price);}

    //Asigna un valor a la variable price
    public void setPrice(String price) {this.price = price;}

    //Obtiene el valor correspondiente a la variable email_owner
    public String getEmail() {return email;}

    //Asigna un valor a la variable collection
    public void setEmail(String email) {this.email = email;}

}

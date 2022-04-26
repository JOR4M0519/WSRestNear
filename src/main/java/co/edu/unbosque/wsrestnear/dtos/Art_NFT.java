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

    public String getCollection() {return collection;}

    public void setCollection(String collection) {this.collection = collection;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getEmail_owner() {return email_owner;}

    public void setEmail_owner(String email_owner) {this.email_owner = email_owner;}


}

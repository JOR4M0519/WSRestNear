package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Collection {
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String quantity;

    public Collection(){

    }

    public Collection(String username, String collection, String quantity) {
        this.username = username;
        this.collection = collection;
        this.quantity = quantity;
    }

    public String getUsername() {return username;}

    public String getCollection() {return collection;}

    public String getQuantity() {return quantity;}
}

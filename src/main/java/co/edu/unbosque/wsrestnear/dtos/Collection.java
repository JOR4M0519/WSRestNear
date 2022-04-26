package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Collection {
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String quantity;

    public String getUsername() {return username;}

    public String getCollection() {return collection;}

    public String getQuantity() {return quantity;}
}

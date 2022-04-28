package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Collection {
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String quantity;

    //Obtiene el valor correspondiente a la variable username
    public String getUsername() {return username;}

    //Obtiene el valor correspondiente a la variable collection
    public String getCollection() {return collection;}

    //Obtiene el valor correspondiente a la variable quantity
    public String getQuantity() {return quantity;}
}

package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class Collection {

    private String username;
    private String collection;


    public Collection(){

    }

    public Collection(String username, String collection) {
        this.username = username;
        this.collection = collection;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "username='" + username + '\'' +
                ", collection='" + collection + '\'' +
                '}';
    }
}

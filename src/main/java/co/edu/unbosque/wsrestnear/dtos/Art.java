package co.edu.unbosque.wsrestnear.dtos;


public class Art {

    private String id;
    private String collection;
    private String title;
    private String author;
    private long price;
    private String email;


    public Art(String id, String collection, String title, String author, long price, String email) {
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


    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    //Obtiene el valor correspondiente a la variable email_owner
    public String getEmail() {return email;}

    //Asigna un valor a la variable collection
    public void setEmail(String email) {this.email = email;}

    @Override
    public String toString() {
        return "Art{" +
                "id='" + id + '\'' +
                ", collection='" + collection + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", email='" + email + '\'' +
                '}';
    }
}

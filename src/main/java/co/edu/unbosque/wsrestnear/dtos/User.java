package co.edu.unbosque.wsrestnear.dtos;

import com.opencsv.bean.CsvBindByName;

public class User {

    @CsvBindByName
    private String username;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String lastname;
    @CsvBindByName
    private String role;
    @CsvBindByName
    private String password;
    @CsvBindByName
    private String fcoins;

    //Método constructor de la clase User
    public User(){

    }

    //Método constructor de la clase User y inicializa las variables declaradas con las pasadas por parámetros
    public User(String username, String name, String lastname, String role, String password, String fcoins) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.password = password;
        this.fcoins = fcoins;
    }

    //Obtiene el valor correspondiente a la variable username
    public String getUsername() {
        return username;
    }

    //Asigna un valor a la variable username
    public void setUsername(String username) {
        this.username = username;
    }

    //Obtiene el valor correspondiente a la variable name
    public String getName() {
        return name;
    }

    //Asigna un valor a la variable name
    public void setName(String name) {
        this.name = name;
    }

    //Obtiene el valor correspondiente a la variable lastname
    public String getLastname() {
        return lastname;
    }

    //Asigna un valor a la variable lastname
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //Obtiene el valor correspondiente a la variable role
    public String getRole() {
        return role;
    }

    //Asigna un valor a la variable role
    public void setRole(String role) {
        this.role = role;
    }

    //Obtiene el valor correspondiente a la variable password
    public String getPassword() {
        return password;
    }

    //Asigna un valor a la variable password
    public void setPassword(String password) {
        this.password = password;
    }

    //Obtiene el valor correspondiente a la variable fcoins
    public String getFcoins() {
        return fcoins;
    }

    //Asigna un valor a la variable fcoins
    public void setFcoins(String fcoins) {
        fcoins = fcoins;
    }
}


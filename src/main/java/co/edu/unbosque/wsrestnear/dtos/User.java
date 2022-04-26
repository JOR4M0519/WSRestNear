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

    public User(){

    }

    public User(String username, String name, String lastname, String role, String password, String fcoins) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.password = password;
        this.fcoins = fcoins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFcoins() {
        return fcoins;
    }

    public void setFcoins(String fcoins) {
        fcoins = fcoins;
    }
}


package com.example.nermaxcookingee;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;

public class UserBeam {
    private String email;
    private String password;
    private String salt;
    private String lastname;
    private String name;
    private String patronymic;
    private HttpSession session;
    private HashMap<String, String> map;

    public enum Enum{
        email,
        lastname,
        name,
        patronymic,
        password
    }

    public HttpSession getSession(){
        return this.session;
    }

    public void setSession(HttpSession session){
        this.session = session;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String toStringForSQLInsert(){
        return "('" + email + "', '" +
                password + "', '" +
                salt + "', '" +
                lastname + "', '" +
                name + "', '" +
                patronymic + "')";
    }

    public HashMap<String, String> getMap(){
        if(map == null){
            map = new HashMap<>();
            map.put(String.valueOf(Enum.email), email);
            map.put(String.valueOf(Enum.patronymic), patronymic);
            map.put(String.valueOf(Enum.name), name);
            map.put(String.valueOf(Enum.lastname), lastname);
            map.put(String.valueOf(Enum.password), password);
            return map;
        }
        return map;
    }

}

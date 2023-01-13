package com.example.nermaxcookingee;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class Recipe implements Serializable {
    private int id;
    private String email;
    private String photo;
    private String name;
    //private double rating;
    //private int voices;
    private int cookingTime;
    private double weight;
    private double calories;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private String composition;
    private String description;
    private HashMap<String, String> mapForJSON;
    public enum Enum{
        email,
        photo,
        name,
        cookingTime,
        weight,
        calories,
        proteins,
        fats,
        carbohydrates,
        composition,
        description
    }

    public Recipe(int id, String email, String photo, String name, int cookingTime, double weight, double calories, double proteins, double fats, double carbohydrates, String composition, String description) {
        this.id = id;
        this.email = email;
        this.photo = photo;
        this.name = name;
        this.cookingTime = cookingTime;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.composition = composition;
        this.description = description;

        mapForJSON = new HashMap<>();
        mapForJSON.put("id", Integer.toString(id));
        mapForJSON.put("name", name);
        mapForJSON.put("email", email);
        mapForJSON.put("cookingTime", Integer.toString(cookingTime));
        mapForJSON.put("weight", Double.toString(weight));
        mapForJSON.put("calories", Double.toString(calories));
        mapForJSON.put("proteins", Double.toString(proteins));
        mapForJSON.put("fats", Double.toString(fats));
        mapForJSON.put("carbohydrates", Double.toString(carbohydrates));
        mapForJSON.put("composition", composition);
        mapForJSON.put("description", description);
        mapForJSON.put("photo", photo);
    }

    public Recipe(String email, String photo, String name, int cookingTime, double weight, double calories, double proteins, double fats, double carbohydrates, String composition, String description) {
        this.email = email;
        this.photo = photo;
        this.name = name;
        this.cookingTime = cookingTime;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.composition = composition;
        this.description = description;

        mapForJSON = new HashMap<>();
        mapForJSON.put("name", name);
        mapForJSON.put("email", email);
        mapForJSON.put("cookingTime", Integer.toString(cookingTime));
        mapForJSON.put("weight", Double.toString(weight));
        mapForJSON.put("calories", Double.toString(calories));
        mapForJSON.put("proteins", Double.toString(proteins));
        mapForJSON.put("fats", Double.toString(fats));
        mapForJSON.put("carbohydrates", Double.toString(carbohydrates));
        mapForJSON.put("composition", composition);
        mapForJSON.put("description", description);
        mapForJSON.put("photo", photo);
    }

    public Recipe(HashMap<String, String> map){
        this.mapForJSON = map;
        unpackHashMap(map);
    }

    public Recipe(JSONObject jsonObject){
        mapForJSON = new HashMap<>();
        for(Enum params: Enum.values()){
            mapForJSON.put(String.valueOf(params), jsonObject.getString(String.valueOf(params)));
        }
        mapForJSON.put("id", jsonObject.getString("id"));
        unpackHashMap(mapForJSON);
    }


    private void unpackHashMap(HashMap<String, String> map){
        this.email = map.get(String.valueOf(Enum.email));
        this.photo = map.get(String.valueOf(Enum.photo));
        this.name = map.get(String.valueOf(Enum.name));
        this.cookingTime = Integer.parseInt(map.get(String.valueOf(Enum.cookingTime)));
        this.weight = Double.parseDouble(map.get(String.valueOf(Enum.weight)));
        this.calories = Double.parseDouble(map.get(String.valueOf(Enum.calories)));
        this.proteins = Double.parseDouble(map.get(String.valueOf(Enum.proteins)));
        this.fats = Double.parseDouble(map.get(String.valueOf(Enum.fats)));
        this.carbohydrates = Double.parseDouble(map.get(String.valueOf(Enum.carbohydrates)));
        this.composition = map.get(String.valueOf(Enum.composition));
        this.description = map.get(String.valueOf(Enum.description));
        if(map.get("id") != null) this.id = Integer.parseInt(map.get("id"));
    }

    public HashMap<String, String> getMapForJSON() {
        return mapForJSON;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public double getWeight() {
        return weight;
    }

    public double getCalories() {
        return calories;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public String getComposition() {
        return composition;
    }

    public String getDescription() {
        return description;
    }
}

package com.example.nermaxcookingee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerIngredients {
    private static ControllerIngredients controllerIngredients;
    private HashMap<String, Ingredient> ingredients;

    private ControllerIngredients(){

    }

    public static synchronized ControllerIngredients getController(){
        if(controllerIngredients == null)
            controllerIngredients =  new ControllerIngredients();
        return controllerIngredients;
    }

    public HashMap<String, Ingredient> getIngredients(){
        if(ingredients == null){
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            ingredients = new HashMap<>();

            try {
                Class.forName(SQLData.DRIVER);
                System.out.println("Диск успешно загружен!");

                if (connection == null) {
                    connection = DriverManager.getConnection(SQLData.URL, SQLData.USER, SQLData.PASSWORD);
                    System.out.println("Успешное подключение к базе данных!");
                } else System.out.println("Вы пытаетесь подключиться к базе данных, но вы уже подключены!");

                String[] params = {"*"};
                String sql = SQLRequests.select("ingredients", params);
                System.out.println(sql);
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery(sql);

                Ingredient ingredient;

                while(rs.next()){
                    ingredient = new Ingredient();
                    ingredient.setName(rs.getString("name"));
                    ingredient.setCalories(rs.getDouble("calories"));
                    ingredient.setProteins(rs.getDouble("proteins"));
                    ingredient.setFats(rs.getDouble("fats"));
                    ingredient.setCarbohydrates(rs.getDouble("carbohydrates"));

                    ingredients.put(ingredient.getName(),ingredient);
                    System.out.println(ingredient.getName() + ingredient.getCalories() + ingredient.getProteins());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        System.out.println("Успешное отключение от базы данных!");
                    } catch (Exception e) {
                        System.out.println("Неуспешное отключение от базы данных!");
                    }
                }
            }

        }
        return ingredients;
    }


}

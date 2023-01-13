package com.example.nermaxcookingee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerRecipes {
    private static ControllerRecipes controllerRecipes;
    private ArrayList<HashMap<String, String>> recipes;

    private ControllerRecipes(){

    }

    public static synchronized ControllerRecipes getController(){
        if(controllerRecipes == null)
            return new ControllerRecipes();
        else return controllerRecipes;
    }

    public ArrayList<HashMap<String, String>> getRecipes(){
        if(recipes == null){
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            recipes = new ArrayList<>();

            try {
                Class.forName(SQLData.DRIVER);
                System.out.println("Диск успешно загружен!");

                if (connection == null) {
                    connection = DriverManager.getConnection(SQLData.URL, SQLData.USER, SQLData.PASSWORD);
                    System.out.println("Успешное подключение к базе данных!");
                } else System.out.println("Вы пытаетесь подключиться к базе данных, но вы уже подключены!");

                String[] params = {"*"};
                String sql = SQLRequests.select("recipes", params);
                System.out.println(sql);
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery(sql);

                Recipe recipe;
                HashMap<String, String> map;

                while(rs.next()){
                    map = new HashMap<>();

                    for(Recipe.Enum prm: Recipe.Enum.values()){
                        map.put(String.valueOf(prm), rs.getString(String.valueOf(prm)));
                    }
                    map.put("id", rs.getString("id"));
                    recipe = new Recipe(map);
                    recipes.add(recipe.getMapForJSON());
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
        return recipes;
    }

}

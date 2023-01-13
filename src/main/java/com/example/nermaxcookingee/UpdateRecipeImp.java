package com.example.nermaxcookingee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class UpdateRecipeImp {

    public int updateRecipe(int id, HashMap<String, String> map){
        int i=0;
        Connection connection = null;

        try {
            Class.forName(SQLData.DRIVER);
            System.out.println("Диск успешно загружен!");

            if (connection == null) {
                connection = DriverManager.getConnection(SQLData.URL, SQLData.USER, SQLData.PASSWORD);
                System.out.println("Успешное подключение к базе данных!");
            } else System.out.println("Вы пытаетесь подключиться к базе данных, но вы уже подключены!");

            PreparedStatement ps;
            String sql;

            sql = SQLRequests.updateRecipe("recipes", id, map);
            ps = connection.prepareStatement(sql);
            i = ps.executeUpdate(sql);
            System.out.println(sql);

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

        return i;
    }
}

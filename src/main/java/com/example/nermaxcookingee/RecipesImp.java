package com.example.nermaxcookingee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RecipesImp {
    private Connection connection = null;
    public int insertRecipe(Recipe recipe) {
        int i=0;

        try {
            Class.forName(SQLData.DRIVER);
            System.out.println("Диск успешно загружен!");

            if (connection == null) {
                connection = DriverManager.getConnection(SQLData.URL, SQLData.USER, SQLData.PASSWORD);
                System.out.println("Успешное подключение к базе данных!");
            } else System.out.println("Вы пытаетесь подключиться к базе данных, но вы уже подключены!");

            PreparedStatement ps;
            String sql;

            sql = SQLRequests.insertRecipe(recipe);
            System.out.println(sql);
            ps = connection.prepareStatement(sql);
            i = ps.executeUpdate(sql);

            if(i>0) {
                ResultSet rs;
                String[] params = {"id"};
                sql = SQLRequests.select("recipes", params);
                System.out.println(sql);
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery(sql);

                while (rs.next()) {
                    i = rs.getInt("id");
                }
            }
            return i;
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

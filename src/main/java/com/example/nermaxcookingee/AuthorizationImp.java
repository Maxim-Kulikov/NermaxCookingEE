package com.example.nermaxcookingee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthorizationImp {
    private Connection connection = null;

    public UserBeam authorization(String email, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserBeam userBeam = null;

        try {
            Class.forName(SQLData.DRIVER);
            System.out.println("Диск успешно загружен!");

            if (connection == null) {
                connection = DriverManager.getConnection(SQLData.URL, SQLData.USER, SQLData.PASSWORD);
                System.out.println("Успешное подключение к базе данных!");
            } else System.out.println("Вы пытаетесь подключиться к базе данных, но вы уже подключены!");

            String[] params = {"*"};
            String sql = SQLRequests.selectUser(email, "users", params);
            System.out.println(sql);
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            while(rs.next()){

                //System.out.println(SHA512.getHash(password, rs.getString("salt")));
                System.out.println("В AuthorizationImp в rs.next()");

                if(rs.getString("password").equals(/*SHA512.getHash(password, rs.getString("salt"))*/ password)){
                    userBeam = new UserBeam();


                    userBeam.setEmail(email);
                    userBeam.setPassword(rs.getString("password"));
                    userBeam.setSalt(rs.getString("salt"));
                    userBeam.setLastname(rs.getString("lastname"));
                    userBeam.setName(rs.getString("name"));
                    userBeam.setPatronymic(rs.getString("patronymic"));
                    System.out.println(userBeam.toString());
                }
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

        return userBeam;
    }

}

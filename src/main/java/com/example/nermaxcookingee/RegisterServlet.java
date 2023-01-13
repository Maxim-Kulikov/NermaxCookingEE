package com.example.nermaxcookingee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email"),
                password = request.getParameter("password"),
                lastname = request.getParameter("lastname"),
                name = request.getParameter("name"),
                patronymic = request.getParameter("patronymic");
                //salt = SHA512.getSalt();


        UserBeam newUser = new UserBeam();

        newUser.setEmail(email);
        newUser.setPassword(password);
        //newUser.setPassword(SHA512.getHash(password, salt));
        //newUser.setSalt(salt);
        newUser.setLastname(lastname);
        newUser.setName(name);
        newUser.setPatronymic(patronymic);

        RegisterImp registerImp = new RegisterImp();

        int i = registerImp.registerUser(newUser);

        if(i == Errors.EXISTENT_USER.getCode()){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", Errors.EXISTENT_USER.getCode());
                PrintWriter pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.print(jsonObject.toString());
            }catch(JSONException e){
                e.printStackTrace();
            }
        } else if(i>0){
            HttpSession session = request.getSession();
            session.setAttribute("email", newUser.getEmail());

            if(!ControllerUsers.getController().getUsers().containsKey(newUser.getEmail()))
                ControllerUsers.getController().setUser(newUser.getEmail(), newUser);
            //ControllerUsers.getController().getUser(newUser.getEmail()).setSession(session);

            int id = getIdFromDB(email);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", Errors.SUCCESSFUL_REGISTRATION.getCode());
                jsonObject.put("id", id);
                PrintWriter pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.print(jsonObject.toString());
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    public int getIdFromDB(String email){
        int id = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName(SQLData.DRIVER);
            System.out.println("Диск успешно загружен!");

            if (connection == null) {
                connection = DriverManager.getConnection(SQLData.URL, SQLData.USER, SQLData.PASSWORD);
                System.out.println("Успешное подключение к базе данных!");
            } else System.out.println("Вы пытаетесь подключиться к базе данных, но вы уже подключены!");

            String[] params = {"id"};
            String sql = SQLRequests.selectUser(email, "users", params);
            System.out.println(sql);
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            while(rs.next()){
                return rs.getInt("id");
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

        return id;
    }

}

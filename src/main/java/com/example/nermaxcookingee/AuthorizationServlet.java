package com.example.nermaxcookingee;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AuthorizationServlet", value = "/AuthorizationServlet")
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerUsers controllerUsers = ControllerUsers.getController();

        String email = request.getParameter("email"),
                password = request.getParameter("password");

        AuthorizationImp authorizationImp = new AuthorizationImp();

        UserBeam userBeam = authorizationImp.authorization(email, password);

        if(!controllerUsers.getUsers().containsKey(userBeam.getEmail()))
            controllerUsers.setUser(userBeam.getEmail(), userBeam);

        JSONObject jsonObject = new JSONObject();


        if(userBeam!=null) {
            try {
                jsonObject.put("email", userBeam.getEmail());
                jsonObject.put("password", userBeam.getPassword());
                jsonObject.put("lastname", userBeam.getLastname());
                jsonObject.put("name", userBeam.getName());
                jsonObject.put("patronymic", userBeam.getPatronymic());
                jsonObject.put("message", Errors.SUCCESSFUL_AUTHORIZATION.getCode());

                HttpSession session = request.getSession();
                session.setAttribute("email", userBeam.getEmail());
                System.out.println("Сессия атрибут " + session.getAttribute("email"));
                //controllerUsers.getUser(userBeam.getEmail()).setSession(session);


                PrintWriter pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.print(jsonObject.toString());
                System.out.println("Authorization successful" + jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try{
                jsonObject.put("message", Errors.NON_EXISTENT_USER.getCode());
                PrintWriter pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.print(jsonObject.toString());

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }
}

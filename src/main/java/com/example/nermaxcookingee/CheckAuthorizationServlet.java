package com.example.nermaxcookingee;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "CheckAuthorizationServlet", value = "/CheckAuthorizationServlet")
public class CheckAuthorizationServlet extends HttpServlet {
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        JSONObject jsonObject = new JSONObject();

        if(email == null){
            session.setAttribute("email", "kulikov.m.a102@gmail.com");
            System.out.println("CheckAuthorization false");
            jsonObject.put("status", "false");
            PrintWriter pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.print(jsonObject.toString());
        }else {
            UserBeam user = ControllerUsers.getController().getUser(email);
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("lastname", user.getLastname());
            jsonObject.put("name", user.getName());
            jsonObject.put("patronymic", user.getPatronymic());
            jsonObject.put("status", "true");

            session.setAttribute("email", user.getEmail());

            PrintWriter pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.print(jsonObject.toString());
            System.out.println("CheckAuthorization true" + jsonObject.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

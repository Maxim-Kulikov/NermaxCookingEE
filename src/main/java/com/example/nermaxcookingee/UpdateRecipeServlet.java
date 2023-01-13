package com.example.nermaxcookingee;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

@WebServlet(name = "UpdateRecipeServlet", value = "/UpdateRecipeServlet")
public class UpdateRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, String> map = new HashMap<>();

        for(Recipe.Enum param: Recipe.Enum.values()){
            if(request.getParameter(String.valueOf(param)) != null && !request.getParameter(String.valueOf(param)).equals("id")){
                map.put(String.valueOf(param), request.getParameter(String.valueOf(param)));
            }
        }

        int i = new UpdateRecipeImp().updateRecipe(Integer.parseInt(request.getParameter(String.valueOf("id"))), map);
        System.out.println("после imp");

        if(i>0){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", Errors.SUCCESSFUL_RECIPE_UPDATE.getCode());
                PrintWriter pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.print(jsonObject.toString());
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

    }
}

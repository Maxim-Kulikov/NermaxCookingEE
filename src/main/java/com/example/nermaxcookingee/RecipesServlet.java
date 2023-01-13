package com.example.nermaxcookingee;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RecipesServlet", value = "/RecipesServlet")
public class RecipesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        int i = 0;
        try {
            for(HashMap<String, String> recipeMap: ControllerRecipes.getController().getRecipes()) {
                for(Recipe.Enum param: Recipe.Enum.values()){
                    jsonObject.put(String.valueOf(param) + String.valueOf(i), recipeMap.get(String.valueOf(param)));
                }
                jsonObject.put("id" + i, recipeMap.get("id"));
                i++;
            }
            jsonObject.put("size", ControllerRecipes.getController().getRecipes().size());
            PrintWriter pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.print(jsonObject.toString());
            System.out.println("Рецепты получены успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, String> map = new HashMap<>();
        for(Recipe.Enum param: Recipe.Enum.values()){
            map.put(String.valueOf(param), request.getParameter(String.valueOf(param)));
            System.out.println(param + " " + map.get(String.valueOf(param)) + " " + request.getParameter(String.valueOf(param)));
        }
        RecipesImp recipesImp = new RecipesImp();
        int i = recipesImp.insertRecipe(new Recipe(map));
        System.out.println("после imp");

        if(i>0){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", i);
                jsonObject.put("message", Errors.SUCCESSFUL_RECIPE_CREATION.getCode());
                PrintWriter pw = response.getWriter();
                pw.write(jsonObject.toString());
                pw.print(jsonObject.toString());
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}

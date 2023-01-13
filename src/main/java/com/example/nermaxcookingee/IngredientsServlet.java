package com.example.nermaxcookingee;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "IngredientsServlet", value = "/IngredientsServlet")
public class IngredientsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        HashMap<String, Ingredient> ingredients = ControllerIngredients.getController().getIngredients();

        try {
            int i = 0;
            for(Map.Entry<String, Ingredient> entry: ingredients.entrySet()) {
                jsonObject.put("name" + Integer.toString(i), ingredients.get(entry.getKey()).getName());
                jsonObject.put("calories" + Integer.toString(i), ingredients.get(entry.getKey()).getCalories());
                jsonObject.put("proteins" + Integer.toString(i), ingredients.get(entry.getKey()).getProteins());
                jsonObject.put("fats" + Integer.toString(i), ingredients.get(entry.getKey()).getFats());
                jsonObject.put("carbohydrates" + Integer.toString(i), ingredients.get(entry.getKey()).getCarbohydrates());
                System.out.println(jsonObject.toString());
                i++;
            }
            jsonObject.put("size", ingredients.size());
            PrintWriter pw = response.getWriter();
            pw.write(jsonObject.toString());
            pw.print(jsonObject.toString());
            System.out.println("Овощи получены успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

package com.tiarintsoa.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tiarintsoa.restaurant.data.DAOFactory;
import com.tiarintsoa.restaurant.pojo.Client;
import com.tiarintsoa.restaurant.pojo.Dish;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

@WebServlet(name = "dishes-servlet", urlPatterns = "/dishes-servlet")
public class DishesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Vector<Dish> dishes = DAOFactory.getDishDAO().all();

        Gson gson = new Gson();
        String jsonStr = gson.toJson(dishes);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonStr);
    }

}

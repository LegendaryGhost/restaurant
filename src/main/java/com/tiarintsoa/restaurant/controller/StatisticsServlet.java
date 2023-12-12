package com.tiarintsoa.restaurant.controller;

import com.google.gson.Gson;
import com.tiarintsoa.restaurant.data.DAOFactory;
import com.tiarintsoa.restaurant.pojo.Statistics;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "statistics-servlet", urlPatterns = "/statistics-servlet")
public class StatisticsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Statistics statistics = DAOFactory.getStatisticsDAO().total();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(statistics);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonStr);
    }
}

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

@WebServlet(name = "date-statistics-servlet", urlPatterns = "/date-statistics-servlet")
public class DateStatisticsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int year = Integer.parseInt(req.getParameter("year"));
        int month = Integer.parseInt(req.getParameter("month"));
        Statistics statistics = DAOFactory.getStatisticsDAO().totalPerYearMonth(year, month);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(statistics);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonStr);
    }
}

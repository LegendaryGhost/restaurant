package com.tiarintsoa.restaurant.controller;

import com.google.gson.Gson;
import com.tiarintsoa.restaurant.data.DAOFactory;
import com.tiarintsoa.restaurant.pojo.SuggesterStats;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Vector;

@WebServlet(name = "suggester-stats-servlet", value = "/suggester-stats-servlet")
public class SuggesterStatsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int year = Integer.parseInt(req.getParameter("year"));
        int month = Integer.parseInt(req.getParameter("month"));

        Vector<SuggesterStats> suggesterStats = DAOFactory.getSuggesterStatsDAO().getByYearMonth(year, month);

        for (SuggesterStats singleStats : suggesterStats) {
            BigDecimal totalCommission = singleStats.getTotalCommission();
            BigDecimal valableCommission;
            if (totalCommission.doubleValue() < 1000) {
                valableCommission = BigDecimal.valueOf(0);
            } else {
                if (totalCommission.doubleValue() > 1500) {
                    valableCommission = BigDecimal.valueOf(1500);
                } else {
                    valableCommission = totalCommission;
                }
            }
            singleStats.setValableCommission(valableCommission);
        }

        Gson gson = new Gson();
        String jsonStr = gson.toJson(suggesterStats);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonStr);
    }
}

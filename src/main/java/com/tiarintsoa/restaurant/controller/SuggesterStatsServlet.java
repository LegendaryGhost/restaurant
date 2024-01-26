package com.tiarintsoa.restaurant.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        double cumul_income = 0;
        double cumul_commission = 0;
        double income = 0;
        double total_income = 0;
        double net_income = 0;


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

            cumul_income += singleStats.getCumulative_income().doubleValue();
            cumul_commission += singleStats.getCumulative_commission().doubleValue();
            income += singleStats.getRestau_income().doubleValue();
            total_income += singleStats.getTotalCommission().doubleValue();
            net_income += singleStats.getValable_commission().doubleValue();
        }

        SuggesterStats total = new SuggesterStats();
        total.setCumulative_income(BigDecimal.valueOf(cumul_income));
        total.setCumulative_commission(BigDecimal.valueOf(cumul_commission));
        total.setRestau_income(BigDecimal.valueOf(income));
        total.setTotal_commission(BigDecimal.valueOf(total_income));
        total.setValableCommission(BigDecimal.valueOf(net_income));
        total.setFirstname("Total");
        total.setLastname("Total");

        suggesterStats.add(total);

        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(suggesterStats);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonStr);
    }
}

package com.tiarintsoa.restaurant.controller;

import com.google.gson.Gson;
import com.tiarintsoa.restaurant.data.DAOFactory;
import com.tiarintsoa.restaurant.pojo.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

@WebServlet(name = "suggester-servlet", urlPatterns = "/suggester-servlet")
public class SuggesterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Vector<Client> suggesters = DAOFactory.getClientDAO().allCond("type = 'suggester'");

        Gson gson = new Gson();
        String jsonStr = gson.toJson(suggesters);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonStr);
    }
}

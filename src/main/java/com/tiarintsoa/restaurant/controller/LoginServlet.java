package com.tiarintsoa.restaurant.controller;

import com.tiarintsoa.restaurant.data.DAOFactory;
import com.tiarintsoa.restaurant.pojo.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "login-servlet", urlPatterns = "/login-servlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        Client client = DAOFactory.getClientDAO().findByPhonePwd(phone, password);

        JsonObject json = new JsonObject();

        if (client.getId() == 0) {
            json.addProperty("connected", false);
            json.addProperty("message", "Numéro ou mot de passe incorrect");
        } else {
            HttpSession session = req.getSession(true);
            session.setAttribute("id_session", client.getId());
            json.addProperty("connected", true);
            json.addProperty("message", "Vous êtes connecté !");
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(json);
    }

}

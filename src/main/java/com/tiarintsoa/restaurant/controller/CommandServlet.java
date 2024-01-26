package com.tiarintsoa.restaurant.controller;

import com.google.gson.Gson;
import com.tiarintsoa.restaurant.data.DAO;
import com.tiarintsoa.restaurant.data.DAOFactory;
import com.tiarintsoa.restaurant.pojo.Command;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

@WebServlet(name = "command-servlet", urlPatterns = "/command-servlet")
public class CommandServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idSuggesterStr = req.getParameter("id_suggester");
        Command command = new Command();
        DAO<Command> commandDAO = DAOFactory.getCommandDAO();
        Vector<String> ignoredFields = new Vector<>();
        HttpSession session = req.getSession();
        int idClient = (int) session.getAttribute("id_session"),
            idCommand;

        command.setIdBuyer(idClient);

        ignoredFields.add("id");
        ignoredFields.add("date_time");

        if (idSuggesterStr.equals("null")) {
            ignoredFields.add("id_suggester");
        } else {
            int idSuggester = Integer.parseInt(idSuggesterStr);
            command.setIdSuggester(idSuggester);
        }

        idCommand = commandDAO.create(command, ignoredFields);

        command = commandDAO.find(idCommand);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(command);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonStr);
    }
}

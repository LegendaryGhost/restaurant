package com.tiarintsoa.restaurant.controller;

import com.tiarintsoa.restaurant.data.DAOFactory;
import com.tiarintsoa.restaurant.pojo.Contain;
import com.tiarintsoa.restaurant.pojo.Dish;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "contain-servlet", urlPatterns = "/contain-servlet")
public class ContainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idDish = Integer.parseInt(req.getParameter("id_dish"));
        int idCommand = Integer.parseInt(req.getParameter("id_command"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        Dish dish = DAOFactory.getDishDAO().find(idDish);

        Contain contain = new Contain(idDish, idCommand, quantity, dish.getPrice(), dish.getProductPrice(), dish.getCommission());
        DAOFactory.getContainDAO().create(contain);
    }
}

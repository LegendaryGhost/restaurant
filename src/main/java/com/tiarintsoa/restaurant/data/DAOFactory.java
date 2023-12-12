package com.tiarintsoa.restaurant.data;

import java.sql.Connection;

import com.tiarintsoa.restaurant.pojo.*;

public class DAOFactory {

    public static Connection mysql = DBConnection.getMysqlConnection();

    public static ClientDAO getClientDAO() {
        return new ClientDAO(mysql, Client.class);
    }

    public static DAO<Dish> getDishDAO() {
        return new DAO<Dish>(mysql, Dish.class, "dish");
    }

    public static DAO<Command> getCommandDAO() {
        return new DAO<Command>(mysql, Command.class, "command");
    }

    public static DAO<Contain> getContainDAO() {
        return new DAO<Contain>(mysql, Contain.class, "contain");
    }

    public static StatisticsDAO getStatisticsDAO() {
        return new StatisticsDAO(mysql);
    }

    public static SuggesterStatsDAO getSuggesterStatsDAO() {
        return new SuggesterStatsDAO(mysql);
    }

}

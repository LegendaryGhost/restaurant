package com.tiarintsoa.restaurant.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection mysql;

    private static String mysql_hote = "localhost";
    private static String mysql_port = "3306";
    private static String mysql_bdd = "restaurant";
    private static String mysql_utilisateur = "root";
    private static String mysql_mdp = "";

    private static final String mysql_url = "jdbc:mysql://" + mysql_hote + ":" + mysql_port + "/" + mysql_bdd;

    public static final String mysql_driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getMysqlConnection() {
        if (mysql == null) {
            try {
                Class.forName(mysql_driver); // Utilisez la classe du pilote mysqlQL
                mysql = DriverManager.getConnection(mysql_url, mysql_utilisateur, mysql_mdp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mysql;
    }

}

package com.tiarintsoa.restaurant.data;

import com.tiarintsoa.restaurant.pojo.Statistics;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class StatisticsDAO {

    private final Connection connect;

    public StatisticsDAO (Connection connect) {
        this.connect = connect;
    }

    public Statistics total() {
        return totalPerYearMonth(0, 0);
    }

    public Statistics totalPerYearMonth(int year, int month) {
        Statistics obj = new Statistics();
        String query = """
                SELECT sum(contain.quantity * contain.price) AS income,
                       sum(contain.quantity * contain.commission) AS total_commission,
                       sum(contain.quantity * contain.product_price) AS expense
                FROM command
                    INNER JOIN contain
                        ON command.id = contain.id_command""";

        if (year != 0 && month != 0) {
            query += " WHERE YEAR(command.date_time) = ? AND\n" +
                    "    MONTH(command.date_time) = ?";
        }

        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            if (year != 0 && month != 0) {
                prepare.setInt(1, year);
                prepare.setInt(2, month);
            }
            ResultSet rs = prepare.executeQuery();

            while(rs.next()) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);

                    Field field = obj.getClass().getDeclaredField(columnName);

                    // Assurez-vous que le champ est accessible (y compris s'il est privé)
                    field.setAccessible(true);

                    // Assigne la valeur du ResultSet au champ de l'objet obj
                    if (columnValue != null) {
                        field.set(obj, columnValue);
                    }
                    field.setAccessible(false);
                }

                rs.close();
                prepare.close();
                return obj;
            }

            rs.close();
            prepare.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}

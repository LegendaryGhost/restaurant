package com.tiarintsoa.restaurant.data;

import com.tiarintsoa.restaurant.pojo.SuggesterStats;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class SuggesterStatsDAO {

    private final Connection connect;

    public SuggesterStatsDAO (Connection connect) {
        this.connect = connect;
    }

    public Vector<SuggesterStats> getByYearMonth(int year, int month) {
        Vector<SuggesterStats> resultat = new Vector<SuggesterStats>();
        // deviner le nom de la table à partir du nom de la classe actuelle
        // fait par getNomTable()

        // faire une requête select
        String sql = """
                SELECT
                    COALESCE(
                            (
                                SELECT
                                    COALESCE(
                                            SUM(COALESCE(ct.price, 0) * COALESCE(ct.quantity, 0)),
                                            0
                                        )
                                FROM
                                    command AS c
                                        LEFT JOIN contain AS ct
                                                  ON c.id = ct.id_command
                                WHERE
                                        c.id_suggester = suggester.id
                                  AND YEAR(c.date_time) = ?
                                  AND MONTH(c.date_time) < MONTH(command.date_time)
                                GROUP BY
                                    c.id_suggester  -- Ajout du GROUP BY pour agréger par suggester
                            ),
                            0
                        ) AS cumulative_income,
                    COALESCE(
                            (
                                SELECT
                                    COALESCE(
                                            SUM(COALESCE(ct.commission, 0) * COALESCE(ct.quantity, 0)),
                                            0
                                        )
                                FROM command AS c
                                         LEFT JOIN contain AS ct
                                                   ON c.id = ct.id_command
                                WHERE c.id_suggester = suggester.id
                                  AND YEAR(c.date_time) = ?
                                  AND MONTH(c.date_time) < MONTH(command.date_time)
                                GROUP BY
                                    c.id_suggester  -- Ajout du GROUP BY pour agréger par suggester
                            ),
                            0
                        ) AS cumulative_commission,
                    suggester.id AS id_suggester,
                    suggester.firstname AS firstname,
                    suggester.lastname AS lastname,
                    SUM(contain.price * contain.quantity) AS restau_income,
                    SUM(contain.commission * contain.quantity) AS total_commission
                FROM command
                         LEFT JOIN contain
                                    ON command.id = contain.id_command
                         LEFT JOIN client AS suggester
                                   ON command.id_suggester = suggester.id
                WHERE YEAR(command.date_time) = ? AND
                        MONTH(command.date_time) = ?
                GROUP BY suggester.id, suggester.lastname, suggester.firstname;""";

        try {
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, year);
            prepare.setInt(2, year);
            prepare.setInt(3, year);
            prepare.setInt(4, month);
            ResultSet rs = prepare.executeQuery();

            // boucler sur chaque ligne
            while(rs.next()) {
                // instancier un objet de type T
                SuggesterStats obj = new SuggesterStats();

                // boucler sur chaque colonne
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    System.out.println(columnCount);
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

                // Add to Vector
                resultat.add(obj);

            }
            rs.close();
            prepare.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;

    }

}

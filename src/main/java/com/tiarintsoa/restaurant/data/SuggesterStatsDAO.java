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
                SELECT suggester.id AS id_suggester,
                    suggester.firstname AS firstname,
                    suggester.lastname AS lastname,
                    SUM(contain.commission * contain.quantity) AS total_commission
                FROM command
                    INNER JOIN contain
                        ON command.id = contain.id_command
                    INNER JOIN client AS suggester
                        ON command.id_suggester = suggester.id
                WHERE YEAR(command.date_time) = ? AND
                    MONTH(command.date_time) = ?
                GROUP BY suggester.id, suggester.lastname, suggester.firstname;""";

        try {
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, year);
            prepare.setInt(2, month);
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
                    Object columnValue = rs.getObject(i);

                    Field field = obj.getClass().getDeclaredField(columnName);

                    // Assurez-vous que le champ est accessible (y compris s'il est privé)
                    field.setAccessible(true);

                    // Assigne la valeur du ResultSet au champ de l'objet obj
                    field.set(obj, columnValue);
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

package com.tiarintsoa.restaurant.data;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.tiarintsoa.restaurant.pojo.Client;

public class ClientDAO extends DAO<Client> {

    public ClientDAO(Connection conn, Class<Client> clazz) {
        super(conn, clazz);
    }

    public Client findByPhonePwd(String phone, String password) {
        Client obj = createInstance();
        String query = "SELECT * FROM " + getTableName() + " WHERE phone = ? AND password = ?";
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            ResultSet rs;
            prepare.setString(1, phone);
            prepare.setString(2, password);
            rs = prepare.executeQuery();
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
                    field.set(obj, columnValue);
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

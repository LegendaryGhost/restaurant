package com.tiarintsoa.restaurant.data;

import java.sql.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;

/**
 * @author <a href="mailto:rihantiana000@gmail.com">Rihantiana</a>
 * @version 1
 * Direct Access Object
 */
public class DAO<T> {
    protected Connection connect = null;
    protected String tableName = null;
    /**
     * Name of the fields in the database's table
     */
    protected Vector<String> fieldsName = null;
    protected Class<T> clazz;

    /**
     * @param clazz Class of the Plain Old Java Object used to create instances of this object
     */
    public DAO(Connection conn, Class<T> clazz){
        this.connect = conn;
        this.clazz = clazz;
    }

    public DAO(Connection conn, Class<T> clazz, String tableName){
        this.connect = conn;
        this.clazz = clazz;
        this.tableName = tableName;
    }

    protected T createInstance() {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Vector<String> getFieldsName() {
        if (fieldsName == null) {
            String query = "SELECT * FROM " + getTableName();
            try {
                PreparedStatement prepare = connect.prepareStatement(query);
                ResultSet rs;
                rs = prepare.executeQuery();
                while(rs.next()) {

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    fieldsName = new Vector<String>();

                    for (int i = 1; i <= columnCount; i++) {
                        fieldsName.add(metaData.getColumnName(i));
                    }
                }
                rs.close();
                prepare.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fieldsName;
    }

    /**
     * Get the fields of the table that should not be ignored form all the fields during the query execution
     * @param ignoredFields
     * @return
     */
    protected Vector<String> getNotIgnoredFields(Vector<String> ignoredFields) {
        Vector<String> result = new Vector<String>();
        for (String field : getFieldsName()) {
            if (!ignoredFields.contains(field)) {
                result.add(field);
            }
        }
        return result;
    }

    /**
     * Creates a new line in the table
     * @param obj
     * @return boolean
     */
    public int create(T obj) {
        return create(obj, new Vector<String>());
    }

    /**
     * Creates a new line in the table
     * @param obj
     * @return boolean
     */
    public int create(T obj, Vector<String> ignoredFields) {
        int insertedId = 0;
        Vector<String> fields_name = getNotIgnoredFields(ignoredFields);
        String insertQuery = "INSERT INTO " + getTableName() + " (";
        String queryPart2 = "(";
        // "(cin, nom) VALUES (?, ?)";
        for (int i = 0; i < fields_name.size() - 1; i++) {
            String field = fields_name.get(i);
            if (!ignoredFields.contains(field)) {
                insertQuery += field + ", ";
                queryPart2 += "?, ";
            }
        }

        queryPart2 += "?)";
        insertQuery += fields_name.get(fields_name.size() - 1) +  ") VALUES " + queryPart2;

        try {
            PreparedStatement preparedState = connect.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            // Remplir les valeurs des paramètres de la requête d'insertion
            // preparedState.setInt(1, obj.getCin());
            // preparedState.setString(2, obj.getNom());
            for (int i = 0; i < fields_name.size() ; i++) {
                String field_name = fields_name.get(i);
                Field field = obj.getClass().getDeclaredField(field_name);

                // Assurez-vous que le champ est accessible (y compris s'il est privé)
                field.setAccessible(true);

                // Assigne la valeur du ResultSet au champ de l'objet obj
                if (field.get(obj) instanceof Date date) {
                    preparedState.setObject(i + 1, new java.sql.Date(date.getTime()));
                } else {
                    preparedState.setObject(i + 1, field.get(obj));
                }

                field.setAccessible(false);
            }

            preparedState.execute();

            // Récupère le nouveau CIN
            ResultSet generatedKeys = preparedState.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
            }

            // Fermer le statement
            preparedState.close();
            generatedKeys.close();

            return insertedId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return insertedId;
    }

    /**
     * Delete lines from the table
     * @param obj
     * @return boolean
     */
    public boolean delete(T obj) {
        // Not implemented yet
        return false;
    }

    /**
     * Update lines from the table
     * @param obj
     * @return
     */
    public boolean update(T obj) {
        return update(obj, "id");
    }

    /**
     * Update lines from the table
     * @param obj
     * @return
     */
    public boolean update(T obj, String col_id_name) {
        return update(obj, col_id_name, new Vector<String>());
    }

    /**
     * Update lines from the table
     * @param obj
     * @return
     */
    public boolean update(T obj, String col_id_name, Vector<String> ignoredFields) {
        if (!ignoredFields.contains(col_id_name)) {
            ignoredFields.add(col_id_name);
        }
        Vector<String> fields_name = getNotIgnoredFields(ignoredFields);

        // UPDATE ma_table
        // SET colonne1 = 'nouvelle_valeur_colonne1',
        //     colonne2 = 'nouvelle_valeur_colonne2',
        //     colonne3 = 'nouvelle_valeur_colonne3'
        // WHERE id = votre_id;

        String updateQuery = "UPDATE " + getTableName() + " SET ";
        for (int i = 0; i < fields_name.size() - 1; i++) {
            String field = fields_name.get(i);
            updateQuery += field + " = ?, ";
        }

        updateQuery += fields_name.lastElement() + " = ? WHERE " + col_id_name + " = ?";

        try {
            PreparedStatement preparedState = connect.prepareStatement(updateQuery);

            // Remplir les valeurs des paramètres de la requête d'insertion
            // preparedState.setInt(1, obj.getCin());
            // preparedState.setString(2, obj.getNom());
            for (int i = 0; i < fields_name.size() ; i++) {
                String field_name = fields_name.get(i);
                Field field = obj.getClass().getDeclaredField(field_name);

                // Assurez-vous que le champ est accessible (y compris s'il est privé)
                field.setAccessible(true);

                // Assigne la valeur du ResultSet au champ de l'objet obj
                if (field.get(obj) instanceof Date date) {
                    preparedState.setObject(i + 1, new java.sql.Date(date.getTime()));
                } else {
                    preparedState.setObject(i + 1, field.get(obj));
                }

                field.setAccessible(false);
            }

            Field field = obj.getClass().getDeclaredField(col_id_name);
            field.setAccessible(true);
            preparedState.setObject(fields_name.size() + 1, field.get(obj));
            field.setAccessible(false);

            preparedState.executeUpdate();

            // Fermer le statement
            preparedState.close();

            return true; // La transaction a été créée avec succès
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Find a specific line from the id column of the table
     * @param id
     * @return T
     */
    public T find(int id) {
        return find(id, "id");
    }

    /**
     * Find a specific line from the id column of the table
     * @param id
     * @param col_id_name name of the primary key column
     * @return T
     */
    public T find(int id, String col_id_name) {
        T obj = createInstance();
        String query = "SELECT * FROM " + getTableName() + " WHERE " + col_id_name + " = ?";
        try {
            PreparedStatement prepare = connect.prepareStatement(query);
            ResultSet rs;
            prepare.setInt(1, id);
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

    /**
     * Get all lines inside of the table
     * @return T
     */
    public Vector<T> all() {
        return allCond(null);
    }

    /**
     * Get all lines inside of the table according to the condition
     * @return T
     */
    public Vector<T> allCond(String condition) {
        Vector<T> resultat = new Vector<T>();
        // deviner le nom de la table à partir du nom de la classe actuelle
        // fait par getNomTable()

        // faire une requête select
        String sql = "SELECT * FROM " + getTableName();
        if (condition != null) {
            sql += " WHERE " + condition;
        }

        try {
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet rs = prepare.executeQuery();

            // boucler sur chaque ligne
            while(rs.next()) {
                // instancier un objet de type T
                T obj = createInstance();

                // boucler sur chaque colonne
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

                // ajouter au Vector
                resultat.add(obj);

            }
            rs.close();
            prepare.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }

    public String getTableName() {
        String className;
        if(tableName == null) {
            className = getClass().getSimpleName();
            tableName = className.replace("DAO", "").toLowerCase();
        }
        return tableName;
    }

}

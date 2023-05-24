package service;


import entity.Type;

import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Asma Laaribi
 */
public class TypeService {

    private Connection connection;

    public TypeService() {
        connection = DataSource.getInstance().getCnx();
    }

    public Boolean insertType(Type type) {
        String requete = "insert into type(nom_type) values (?)";
        Boolean resu = false;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, type.getNom_type());
            preparedStatement.executeUpdate();
            resu = true;
        } catch (SQLException ex) {
            Logger.getLogger(TypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resu;
    }

    public List<Type> readTypes() {
        String requete = "select * from type";
        List<Type> typeList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                Type type = new Type();
                type.setId(resultSet.getInt("id"));
                type.setNomType(resultSet.getString("nom_type"));

                typeList.add(type);

            }
        } catch (SQLException ex) {
            Logger.getLogger(TypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typeList;
    }

    public Boolean deleteType(int typeId) {
        String requete = "delete from type where id = " + typeId;
        Boolean resu = false;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(requete);
            resu = true;
        } catch (SQLException ex) {
            Logger.getLogger(TypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resu;
    }
    public Type retournerType(int idtype) throws SQLException {
        Type type = new Type();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM type where `id` = " + idtype);
        while (res.next()) {
            int id = res.getInt("id");
            String nom = res.getString("nom_type");
            type.setId(id);
            type.setNomType(nom);
            
        }
        return type;
    }

}

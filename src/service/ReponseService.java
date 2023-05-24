package service;


import entity.Reclamation;
import entity.Reponse;

import utils.DataSource;

import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Asma Laaribi
 */
public class ReponseService {

    private Connection connection;

    public ReponseService() {
        connection = DataSource.getInstance().getCnx();
    }

    public Boolean insertReponse(Reponse reponse) {
        String requete = "insert into reponse(reclamation_id, message,date_reponse) values (?, ?, ?)";
        Boolean res = false;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, reponse.getReclamation_id().getId());
            preparedStatement.setString(2, reponse.getMessage());
            preparedStatement.setDate(3, reponse.getDate_reponse());

            preparedStatement.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(ReponseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Reponse getReponse(int recId) {
        Reponse reponse = new Reponse();
        String requete = "select DISTINCT * from reponse where reclamation_id=" + recId + " LIMIT 1;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {

                reponse.setId(resultSet.getInt("id"));
                reponse.setMessage(resultSet.getString("message"));
                reponse.setDate_reponse(resultSet.getDate("date_reponse"));
                ReclamationService reclamationService = new ReclamationService();
                Reclamation rec = reclamationService.retournerReclamation(resultSet.getInt("reclamation_id"));
                reponse.setReclamation_id(rec);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReponseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reponse;
    }

    public void updateReponse(Reponse reponse) {
        try {
            String requete = "update reponse set message=? , date_reponse=? where id='" + reponse.getId()+ "'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, reponse.getMessage());
            preparedStatement.setDate(2, reponse.getDate_reponse());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReponseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  

}

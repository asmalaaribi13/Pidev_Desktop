package service;

import service.*;
import entity.Reclamation;
import entity.Type;
import entity.User;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Asma Laaribi
 */
public class ReclamationService {

    private Connection connection;

    public ReclamationService() {
        connection = DataSource.getInstance().getCnx();
    }

    public Boolean insertReclamation(Reclamation reclamation) {
        String requete = "insert into reclamation(user_id, type_id,description,date_reclamation,etat_reclamation,libelle,photo) values (?, ?, ?, ?, ?, ?,?)";
        Boolean res = false;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, reclamation.getUser().getIdUser());
            preparedStatement.setInt(2, reclamation.getType().getId());
            preparedStatement.setString(3, reclamation.getDescription());
            preparedStatement.setDate(4, reclamation.getDate_reclamation());
            preparedStatement.setString(5, reclamation.getEtat_reclamation());
            preparedStatement.setString(6, reclamation.getLibelle());
            preparedStatement.setString(7, reclamation.getPhoto());
            preparedStatement.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public List<Reclamation> readAllReclamations() {
        String requete = "select * from reclamation";
        List<Reclamation> reclamationList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setEtat_reclamation(resultSet.getString("etat_reclamation"));
                reclamation.setLibelle(resultSet.getString("libelle"));
                reclamation.setPhoto(resultSet.getString("photo"));
                reclamation.setDate_reclamation(resultSet.getDate("date_reclamation"));
                UserService userService = new UserService();
                TypeService typeService = new TypeService();
                User user = userService.retournerUser(resultSet.getInt("user_id"));
                reclamation.setUser(user);
                Type type = typeService.retournerType(resultSet.getInt("type_id"));
                reclamation.setType(type);

                reclamationList.add(reclamation);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reclamationList;
    }

    public List<Reclamation> readAllMyReclamations(int userrid) {
        String requete = "select * from reclamation WHERE user_id=" + userrid;
        List<Reclamation> reclamationList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setEtat_reclamation(resultSet.getString("etat_reclamation"));
                reclamation.setLibelle(resultSet.getString("libelle"));
                reclamation.setPhoto(resultSet.getString("photo"));
                reclamation.setDate_reclamation(resultSet.getDate("date_reclamation"));
                UserService userService = new UserService();
                TypeService typeService = new TypeService();
                User user = userService.retournerUser(resultSet.getInt("user_id"));
                reclamation.setUser(user);
                Type type = typeService.retournerType(resultSet.getInt("type_id"));
                reclamation.setType(type);

                reclamationList.add(reclamation);

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return reclamationList;
    }

    public List<Reclamation> readRecTraites() {
        String requete = "select * from reclamation where etat_reclamation='Traitée'";
        List<Reclamation> reclamationList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setEtat_reclamation(resultSet.getString("etat_reclamation"));
                reclamation.setLibelle(resultSet.getString("libelle"));
                reclamation.setPhoto(resultSet.getString("photo"));
                reclamation.setDate_reclamation(resultSet.getDate("date_reclamation"));
                UserService userService = new UserService();
                TypeService typeService = new TypeService();
                User user = userService.retournerUser(resultSet.getInt("user_id"));
                reclamation.setUser(user);
                Type type = typeService.retournerType(resultSet.getInt("type_id"));
                reclamation.setType(type);
                reclamationList.add(reclamation);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reclamationList;
    }

    public List<Reclamation> readRecEnAttente() {
        String requete = "select * from reclamation where etat_reclamation='en cours'";
        List<Reclamation> reclamationList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setEtat_reclamation(resultSet.getString("etat_reclamation"));
                reclamation.setLibelle(resultSet.getString("libelle"));
                reclamation.setPhoto(resultSet.getString("photo"));
                reclamation.setDate_reclamation(resultSet.getDate("date_reclamation"));
                UserService userService = new UserService();
                TypeService typeService = new TypeService();
                User user = userService.retournerUser(resultSet.getInt("user_id"));
                reclamation.setUser(user);
                Type type = typeService.retournerType(resultSet.getInt("type_id"));
                reclamation.setType(type);
                reclamationList.add(reclamation);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reclamationList;
    }

    public ObservableList<Reclamation> displayReclamationOrdredByDescDate() {
        //instance liste de Reclamation
        ObservableList<Reclamation> list = FXCollections.observableArrayList();
        //ecrire requete sql pour recuperer les Reclamation
        String req = "SELECT r.* FROM reclamation r ORDER BY r.date_reclamation DESC;";

        try {
            //creation de statement
            Statement st = connection.createStatement();
            // executer la requette et recuperer le resultat 
            ResultSet rs = st.executeQuery(req);
            // tant que on a un resultat
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setEtat_reclamation(rs.getString("etat_reclamation"));
                reclamation.setLibelle(rs.getString("libelle"));
                reclamation.setPhoto(rs.getString("photo"));
                reclamation.setDate_reclamation(rs.getDate("date_reclamation"));
                UserService userService = new UserService();
                TypeService typeService = new TypeService();
                User user = userService.retournerUser(rs.getInt("user_id"));
                reclamation.setUser(user);
                Type type = typeService.retournerType(rs.getInt("type_id"));
                reclamation.setType(type);
                list.add(reclamation);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public ObservableList<Reclamation> displayReclamationOrdredByAscDate() {
        //instance liste de Reclamation
        ObservableList<Reclamation> list = FXCollections.observableArrayList();
        //ecrire requete sql pour recuperer les Reclamation
        String req = "SELECT r.* FROM reclamation r ORDER BY r.date_reclamation ASC;";

        try {
            //creation de statement
            Statement st = connection.createStatement();
            // executer la requette et recuperer le resultat 
            ResultSet rs = st.executeQuery(req);
            // tant que on a un resultat
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setEtat_reclamation(rs.getString("etat_reclamation"));
                reclamation.setLibelle(rs.getString("libelle"));
                reclamation.setPhoto(rs.getString("photo"));
                reclamation.setDate_reclamation(rs.getDate("date_reclamation"));
                UserService userService = new UserService();
                TypeService typeService = new TypeService();
                User user = userService.retournerUser(rs.getInt("user_id"));
                reclamation.setUser(user);
                Type type = typeService.retournerType(rs.getInt("type_id"));
                reclamation.setType(type);
                list.add(reclamation);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
    

    
    public Map<String, Integer> countReclamationByType() {
    Map<String, Integer> reclamationByType = new HashMap<>();
    try {
        PreparedStatement pt = connection.prepareStatement("SELECT type_id, COUNT(*) FROM reclamation GROUP BY type_id");
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            String typeId = rs.getString("type_id");
            int count = rs.getInt("COUNT(*)");
            reclamationByType.put(typeId, count);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return reclamationByType;
}

//    public int countReclamationByType() {
//        try {
//            PreparedStatement pt = connection.prepareStatement("SELECT type_id, COUNT(*) FROM reclamation GROUP BY type_id");
//            ResultSet rs = pt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//            return 0;
//        } catch (SQLException ex) {
//            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return 0;
//
//    }

    public void updateReclamation(Reclamation reclamation) {
        try {
            String requete = "update reclamation set  etat_reclamation=? where id='" + reclamation.getId() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, reclamation.getEtat_reclamation());
            preparedStatement.executeUpdate();
            System.out.println("updated");
        } catch (SQLException ex) {
            System.out.println("cannot updtae");
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean userUpdateReclamation(Reclamation reclamation) {
        Boolean res = false;
        try {
            String requete = "update reclamation set description=?,date_reclamation=?,etat_reclamation=?,libelle=? where id='" + reclamation.getId() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, reclamation.getDescription());
            preparedStatement.setDate(2, reclamation.getDate_reclamation());
            preparedStatement.setString(3, reclamation.getEtat_reclamation());
            preparedStatement.setString(4, reclamation.getLibelle());

            preparedStatement.executeUpdate();
            System.out.println("updated");
            res = true;
        } catch (SQLException ex) {
            System.out.println("cannot update");
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Boolean deleteReclamation(int reclamationId) {
        String requete = "delete from reclamation where id = " + reclamationId;
        Boolean res = false;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(requete);
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Reclamation retournerReclamation(int idReclamation) throws SQLException {
        Reclamation reclamation = new Reclamation();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Reclamation where `id` = " + idReclamation);
        while (rs.next()) {

            reclamation.setId(rs.getInt("id"));
            reclamation.setDescription(rs.getString("description"));
            reclamation.setEtat_reclamation(rs.getString("etat_reclamation"));
            reclamation.setLibelle(rs.getString("libelle"));
            reclamation.setPhoto(rs.getString("photo"));
            reclamation.setDate_reclamation(rs.getDate("date_reclamation"));
            UserService userService = new UserService();
            TypeService typeService = new TypeService();
            User user = userService.retournerUser(rs.getInt("user_id"));
            reclamation.setUser(user);
            Type type = typeService.retournerType(rs.getInt("type_id"));
            reclamation.setType(type);
        }
        return reclamation;
    }

}

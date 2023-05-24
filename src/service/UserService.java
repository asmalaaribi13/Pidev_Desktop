/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

/**
 *
 * @author sassi
 */
public class UserService implements IUser<User>{
    private Connection conn;

    public UserService() {
        conn=DataSource.getInstance().getCnx();
    }
    

    @Override
    public void insert(User p) {
         String requete="insert into user(nomUser,prenomUser,pwd,email,numTel,typeUser,photo) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, p.getNomUser());
            pst.setString(2, p.getPrenomUser());
            pst.setString(3, p.getPwd());
            pst.setString(4, p.getEmail());
            pst.setInt(5, p.getNumTel());
            pst.setString(6, p.getTypeUser());
            pst.setString(7, p.getPhoto());
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    @Override
    public void delete(User t) {
           String requete="delete from user where idUser ="+t.getIdUser();
        try {
           
            Statement st=conn.createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(User p) {
String requete="update user set nomUser=?, prenomUser=?, pwd=?, email=?, numTel=?, typeUser=? ,photo=? where idUser=?";
   try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, p.getNomUser());
            pst.setString(2, p.getPrenomUser());
            pst.setString(3, p.getPwd());
            pst.setString(4, p.getEmail());
            pst.setInt(5, p.getNumTel());
            pst.setString(6, p.getTypeUser());
            pst.setString(7, p.getPhoto());
            pst.setInt(8, p.getIdUser());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
    public List<User> readAll() {
          String requete="select * from user";
        List<User> list=new ArrayList<>();
        
        try {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                User p=new User(rs.getInt("idUser"),
                        rs.getString("nomUser"),
                        rs.getString("prenomUser"),
                        rs.getString("pwd"),
                        rs.getString("email"),
                        rs.getInt("numTel"),
                        rs.getString("typeUser"),
                        rs.getString("photo"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ObservableList readAllUsers() {
        String requete = "select * from user";
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            while (resultSet.next()) {
                int id = resultSet.getInt("IdUser");
                String nom = resultSet.getString("NomUser");
                String prenom = resultSet.getString("PrenomUser");
                User user = new User(id, nom,prenom);
                
                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }
    

    @Override
    public User readByID(int idUser) {
String requete ="select * from user where idUser="+idUser;
    User p = null;
    try {
        Statement st=conn.createStatement();
        ResultSet rs= st.executeQuery(requete);
        if (rs.next()) {
            p = new User(rs.getInt("idUser"),
                        rs.getString("nomUser"),
                        rs.getString("prenomUser"),
                        rs.getString("pwd"),
                        rs.getString("email"),
                        rs.getInt("numTel"),
                        rs.getString("typeUser"),
                        rs.getString("photo"));}
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return p;
    }
    
    
//    public User getUserByEmail(String email) throws SQLException {
//    User u1 = null;
//    String query = "SELECT * FROM user WHERE email = ?";
//    try (Connection connection = DataSource.getInstance().getCnx();
//         PreparedStatement statement = connection.prepareStatement(query)) {
//        statement.setString(1, email);
//        try (ResultSet resultSet = statement.executeQuery()) {
//            if (resultSet.next()) {
//                int id = resultSet.getInt("idUser");
//                String nom = resultSet.getString("nomUser");
//                String prenom = resultSet.getString("prenomUser");
//                int num_tel = resultSet.getInt("numTel");
//                String mdp = resultSet.getString("pwd");
//                String type = resultSet.getString("typeUser");
//                String photo = resultSet.getString("photo");
//                u1 = new User(id, nom, prenom, mdp, email, num_tel, type, photo);
//            }
//        }
//    } catch (SQLException e) {
//        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, e.getMessage(), e);
//        throw e;
//    }
//    return u1;
//}

    @Override
    public List<User> readIdNom() {
          String requete="select idUser,nomUser from user";
        List<User> list=new ArrayList<>();
        
        try {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                User p=new User(rs.getInt("idUser"),rs.getString("nomUser") );
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
        
   }
    
    
    public User getUserByEmail(String email) throws SQLException {

User t = null;
    String query = "SELECT * FROM user WHERE email = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            t = new User(
             rs.getInt("idUser"),
            rs.getString("nomUser"),
             rs.getString("prenomUser"),
            rs.getString("pwd"),
             rs.getString("email"),
            rs.getInt("numTel"),
           rs.getString("typeUser"),
            rs.getString("photo"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return t;
}

    public void setMotDePasse(int idUser,String nouveauMotDePasse) {
         String req="update user set pwd=? where idUser=?";
        try {
           
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, nouveauMotDePasse);
        pst.setInt(2, idUser);
        pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User readByEmail(String e) {
         String requete = "select * from user where email=?";
    User u1 = null;
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setString(1, e);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("idUser");
            String nom = rs.getString("nomUser");
            String prenom = rs.getString("prenomUser");
            String mdp = rs.getString("pwd");
            int num_tel = rs.getInt("numTel");
            String type = rs.getString("typeUser");
            String photo = rs.getString("photo");
            u1 = new User(id, nom, prenom, mdp, e, num_tel, type, photo);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return u1;
//User t = null;
//    String query = "SELECT * FROM user WHERE email = ?";
//    try (PreparedStatement stmt = conn.prepareStatement(query)) {
//        stmt.setString(1, e);
//        ResultSet rs = stmt.executeQuery();
//        while (rs.next()) {
//            t = new User(
//             rs.getInt("idUser"),
//            rs.getString("nomUser"),
//             rs.getString("prenomUser"),
//            rs.getString("pwd"),
//             rs.getString("email"),
//            rs.getInt("numTel"),
//           rs.getString("typeUser"),
//            rs.getString("photo"));
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    return t;
    }

    public User readByNumtel(String phoneNumber) {
        
          String requete = "select * from user where numTel=?";
    User u1 = null;
    try {
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setString(1, phoneNumber);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("idUser");
            String nom = rs.getString("nomUser");
            String prenom = rs.getString("prenomUser");
            String mdp = rs.getString("pwd");
            String email=rs.getString("email");
            int num_tel = rs.getInt("numTel");
            String type = rs.getString("typeUser");
            String photo = rs.getString("photo");
            u1 = new User(id, nom, prenom, mdp, email, num_tel, type, photo);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
        System.out.println("utttttt"+u1);
    return u1;
    }
    public void ChangerNom(String nom,int id) {
          String req="update user set nomUser=? where idUser=?";
        try {
           
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, nom);
        pst.setInt(2, id);
        pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ChangerMail(String mail,int id) {
          String req="update user set Email=? where idUser=?";
        try {
           
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, mail);
        pst.setInt(2, id);
        pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void ChangerNumTel(String num,int id) {
          String req="update user set NumTel=? where idUser=?";
        try {
           
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, num);
        pst.setInt(2, id);
        pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ChangerPhoto(String absolutePath, int id) {
          String req="update user set Photo=? where idUser=?";
        try {
           
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, absolutePath);
        pst.setInt(2, id);
        pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isEmailUsed(String email) {
         String req="SELECT COUNT(*) AS count FROM user WHERE email = ?";
          try {
           
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, email);
         ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("count");
            return count > 0;
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
    }
    public User retournerUser(int idUser) throws SQLException {
        User user = new User();
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM user where `idUser` = " + idUser);
        while (res.next()) {
            int id = res.getInt("IdUser");
            String nom = res.getString("NomUser");
            String prenom = res.getString("PrenomUser");
            user.setIdUser(id);
            user.setNomUser(nom);
            user.setPrenomUser(prenom);
        }
        return user;
    }
    
}

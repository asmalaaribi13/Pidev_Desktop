package gui;

//import Entite.User;
import entity.Panier;
import entity.Produit;
import entity.User;
//import entity.Categorie;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
//import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.ServiceProduit;
import utils.DataSource;
//import java.io.FileOutputStream;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import service.PanierService;
//import javafx.scene.control.DatePicker;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.scene.transform.Scale;
//import javafx.util.Duration;
//import javax.swing.JFileChooser;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MarcheController implements Initializable {

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtQte;

    @FXML
    private TextField txtDesc;

    @FXML
    private ImageView txt_image;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrix;

    private Connection conn;

    private List<Produit> produitList;

    private int currentIndex;
    @FXML
    private Button suiv;
    @FXML
    private Button pred;
//    @FXML
//    private Button exp;
    @FXML
    private TextField txtcteg;

    public MarcheController() {
        conn = DataSource.getInstance().getCnx();
        produitList = new ArrayList<>();
        currentIndex = 0;

    }

    public void ShowEvent(int index) {
        Produit p = produitList.get(index);
        txtNom.setText(p.getNomProduit());
        txtDate.setText(p.getDateProduit().toString());
        txtDesc.setText(p.getDescProduit());
        txtQte.setText(String.valueOf(p.getQte()));
        txtPrix.setText(String.valueOf(p.getPrixProduit()) + " DT");
        txtcteg.setText(String.valueOf(p.getNomCategorie()));

        Image image = new Image(new File(p.getPhotoP()).toURI().toString());
        System.out.println(image);
        txt_image.setImage(image);
        currentIndex = index;
        ServiceProduit ser = new ServiceProduit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//System.out.println("yyyyyyyyyyyyyyy"+currentIndex); 
        System.out.println("oussema"+userConnected);
        ServiceProduit ser = new ServiceProduit();
        //calculer nombre des evenements disponible

        String requete = "select  produit.*, NomCategorie from produit join categorie on categorie.IdCategorie = produit.IdCategorie";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                int Id = rs.getInt("IdProduit");
                String Nom = rs.getString("NomProduit");
                String Desc = rs.getString("DescProduit");
                LocalDate Date = rs.getDate("DateProduit").toLocalDate();
                float Prix = rs.getFloat("PrixProduit");
                int Qte = rs.getInt("Qte");
                String photo = rs.getString("photoP");
                String categ = rs.getString("NomCategorie");

                Produit p = new Produit(Id, Nom, Desc, Date, Prix, Qte, photo, categ);
                produitList.add(p);
                System.out.println(p);
            }
            //show the first event in the list index=0
            ShowEvent(0);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void pred(ActionEvent event) {
        if (currentIndex > 0) {
            ShowEvent(currentIndex - 1);
        }
    }

    @FXML
    void suiv(ActionEvent event) {
        if (currentIndex < produitList.size() - 1) {
            ShowEvent(currentIndex + 1);
        }
    }

//    @FXML
//    private void export(ActionEvent event) {
//        
//        JFileChooser jChoose = new JFileChooser();
//        int option = jChoose.showSaveDialog(jChoose);
//        if (option==JFileChooser.APPROVE_OPTION) {
//            String name = jChoose.getSelectedFile().getName();
//            String path = jChoose.getSelectedFile().getParentFile().getPath();
//            String file = path+ "\\"+ name+".xls";
//        
//      ObservableList<ObservableList<String>> data = FXCollections.observableArrayList(); 
//      String requete = "select * from produit";
//        try {
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(requete);
//           
//            while (rs.next()) {
//                ObservableList<String> row = FXCollections.observableArrayList();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    row.add(rs.getString(i));
//                }
//                data.add(row);
//            }
//            
//            // Create workbook and sheet
//            Workbook workbook = new XSSFWorkbook();
//            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Sheet1");
//            // Add data to sheet
//            int rowIndex = 0;
//            for (ObservableList<String> row : data) {
//                Row excelRow = sheet.createRow(rowIndex++);
//                int cellIndex = 0;
//                for (String value : row) {
//                    Cell excelCell = excelRow.createCell(cellIndex++);
//                    excelCell.setCellValue(value);
//                }
//            }
//            
//            // Write workbook to output stream
//            FileOutputStream fos = new FileOutputStream("Test.xlsx");
//            workbook.write(fos);
//            fos.close();
//
//            
//
//            System.out.println("Data exported to Excel successfully.");
//            
//            
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//      
//        }
//    }
    
            
  static User userConnected;
    public void setUser(User user) {
        this.userConnected = user;
        System.out.println("userrrr"+user);
    }

    @FXML
    private void ajouterProduit(ActionEvent event) {
              
     
        Produit p= produitList.get(currentIndex);
        System.out.println("produitttttt"+userConnected.getIdUser());
   Panier r=new Panier(userConnected.getIdUser(), p.getIdProduit(), 1, p.getNomProduit(), Double.valueOf(p.getPrixProduit()));
        
       PanierService PS= new PanierService();
        PS.ajouter(r);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Félicitation");
        alert.setHeaderText(null);
        alert.setContentText("Ajout au panier avec succès !");
        alert.showAndWait();   
    }
    public void test (){
        System.out.println("iiiiiii"+userConnected);
    }
    }
    
     



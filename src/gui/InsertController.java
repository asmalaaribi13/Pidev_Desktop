/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import java.net.URL;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import entity.Produit;
import service.ServiceProduit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import javafx.scene.control.DatePicker;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author Brahim
 */
public class InsertController implements Initializable {

    
    @FXML
    private TextField txtNom;
    @FXML
    private Button btnExit;
    @FXML
    private TextField txtDesc;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtQte;
    @FXML
    private TextField txtPrix;
    @FXML
    private TableView<Produit> T_View_Produit;
    
    @FXML
    private Button exp;
    
    @FXML
    private TableColumn<Produit, LocalDate> txt_Date;

    @FXML
    private TableColumn<Produit, String> txt_Desc;

    @FXML
    private TableColumn<Produit, Integer> txt_ID;

    @FXML
    private TableColumn<Produit, String> txt_Nom;

    @FXML
    private TableColumn<Produit, String> txt_Photo;

    @FXML
    private TableColumn<Produit, Float> txt_Prix;

    @FXML
    private TableColumn<?, ?> txt_Qte;

    @FXML
    private TableColumn<Produit, Integer> txt_cat;
    @FXML
    private Button imagefx;
    @FXML
    private ImageView tPhotoP;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnAdd3;
    private Object PhotoP;
    String path=""; 
    @FXML
    private ComboBox<String> combocat;
    @FXML
    private Button btnrech;
    @FXML
    private TextField txt_rech;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combocat.setItems(FXCollections.observableArrayList("HOMME","FEMME"));
                   
    } 
    
    
//    @FXML
//    void Rechercher(ActionEvent event) {
//          String requete="select * from produit where NomProduit=?";
//          try {
//            ps=con.prepareStatement(requete);
//            ps.setString(1,rech.getText());
//        } catch (Exception e) {
//        }
//    }
    
    
    @FXML
    void ajout_image(ActionEvent event) throws IOException {
         FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        path=selectedFile.getAbsolutePath();
        String imagePath = selectedFile.getAbsolutePath().replace("\\", "/");

        
        imagefx.setText(imagePath);
    }


      
    }
    
   
    

    @FXML
    private void Exit(ActionEvent event) {
    }

    @FXML
    private void Modifier_Produit(ActionEvent event) {
         if (T_View_Produit.getSelectionModel().getSelectedItem() != null) {
        // Récupérer les données de l'événement sélectionné
        Produit selectedEvent = T_View_Produit.getSelectionModel().getSelectedItem();
        // Charger la vue de modification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Modifier.fxml"));
            Parent root = (Parent) loader.load();
            ModifierController controller = loader.getController();
            controller.setEvent(selectedEvent);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       }
        
    }

    @FXML
    private void Supprimer_Produit(ActionEvent event) {
         if (T_View_Produit.getSelectionModel().getSelectedItem() != null) {
        // Récupérer les données de l'événement sélectionné
        Produit selectedEvent = T_View_Produit.getSelectionModel().getSelectedItem();
       ServiceProduit ser = new ServiceProduit();
        ser.supprime(selectedEvent);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(null);
        alert.setContentText("Suppression avec succès !");
        alert.showAndWait();
     
    } }

    @FXML
    private void Afficher_Produit(ActionEvent event) {
        //ServiceProduit ps = new ServiceProduit();
        // List<Produit> users = ServiceProduit.readIdNom();
           
        //ObservableList<Produit> userData = FXCollections.observableArrayList(users);
        // userComboBox.setItems(userData);
        
        
        ServiceProduit ser = new ServiceProduit();
        // Appeler la méthode "readAll()" de "EventService" pour récupérer la liste des événements
        List<Produit> Produits = ser.readAll();
        // Convertir la liste en une "ObservableList" pour pouvoir l'utiliser avec "TableView"
         ObservableList<Produit> eventData = FXCollections.observableArrayList(Produits);
        // Ajouter les données à la table
        // eventData.addAll(Produits);
        T_View_Produit.setItems(eventData);
        
            
            txt_ID.setCellValueFactory(new PropertyValueFactory<>("idProduit"));
            txt_Nom.setCellValueFactory(new PropertyValueFactory<>("NomProduit"));
            txt_Desc.setCellValueFactory(new PropertyValueFactory<>("DescProduit"));
            txt_Date.setCellValueFactory(new PropertyValueFactory<>("DateProduit"));
            txt_Prix.setCellValueFactory(new PropertyValueFactory<>("PrixProduit"));
            txt_Qte.setCellValueFactory(new PropertyValueFactory<>("Qte"));
            txt_Photo.setCellValueFactory(new PropertyValueFactory<>("PhotoP"));
            txt_cat.setCellValueFactory(new PropertyValueFactory<>("NomCategorie"));
    }

    @FXML
    private void Ajouter_Produit(ActionEvent event) {
        ServiceProduit ser = new ServiceProduit();
        Produit p = new Produit();
          
//        if (txtNom.getText().isEmpty() || txtDesc.getText().isEmpty() || txtDate.getValue()== null || txtPrix.getText().isEmpty() || txtQte.getText().isEmpty() ) {
//    // Afficher un message d'erreur
//    Alert alert = new Alert(Alert.AlertType.ERROR);
//    alert.setTitle("Erreur");
//    alert.setHeaderText(null);
//    alert.setContentText("Veuillez remplir tous les champs obligatoires.");
//    alert.showAndWait();
//    return;
//    }else if (!(txtPrix.getText().toString().matches("[0-100000]+"))|| !(txtQte.getText().toString().matches("[1-100000]+")) )
//       {Alert alert = new Alert(Alert.AlertType.INFORMATION);    
//       alert.setTitle("Information Dialog");
//                    alert.setHeaderText(null);
//                    alert.setContentText("le prix et le quantite des produits doit etre des nombres");
//                    alert.show();
//                    refresh();} 
           
      
        
        p.setNomProduit((txtNom.getText()));
        p.setPrixProduit(Float.parseFloat(txtPrix.getText()));
        p.setDescProduit((txtDesc.getText()));
        p.setQte(Integer.parseInt(txtQte.getText()));
        p.setDateProduit(txtDate.getValue());
        p.setNomCategorie(combocat.getValue());
        p.setPhotoP(imagefx.getText());
        
         ser.ajouter(p);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("produit ajouté avec succes!");
                    alert.show();
                    refresh();
        System.out.println(p.toString());
         
          txtNom.setText("");
          txtDesc.setText("");
          txtPrix.setText("");
          txtQte.setText("");
          
           }

//    @FXML
//    private void Rechercher(ActionEvent event) {
//        
//        List<Produit> list=new ArrayList<>();
//        ServiceProduit ser=new ServiceProduit();
//        if(txt_rech.getText().length()==0)
//        list=ser.readAll();
//        else
//        list.add(ser.readByID(Integer.parseInt(txt_rech.getText())));
//        ObservableList<Produit> obs=FXCollections.observableArrayList(list);
//        IdProduit.setCellValueFactory(new PropertyValueFactory<Produit ,Integer>("IdProduit"));
//        NomProduit.setCellValueFactory(new PropertyValueFactory<Produit ,String>("NomProduit"));
//        pts_depart.setCellValueFactory(new PropertyValueFactory<Produit ,String>("DescProduit"));
//        pts_arrive.setCellValueFactory(new PropertyValueFactory<Produit ,LocalDate>("DateProduit"));
//        T_View_Produit.setItems(obs);
//    }
//    

    public InsertController() {
        conn = DataSource.getInstance().getCnx();
        produitList = new ArrayList<>();
        currentIndex = 0;

    }
    
    
    
 private Connection conn;
    
    private List<Produit> produitList;
    
    private int currentIndex;
    @FXML
    void export(ActionEvent event) {

        JFileChooser jChoose = new JFileChooser();
        int option = jChoose.showSaveDialog(jChoose);
        if (option==JFileChooser.APPROVE_OPTION) {
            String name = jChoose.getSelectedFile().getName();
            String path = jChoose.getSelectedFile().getParentFile().getPath();
            String file = path+ "\\"+ name+".xls";
        
      ObservableList<ObservableList<String>> data = FXCollections.observableArrayList(); 
      String requete = "select * from produit";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
           
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            
            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Sheet1");
            // Add data to sheet
            int rowIndex = 0;
            for (ObservableList<String> row : data) {
                Row excelRow = sheet.createRow(rowIndex++);
                int cellIndex = 0;
                for (String value : row) {
                    Cell excelCell = excelRow.createCell(cellIndex++);
                    excelCell.setCellValue(value);
                }
            }
            
            // Write workbook to output stream
            FileOutputStream fos = new FileOutputStream("Test.xlsx");
            workbook.write(fos);
            fos.close();

            

            System.out.println("Data exported to Excel successfully.");
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
      
        }
    }
    
    }
    


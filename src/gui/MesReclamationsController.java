/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.Reclamation;
import entity.Type;
import entity.User;
import java.io.File;
import java.net.URL;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.filechooser.FileSystemView;
import service.ReclamationService;
import service.ReponseService;

/**
 *
 */
public class MesReclamationsController implements Initializable {

    private User userConnected;
    @FXML
    private TableColumn<Reclamation, Type> colType;
    @FXML
    private TableColumn<Reclamation, String> colLibelle;
    @FXML
    private TableColumn<Reclamation, String> colDesc;
    @FXML
    private TableColumn<Reclamation, DateCell> colDate;
    @FXML
    private TableColumn<Reclamation, String> colEtat;
    @FXML
    private TableColumn<Reclamation, ImageView> colPhoto;
    @FXML
    private TableColumn<Reclamation, String> colReponse;
    @FXML
    private TableColumn<Reclamation, String> colDelete;
    @FXML
    private TableView<Reclamation> tableReclamations;

    ReclamationService recService = new ReclamationService();
    ReponseService rep = new ReponseService();
    @FXML
    private TextField tf_libelle;
    @FXML
    private TextArea tf_desc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    Reclamation selectedData = null;

    public void setUser(User user) {
        this.userConnected = user;
        showReclamations();
        tableReclamations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               
                selectedData = tableReclamations.getSelectionModel().getSelectedItem();
                if(selectedData.getEtat_reclamation().contains("en cours")){
                     tf_libelle.setText(selectedData.getLibelle());
                     tf_desc.setText(selectedData.getDescription());
                }
                else{
                    selectedData=null;
                    tf_libelle.setText("");
                     tf_desc.setText("");
                }
               
            }
        });
    }

    public void showReclamations() {
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colReponse.setCellFactory(column -> {
            return new TableCell<Reclamation, String>() {
                @Override
                protected void updateItem(String reponse, boolean empty) {
                    super.updateItem(reponse, empty);
                    if (empty) {
                        setText(null);
                    } else {

                        Reclamation rec = getTableView().getItems().get(getIndex());
                        reponse = rep.getReponse(rec.getId()).getMessage();

                        if (reponse == null) {
                            setText("pas de reponse");
                        } else {
                            setText(reponse);
                        }

                    }
                }
            };
        });
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat_reclamation"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        colPhoto.setCellValueFactory(new Callback<CellDataFeatures<Reclamation, ImageView>, ObservableValue<ImageView>>() {
            public ObservableValue<ImageView> call(CellDataFeatures<Reclamation, ImageView> cellData) {
                // Get the photo path from the Reclamation object
                FileSystemView view = FileSystemView.getFileSystemView();
                File homeDirectory = view.getHomeDirectory();
                String photoname = cellData.getValue().getPhoto();

                // Create an Image object from the photo path
                String photoPath = homeDirectory.getPath() + "\\" + photoname;

                File file = new File(photoPath);
                Image photo = new Image(file.toURI().toString());
                // Create an ImageView object to display the photo
                ImageView photoView = new ImageView(photo);
                photoView.setFitHeight(100);
                photoView.setFitWidth(100);

                // Return an observable value of the ImageView object
                return new SimpleObjectProperty<>(photoView);
            }
        });
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> deletecellFactory = (TableColumn<Reclamation, String> param) -> {
            final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button();
                        deleteIcon.setText("Supprimer");

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Reclamation selectedReclamation = getTableView().getItems().get(getIndex());

                            //alert
                            if (selectedReclamation != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initStyle(StageStyle.UTILITY);
                                alert.setTitle("Supprimer la Reclamation");
                                alert.setHeaderText(null);
                                alert.setContentText("Etes vous sur de vouloir supprimer la Reclamation ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                                {

                                    if (recService.deleteReclamation(selectedReclamation.getId())) {
                                        Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                                        alerts.initStyle(StageStyle.UTILITY);
                                        alerts.setTitle("Success");
                                        alerts.setHeaderText(null);
                                        alerts.setContentText("Reclamation a été supprimée");
                                        alerts.showAndWait();
                                        showReclamations();
                                    } else {
                                        Alert alertz = new Alert(Alert.AlertType.ERROR);
                                        alertz.initStyle(StageStyle.UTILITY);
                                        alertz.setTitle("Error");
                                        alertz.setHeaderText(null);
                                        alertz.setContentText("Reclamation n'a pas été supprimée");
                                        alertz.showAndWait();
                                    }
                                }

                            } else {
                                Alert alertz = new Alert(Alert.AlertType.ERROR);
                                alertz.initStyle(StageStyle.UTILITY);
                                alertz.setTitle("Error");
                                alertz.setHeaderText(null);
                                alertz.setContentText("selectionnez une Reclamation");
                                alertz.showAndWait();
                            }
                        });

                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };
            return cell;
        };
        colDelete.setCellFactory(deletecellFactory);
        List<Reclamation> list = new ArrayList<>();
        ReclamationService reclamationService = new ReclamationService();

        list = reclamationService.readAllMyReclamations(userConnected.getIdUser());
        tableReclamations.setItems((FXCollections.observableArrayList(list)));
    }

    @FXML
    private void modifierReclamation(ActionEvent event) {
        if (selectedData != null) {
           
            if (tf_libelle.getText().isEmpty() || tf_libelle.getText().length() < 3
                    || tf_desc.getText().isEmpty() || tf_desc.getText().length() < 3) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs du formulaire correctement");
                alert.showAndWait();
            } else {

                selectedData.setLibelle(tf_libelle.getText());
                selectedData.setDescription(tf_desc.getText());
                selectedData.setEtat_reclamation("en cours");
                selectedData.setDate_reclamation(new Date(System.currentTimeMillis()));

                if (recService.userUpdateReclamation(selectedData)) {
                    SendEmail s = new SendEmail();
                    try {
                        s.sendEmailToAdministrator();
                    } catch (Exception e) {

                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Reclamation a été modifiée");
                    alert.showAndWait();
                    showReclamations();
                    tf_desc.setText("");
                    tf_libelle.setText("");
                    selectedData = null;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Reclamation n'a pas été modifiée");
                    alert.showAndWait();
                }

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selectionnez une reclamation svp!");
            alert.showAndWait();
        }

    }
}

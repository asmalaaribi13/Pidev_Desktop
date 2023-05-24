/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gui;

import entity.Type;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import service.TypeService;


public class TypeListFXMLController implements Initializable {

    @FXML
    private TableColumn<Type, String> colType;
    @FXML
    private TableColumn<Type, String> colDelete;
    @FXML
    private TableView<Type> table;
    TypeService typeService = new TypeService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showTypes(FXCollections.observableList(typeService.readTypes()));
    }    
    
        private void showTypes(ObservableList<Type> typeList) {
        table.setItems(typeList);
        colType.setCellValueFactory(new PropertyValueFactory<Type, String>("nom_type"));
       
        Callback<TableColumn<Type, String>, TableCell<Type, String>> deletecellFactory = (TableColumn<Type, String> param) -> {
            final TableCell<Type, String> cell = new TableCell<Type, String>() {
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
                            Type selectedType = getTableView().getItems().get(getIndex());

                            //alert
                            if (selectedType != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.initStyle(StageStyle.UTILITY);
                                alert.setTitle("Supprimer le Type");
                                alert.setHeaderText(null);
                                alert.setContentText("Etes vous sur de vouloir supprimer le Type ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) 
                                {

                                    if (typeService.deleteType(selectedType.getId())) {
                                        Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                                        alerts.initStyle(StageStyle.UTILITY);
                                        alerts.setTitle("Success");
                                        alerts.setHeaderText(null);
                                        alerts.setContentText("Type a été supprimée");
                                        alerts.showAndWait();
                                        showTypes(FXCollections.observableList(typeService.readTypes()));
                                    } else {
                                        Alert alertz = new Alert(Alert.AlertType.ERROR);
                                        alertz.initStyle(StageStyle.UTILITY);
                                        alertz.setTitle("Error");
                                        alertz.setHeaderText(null);
                                        alertz.setContentText("Type n'a pas été supprimée");
                                        alertz.showAndWait();
                                    }
                                }

                            } else {
                                Alert alertz = new Alert(Alert.AlertType.ERROR);
                                alertz.initStyle(StageStyle.UTILITY);
                                alertz.setTitle("Error");
                                alertz.setHeaderText(null);
                                alertz.setContentText("selectionnez un Commentaire");
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
   

    }

}

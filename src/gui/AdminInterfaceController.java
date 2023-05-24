package gui;

import com.itextpdf.text.BadElementException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import entity.Reclamation;
import entity.Reponse;
import entity.Type;
import entity.User;
import service.ReclamationService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import service.ReponseService;
import service.TypeService;

public class AdminInterfaceController implements Initializable {

    @FXML
    public TableColumn<Reclamation, String> columnDesc;
    @FXML
    public TableColumn<Reclamation, String> columnEtat;
    @FXML
    public TableColumn<Reclamation, DateCell> columnDate;
    @FXML
    public TableColumn<Reclamation, String> columnMotif;
    @FXML
    public TableColumn<Reclamation, String> columnReponse;

    @FXML
    public TableView<Reclamation> tableReclamations;
    @FXML
    public TextArea descInput;
    @FXML
    public TextArea reponseInput;
    public Button showReclamation;
    @FXML
    public Button deleteReclamationButton;
    @FXML
    public Button updateReclamationButton;
    @FXML
    public Button downloadPdfButton;
    @FXML
    public Button downloadExcelButton;
    @FXML
    public Button showStat;
    @FXML
    public ComboBox<String> comboBoxEtat;

    ReclamationService recService = new ReclamationService();
    @FXML
    private Button showEnAttente;
    @FXML
    private TableColumn<Reclamation, User> columnUser;
    @FXML
    private TextField tf_type;

    TypeService typeService = new TypeService();
    @FXML
    private TableColumn<Reclamation, Type> columnType;
    @FXML
    private TableColumn<Reclamation, String> columnRepondre;
    ReponseService rep = new ReponseService();

    @Override
    // Cette méthode sera exécuté lors de lancement de l'interface administrateur ; qui va récupérer la liste des réclamation de la base de données
    // et la mettre dans la table 

    public void initialize(URL url, ResourceBundle rb) {
      showReclamation();
    }

    public void showReclamation() {
         columnDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnReponse.setCellFactory(column -> {
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
        columnEtat.setCellValueFactory(new PropertyValueFactory<>("etat_reclamation"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        columnUser.setCellValueFactory(cellData -> {
            String nomUser = cellData.getValue().getUser().getNomUser();
            String prenomUser = cellData.getValue().getUser().getPrenomUser();
            return new SimpleObjectProperty<>(new User(nomUser, prenomUser));
        });
        columnType.setCellValueFactory(cellData -> {
            String type = cellData.getValue().getType().getNom_type();
            return new SimpleObjectProperty<>(new Type(type));
        });
        columnMotif.setCellValueFactory(new PropertyValueFactory<>("libelle"));

        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> RepondreFactory = (TableColumn<Reclamation, String> param) -> {
            final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button repondreBtn = new Button();
                        repondreBtn.setText("Repondre");

                        // current user can report a com only once 
                        if (!tableReclamations.getItems().get(getIndex()).getEtat_reclamation().contains("en cours")) {
                            repondreBtn.setDisable(true);
                        }

                        repondreBtn.setOnMouseClicked((MouseEvent event) -> {
                            Reclamation selectedReclamation = getTableView().getItems().get(getIndex());
                            //alert
                            if (selectedReclamation != null) {

                                if (!repondreBtn.isDisabled()) {
                                    Dialog<String> dialog = new Dialog<>();
                                    dialog.setTitle("Ajouter reponse");
                                    dialog.setHeaderText(null);

                                    Label label = new Label("Message:");
                                    TextArea textArea = new TextArea();
                                    textArea.setPromptText("Entre le message");

                                    VBox content = new VBox(label, textArea);
                                    content.setSpacing(10);
                                    content.setPadding(new Insets(10));

                                    dialog.getDialogPane().setContent(content);

                                    ButtonType customButtonType = new ButtonType("Ajouter reponse", ButtonData.OK_DONE);

                                    Button cancelButton = new Button("Annuler");
                                    cancelButton.setOnAction(e -> dialog.close());
 
                                    dialog.getDialogPane().getButtonTypes().add(customButtonType);
                                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                                    Button customButton = (Button) dialog.getDialogPane().lookupButton(customButtonType);
                                    customButton.setOnAction(e -> {
                                        
                                        String message = textArea.getText();
                                        if (message.isEmpty()) {
                                            showErrorDialog("Le message ne peut pas etre vide!");
                                        } else {
                                            System.out.println(message);
                                            Reponse reponse = new Reponse(selectedReclamation,message,new Date(System.currentTimeMillis()));
                                            rep.insertReponse(reponse);
                                            selectedReclamation.setEtat_reclamation("Traitée");
                                            recService.updateReclamation(selectedReclamation);
                                            showReclamation();
                                            dialog.close();
                                        }
                                    });

                                    dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setOnMouseClicked(e -> dialog.close());

                                    dialog.showAndWait();
                                } 

                            } 
                        });

                        HBox managebtn3 = new HBox(repondreBtn);
                        managebtn3.setStyle("-fx-alignment:center");
                        HBox.setMargin(repondreBtn, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn3);

                        setText(null);

                    }
                }

            };
            return cell;
        };
        columnRepondre.setCellFactory(RepondreFactory);
        List<Reclamation> list = new ArrayList<>();
        ReclamationService reclamationService = new ReclamationService();
        list = reclamationService.readAllReclamations();
        tableReclamations.setItems((FXCollections.observableArrayList(list)));
    }

    
    @FXML
    //Cette méthode s'éxecute lorsque on selecte un élement de la table ;; elle va remplir les champs a gauche 
    public void getselectedReclamation() {
      /*  Reclamation reclamation = tableReclamations.getSelectionModel().getSelectedItem();
        comboBoxEtat.getSelectionModel().select(reclamation.getEtat_reclamation());
        ObservableList<String> etatList = FXCollections.observableArrayList();
        if (reclamation.getEtat_reclamation().equals("En Attente")) {
            etatList.add("Traité");
        }
        comboBoxEtat.setItems(etatList);
        descInput.setText(reclamation.getDescription());*/

    }

    @FXML
    //Cette méthode va exécuter l'update de réclamation 
    public void updateReclamation(ActionEvent event) {
        int idreclamation = tableReclamations.getSelectionModel().getSelectedItem().getId();
        String description = descInput.getText();
        String etatReclamation = comboBoxEtat.getSelectionModel().getSelectedItem();
        String reponseReclamation = reponseInput.getText();
        ReclamationService reclamationService = new ReclamationService();
        Reclamation reclamation = new Reclamation();
        reclamation.setId(idreclamation);
        reclamation.setDescription(description);
        reclamation.setEtat_reclamation(etatReclamation);
        reclamationService.updateReclamation(reclamation);

        //Show Alert 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification du réclamation ");
        alert.setHeaderText("Le Réclamation est bien modifié ");
        alert.setContentText("OK!");
        alert.showAndWait();
        // Refresh table --> Re-read from database list of claims
        List<Reclamation> list = new ArrayList<>();
        list = reclamationService.readAllReclamations();
        tableReclamations.getItems().clear();
        tableReclamations.setItems((FXCollections.observableArrayList(list)));
        //Vider les inputs
        descInput.clear();
        reponseInput.clear();
    }

    @FXML
    public void deleteReclamation(ActionEvent event) {
        //Get Selected Reclamation 
        int idreclamation = tableReclamations.getSelectionModel().getSelectedItem().getId();
        ReclamationService reclamationService = new ReclamationService();
        reclamationService.deleteReclamation(idreclamation);

        // Show Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppresssion du réclamation ");
        alert.setHeaderText("Le Réclamation est bien supprimée ");
        alert.setContentText("OK!");
        alert.showAndWait();
        // Refresh table --> Re-read from database list of claims
        List<Reclamation> list = new ArrayList<>();
        tableReclamations.getItems().clear();
        list = reclamationService.readAllReclamations();
        tableReclamations.setItems((FXCollections.observableArrayList(list)));

    }

    @FXML
    public void showStat(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Stats.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void downalodPdf() throws BadElementException, IOException {
        // Appeler la méthode export pdf qui existe dans la classe excelandpdfexport 
        PDFExport excelAndPdfExport = new PDFExport();
        excelAndPdfExport.exportPdf();
        // Show Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("EXPORT PDF ");
        alert.setHeaderText("La Liste des réclamations est bien exporté en PDF ");
        alert.setContentText("OK!");
        alert.showAndWait();
    }



    @FXML
    private void orderByAscDate(ActionEvent event) {
        tableReclamations.getItems().clear();
        tableReclamations.setItems((FXCollections.observableArrayList(recService.displayReclamationOrdredByAscDate())));
    }

    @FXML
    private void orderByDescDate(ActionEvent event) {
        tableReclamations.getItems().clear();
        tableReclamations.setItems((FXCollections.observableArrayList(recService.displayReclamationOrdredByDescDate())));
    }

    @FXML
    private void resetSort(ActionEvent event) {
        tableReclamations.getItems().clear();
        tableReclamations.setItems((FXCollections.observableArrayList(recService.readAllReclamations())));
    }

    @FXML
    private void showTraite(ActionEvent event) {
        tableReclamations.getItems().clear();
        tableReclamations.setItems((FXCollections.observableArrayList(recService.readRecTraites())));
    }

    @FXML
    private void showEnAttente(ActionEvent event) {
        tableReclamations.getItems().clear();
        tableReclamations.setItems((FXCollections.observableArrayList(recService.readRecEnAttente())));
    }

    @FXML
    private void AjouterType(ActionEvent event) {
        if (tf_type.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un type");
            alert.showAndWait();
        } else {
            Type type = new Type();
            type.setNomType(tf_type.getText());

            if (typeService.insertType(type)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Type a été ajouté");
                alert.showAndWait();
                tf_type.setText("");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Type n'a pas été ajouté");
                alert.showAndWait();
            }

        }
    }

    @FXML
    private void showTypes(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("TypeListFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showErrorDialog(String message) {
        Dialog<String> errorDialog = new Dialog<>();
        errorDialog.setTitle("Erreur");
        errorDialog.setContentText(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> errorDialog.close());
        errorDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        errorDialog.showAndWait();
    }
}

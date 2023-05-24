package gui;

import entity.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import service.ReclamationService;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class FXMLStats implements Initializable {


    @FXML
    public PieChart pieChart;

@Override
public void initialize(URL url, ResourceBundle rb) {
    ReclamationService reclamationService = new ReclamationService();
    Map<String, Integer> reclamationByType = reclamationService.countReclamationByType();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (Map.Entry<String, Integer> entry : reclamationByType.entrySet()) {
        Type type = new Type(entry.getKey());
        pieChartData.add(new PieChart.Data(type.getNom_type(), entry.getValue()));
    }
    pieChart.setData(pieChartData);
    pieChart.setTitle("Statistique sur les réclamations");
}

}


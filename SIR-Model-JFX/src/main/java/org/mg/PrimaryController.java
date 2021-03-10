package org.mg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    // the line chart

    @FXML
    private NumberAxis lineChartXAxis;

    @FXML
    private NumberAxis lineChartYAxis;

    @FXML
    private LineChart<Double, Double> lineChart;

    // Slider Labels

    @FXML
    private Label maxTimeLabel;

    @FXML
    private Label susceptiblePopLabel;

    @FXML
    private Label infectedPopLabel;

    @FXML
    private Label rateOfInfectionLabel;

    @FXML
    private Label rateOfRemovalLabel;

    // Sliders

    @FXML
    private Slider maxTime;

    @FXML
    private Slider susceptiblePop;

    @FXML
    private Slider infectedPop;

    @FXML
    private Slider rateOfInfection;

    @FXML
    private Slider rateOfRemoval;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lineChartXAxis.setAutoRanging(false);
        lineChartYAxis.setAutoRanging(false);

        // Make labels change with slider values

        maxTime.valueProperty().addListener((ob, ov, nv) -> {
            maxTimeLabel.setText("Total Time: "+nv.intValue());
            lineChartXAxis.setUpperBound(nv.intValue());
        });

        susceptiblePop.valueProperty().addListener((ob, ov, nv) -> { susceptiblePopLabel.setText("Susceptible: "+nv.intValue()); });
        infectedPop.valueProperty().addListener((ob, ov, nv) -> { infectedPopLabel.setText("Infected: "+nv.intValue()); });
        rateOfInfection.valueProperty().addListener((ob, ov, nv) -> { rateOfInfectionLabel.setText("Rate of Infection: "+Math.round(nv.doubleValue()*100000.0)/100000.0); });
        rateOfRemoval.valueProperty().addListener((ob, ov, nv) -> { rateOfRemovalLabel.setText("Rate of Removal: "+Math.round(nv.doubleValue()*100.0)/100.0); });

        // Set initial values

        maxTime.setValue(maxTime.getMax());
        susceptiblePop.setValue(susceptiblePop.getMax()/2);
        infectedPop.setValue(infectedPop.getMax()/4);
        rateOfInfection.setValue(rateOfInfection.getMax()/2);
        rateOfRemoval.setValue(rateOfRemoval.getMax()/3);

    }
}

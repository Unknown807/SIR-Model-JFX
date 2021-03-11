package org.mg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

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

    private XYChart.Series susceptibleSeries;
    private XYChart.Series infectedSeries;
    private XYChart.Series removedSeries;

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
    private Slider maxTimeSlider;

    @FXML
    private Slider susceptiblePopSlider;

    @FXML
    private Slider infectedPopSlider;

    @FXML
    private Slider rateOfInfectionSlider;

    @FXML
    private Slider rateOfRemovalSlider;

    // Fields

    @FXML
    private TextField maxTimeField;

    @FXML
    private TextField maxPopField;

    @FXML
    private TextField maxInfectionRateField;

    // variables

    int maxTime;

    int maxPop = 100;
    int susceptiblePop;
    int infectedPop;

    double rateOfInfection;
    double rateOfRemoval;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        susceptibleSeries = new XYChart.Series();

        infectedSeries = new XYChart.Series<>();
        removedSeries = new XYChart.Series<>();

        lineChart.getData().addAll(susceptibleSeries, infectedSeries, removedSeries);

        lineChartXAxis.setAutoRanging(false);
        lineChartYAxis.setAutoRanging(false);
        lineChart.setLegendVisible(false);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);

        // Make labels change with slider values

        maxTimeSlider.valueProperty().addListener((ob, ov, nv) -> {
            maxTime = nv.intValue();
            maxTimeLabel.setText("Total Time: "+maxTime);
            lineChartXAxis.setUpperBound(maxTime);
        });

        susceptiblePopSlider.valueProperty().addListener((ob, ov, nv) -> {
            susceptiblePop = nv.intValue();
            susceptiblePopLabel.setText("Susceptible: "+susceptiblePop);
            infectedPopSlider.setValue(maxPop-susceptiblePop);
            drawLines();
        });

        infectedPopSlider.valueProperty().addListener((ob, ov, nv) -> {
            infectedPop = nv.intValue();
            infectedPopLabel.setText("Infected: "+infectedPop);
            susceptiblePopSlider.setValue(maxPop-infectedPop);
            drawLines();
        });

        rateOfInfectionSlider.valueProperty().addListener((ob, ov, nv) -> {
            rateOfInfection = roundNum2DP(nv.doubleValue());
            rateOfInfectionLabel.setText("Rate of Infection: "+rateOfInfection);
            drawLines();
        });

        rateOfRemovalSlider.valueProperty().addListener((ob, ov, nv) -> {
            rateOfRemoval = roundNum2DP(nv.doubleValue());
            rateOfRemovalLabel.setText("Rate of Removal: "+rateOfRemoval);
            drawLines();
        });

        // Set initial values

        maxTimeSlider.setValue(maxTimeSlider.getMax());
        susceptiblePopSlider.setValue(susceptiblePopSlider.getMax()/2);
        infectedPopSlider.setValue(infectedPopSlider.getMax()/6);
        rateOfInfectionSlider.setValue(rateOfInfectionSlider.getMax()/2);
        rateOfRemovalSlider.setValue(rateOfRemovalSlider.getMax()/3);

    }

    @FXML
    public void submitFields() {
        String newMT = maxTimeField.getText();
        String newMP = maxPopField.getText();
        String newMIR = maxInfectionRateField.getText();

        // If the field is valid then it can be used, otherwise skip

        if (validateField(newMT)) {
            maxTime = Integer.parseInt(newMT);
            maxTimeSlider.setMax(maxTime);
        }

        if (validateField(newMP)) {
            maxPop = Integer.parseInt(newMP);
            susceptiblePopSlider.setMax(maxPop);
            infectedPopSlider.setMax(maxPop);
            lineChartYAxis.setUpperBound(maxPop);
        }

        if (validateField(newMIR)) {
            rateOfInfectionSlider.setMax(Integer.parseInt(newMIR));
        }

        drawLines();

    }

    public double roundNum2DP(double num) {
        return Math.round(num*100.0)/100.0;
    }

    public boolean validateField(String field) {
        return (!field.isBlank() && field.matches("\\d+"));
    }

    public void drawLines() {

        susceptibleSeries.getData().clear();
        infectedSeries.getData().clear();
        removedSeries.getData().clear();

        double susceptiblePopTemp = susceptiblePop;
        double infectedPopTemp = infectedPop;
        double removedPop = 0;

        double dS;
        double newS;
        double newI;
        double newR;

        for (int t=0; t<maxTime; t++) {

            susceptibleSeries.getData().add(new XYChart.Data<>(t, susceptiblePopTemp));
            infectedSeries.getData().add(new XYChart.Data<>(t, infectedPopTemp));
            removedSeries.getData().add(new XYChart.Data<>(t, removedPop));

            dS = (rateOfInfection*susceptiblePopTemp*infectedPopTemp)/maxPop;
            newS = susceptiblePopTemp - dS;
            newI = (infectedPopTemp + dS - rateOfRemoval * infectedPopTemp);
            newR = (removedPop + rateOfRemoval * infectedPopTemp);

            susceptiblePopTemp = newS;
            infectedPopTemp = newI;
            removedPop = newR;

        }
    }
}

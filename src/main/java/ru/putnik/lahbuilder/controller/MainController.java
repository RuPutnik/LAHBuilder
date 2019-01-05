package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.putnik.lahbuilder.axis.LogarithmicNumberAxis;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 03.01.2019 в 23:45
 */
public class MainController extends Application implements Initializable {
    @FXML
    LineChart<Integer,Integer> chart;
    @FXML
    NumberAxis yAxis;
    @FXML
    LogarithmicNumberAxis xAxis;

    //XYChart.Series<Integer,Integer> ser1=new XYChart.Series<>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("MainPaneBuilder.fxml")));
        primaryStage.setScene(new Scene(parent));

        primaryStage.setWidth(915);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chart.setCreateSymbols(false);
        chart.setLegendVisible(false);
        yAxis.setAutoRanging(false);
        xAxis.setAutoRanging(false);

        yAxis.setUpperBound(60);
        yAxis.setLowerBound(-60);
        yAxis.setTickUnit(20);

        xAxis.setLowerBound(0.1);
        xAxis.setUpperBound(1000);
        xAxis.setTickLabelFont(new Font(8));

        //ser1.getData().addAll(new XYChart.Data<>(10,20),new XYChart.Data<>(100,40));

        //chart.getData().add(ser1);


    }
}

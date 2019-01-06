package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.putnik.lahbuilder.Link;
import ru.putnik.lahbuilder.axis.LogarithmicNumberAxis;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 03.01.2019 в 23:45
 */
public class MainController extends Application implements Initializable {
    @FXML
    private LineChart<Integer,Integer> chart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LogarithmicNumberAxis xAxis;
    @FXML
    private Label tfLabel;
    @FXML
    private ListView<Link> linksListView;
    @FXML
    private Button addLinkButton;
    @FXML
    private Button deleteLinkButton;
    @FXML
    private Button editLinkButton;

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

        linksListView.setCellFactory(param -> {

            ListCell<Link> listCell=new ListCell<Link>(){
                @Override
                protected void updateItem(Link item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item!=null) {
                        setText(item.getNameLink());
                    }
                }
            };
            return listCell;
        });

        //Testing code
        linksListView.getItems().add(new Link(Link.Type.Апериодическое1,3));
        linksListView.getItems().add(new Link(Link.Type.Усилитель,10));

        addLinkButton.setOnAction(new AddingLink());
        deleteLinkButton.setOnAction(new DeletingLink());
        editLinkButton.setOnAction(new EditingAddingLink());
        linksListView.getSelectionModel().selectedIndexProperty().addListener(new ChoiceLinkOnView());
    }
    public class AddingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class DeletingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class EditingAddingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class ChoiceLinkOnView<Link> implements ChangeListener<Link> {

        @Override
        public void changed(ObservableValue<? extends Link> observable, Link oldValue, Link newValue) {

        }
    }
}

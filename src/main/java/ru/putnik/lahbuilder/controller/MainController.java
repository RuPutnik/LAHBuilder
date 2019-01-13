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
import ru.putnik.lahbuilder.link.AmplificationLink;
import ru.putnik.lahbuilder.link.AperiodicLink1;
import ru.putnik.lahbuilder.link.Link;
import ru.putnik.lahbuilder.axis.LogarithmicNumberAxis;
import ru.putnik.lahbuilder.model.MainModel;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 03.01.2019 в 23:45
 */
public class MainController extends Application implements Initializable {
    private MainModel mainModel=new MainModel();
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

    private int numberSelectedLink=0;
    private boolean autoRanging=false;

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
        yAxis.setAutoRanging(autoRanging);
        xAxis.setAutoRanging(autoRanging);

        yAxis.setUpperBound(60);
        yAxis.setLowerBound(-60);
        yAxis.setTickUnit(20);

        xAxis.setLowerBound(0.1);
        xAxis.setUpperBound(1000);
        xAxis.setTickLabelFont(new Font(8));

        //ser1.getData().addAll(new XYChart.Data<>(10,20),new XYChart.Data<>(100,40));

        //chart.getData().add(ser1);

        linksListView.setCellFactory(param -> new ListCell<Link>(){
            @Override
            protected void updateItem(Link item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null) {
                    String text=item.getNameLink();

                    if(item.getValueT()!=0){
                        text=text+" T = "+item.getValueT();
                    }
                    if(item.getValueK()!=1){
                        text=text+" K = "+item.getValueK();
                    }
                    setText(text);
                }
                if(empty){
                    setText(null);
                    setGraphic(null);
                }
            }
        });

        ////Testing code
        Link l1=new AperiodicLink1(3);
        mainModel.addLink(l1);
        linksListView.getItems().add(l1);
        Link l2=new AmplificationLink(10);
        mainModel.addLink(l2);
        linksListView.getItems().add(l2);
        ////

        addLinkButton.setOnAction(new AddingLink());
        deleteLinkButton.setOnAction(new DeletingLink());
        editLinkButton.setOnAction(new EditingLink());
        linksListView.getSelectionModel().selectedIndexProperty().addListener(new ChoiceLinkOnView<>());
    }
    public class AddingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class DeletingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            mainModel.deleteLink(linksListView.getItems(),numberSelectedLink);
        }
    }
    public class EditingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class ChoiceLinkOnView<Link> implements ChangeListener<Link> {

        @Override
        public void changed(ObservableValue<? extends Link> observable, Link oldValue, Link newValue) {
            numberSelectedLink=linksListView.getSelectionModel().getSelectedIndex();
        }
    }
}

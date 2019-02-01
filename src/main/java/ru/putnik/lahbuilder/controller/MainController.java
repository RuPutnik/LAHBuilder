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
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.putnik.lahbuilder.link.Link;
import ru.putnik.lahbuilder.axis.LogarithmicNumberAxis;
import ru.putnik.lahbuilder.model.AddingLinkModel;
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
    private LineChart<Double,Double> chart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LogarithmicNumberAxis xAxis;
    @FXML
    private TextField tfLabel;
    @FXML
    private ListView<Link> linksListView;
    @FXML
    private Button addLinkButton;
    @FXML
    private Button deleteLinkButton;
    @FXML
    private Button editLinkButton;
    @FXML
    private Button buildLah;
    private AddingLinkController addingLinkController;
    private EditingLinkController editingLinkController;

    private int numberSelectedLink=-1;
    private boolean autoRanging=false;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController.primaryStage=primaryStage;
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainPaneBuilder.fxml")));
        primaryStage.setScene(new Scene(parent));

        primaryStage.setWidth(915);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfLabel.setEditable(false);
        tfLabel.setFocusTraversable(false);
        chart.setCreateSymbols(false);
        chart.setLegendVisible(false);
        yAxis.setAutoRanging(autoRanging);
        xAxis.setAutoRanging(autoRanging);

        yAxis.setUpperBound(80);
        yAxis.setLowerBound(-80);
        yAxis.setTickUnit(20);

        xAxis.setLowerBound(0.1);
        xAxis.setUpperBound(1000);
        xAxis.setTickLabelFont(new Font(8));


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
                    if(item.getValueT2Ksi()!=0){
                        double ksi=item.getValueT2Ksi()/(2*item.getValueT());
                        text=text+" ξ = "+mainModel.rounding(ksi);
                    }
                    setText(text);
                }
                if(empty){
                    setText(null);
                    setGraphic(null);
                }
            }
        });

        addLinkButton.setOnAction(new AddingLink());
        deleteLinkButton.setOnAction(new DeletingLink());
        editLinkButton.setOnAction(new EditingLink());
        buildLah.setOnAction(new BuildingLah());
        linksListView.getSelectionModel().selectedIndexProperty().addListener(new ChoiceLinkOnView<>());
        primaryStage.setOnCloseRequest(new CloseMainWindow());

        addingLinkController=new AddingLinkController(linksListView,tfLabel);


    }
    public class AddingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            addingLinkController.createWindow(new Stage(),400,300);
        }
    }
    public class DeletingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            mainModel.deleteLink(linksListView.getItems(),numberSelectedLink);
            AddingLinkModel model = addingLinkController.getLinkModel();
            model.setTransferFunction(AddingLinkModel.formationFunction(linksListView.getItems()));
            tfLabel.setText("W(s) = "+ model.getTransferFunction());
        }
    }
    public class EditingLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            if(numberSelectedLink!=-1) {
                editingLinkController=new EditingLinkController(linksListView,tfLabel,numberSelectedLink);
                editingLinkController.createWindow(new Stage(), 650, 325);

            }else {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка редактирования звена");
                alert.setHeaderText("При попытке отредактировать звено возникла ошибка!");
                alert.setContentText("Звено для редактирования не выбрано");
                alert.show();
            }
        }
    }
    public class BuildingLah implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            mainModel.buildLAH(chart);
        }
    }
    public class ChoiceLinkOnView<Link> implements ChangeListener<Link> {

        @Override
        public void changed(ObservableValue<? extends Link> observable, Link oldValue, Link newValue) {
            numberSelectedLink=linksListView.getSelectionModel().getSelectedIndex();
        }
    }
    public class CloseMainWindow implements EventHandler<WindowEvent>{

        @Override
        public void handle(WindowEvent event) {
            System.exit(0);
        }
    }
}

package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.putnik.lahbuilder.MainConstants;
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
    @FXML
    private Button clearChart;
    @FXML
    private Button deleteAll;
    @FXML
    private Button copyLink;
    @FXML
    public TextField lowFreqField;
    @FXML
    public TextField upperFreqField;
    @FXML
    public TextField minValueAmplitude;
    @FXML
    public TextField maxValueAmplitude;
    @FXML
    public CheckBox autoscaleCheckBox;
    @FXML
    public MenuItem loadTFMenu;
    @FXML
    public MenuItem saveTFMenu;
    @FXML
    public MenuItem helpMenu;
    @FXML
    public MenuItem exitMenu;
    @FXML
    public CheckBox pointsCheckBox;

    private AddingLinkController addingLinkController;
    private EditingLinkController editingLinkController;
    private InfoController infoController;

    private int numberSelectedLink=-1;
    private boolean autoRanging=false;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController.primaryStage=primaryStage;
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainPaneBuilder.fxml")));
        primaryStage.setScene(new Scene(parent));

        primaryStage.setWidth(915);
        primaryStage.setHeight(560);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfLabel.setEditable(false);
        tfLabel.setFocusTraversable(false);
        chart.setCreateSymbols(false);// - добавить отдельный чекбокс "информация в точках"
        chart.setLegendVisible(false);
        chart.setAnimated(false);

        yAxis.setUpperBound(MainConstants.MAX_VALUE_AMPLITUDE);
        yAxis.setLowerBound(MainConstants.MIN_VALUE_AMPLITUDE);
        yAxis.setTickUnit(20);

        xAxis.setLowerBound(MainConstants.LOW_FREQUENCY);
        xAxis.setUpperBound(MainConstants.UPPER_FREQUENCY);
        yAxis.setAutoRanging(autoRanging);
        xAxis.setAutoRanging(autoRanging);
        xAxis.setTickLabelFont(new Font(8.9));

        lowFreqField.setText(String.valueOf(MainConstants.LOW_FREQUENCY));
        upperFreqField.setText(String.valueOf(MainConstants.UPPER_FREQUENCY));
        minValueAmplitude.setText(String.valueOf(MainConstants.MIN_VALUE_AMPLITUDE));
        maxValueAmplitude.setText(String.valueOf(MainConstants.MAX_VALUE_AMPLITUDE));

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
        clearChart.setOnAction(new ClearChart());
        linksListView.getSelectionModel().selectedIndexProperty().addListener(new ChoiceLinkOnView<>());
        primaryStage.setOnCloseRequest(new CloseMainWindow());
        deleteAll.setOnAction(new DeleteAllLinks());
        copyLink.setOnAction(new CopyLink());
        autoscaleCheckBox.setOnAction(new AutoScalable());
        exitMenu.setOnAction(new Exit());
        helpMenu.setOnAction(new Help());
        saveTFMenu.setOnAction(new SaveTF());
        loadTFMenu.setOnAction(new LoadTF());

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
            if (!autoRanging) {
                if (checkBorders()) {
                    yAxis.setUpperBound(Double.parseDouble(maxValueAmplitude.getText()));
                    yAxis.setLowerBound(Double.parseDouble(minValueAmplitude.getText()));
                    xAxis.setLowerBound(Double.parseDouble(lowFreqField.getText()));
                    xAxis.setUpperBound(Double.parseDouble(upperFreqField.getText()));
                    mainModel.buildLAH(chart, Double.parseDouble(lowFreqField.getText()), Double.parseDouble(upperFreqField.getText()), false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошибка построения асимптотической ЛАХ");
                    alert.setHeaderText("При попытке построить ЛАХ возникла ошибка!");
                    alert.setContentText("Значения границ области построения ЛАХ заданы неверно! Возвращение к стандартным значениям");
                    alert.show();
                    lowFreqField.setText(String.valueOf(MainConstants.LOW_FREQUENCY));
                    upperFreqField.setText(String.valueOf(MainConstants.UPPER_FREQUENCY));
                    minValueAmplitude.setText(String.valueOf(MainConstants.MIN_VALUE_AMPLITUDE));
                    maxValueAmplitude.setText(String.valueOf(MainConstants.MAX_VALUE_AMPLITUDE));
                }
            }else {
                mainModel.buildLAH(chart, MainConstants.LOW_FREQUENCY, MainConstants.UPPER_FREQUENCY, true);
                double minFreq=chart.getData().get(0).getData().get(0).getXValue();
                double maxFreq=chart.getData().get(0).getData().get(chart.getData().get(0).getData().size()-1).getXValue();
                double minAmplit=Double.MAX_VALUE;
                double maxAmplit=-Double.MAX_VALUE;
                for (int a=0;a<chart.getData().get(0).getData().size();a++){
                    if(chart.getData().get(0).getData().get(a).getYValue()<minAmplit){
                        minAmplit=chart.getData().get(0).getData().get(a).getYValue();
                    }
                }
                for (int a=0;a<chart.getData().get(0).getData().size();a++){
                    if(chart.getData().get(0).getData().get(a).getYValue()>maxAmplit){
                        maxAmplit=chart.getData().get(0).getData().get(a).getYValue();
                    }
                }
                yAxis.setUpperBound(maxAmplit);
                yAxis.setLowerBound(minAmplit);
                xAxis.setLowerBound(minFreq);
                xAxis.setUpperBound(maxFreq);
            }
            if(pointsCheckBox.isSelected()){
                chart.setCreateSymbols(true);
                for (int c = 0; c < chart.getData().size(); c++) {
                    ObservableList<XYChart.Data<Double, Double>> dataList = ((XYChart.Series<Double, Double>) chart.getData().get(c)).getData();
                    dataList.forEach(data -> {
                        Node node = data.getNode();
                        Tooltip tooltip = new Tooltip("Частота: " + data.getXValue().toString() + '\n' + "Амплитуда: " + data.getYValue().toString());
                        Tooltip.install(node, tooltip);
                    });
                }
            }else {
                chart.setCreateSymbols(false);
            }
        }
        boolean checkBorders(){
            double maxVA;
            double minVA;
            double lf;
            double uf;
            try{
                maxVA=Double.parseDouble(maxValueAmplitude.getText());
                minVA=Double.parseDouble(minValueAmplitude.getText());
                lf=Double.parseDouble(lowFreqField.getText());
                uf=Double.parseDouble(upperFreqField.getText());
            }catch (NumberFormatException e){
                return false;
            }
            if(maxVA<=minVA||uf<=lf) {
                return false;
            }

            return true;
        }
    }
    public class ClearChart implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            chart.getData().clear();
        }
    }
    public class DeleteAllLinks implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            linksListView.getItems().clear();
            MainModel.getListLinks().clear();
            tfLabel.setText("W(s) = ");
        }
    }
    public class CopyLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            if(numberSelectedLink!=-1) {
               Link cLink=linksListView.getItems().get(numberSelectedLink).clone();
               linksListView.getItems().add(cLink);
                MainModel.addLink(cLink);
                AddingLinkModel model = addingLinkController.getLinkModel();
                model.setTransferFunction(AddingLinkModel.formationFunction(linksListView.getItems()));
                tfLabel.setText("W(s) = "+ model.getTransferFunction());
            }else {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка копирования звена");
                alert.setHeaderText("При попытке копировать звено возникла ошибка!");
                alert.setContentText("Звено для копирования не выбрано");
                alert.show();
            }
        }
    }
    public class AutoScalable implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            autoRanging=!autoRanging;

            lowFreqField.setEditable(!autoRanging);
            upperFreqField.setEditable(!autoRanging);
            minValueAmplitude.setEditable(!autoRanging);
            maxValueAmplitude.setEditable(!autoRanging);
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
    public class Exit implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }
    }
    public class Help implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            infoController=new InfoController();
            infoController.createWindow(new Stage(),425,490);
        }
    }
    public class SaveTF implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class LoadTF implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
}

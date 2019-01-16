package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.putnik.lahbuilder.link.*;
import ru.putnik.lahbuilder.model.AddingLinkModel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 15.01.2019 в 20:19
 */
public class EditingLinkController extends Application implements Initializable {
    @FXML
    public TextField valueKTextField;
    @FXML
    public TextField valueTTextField;
    @FXML
    public TextField value2TKsiTextField;
    @FXML
    public Label oldKLabel;
    @FXML
    public Label oldTLabel;
    @FXML
    public Label old2TKsiLabel;
    @FXML
    public Label oldLinkType;
    @FXML
    public Button editLinkButton;
    @FXML
    public Button cancelButton;

    private static Link link;
    private static Stage primaryStage;
    private static TextField tfLabel;
    private static ListView<Link> list;
    private static int numberEditLink;

    public EditingLinkController(){}
    public EditingLinkController(ListView<Link> list, TextField tfLabel,int numberLink){
        EditingLinkController.link=list.getItems().get(numberLink);
        EditingLinkController.tfLabel=tfLabel;
        EditingLinkController.list=list;
        numberEditLink=numberLink;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {}

    public void createWindow(Stage primaryStage, double width, double height){
        Parent parent= null;
        EditingLinkController.primaryStage=primaryStage;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(AddingLinkController.class.getClassLoader().getResource("fxml/EditingLinkPane.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(Objects.requireNonNull(parent)));

        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Редактировние звена");
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oldLinkType.setText(link.getNameLink());
        oldKLabel.setText(String.valueOf(link.getValueK()));
        oldTLabel.setText(String.valueOf(link.getValueT()));
        old2TKsiLabel.setText(String.valueOf(link.getValueT2Ksi()));
        editLinkButton.setOnAction(new EditLink());
        cancelButton.setOnAction(new Cancel());

        valueKTextField.setText(oldKLabel.getText());
        valueTTextField.setText(oldTLabel.getText());
        value2TKsiTextField.setText(old2TKsiLabel.getText());

        if(link instanceof AmplificationLink){
            valueKTextField.setEditable(true);
            valueTTextField.setEditable(false);
            value2TKsiTextField.setEditable(false);
        }else if(link instanceof AperiodicLink1){
            valueKTextField.setEditable(true);
            valueTTextField.setEditable(true);
            value2TKsiTextField.setEditable(false);
        }else if(link instanceof AperiodicLink2) {
            valueKTextField.setEditable(true);
            valueTTextField.setEditable(true);
            value2TKsiTextField.setEditable(true);
        }else if(link instanceof DifferentialLink) {
            valueKTextField.setEditable(true);
            valueTTextField.setEditable(false);
            value2TKsiTextField.setEditable(false);
        }else if(link instanceof DifferentialLink1){
            valueKTextField.setEditable(true);
            valueTTextField.setEditable(true);
            value2TKsiTextField.setEditable(false);
        }else if(link instanceof IntegratingLink){
            valueKTextField.setEditable(true);
            valueTTextField.setEditable(false);
            value2TKsiTextField.setEditable(false);
        }else if(link instanceof OscillatoryLink){
            valueKTextField.setEditable(true);
            valueTTextField.setEditable(true);
            value2TKsiTextField.setEditable(true);
        }
    }

    public boolean checkParametersLink(Link link,String kS,String tS,String t2ksiS){
        double k=0,t=0,t2ksi=0;
        if(tS.equals("")) tS="0";
        if(t2ksiS.equals("")) t2ksiS="0";
        if(kS.equals("")) kS="0";

        try {
            k = Double.parseDouble(kS);
            t = Double.parseDouble(tS);
            t2ksi = Double.parseDouble(t2ksiS);
        }catch (NumberFormatException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("При редактировании звена возникла ошибка");
            alert.setHeaderText("Параметры звена должны задаваться числом!");
            alert.setTitle("Ошибка редактирования звена");
            alert.show();
        }

        if(k==0) return false;
        if(k<0||t<0||t2ksi<0) return false;

        if(link instanceof AmplificationLink){
            link.setValueK(k);
            return true;
        }else if(link instanceof AperiodicLink1){
            if(t!=0) {
                link.setValueK(k);
                link.setValueT(t);
                return true;
            }
        }else if(link instanceof AperiodicLink2) {
            if (t != 0 && t2ksi != 0) {
                if(t2ksi / (t * 2) > 1) {
                    link.setValueK(k);
                    link.setValueT(t);
                    link.setValueT2Ksi(t2ksi);
                    return true;
                }
            }
        }else if(link instanceof DifferentialLink) {
            link.setValueK(k);
            return true;
        }else if(link instanceof DifferentialLink1){
            if(t!=0) {
                link.setValueK(k);
                link.setValueT(t);
                return true;
            }
        }else if(link instanceof IntegratingLink){
            link.setValueK(k);
            return true;
        }else if(link instanceof OscillatoryLink){
            if(t!=0&&t2ksi!=0){
                if(t2ksi / (t * 2) < 1) {
                    link.setValueK(k);
                    link.setValueT(t);
                    link.setValueT2Ksi(t2ksi);
                    return true;
                }
            }
        }
        return false;
    }

    public class EditLink implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(checkParametersLink(link,valueKTextField.getText(),valueTTextField.getText(),value2TKsiTextField.getText())) {
                list.getItems().set(numberEditLink, link);
                tfLabel.setText("W(s) = "+AddingLinkModel.formationFunction(list.getItems()));
            }else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("При редактировании звена возникла ошибка");
                alert.setHeaderText("Выбранное звено не может существовать с заданными параметрами");
                alert.setTitle("Ошибка редактирования звена");
                alert.show();
            }

            primaryStage.close();
        }
    }
    public class Cancel implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            primaryStage.close();
        }
    }
}

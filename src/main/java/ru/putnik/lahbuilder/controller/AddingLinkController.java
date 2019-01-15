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
import ru.putnik.lahbuilder.model.MainModel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 13.01.2019 в 16:49
 */
public class AddingLinkController extends Application implements Initializable {
    private AddingLinkModel linkModel=new AddingLinkModel();
    private static TextField tfLabel;
    private static ListView<Link> list;
    public AddingLinkController(){}
    AddingLinkController(ListView<Link> list, TextField tfLabel){
        AddingLinkController.tfLabel=tfLabel;
        AddingLinkController.list=list;
    }

    @FXML
    private ComboBox<Link> listLinksComboBox;
    @FXML
    private TextField valueKTextField;
    @FXML
    private TextField valueTTextField;
    @FXML
    private TextField value2TKsiTextField;
    @FXML
    private Button addLinkButton;
    @FXML
    private Button addLinkAndExitButton;
    @FXML
    private Button exitButton;

    private static Stage primaryStage;
    private Link choiceLink;

    @Override
    public void start(Stage primaryStage){}

    public void createWindow(Stage primaryStage, double width, double height){
        Parent parent= null;
        AddingLinkController.primaryStage=primaryStage;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(AddingLinkController.class.getClassLoader().getResource("fxml/AddingLinkPane.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(parent));

        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Добавить звено");
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valueKTextField.setEditable(false);
        valueTTextField.setEditable(false);
        value2TKsiTextField.setEditable(false);
        listLinksComboBox.setItems(linkModel.getListLinks());
        listLinksComboBox.setOnAction(new ChoiceTypeLink());
        addLinkAndExitButton.setOnAction(new AddLinkAndExit());
        addLinkButton.setOnAction(new AddLink());
        exitButton.setOnAction(new Exit());
        listLinksComboBox.setCellFactory(callback -> new ListCell<Link>() {
            @Override
            protected void updateItem(Link item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    String text = item.getNameLink();
                    setText(text);
                }
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }
    public class ChoiceTypeLink implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Link link=listLinksComboBox.getValue();
            choiceLink=link;
            valueKTextField.setText("");
            valueTTextField.setText("");
            value2TKsiTextField.setText("");
            switch (link.getNameLink()){
                case "Апериодическое звено 1-го порядка":{
                     valueKTextField.setEditable(true);
                     valueTTextField.setEditable(true);
                     value2TKsiTextField.setEditable(false);
                    break;
                }
                case "Апериодическое звено 2-го порядка":{
                    valueKTextField.setEditable(true);
                    valueTTextField.setEditable(true);
                    value2TKsiTextField.setEditable(true);
                    break;
                }
                case "Усилительное звено":{
                    valueKTextField.setEditable(true);
                    valueTTextField.setEditable(false);
                    value2TKsiTextField.setEditable(false);
                    break;
                }
                case "Интегрирующее звено":{
                    valueKTextField.setEditable(true);
                    valueTTextField.setEditable(false);
                    value2TKsiTextField.setEditable(false);
                    break;
                }
                case "Дифференцирующее звено":{
                    valueKTextField.setEditable(true);
                    valueTTextField.setEditable(false);
                    value2TKsiTextField.setEditable(false);
                    break;
                }
                case "Дифференцирующее звено 1-го порядка":{
                    valueKTextField.setEditable(true);
                    valueTTextField.setEditable(true);
                    value2TKsiTextField.setEditable(false);
                    break;
                }
                case "Колебательное звено":{
                    valueKTextField.setEditable(true);
                    valueTTextField.setEditable(true);
                    value2TKsiTextField.setEditable(true);
                    break;
                }
            }
        }
    }
    public class AddLinkAndExit implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            choiceLink=choiceLink.clone();
            if(checkParametersLink(choiceLink,valueKTextField.getText(),valueTTextField.getText(),value2TKsiTextField.getText())){
                list.getItems().add(choiceLink);
                MainModel.addLink(choiceLink);
                linkModel.setTransferFunction(linkModel.formationFunction(list.getItems()));
                tfLabel.setText("W(s) = "+linkModel.getTransferFunction());
                primaryStage.close();
            }else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("При добавлении нового звена возникла ошибка");
                alert.setHeaderText("Выбранное звено не может существовать с заданными параметрами!");
                alert.setTitle("Ошибка добавления звена");
                alert.show();
            }

        }
    }
    public class AddLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            choiceLink=choiceLink.clone();
            if(checkParametersLink(choiceLink,valueKTextField.getText(),valueTTextField.getText(),value2TKsiTextField.getText())){
                MainModel.addLink(choiceLink);
                list.getItems().add(choiceLink);
                linkModel.setTransferFunction(linkModel.formationFunction(list.getItems()));
                tfLabel.setText("W(s) = "+linkModel.getTransferFunction());
            }else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("При добавлении нового звена возникла ошибка");
                alert.setHeaderText("Выбранное звено не может существовать с заданными параметрами! ");
                alert.setTitle("Ошибка добавления звена");
                alert.show();
            }
        }
    }
    public class Exit implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            primaryStage.close();
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
            alert.setContentText("При добавлении нового звена возникла ошибка");
            alert.setHeaderText("Параметры звенадолжны задаваться числом!");
            alert.setTitle("Ошибка добавления звена");
            alert.show();
        }
        choiceLink.setValueK(k);
        choiceLink.setValueT(t);
        choiceLink.setValueT2Ksi(t2ksi);

        if(k==0) return false;
        if(k<0||t<0||t2ksi<0) return false;

        if(link instanceof AmplificationLink){
            return true;
        }else if(link instanceof AperiodicLink1){
            return t != 0;
        }else if(link instanceof AperiodicLink2) {
            if (t != 0 && t2ksi != 0) {
                return t2ksi / (t * 2) > 1;
            } else {
                return false;
            }
        }else if(link instanceof DifferentialLink) {
            return true;
        }else if(link instanceof DifferentialLink1){
            return t != 0;
        }else if(link instanceof IntegratingLink){
            return true;
        }else if(link instanceof OscillatoryLink){
            if(t!=0&&t2ksi!=0){
                return t2ksi / (t * 2) < 1;
            }else {
                return false;
            }
        }else
            return false;
    }

    AddingLinkModel getLinkModel() {
        return linkModel;
    }
}

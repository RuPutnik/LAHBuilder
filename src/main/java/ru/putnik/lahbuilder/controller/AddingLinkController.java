package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.putnik.lahbuilder.link.Link;
import ru.putnik.lahbuilder.model.AddingLinkModel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 13.01.2019 в 16:49
 */
public class AddingLinkController extends Application implements Initializable {
    private AddingLinkModel linkModel=new AddingLinkModel();

    @FXML
    private ComboBox<String> listLinksComboBox;
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
        listLinksComboBox.setItems(linkModel.getListNamesLinks());
        listLinksComboBox.setOnAction(new ChoiceTypeLink());
        addLinkAndExitButton.setOnAction(new AddLinkAndExit());
        addLinkButton.setOnAction(new AddLink());
        exitButton.setOnAction(new Exit());

    }
    public class ChoiceTypeLink implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            String nameLink=listLinksComboBox.getValue();
            valueKTextField.setText("");
            valueTTextField.setText("");
            value2TKsiTextField.setText("");
            switch (nameLink){
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

            primaryStage.close();
        }
    }
    public class AddLink implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

        }
    }
    public class Exit implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            primaryStage.close();
        }
    }
}

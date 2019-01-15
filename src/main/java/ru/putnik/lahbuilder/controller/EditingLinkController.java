package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    public ComboBox listLinksComboBox;
    @FXML
    public Button editLinkButton;
    @FXML
    public Button cancelButton;

    private static Link link;
    private static Stage primaryStage;

    public EditingLinkController(){}
    public EditingLinkController(Link link){
        EditingLinkController.link =link;
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
        listLinksComboBox.setItems(AddingLinkModel.getListLinks());

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
}

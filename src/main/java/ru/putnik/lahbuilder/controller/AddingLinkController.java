package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 13.01.2019 в 16:49
 */
public class AddingLinkController extends Application implements Initializable {
    @Override
    public void start(Stage primaryStage) throws Exception {}

    public void createWindow(Stage primaryStage, double width, double height){
        Parent parent= null;
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

    }
}

package ru.putnik.lahbuilder.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static ru.putnik.lahbuilder.MainConstants.NAME_PROGRAM;
import static ru.putnik.lahbuilder.MainConstants.VERSION_PROGRAM;

/**
 * Created by My Computer on 30.01.2018.
 */
public class InfoController extends Application implements Initializable {
    @FXML
    private TextArea infoArea;
    @FXML
    private Button exitButton;

    public static Stage infoStage;
    public final String pathHelpFile="text/info.txt";

    public InfoController(){}

    @Override
    public void start(Stage primaryStage) throws Exception {}

    void createWindow(Stage primaryStage, double width, double height){
        Parent parent= null;
        InfoController.infoStage=primaryStage;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(AddingLinkController.class.getClassLoader().getResource("fxml/InfoPane.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(Objects.requireNonNull(parent)));
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public InfoController(Stage stage){
        infoStage=stage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoStage.setTitle(NAME_PROGRAM+" "+VERSION_PROGRAM+"|Помощь");
        //Заполняем текстовое поле информацией из файла
        printHelpInfo();
        //Устанавливаем каретку на 0 символ
        infoArea.positionCaret(0);
        exitButton.setOnAction(event ->infoStage.close());
    }
    //Чтение текста справки из файла, расположенного в resources
    public void printHelpInfo(){
        try {
            String realPath=getClass().getClassLoader().getResource(pathHelpFile).getFile();
            InputStreamReader fileReader=new InputStreamReader(new FileInputStream(realPath));
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String lineText;
            while ((lineText=bufferedReader.readLine())!=null){
                infoArea.appendText(lineText+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка загрузки справки");
            alert.setContentText("Текст справки не смог загрузиться. Возможно файл справки не существует или поврежден.");
            alert.show();
        }
    }
}

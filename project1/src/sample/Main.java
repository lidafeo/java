package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;
import java.io.*;
import java.util.regex.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        ObservableList<String> lines = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(lines);
        Button btnM = new Button("Изменить файл");
        Button btnR = new Button("Вывести результат из нового файла");
        btnR.setDisable(true);
        btnM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modifyFile();
                btnR.setDisable(false);
            }
        });
        btnR.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                readNewFile(listView);
                btnR.setDisable(true);
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, listView, btnM, btnR);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 475);

        primaryStage.setTitle("Задание №1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Прочитать исходный файл, преобразовать и записать в новый файл
    private void modifyFile() {
        try {
            File file = new File("679549712.log");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            Pattern pattern = Pattern.compile("(\\s{3}0\\s)|(\\s{1}0,0\\s)|(\\s{3}0$)|(^0\\s)");
            FileWriter writer = new FileWriter("output.log");
            while(line != null) {
                Matcher matcher = pattern.matcher(line);
                String newStr = matcher.replaceAll("null ");
                writer.write(newStr + System.getProperty("line.separator"));
                line = reader.readLine();
            }
             writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Прочитать полученный файл
    private void readNewFile(ListView listView) {
        try {
            File file = new File("output.log");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while(line != null) {
                listView.getItems().add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

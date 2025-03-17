package com.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root,700,500);

            primaryStage.setTitle("Puzzle-8 Solver");
            primaryStage.setScene(scene);

            primaryStage.setMinWidth(1250);
            primaryStage.setMinHeight(820);

            primaryStage.setMaxWidth(1250);
            primaryStage.setMaxHeight(820);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 public static void main(String[] args) {
        launch(args);
    }
}

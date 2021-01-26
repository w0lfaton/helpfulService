package com.w0lfaton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() throws Exception {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Wolf Screen");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
    }

    public static void main(String[] args) {
        launch(args);
        /*Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String[] command = sc.nextLine().split(" ");
            if (command.length == 2) {
                scoroAPIService.request(command[0], command[1]);
            } else if (command.length == 3) {
                scoroAPIService.request(command[0], command[1], Integer.parseInt(command[2]));
            } else {
                System.out.println("Please provide an action.");
            }
        }*/
    }
}

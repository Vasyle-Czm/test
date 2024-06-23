package org;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.*;

// https://www.youtube.com/watch?v=TVLq9iSpuMs

public class App extends Application{
    public static Parent register,login;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner in = new Scanner(new FileReader(new File("src\\main\\resources\\org\\Database\\accountsInfo.txt")));
        while(in.hasNext()){
            Controller.acc.add(new Users(in.next(),in.next(),in.next(),in.next(),in.next(),in.next(),in.next(),Integer.parseInt(in.next()),Integer.parseInt(in.next())));
        }
        Scanner bin = new Scanner(new FileReader(new File("src\\main\\resources\\org\\Database\\buget.txt")));
        Users.setBuget(bin.nextInt());
        bin.close();
        in.close();
        try {
            primaryStage.getIcons().add(new Image("Database/icon.png"));
        } catch (Exception e) {}
        


        Scanner dqin = new Scanner(new FileReader(new File("src\\main\\resources\\org\\Database\\deleteQueue.txt")));
        while(dqin.hasNext()){
            Controller.deleteQueue.add(dqin.next());
        }
        dqin.close();



        Scanner min = new Scanner(new FileReader(new File("src\\main\\resources\\org\\Database\\managersInfo.txt")));
        while(min.hasNext()){
            Controller.manAcc.add(new Manageri(min.next(),min.next(), min.next(), min.next(), min.next(),min.next()));
        }
        min.close();



        primaryStage.setTitle("Consulting Company");
        primaryStage.setResizable(false);

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/login.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception,FileNotFoundException {
        launch(args);
    }
}

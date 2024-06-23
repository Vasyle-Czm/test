package org;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewManagerController implements Initializable{

    @FXML
    TextField email,username,nume,prenume,parola;
    @FXML
    Label message;

    public void backToManagerApp(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/managerApp.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/managerApp.css").toExternalForm());
        stage.setScene(scene);      
    }

    private void save() throws IOException{
        FileWriter save = new FileWriter(new File("Database/managersInfo.txt"));
        for(int i = 0;i<Controller.manAcc.size();i++){
            save.write(Controller.manAcc.get(i).getEmail() + " " + Controller.manAcc.get(i).getUsername() + " " + Controller.manAcc.get(i).getNume() + " " + Controller.manAcc.get(i).getPrenume() + " " + Controller.manAcc.get(i).getParola() + " " + Controller.manAcc.get(i).getCreationDate() + "\n");
        }
        save.close();
    }

    private boolean check(String email,String username){
        for(int i=0; i<Controller.acc.size(); i++){
            if(Controller.acc.get(i).getUsername().equals(username) || Controller.acc.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    @FXML
    private void confirm(){
        try {
            message.setVisible(false);
                if(email.getText().trim().isEmpty() || username.getText().trim().isEmpty() || nume.getText().trim().isEmpty() || prenume.getText().trim().isEmpty() || parola.getText().trim().isEmpty()){
                    message.setText("Nu puteti lăsa câmpuri libere!");
                    message.setTextFill(Color.RED);
                    message.setVisible(true);
                }
                else{
                    boolean k = false;
                    for(int i=0;i<Controller.manAcc.size();i++){
                        if(Controller.manAcc.get(i).getUsername().equals(username.getText()) || Controller.manAcc.get(i).getEmail().equals(email.getText())){
                            k = true;
                            break;
                        }
                    }

                    k = check(email.getText(), username.getText()) ? true : false;
        
                    if(k == true){
                        message.setVisible(true);
                        message.setTextFill(Color.RED);
                        message.setText("Contul deja există");
                    }
                    else{
                        Controller.manAcc.add(new Manageri(email.getText(), username.getText(), nume.getText(), prenume.getText(), parola.getText()));
                        try {
                            save();
                        } catch (IOException e1) {
                            System.out.println("EROARE DE SALVARE IN FISIER");
                        }
                    }
                }
            
        } catch (Exception e) {
            System.out.println(e);    
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

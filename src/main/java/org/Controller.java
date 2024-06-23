package org;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

// ListView
// https://www.youtube.com/watch?v=Pqfd4hoi5cc&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev&index=21


public class Controller implements Initializable{
    protected static String[] sub = {"Consultanță în cetățenii străine","Consultanță privind strategii","Consultanță în afaceri","Consultanță financiară","Consultanță IT","Consultanță în management","Consultanță în vânzări","Consultanță în marketing","Consultanță de brand","Servicii de consultanță în imobiliare"};
    @FXML
    private TextField loginRegister,passwordRegister,loginLogin,passwordLogin,userName,firstName,lastName,reportName,reportPrice,buget,settingsTextFIeld1,settingsTextFIeld2,settingsTextFIeld3,dezactivationConfirmation;
    @FXML
    private Label registerMessage,registerMessage1,loginMessage,myLabel,infoLabel,activationMessage,notnullMessage,USER,fileInputMessage,reportSuccess,reportInfo,reportInfo1,reportInfo2,appControlPanelInfo,appControlPanelInfo1,bugetLabel,appInfo,appBuget,settingsInfo,settingsInfo1,changeError,label,label1,label2,labelSubdivision,accountDezactivationMessage,confirmationMessageForDez,notnullDezactivation,deleteMessage,priceError,notnullError;
    @FXML
    private ListView<String> myListView = new ListView<>();
    @FXML
    private ListView<String> reportsList = new ListView<>();
    @FXML
    private ListView<String> reports = new ListView<>();
    @FXML
    private ListView<String> deleteCon;
    @FXML
    private ChoiceBox<String> selectSubdivision = new ChoiceBox<>();
    @FXML
    private Button newReport,changeConfirm,userAccountDesactivation,button1,button2,deleteAcc,deleteAcc1,button3; 
    @FXML
    private ImageView imgview,reportPhoto,companyLogo;
    @FXML
    private TextArea reportDesc;

    protected static ArrayList<Users> acc = new ArrayList<>();
    protected static ArrayList<String> deleteQueue = new ArrayList<>();
    protected static ArrayList<Manageri> manAcc = new ArrayList<>();
    private static int userIndex;

    int k = 0;


    private boolean check(String login,String password){
        for(int i = 0; i<manAcc.size();i++){
            if((manAcc.get(i).getUsername().equals(login) || manAcc.get(i).getEmail().equals(login)) && (manAcc.get(i).getParola().equals(password)))
                return true; 
        }
        return false;
    }

    public void login(ActionEvent event) throws IOException{
        boolean k = false;
        int index = 0;
        activationMessage.setTextFill(Color.TRANSPARENT);
        loginMessage.setTextFill(Color.TRANSPARENT);
        accountDezactivationMessage.setTextFill(Color.TRANSPARENT);


        if(check(loginLogin.getText(), passwordLogin.getText())){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/managerApp.fxml")));
            scene.getStylesheets().add(this.getClass().getResource("Style/managerApp.css").toExternalForm());
            stage.setScene(scene);     
            stage.setResizable(false);
        }
        else{
            for(int i=0;i<acc.size();i++){
                if((acc.get(i).getEmail().equals(loginLogin.getText()) || acc.get(i).getUsername().equals(loginLogin.getText())) && acc.get(i).getParola().equals(passwordLogin.getText())){
                    k = true;
                    index = i;
                    break;
                }
            }
            if(k == false){
                loginMessage.setStyle("-fx-text-fill: red;");
            }
            else{
                if(acc.get(index).getActivation() == true){
                    userIndex = index;
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/app.fxml")));
                    scene.getStylesheets().add(this.getClass().getResource("Style/app.css").toExternalForm());
                    stage.setResizable(false);
                    stage.setScene(scene);
                }
                else{
                    activationMessage.setTextFill(Color.RED);
                    System.out.println("CONTUL NU E ACTIVAT");
                }
            }
        }
    }
    
    public void register(ActionEvent event) throws IOException{
        notnullMessage.setTextFill(Color.TRANSPARENT);
        registerMessage.setTextFill(Color.TRANSPARENT);
        registerMessage1.setTextFill(Color.TRANSPARENT);

        boolean k = false;
        for(int i=0;i<acc.size();i++){
            if(acc.get(i).getEmail().equals(loginRegister.getText()) || acc.get(i).getUsername().equals(userName.getText())){
                registerMessage.setTextFill(Color.RED);
                registerMessage1.setTextFill(Color.TRANSPARENT);
                k = true;
                break;
            }
        }

        for(int i=0;i<manAcc.size();i++){
            if(manAcc.get(i).getEmail().equals(loginRegister.getText()) || manAcc.get(i).getUsername().equals(userName.getText())){
                registerMessage.setTextFill(Color.RED);
                registerMessage1.setTextFill(Color.TRANSPARENT);
                k = true;
                break;
            }
        }

        if(k == false){
            if(loginRegister.getText().isEmpty() || userName.getText().isEmpty() || firstName.getText().isEmpty() || lastName.getText().isEmpty() || passwordRegister.getText().isEmpty() || selectSubdivision.getSelectionModel().isEmpty()){
                notnullMessage.setTextFill(Color.RED);
            } 
            else{
                registerMessage.setTextFill(Color.TRANSPARENT);
                registerMessage1.setTextFill(Color.GREEN);
                FileWriter out = new FileWriter(new File("src\\main\\resources\\org\\Database\\accountsInfo.txt"),true);
                int sub = -1;
                sub = selectSubdivision.getSelectionModel().getSelectedIndex(); 
             
                acc.add(new Users(loginRegister.getText(), userName.getText(), firstName.getText(), lastName.getText(), passwordRegister.getText(), sub));
                out.write(" \n"+ loginRegister.getText()+ " " +userName.getText()+ " " +firstName.getText()+ " " +lastName.getText()+ " " +passwordRegister.getText()+ " "+ acc.get(acc.size() - 1).getCreationDate() + " " + acc.get(acc.size() - 1).getActivation() + " " + "0" + " " + acc.get(acc.size() - 1).getSubdivision());
                out.close();    
            }
        }
    }

    public void toCreateNewAccount(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/register.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/register.css").toExternalForm());
        stage.setScene(scene);
    }

    public void toLoginPage(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/login.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/login.css").toExternalForm());
        stage.setScene(scene);
    }

    public void backToApp(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/app.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/app.css").toExternalForm());
        stage.setScene(scene);
    }

    public void backToManagerApp(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/managerApp.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/managerApp.css").toExternalForm());
        stage.setScene(scene);    
    }

    public void toNewReport(ActionEvent event) throws IOException{
        sourcePath = null;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/appNewReport.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/appNewReport.css").toExternalForm());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void toAppSettings(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/appSettings.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/appSettings.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void toMyReports(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/appMyReports.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/appMyReports.css").toExternalForm());
        stage.setScene(scene);        
    }

    @FXML 
    private void toConfirmDelete(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/deleteConfirmation.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/deleteConfirmation.css").toExternalForm());
        stage.setScene(scene);     
    }

    @FXML
    private void toNewManager(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/newManager.fxml")));
        scene.getStylesheets().add(this.getClass().getResource("Style/newManager.css").toExternalForm());
        stage.setScene(scene);     
    }

    public void accountActivation() throws IOException{
        int index = myListView.getSelectionModel().getSelectedIndex();
        
        if(acc.get(index).getActivation() == false){
            acc.get(index).setActivation(true);
            FileWriter save = new FileWriter(new File("src\\main\\resources\\org\\Database\\accountsInfo.txt"));

            String activ = acc.get(index).getActivation() ? "Activat" : "Dezactivat";
            myLabel.setText(myListView.getSelectionModel().getSelectedItem() + "\n" + acc.get(index).getEmail() + "\n" +acc.get(index).getNume() + "\n" +acc.get(index).getPrenume() + "\n" + acc.get(index).getCreationDate() + "\n" + acc.get(index).getRaport() + "\n" + sub[acc.get(index).getSubdivision()] + "\n" + activ);

            for(int i=0;i<acc.size();i++){
                save.write(acc.get(i).getEmail()+ " " +acc.get(i).getUsername()+ " " +acc.get(i).getNume()+ " " +acc.get(i).getPrenume()+ " " +acc.get(i).getParola()+ " "+ acc.get(i).getCreationDate() + " " + acc.get(i).getActivation() + " " + acc.get(i).getRaport() + " " + acc.get(i).getSubdivision());
                if(i != acc.size() - 1){
                    save.write("\n");
                }
            }
            save.close();
        }
        else
            System.out.println("CONTUL ESTE DEJA ACTIVAT");   
    
    }
    
    public void accountDezactivation() throws IOException{
        int index = myListView.getSelectionModel().getSelectedIndex();

        if(acc.get(index).getActivation() == true){
            acc.get(index).setActivation(false);
            FileWriter save = new FileWriter(new File("src\\main\\resources\\org\\Database\\accountsInfo.txt"));

            String activ = acc.get(index).getActivation() ? "Activat" : "Dezactivat";
            myLabel.setText(myListView.getSelectionModel().getSelectedItem() + "\n" + acc.get(index).getEmail() + "\n" +acc.get(index).getNume() + "\n" +acc.get(index).getPrenume() + "\n" + acc.get(index).getCreationDate() + "\n" + acc.get(index).getRaport() + "\n" + sub[acc.get(index).getSubdivision()] + "\n" + activ);

            for(int i=0;i<acc.size();i++){
                save.write(acc.get(i).getEmail()+ " " +acc.get(i).getUsername()+ " " +acc.get(i).getNume()+ " " +acc.get(i).getPrenume()+ " " +acc.get(i).getParola()+ " "+ acc.get(i).getCreationDate() + " " + acc.get(i).getActivation() + " " + acc.get(i).getRaport() + " " + acc.get(i).getSubdivision());
                if(i != acc.size() - 1){
                    save.write("\n");
                }
            }

            save.close();
        }
        else
            System.out.println("CONTUL ESTE DEJA DEZACTIVAT");
    }

    public void accountDeletion() throws IOException{
        try {
            int index = myListView.getSelectionModel().getSelectedIndex();
            acc.remove(index);
    
            save();
            
            String[] c = new String[acc.size()];
            for(int i=0;i<acc.size();i++){
                c[i] = acc.get(i).getUsername();
            }

            myLabel.setText(null);
            infoLabel.setText(null);
            myListView.getSelectionModel().clearSelection();
            myListView.getItems().setAll(c);
        }
        catch(Exception e){}
        
    }

    @FXML
    private void setBuget(){
        try{
            Users.setBuget(Integer.parseInt(buget.getText()));
            bugetLabel.setText(buget.getText() + " €");
            FileWriter save = new FileWriter(new File("src\\main\\resources\\org\\Database\\buget.txt"));
            save.write(buget.getText());
            save.close();

            buget.clear();
        }
        catch(Exception e){
            System.out.println("Buget : "+e);
        }
    }


    static Path sourcePath,targetPath;
    
    @FXML
    private void singeFileChooser(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Imagini", "*.png")
        );
        fc.setInitialDirectory(new File("src\\main\\resources\\org\\Database\\PozeChitante")); 
        File selectedFile = fc.showOpenDialog(stage);
    
        if (selectedFile != null) {
            try {
                String newFileName = "raport---"+acc.get(userIndex).getUsername()+"---"+acc.get(userIndex).getRaport()+".png";

                
                sourcePath = selectedFile.toPath();
                targetPath = new File("src/main/resources/org/Database/PozeChitante/" + newFileName).toPath();
                
                fileInputMessage.setText(selectedFile.getName());
                fileInputMessage.setTextFill(Color.WHITE);
            } catch (Exception ex) {
                fileInputMessage.setText("EROARE !!! Adresativă la un manager !");
            }
        } else {
            fileInputMessage.setTextFill(Color.RED);
            fileInputMessage.setText("Niciun fisier nu a fost selectat!");
        }
    }
    
    @FXML
    private void newReport(ActionEvent event) throws IOException{
        try {
            notnullError.setVisible(false);
            if(reportName.getText().trim().isEmpty() || reportPrice.getText().trim().isEmpty() || reportDesc.getText().trim().isEmpty() || sourcePath == null){
                notnullError.setVisible(true);
            }
            else{
                if(Users.getBuget() - Integer.parseInt(reportPrice.getText()) < 0){
                    priceError.setVisible(true);
                }
                else{
                    FileWriter out = new FileWriter(new File("src/main/resources/org/Database/Reports/"+"raport---"+acc.get(userIndex).getUsername()+"---"+acc.get(userIndex).getRaport() + ".txt"));
                    out.write(reportName.getText() + " " + reportPrice.getText()+ " \n" + reportDesc.getText());
                    out.close();
                    acc.get(userIndex).setRaport(acc.get(userIndex).getRaport() + 1);
                    reportSuccess.setTextFill(Color.GREEN);
                    
                    Users.setBuget(Users.getBuget() - Integer.parseInt(reportPrice.getText()));
                    
                    FileWriter save = new FileWriter(new File("src\\main\\resources\\org\\Database\\buget.txt"));
                    save.write(Users.getBuget() + " ");
                    save.close();
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    save();


                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/app.fxml")));
                    scene.getStylesheets().add(this.getClass().getResource("Style/app.css").toExternalForm());
                    stage.setScene(scene);
                }
            }

        
        } catch (Exception e) {
            reportSuccess.setText("Eroare !!! (adresativa la un manager)");
            reportSuccess.setTextFill(Color.RED);
        }
    }

    @FXML
    private void errorClear(){
        changeError.setVisible(false);
    }

    @FXML
    private void newUsername(){
        settingsTextFIeld1.clear();
        settingsTextFIeld2.clear();
        settingsTextFIeld3.clear();
        settingsTextFIeld1.setPromptText("Username nou");
        settingsTextFIeld2.setPromptText("Parola");
        settingsTextFIeld1.setVisible(true);
        settingsTextFIeld2.setVisible(true);
        settingsTextFIeld3.setVisible(false);
        changeConfirm.setVisible(true);
        changeError.setVisible(false);
        confirmationMessageForDez.setVisible(false);
        deleteMessage.setVisible(false);
        dezactivationConfirmation.setVisible(false);
        notnullDezactivation.setVisible(false);

        changeConfirm.setOnAction(e -> {
            if(settingsTextFIeld2.getText().equals(acc.get(userIndex).getParola())){
                if(settingsTextFIeld1.getText().equals(acc.get(userIndex).getUsername())){
                    changeError.setTextFill(Color.RED);
                    changeError.setText("Deja aveti acest username!");
                    changeError.setVisible(true);
                }
                else{
                    boolean check = false;
                    for(int i=0;i<acc.size();i++){
                        if(acc.get(i).getUsername().equals(settingsTextFIeld1.getText())){
                            check = true;
                            break;
                        }
                    }

                    if(check == true){
                        changeError.setTextFill(Color.RED);
                        changeError.setText("Username-ul deja există!");
                        changeError.setVisible(true);
                    }
                    else{
                        try {
                            acc.get(userIndex).setUsername(settingsTextFIeld1.getText());
                            save();
                            
                            changeError.setTextFill(Color.GREEN);
                            changeError.setText("Succes!");
                            changeError.setVisible(true);
                        } catch (Exception error) {
                            changeError.setTextFill(Color.RED);
                            changeError.setText("A apărut o eroare!\n"+error);
                            changeError.setVisible(true);
                        }
                    }
                }
            }
            else{
                changeError.setTextFill(Color.RED);
                changeError.setText("Parola nu corespunde!");
                changeError.setVisible(true);
            }
        });
    
    }

    @FXML
    private void newEmail(){
        settingsTextFIeld1.clear();
        settingsTextFIeld2.clear();
        settingsTextFIeld3.clear();
        settingsTextFIeld1.setPromptText("Email nou");
        settingsTextFIeld2.setPromptText("Parola");
        settingsTextFIeld1.setVisible(true);
        settingsTextFIeld2.setVisible(true);
        settingsTextFIeld3.setVisible(false);
        deleteMessage.setVisible(false);
        changeConfirm.setVisible(true);
        changeError.setVisible(false);
        confirmationMessageForDez.setVisible(false);
        dezactivationConfirmation.setVisible(false);
        notnullDezactivation.setVisible(false);
        

        changeConfirm.setOnAction(e -> {
            if(acc.get(userIndex).getParola().equals(settingsTextFIeld2.getText())){
                if(acc.get(userIndex).getEmail().equals(settingsTextFIeld1.getText())){
                    changeError.setTextFill(Color.RED);
                    changeError.setText("Aveti deja acest email!");
                    changeError.setVisible(true);
                }
                else{
                    boolean check = false;
                    for(int i=0;i<acc.size();i++){
                        if(acc.get(i).getEmail().equals(settingsTextFIeld1.getText())){
                            check = true;
                            break;
                        }
                    }
                    
                    if(check == true){
                        changeError.setTextFill(Color.RED);
                        changeError.setText("Alt utilizator are\nacest email!");
                        changeError.setVisible(true);
                    }
                    else{
                        try {
                            acc.get(userIndex).setEmail(settingsTextFIeld1.getText());
                            save();

                            changeError.setTextFill(Color.GREEN);
                            changeError.setText("Succes!");
                            changeError.setVisible(true);
                        } catch (Exception error) {
                            changeError.setTextFill(Color.RED);
                            changeError.setText("A apărut o eroare!");
                            changeError.setVisible(true);
                        }
                    }
                }
            }
            else{
                changeError.setTextFill(Color.RED);
                changeError.setText("Parola nu corespunde!");
                changeError.setVisible(true);
            }
        });
    }

    @FXML
    private void newPassword(){
        settingsTextFIeld1.clear();
        settingsTextFIeld2.clear();
        settingsTextFIeld3.clear();
        settingsTextFIeld3.setPromptText("Parola veche");
        settingsTextFIeld2.setPromptText("Parola nouă");
        settingsTextFIeld3.setVisible(true);
        settingsTextFIeld2.setVisible(true);
        settingsTextFIeld1.setVisible(false);
        deleteMessage.setVisible(false);
        confirmationMessageForDez.setVisible(false);
        dezactivationConfirmation.setVisible(false);
        changeConfirm.setVisible(true);
        changeError.setVisible(false);
        notnullDezactivation.setVisible(false);

        changeConfirm.setOnAction(e -> {
            if(acc.get(userIndex).getParola().equals(settingsTextFIeld3.getText())){
                if(acc.get(userIndex).getParola().equals(settingsTextFIeld2.getText())){
                    changeError.setText("Nu vă puteți seta aceiași parolă!");
                    changeError.setTextFill(Color.RED);
                    changeError.setVisible(true);
                }
                else{
                    try {
                        acc.get(userIndex).setParola(settingsTextFIeld2.getText());
                        save();

                        changeError.setText("Succes!");
                        changeError.setTextFill(Color.GREEN);
                        changeError.setVisible(true);
                    } catch (Exception error) {
                        changeError.setText("A apărut o eroare!");
                        changeError.setTextFill(Color.RED);
                        changeError.setVisible(true);
                    }
                }
            }
            else{
                changeError.setText("Parola veche nu\ncorespunde!");
                changeError.setTextFill(Color.RED);
                changeError.setVisible(true);
            }
        });
    }

    @FXML
    private void newDesactivation(){
        settingsTextFIeld1.clear();
        settingsTextFIeld2.clear();
        settingsTextFIeld3.clear();
        settingsTextFIeld1.setVisible(false);
        settingsTextFIeld2.setVisible(false);
        settingsTextFIeld3.setVisible(false);
        deleteMessage.setVisible(false);
        changeError.setVisible(false);
        changeConfirm.setVisible(true);
        confirmationMessageForDez.setVisible(true);
        confirmationMessageForDez.setText("Pentru a dezactiva contul\nintroduceți email-ul");
        dezactivationConfirmation.setVisible(true);
        notnullDezactivation.setVisible(false);


        changeConfirm.setOnAction(e -> {
            if(dezactivationConfirmation.getText().equals(acc.get(userIndex).getEmail())){
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                try {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Interface/login.fxml")));
                    scene.getStylesheets().add(this.getClass().getResource("Style/login.css").toExternalForm());
                    stage.setScene(scene);
    


                    
                    try {
                        acc.get(userIndex).setActivation(false);
                        save();
                    } catch (Exception error) {

                    }
                        Stage mess = new Stage();
                        Label m = new Label("Contul a fost dezactivat cu succes !\nPentru reactivarea lui apropiațivă de un manager !");
                        m.setFont(new Font(20));
                        m.setAlignment(Pos.CENTER);
                        m.setTextAlignment(TextAlignment.CENTER);                        
                        Scene scene1 = new Scene(new Pane(m),500,80);
                        scene1.getStylesheets().add(this.getClass().getResource("Style/dezactivationMessage.css").toExternalForm());
                        mess.setScene(scene1);
                        mess.setResizable(false);
                        mess.show();
                    
                    // accountDezactivationMessage.setTextFill(Color.GREEN);
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
            else if(dezactivationConfirmation.getText().trim().isEmpty()){
                notnullDezactivation.setText("Nu puteți lăsa câmpuri goale !");
                notnullDezactivation.setVisible(true);
            }
            else{
                notnullDezactivation.setText("Email-ul nu corespunde !");
                notnullDezactivation.setVisible(true);
            }
        });
    }

    @FXML
    private void newDelete() throws IOException{
        settingsTextFIeld1.clear();
        settingsTextFIeld2.clear();
        settingsTextFIeld3.clear();
        settingsTextFIeld1.setVisible(false);
        settingsTextFIeld2.setVisible(false);
        settingsTextFIeld3.setVisible(false);
        deleteMessage.setVisible(false);
        changeError.setVisible(false);
        changeConfirm.setVisible(false);
        confirmationMessageForDez.setVisible(false);
        dezactivationConfirmation.setVisible(false);
        notnullDezactivation.setVisible(false);
        
        
        
        
        
        boolean check = false;
        for(String user : deleteQueue){
            if(acc.get(userIndex).getUsername().equals(user)){
                check = true;
                break;
            }
        }

        if(!check){
            deleteMessage.setText("Contul s-a înregistrat cu succes!\nAșteptați aprobarea unui\nmanager.");
            deleteMessage.setTextFill(Color.DARKRED);
            deleteMessage.setVisible(true);
            deleteQueue.add(acc.get(userIndex).getUsername());
            FileWriter saveQ = new FileWriter(new File("src\\main\\resources\\org\\Database\\deleteQueue.txt"),true);
            saveQ.write(acc.get(userIndex).getUsername() + "\n");
            saveQ.close();        
        }
        else{
            deleteMessage.setText("Contul este în lista de așteptare!");
            deleteMessage.setTextFill(Color.RED);
            deleteMessage.setVisible(true);
        }
    }


    private void save() throws IOException{
        FileWriter save  = new FileWriter(new File("src\\main\\resources\\org\\Database\\accountsInfo.txt"));
        for(int i=0;i<acc.size();i++){
            save.write(acc.get(i).getEmail()+ " " +acc.get(i).getUsername()+ " " +acc.get(i).getNume()+ " " +acc.get(i).getPrenume()+ " " +acc.get(i).getParola()+ " "+ acc.get(i).getCreationDate() + " " + acc.get(i).getActivation() + " " + acc.get(i).getRaport() + " " + acc.get(i).getSubdivision());
            if(i != acc.size() - 1){
                save.write("\n");
            }
        }
        save.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            appControlPanelInfo.setText("User name: "+"\n"+"Email: "+"\n"+"Nume: "+"\n"+"Prenume:"+"\n"+ "Data crearii contului:"+"\n"+"Numarul de rapoarte:");
        } catch (Exception e) {
            
        }
        
        try {                
            appInfo.setText(acc.get(userIndex).getUsername()+ "\n" +acc.get(userIndex).getEmail() + "\n" +acc.get(userIndex).getNume() + "\n" +acc.get(userIndex).getPrenume() + "\n" + acc.get(userIndex).getCreationDate() + "\n" + acc.get(userIndex).getRaport());
        } catch (Exception e) {}

        try {
            Arrays.sort(sub);
            selectSubdivision.getItems().addAll(sub);
            
            selectSubdivision.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                    labelSubdivision.setText(null);
                }
                
            });

            String[] c = new String[acc.size()];
            for(int i=0;i<acc.size();i++){
                c[i] = acc.get(i).getUsername();
            }
            
            myListView.getItems().setAll(c);
            myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                try {
                    int index = myListView.getSelectionModel().getSelectedIndex();
                    
                    infoLabel.setText("User name: "+"\n"+"Email: "+"\n"+"Nume: "+"\n"+"Prenume:"+"\n"+ "Data crearii contului:"+"\n"+"Numarul de rapoarte:"+"\n"+"Subdiviziune:"+"\n"+"Statutul contului:");
                    String activ = acc.get(index).getActivation() ? "Activat" : "Dezactivat";
                    myLabel.setText(myListView.getSelectionModel().getSelectedItem() + "\n" + acc.get(index).getEmail() + "\n" +acc.get(index).getNume() + "\n" +acc.get(index).getPrenume() + "\n" + acc.get(index).getCreationDate() + "\n" + acc.get(index).getRaport()+ "\n" + sub[acc.get(index).getSubdivision()] + "\n" + activ);
                    myLabel.setTextFill(Color.WHITE);
                    imgview.setImage(null);
                    
                    label.setText(null);
                    label1.setText(null);
                    label2.setText(null);

                    
                    String[] a = new String[acc.get(index).getRaport()];
                    Scanner in;
                    
                    for(int i=0;i<acc.get(index).getRaport();i++){
                        in = new Scanner(new FileReader(new File("src/main/resources/org/Database/Reports/raport---"+acc.get(index).getUsername()+"---"+i+".txt")));
                        a[i] = "Raportul nr." + (i+1) + " - " + in.next();
                        
                        
                    }
                    
                    reports.getItems().setAll(a);
                    

                    reports.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                        @Override
                        public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                            
                            try {
                                int reportIndex = reports.getSelectionModel().getSelectedIndex();
                                Scanner in = new Scanner(new FileReader(new File("src/main/resources/org/Database/Reports/raport---"+acc.get(index).getUsername()+"---"+reportIndex+".txt")));
                                label.setText(in.next());
                                label1.setText(in.next() + " €");


                                String c = "";
                                while (in.hasNextLine()) {
                                    String line = in.nextLine(); 
                                    Scanner lineScanner = new Scanner(line); 
                                    while (lineScanner.hasNext()) {
                                        c += lineScanner.next() + " "; 
                                    }
                                    c += "\n"; 
                                    lineScanner.close(); 
                                }

                                label2.setText(c);
                                in.close();

                                imgview.setImage(null);
                                imgview.setImage(new Image("src/main/resources/org/Database/PozeChitante/raport---"+acc.get(index).getUsername()+"---"+reportIndex+".png"));
                                
                                
                            } catch (Exception e) {}    
                        }
                    });

                
                } catch (Exception e) {}
            }    
        });

        USER.setText(acc.get(userIndex).getUsername());

        } catch (Exception e) {}

        

        //////////////////////////////////////////////////////////////


        try {
            String[] list = new String[acc.get(userIndex).getRaport()];
            for(int i=0;i<acc.get(userIndex).getRaport();i++){
                list[i] = "Raportul numărul "+(i+1);
            }
            reportsList.getItems().addAll(list);
    
            reportsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                    try {
                        int index = reportsList.getSelectionModel().getSelectedIndex();
                        FileReader fin = new FileReader(new File("src/main/resources/org/Database/Reports/raport---"+acc.get(userIndex).getUsername()+"---"+index+".txt"));
                        Scanner in = new Scanner(fin);
    
                        String a = in.next();
                        String b = in.next();
                        String c = "";
                        while (in.hasNextLine()) {
                            String line = in.nextLine(); 
                            Scanner lineScanner = new Scanner(line); 
                            while (lineScanner.hasNext()) {
                                c += lineScanner.next() + " "; 
                            }
                            c += "\n"; 
                            lineScanner.close(); 
                        }
                        in.close();
                        reportInfo.setText(a);
                        reportInfo1.setText(b + " €");
                        reportInfo2.setText(c);

                        try {
                            reportPhoto.setImage(new Image("src/main/resources/org/Database/PozeChitante/raport---"+acc.get(userIndex).getUsername()+"---"+index+".png"));
                        } catch (Exception e) {
                            reportPhoto.setImage(null);
                        }


                    } catch (FileNotFoundException e) {}
                }
            });
        } catch (Exception e) {}


        //////////////////////////////////////////////////////////

        try {
            deleteCon.getItems().setAll(deleteQueue);
        
            deleteCon.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                    
                    deleteAcc.setOnAction(e -> {
                        String user = deleteCon.getSelectionModel().getSelectedItem();
                        for(int i = 0; i < acc.size(); i++){
                            if(acc.get(i).getUsername().equals(user)){
                                acc.remove(i);
                                try {
                                    save();
                                } catch (IOException e1) {}

                                try {
                                    FileWriter savedelQ = new FileWriter(new File("src\\main\\resources\\org\\Database\\deleteQueue.txt"));
                                    deleteQueue.remove(deleteCon.getSelectionModel().getSelectedIndex());
                                    
                                    for(int j=0;j<deleteQueue.size();j++){
                                        savedelQ.write(deleteQueue.get(j)+"\n");
                                    }
                                    
                                    savedelQ.close();
                                    deleteCon.getItems().setAll(deleteQueue);
                                    
                                }
                                catch (IOException e1) {}
                                break;
                            }
                        }
                        deleteCon.getSelectionModel().clearSelection();
                    });
                }
            });
        
        } catch (Exception e) {}

        ///////////////////////////////////////////////////////////

        try {
            deleteAcc1.setOnAction(e -> {
                try {
                    FileWriter savedelQ = new FileWriter(new File("src\\main\\resources\\org\\Database\\deleteQueue.txt"));
                    deleteQueue.remove(deleteCon.getSelectionModel().getSelectedIndex());
                    for(int j=0;j<deleteQueue.size();j++){
                        savedelQ.write(deleteQueue.get(j)+"\n");
                    }
                    savedelQ.close();
                    deleteCon.getSelectionModel().clearSelection();
                    deleteCon.getItems().setAll(deleteQueue);
                } catch (Exception error) {}
    
            });
        } catch (Exception e) {}


        ///////////////////////////////////////////////////////////

        try {
            appBuget.setText(Users.getBuget()+ " €");
        } catch (Exception e) {}
        try {   
            bugetLabel.setText(Users.getBuget() + " €");
        } catch (Exception e) {}
        try{
            settingsInfo.setText(acc.get(userIndex).getUsername()+ "\n" +acc.get(userIndex).getEmail() + "\n" +acc.get(userIndex).getNume() + "\n" +acc.get(userIndex).getPrenume() + "\n" + acc.get(userIndex).getCreationDate() + "\n" + acc.get(userIndex).getRaport());
        }
        catch(Exception e){}
        try {
            settingsInfo1.setText("User name: "+"\n"+"Email: "+"\n"+"Nume: "+"\n"+"Prenume:"+"\n"+ "Data crearii contului:"+"\n"+"Numarul de rapoarte:");
        } catch (Exception e) {}
        
        try {
            reportName.setOnMouseClicked(e -> {
                notnullError.setVisible(false);
                priceError.setVisible(false);
                reportSuccess.setVisible(false);
            });
            reportPrice.setOnMouseClicked(e -> {
                notnullError.setVisible(false);
                priceError.setVisible(false);
                reportSuccess.setVisible(false);    
            });
            reportDesc.setOnMouseClicked(e -> {
                notnullError.setVisible(false);
                priceError.setVisible(false);
                reportSuccess.setVisible(false);
            });
        } catch (Exception e) {}

        try {
            companyLogo.setImage(new Image("Database/icon.png"));
        } catch (Exception e) {}

    }
}


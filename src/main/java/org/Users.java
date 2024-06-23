package org;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Users extends Muncitori{
    private boolean activation;
    private int subdivision;

    Users(String email,String username,String nume,String prenume,String parola,int subdivision){
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        this.activation = false;
        this.subdivision = subdivision;
        creationDate = new SimpleDateFormat("dd-MM-yyyy|HH:mm").format(new Date());
    }

    Users(String email,String username,String nume,String prenume, String parola, String creationDate, String activationStatus, int raport, int subdivision){
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        this.creationDate = creationDate;
        this.subdivision = subdivision;
        
        if(activationStatus.equals("true"))
            this.activation = true;
        else
            this.activation = false;
        this.raport = raport;
    }
    
    
    public int getSubdivision() {
        return subdivision;
    }
    public void setSubdivision(int subdivision) {
        this.subdivision = subdivision;
    }
    public boolean getActivation(){
        return activation;
    }
    public void setActivation(boolean activation) {
        this.activation = activation;
    }
}

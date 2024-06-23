package org;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Manageri extends Muncitori{
    
    Manageri(String email,String username,String nume,String prenume,String parola){
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        creationDate = new SimpleDateFormat("dd-MM-yyyy|HH:mm").format(new Date());
    }

    Manageri(String email,String username,String nume,String prenume, String parola, String creationDate){
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        this.creationDate = creationDate;
    }
}

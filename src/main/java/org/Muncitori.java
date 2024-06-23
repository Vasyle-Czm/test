package org;


public abstract class Muncitori{
    public String username,nume,prenume,email,parola,creationDate;
    public int raport;
    public static int buget;


    public static int getBuget() {
        return buget;
    }
    public static void setBuget(int buget) {
        Muncitori.buget = buget;
    }
    public int getRaport() {
        return raport;
    }
    public String getParola() {
        return parola;
    }
    public String getEmail() {
        return email;
    }
    public String getNume() {
        return nume;
    }
    public String getPrenume() {
        return prenume;
    }
    public String getUsername() {
        return username;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public void setParola(String parola) {
        this.parola = parola;
    }
    public void setRaport(int raport) {
        this.raport = raport;
    }
}
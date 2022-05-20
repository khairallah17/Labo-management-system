package Labo.classes;

import java.sql.Date;

public class Salle {
    int id;
    String nom;
    int nombreEtudiant;
    Date reservation;

    public Salle(){
        this.id = 0;
        this.nom = null;
        this.nombreEtudiant = 0;
        this.reservation = null;
    }

    public Salle(int id, String nom, int nombreEtudiant, Date reservation){
        this.id = id;
        this.nom = nom;
        this.nombreEtudiant = nombreEtudiant;
        this.reservation = reservation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setNombreEtudiant(int nombresetudiant){
        this.nombreEtudiant = nombresetudiant;
    }

    public void setReservation(Date reservation){
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public String getNom(){
        return this.nom;
    }

    public int getNombreEtudiant(){
        return this.nombreEtudiant;
    }

    public Date getReservation(){
        return this.reservation;
    }
}

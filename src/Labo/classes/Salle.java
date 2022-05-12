package Labo.classes;

import java.sql.Date;

public class Salle {
    String nom;
    int nombreEtudiant;
    Date reservation;

    public Salle(){
        this.nom = null;
        this.nombreEtudiant = 0;
        this.reservation = null;
    }

    public Salle(String nom, int nombreEtudiant, Date reservation){
        this.nom = nom;
        this.nombreEtudiant = nombreEtudiant;
        this.reservation = reservation;
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

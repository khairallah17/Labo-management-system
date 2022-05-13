package Labo.classes;

import java.sql.Date;

public class Reservation {
    String nom;
    String cin;
    String materiel;
    Date dateDebut;
    Date dateFin;

    public Reservation(){
        this.nom = null;
        this.cin = null;
        this.materiel = null;
        this.dateDebut = null;
        this.dateFin = null;
    }

    public Reservation(String nom, String cin, String materiel, Date dateDebut, Date dateFin){
        this.nom = nom;
        this.cin = cin;
        this.materiel = materiel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setMateriel(String materiel) {
        this.materiel = materiel;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCin() {
        return cin;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getMateriel() {
        return materiel;
    }

    public String getNom() {
        return nom;
    }
}

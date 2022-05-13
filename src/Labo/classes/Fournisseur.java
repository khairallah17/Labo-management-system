package Labo.classes;

public class Fournisseur {
    String nomComplet;
    String email;
    String cin;
    int tel;

    public Fournisseur() {
        this.nomComplet = null;
        this.email = null;
        this.cin = null;
        this.tel = 0;
    }

    public Fournisseur(String nomComplet, String email, String cin, int tel){
        this.nomComplet = nomComplet;
        this.email = email;
        this.cin = cin;
        this.tel = tel;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getCin() {
        return cin;
    }

    public String getEmail() {
        return email;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public int getTel() {
        return tel;
    }
}

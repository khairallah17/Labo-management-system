package Labo.classes;
public class Materiels {
  String nom;
  int quantite;
  String reference;
  String status;
  
  public Materiels(){
      this.nom = null;
      this.reference = null;
      this.quantite = 0;
      this.status = null;
  }

  public Materiels(String nom, String reference, int quantite, String status){
    this.nom = nom;
    this.reference = reference;
    this.quantite = quantite;
    this.status = status;
  }

  public void setNom(String nom) {
      this.nom = nom;
  }

  public void setQuantite(int quantite) {
      this.quantite = quantite;
  }
  
  public void setReference(String reference) {
      this.reference = reference;
  }

  public void setStatus(String status) {
      this.status = status;
  }

  public String getNom() {
      return nom;
  }

  public int getQuantite() {
      return quantite;
  }

  public String getReference() {
      return reference;
  }

  public String getStatus() {
      return status;
  }

}

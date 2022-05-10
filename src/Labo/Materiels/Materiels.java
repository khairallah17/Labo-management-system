package Labo.Materiels;

public class Materiels {
  protected String nom;
  protected int nombre;
  protected String reference;
  protected String module;
  protected String status;
  
  public Materiels(){
      this.nom = null;
      this.reference = null;
      this.module = null;
      this.nombre = 0;
      this.status = null;
  }

  public Materiels(String nom, String reference, int nombre, String module, String status){
    this.nom = nom;
    this.reference = reference;
    this.module = module;
    this.nombre = nombre;
    this.status = status;
  }

  public void getInfo(){
    System.out.println(this.nom + "," + this.reference + "," + this.nombre + "," + this.module +","+ this.status);
  }
}

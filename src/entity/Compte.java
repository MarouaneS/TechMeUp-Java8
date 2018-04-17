package entity;

import java.util.Objects;
import java.util.Set;

public class Compte {

    private int id;
    private String nom;
    private int anneeOuverture;

    private Set<Emprunt> emprunts;

    public Compte(int id, String nom, int anneeOuverture) {
        this.id = id;
        this.nom = nom;
        this.anneeOuverture = anneeOuverture;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAnneeOuverture() {
        return anneeOuverture;
    }

    public void setAnneeOuverture(int anneeOuverture) {
        this.anneeOuverture = anneeOuverture;
    }

    public Set<Emprunt> getEmprunts() {
        return emprunts;
    }

    public void setEmprunts(Set<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Compte compte = (Compte) o;
        return id == compte.id &&
               anneeOuverture == compte.anneeOuverture &&
               Objects.equals(nom, compte.nom) &&
               Objects.equals(emprunts, compte.emprunts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nom, anneeOuverture, emprunts);
    }
}

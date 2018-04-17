package entity;

import java.util.Objects;

public class Emprunt {

    int idCompte;
    int id;
    int montant;
    int anneeRealisation;

    public Emprunt(int idCompte, int id, int montant, int anneeRealisation) {
        this.idCompte = idCompte;
        this.id = id;
        this.montant = montant;
        this.anneeRealisation = anneeRealisation;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getAnneeRealisation() {
        return anneeRealisation;
    }

    public void setAnneeRealisation(int anneeRealisation) {
        this.anneeRealisation = anneeRealisation;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
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
        Emprunt emprunt = (Emprunt) o;
        return idCompte == emprunt.idCompte &&
               id == emprunt.id &&
               montant == emprunt.montant &&
               anneeRealisation == emprunt.anneeRealisation;
    }

    @Override
    public int hashCode() {

        return Objects.hash(idCompte, id, montant, anneeRealisation);
    }
}

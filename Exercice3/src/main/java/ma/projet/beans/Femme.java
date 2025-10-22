package ma.projet.beans;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedNativeQuery(
        name = "Femme.countEnfantsBetweenDates",
        query = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM mariage m WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :dateDebut AND :dateFin",
        resultClass = Long.class
)
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mariage> mariages = new ArrayList<>();

    // Constructeurs
    public Femme() {
        super();
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    // Getters et Setters
    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}
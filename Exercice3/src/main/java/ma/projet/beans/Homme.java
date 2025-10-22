package ma.projet.beans;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mariage> mariages = new ArrayList<>();

    // Constructeurs
    public Homme() {
        super();
    }

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
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
package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projet")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Chef de projet (many projets -> one employe)
    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Employe chefDeProjet;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> taches = new ArrayList<>();

    // getters / setters / constructors
    public Projet() {}
    public Projet(String nom, LocalDate dateDebut, LocalDate dateFin) {
        this.nom = nom; this.dateDebut = dateDebut; this.dateFin = dateFin;
    }

    // add/remove helper
    public void addTache(Tache t) {
        t.setProjet(this);
        taches.add(t);
    }

    // getters / setters omitted for brevity - add all standard ones
    // ... (complete getters and setters)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public Employe getChefDeProjet() { return chefDeProjet; }
    public void setChefDeProjet(Employe chefDeProjet) { this.chefDeProjet = chefDeProjet; }
    public List<Tache> getTaches() { return taches; }
    public void setTaches(List<Tache> taches) { this.taches = taches; }
}

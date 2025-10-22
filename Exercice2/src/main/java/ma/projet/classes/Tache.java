package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tache")
@NamedQueries({
        @NamedQuery(name = "Tache.findByPrixGreaterThan",
                query = "SELECT t FROM Tache t WHERE t.prix > :prix")
})
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeTache> employeTaches = new HashSet<>();

    public Tache() {}
    public Tache(String nom, LocalDate dateDebut, LocalDate dateFin, double prix) {
        this.nom = nom; this.dateDebut = dateDebut; this.dateFin = dateFin; this.prix = prix;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }
    public Set<EmployeTache> getEmployeTaches() { return employeTaches; }
    public void setEmployeTaches(Set<EmployeTache> employeTaches) { this.employeTaches = employeTaches; }
}

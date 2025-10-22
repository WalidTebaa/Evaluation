package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(
        name = "Produit.findPrixSup100", // 🔹 même nom que dans ProduitService
        query = "FROM Produit p WHERE p.prix > 100"
)
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id") // 🔹 clé étrangère
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommande> ligneCommandes;

    // ==== Getters & Setters ====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public List<LigneCommande> getLigneCommandes() { return ligneCommandes; }
    public void setLigneCommandes(List<LigneCommande> ligneCommandes) { this.ligneCommandes = ligneCommandes; }
}

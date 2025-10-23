package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.service.ProduitService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.CategorieService;
import ma.projet.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneService = context.getBean(LigneCommandeService.class);
        CategorieService categorieService = context.getBean(CategorieService.class);


        // Récupérer ou créer les catégories pour éviter la contrainte d'unicité sur 'code'
        Categorie cat1 = categorieService.findByCode("C1");
        if (cat1 == null) {
            cat1 = new Categorie();
            cat1.setCode("C1");
            cat1.setLibelle("Catégorie 1");
            categorieService.create(cat1);
        }

        Categorie cat2 = categorieService.findByCode("C2");
        if (cat2 == null) {
            cat2 = new Categorie();
            cat2.setCode("C2");
            cat2.setLibelle("Catégorie 2");
            categorieService.create(cat2);
        }


        Produit p1 = produitService.findByReference("ES12");
        if (p1 == null) {
            p1 = new Produit();
            p1.setReference("ES12");
            p1.setPrix(120f);
            p1.setCategorie(cat1);
            produitService.create(p1);
        }

        Produit p2 = produitService.findByReference("ZR85");
        if (p2 == null) {
            p2 = new Produit();
            p2.setReference("ZR85");
            p2.setPrix(100f);
            p2.setCategorie(cat1);
            produitService.create(p2);
        }

        Produit p3 = produitService.findByReference("EE85");
        if (p3 == null) {
            p3 = new Produit();
            p3.setReference("EE85");
            p3.setPrix(200f);
            p3.setCategorie(cat2);
            produitService.create(p3);
        }


        Commande c1 = new Commande();
        c1.setDate(LocalDate.of(2013, 3, 14));
        commandeService.create(c1);

        Commande c2 = new Commande();
        c2.setDate(LocalDate.of(2013, 5, 20));
        commandeService.create(c2);


        LigneCommandeProduit l1 = new LigneCommandeProduit();
        l1.setProduit(p1);
        l1.setCommande(c1);
        l1.setQuantite(7);

        LigneCommandeProduit l2 = new LigneCommandeProduit();
        l2.setProduit(p2);
        l2.setCommande(c1);
        l2.setQuantite(14);

        LigneCommandeProduit l3 = new LigneCommandeProduit();
        l3.setProduit(p3);
        l3.setCommande(c2);
        l3.setQuantite(5);

        ligneService.create(l1);
        ligneService.create(l2);
        ligneService.create(l3);



        System.out.println("\n--- Produits par catégorie C1 ---");
        List<Produit> produitsCat1 = produitService.findByCategorie(cat1);
        for (Produit p : produitsCat1) {
            System.out.printf("Réf : %-5s Prix : %-6.2f Catégorie : %s%n",
                    p.getReference(), p.getPrix(), p.getCategorie().getLibelle());
        }

        System.out.println("\n--- Produits par commande c1 ---");
        produitService.findByCommande(c1.getId());

        System.out.println("\n--- Produits commandés entre 2013-01-01 et 2013-12-31 ---");
        LocalDate start = LocalDate.of(2013, 1, 1);
        LocalDate end = LocalDate.of(2013, 12, 31);
        produitService.findByDateCommande(start, end);

        System.out.println("\n--- Produits prix > 100 DH ---");
        produitService.findProduitsPrixSuperieur(100f);

        context.close();
    }
}

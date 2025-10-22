package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

public class TestApp {
    public static void main(String[] args) throws Exception {

        // ==== Initialisation des services ====
        CategorieService catService = new CategorieService();
        ProduitService prodService = new ProduitService();
        CommandeService cmdService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        // ==== Création de catégories ====
        Categorie c1 = new Categorie();
        c1.setCode("CAT01");
        c1.setLibelle("Ordinateurs");
        catService.create(c1);

        Categorie c2 = new Categorie();
        c2.setCode("CAT02");
        c2.setLibelle("Accessoires");
        catService.create(c2);

        // ==== Création de produits ====
        Produit p1 = new Produit();
        p1.setReference("ES12");
        p1.setPrix(120);
        p1.setCategorie(c1);
        prodService.create(p1);

        Produit p2 = new Produit();
        p2.setReference("ZR85");
        p2.setPrix(100);
        p2.setCategorie(c1);
        prodService.create(p2);

        Produit p3 = new Produit();
        p3.setReference("EE85");
        p3.setPrix(200);
        p3.setCategorie(c2);
        prodService.create(p3);

        // ==== Création d'une commande ====
        Commande cmd1 = new Commande();
        cmd1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-14"));
        cmdService.create(cmd1);

        // ==== Ajout de lignes de commande ====
        LigneCommande l1 = new LigneCommande();
        l1.setProduit(p1);
        l1.setCommande(cmd1);
        l1.setQuantite(7);
        ligneService.create(l1);

        LigneCommande l2 = new LigneCommande();
        l2.setProduit(p2);
        l2.setCommande(cmd1);
        l2.setQuantite(14);
        ligneService.create(l2);

        LigneCommande l3 = new LigneCommande();
        l3.setProduit(p3);
        l3.setCommande(cmd1);
        l3.setQuantite(5);
        ligneService.create(l3);

        // ==== Affichage formaté de la commande ====
        SimpleDateFormat outputFormat = new SimpleDateFormat(
                "d MMMM yyyy", new DateFormatSymbols(Locale.FRENCH));
        String dateFormatee = outputFormat.format(cmd1.getDate());

        System.out.println("========================================");
        System.out.println("      Application de gestion de stock");
        System.out.println("========================================\n");

        System.out.println("Commande : " + cmd1.getId() + "     Date : " + dateFormatee);
        System.out.println("Liste des produits :");
        System.out.println(String.format("%-10s %-10s %-10s", "Référence", "Prix", "Quantité"));
        System.out.println("----------------------------------------");

        List<Object[]> produitsCmd = prodService.getProduitsParCommande(cmd1.getId());
        for (Object[] p : produitsCmd) {
            String ref = (String) p[0];
            String prixStr = String.format("%.0f DH", (Float) p[1]);
            int quantite = (Integer) p[2];
            System.out.println(String.format("%-10s %-10s %-10d", ref, prixStr, quantite));
        }

        System.out.println("----------------------------------------\n");
        System.out.println("Fin du rapport de commande.\n");
    }
}

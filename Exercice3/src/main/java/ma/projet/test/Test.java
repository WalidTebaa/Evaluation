package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        FemmeService femmeService = context.getBean(FemmeService.class);
        HommeService hommeService = context.getBean(HommeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        // Vérifier si les données existent déjà
        List<Femme> femmesExistantes = femmeService.findAll();

        if (femmesExistantes.isEmpty()) {
            System.out.println("Insertion des données de test...");

            long now = System.currentTimeMillis();

            // --- test data: femmes (CIN uniques par exécution) ---
            Femme f1 = new Femme(); f1.setCin("C-" + now + "-1"); f1.setNom("Naima"); f1.setPrenom("El Idrissi"); f1.setDateNaissance(LocalDate.of(1960, 2, 18));
            Femme f2 = new Femme(); f2.setCin("C-" + now + "-2"); f2.setNom("Sara"); f2.setPrenom("Zerouali"); f2.setDateNaissance(LocalDate.of(1978, 11, 2));
            Femme f3 = new Femme(); f3.setCin("C-" + now + "-3"); f3.setNom("Mouna"); f3.setPrenom("Rashid"); f3.setDateNaissance(LocalDate.of(1983, 7, 14));
            Femme f4 = new Femme(); f4.setCin("C-" + now + "-4"); f4.setNom("Fatima"); f4.setPrenom("El Amrani"); f4.setDateNaissance(LocalDate.of(1972, 9, 9));
            Femme f5 = new Femme(); f5.setCin("C-" + now + "-5"); f5.setNom("Hind"); f5.setPrenom("Benjelloun"); f5.setDateNaissance(LocalDate.of(1991, 4, 25));

            Arrays.asList(f1, f2, f3, f4, f5).forEach(femmeService::create);

            // --- test data: hommes ---
            Homme h1 = new Homme(); h1.setNom("Tahar"); h1.setPrenom("El Faroq");
            Homme h2 = new Homme(); h2.setNom("Rayan"); h2.setPrenom("Kabbaj");
            Homme h3 = new Homme(); h3.setNom("Adil"); h3.setPrenom("Souiri");

            Arrays.asList(h1, h2, h3).forEach(hommeService::create);

            // --- test data: mariages ---
            Mariage m1 = new Mariage(); m1.setHomme(h1); m1.setFemme(f1); m1.setDateDebut(LocalDate.of(1985, 5, 20)); m1.setNbrEnfant(2);
            Mariage m2 = new Mariage(); m2.setHomme(h1); m2.setFemme(f2); m2.setDateDebut(LocalDate.of(1990, 3, 11)); m2.setNbrEnfant(1);
            Mariage m3 = new Mariage(); m3.setHomme(h1); m3.setFemme(f3); m3.setDateDebut(LocalDate.of(1998, 12, 7)); m3.setNbrEnfant(3);
            Mariage m4 = new Mariage(); m4.setHomme(h1); m4.setFemme(f4); m4.setDateDebut(LocalDate.of(2002, 6, 30)); m4.setDateFin(LocalDate.of(2006, 6, 30)); m4.setNbrEnfant(0);

            Mariage m5 = new Mariage(); m5.setHomme(h2); m5.setFemme(f2); m5.setDateDebut(LocalDate.of(1994, 1, 5)); m5.setNbrEnfant(1);
            Mariage m6 = new Mariage(); m6.setHomme(h2); m6.setFemme(f5); m6.setDateDebut(LocalDate.of(2001, 8, 18)); m6.setNbrEnfant(2);
            Mariage m7 = new Mariage(); m7.setHomme(h2); m7.setFemme(f3); m7.setDateDebut(LocalDate.of(2007, 4, 2)); m7.setNbrEnfant(1);
            Mariage m8 = new Mariage(); m8.setHomme(h2); m8.setFemme(f4); m8.setDateDebut(LocalDate.of(2012, 10, 10)); m8.setNbrEnfant(0);

            Mariage m9 = new Mariage(); m9.setHomme(h3); m9.setFemme(f1); m9.setDateDebut(LocalDate.of(2015, 9, 9)); m9.setNbrEnfant(1);
            Mariage m10 = new Mariage(); m10.setHomme(h3); m10.setFemme(f5); m10.setDateDebut(LocalDate.of(2019, 2, 14)); m10.setNbrEnfant(0);

            Arrays.asList(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10).forEach(mariageService::create);

            System.out.println("Données insérées avec succès!\n");
        } else {
            System.out.println("Les données existent déjà dans la base de données.\n");
        }

        // Récupérer les données pour les tests
        List<Femme> femmes = femmeService.findAll();
        List<Homme> hommes = hommeService.findAll();

        Femme f1 = femmes.isEmpty() ? null : femmes.get(0);
        Homme h1 = hommes.isEmpty() ? null : hommes.get(0);

        System.out.println("\n--- Liste des Femmes ---");
        femmeService.findAll().forEach(f -> System.out.printf("%-5s %-25s %-25s %-12s%n",
                f.getCin(), f.getNom(), f.getPrenom(), f.getDateNaissance()));

        Femme plusAgee = femmeService.findAll().stream()
                .min(Comparator.comparing(Femme::getDateNaissance))
                .orElse(null);
        System.out.println("\nFemme la plus âgée : " + (plusAgee != null ? plusAgee.getCin() + " " + plusAgee.getNom() + " " + plusAgee.getPrenom() : "N/A"));

        if (h1 != null) {
            System.out.println("\n--- Épouses de l'homme " + h1.getNom() + " ---");
            hommeService.afficherEpousesEntreDates(h1.getId(), LocalDate.of(1980, 1, 1), LocalDate.of(2025, 12, 31));
        }

        if (f1 != null) {
            System.out.println("\n--- Nombre d'enfants de " + f1.getNom() + " entre 1980 et 2020 ---");
            int nbEnfants = femmeService.nombreEnfantsEntreDates(f1.getId(), LocalDate.of(1980, 1, 1), LocalDate.of(2020, 12, 31));
            System.out.println("Nombre d'enfants : " + nbEnfants);
        }

        System.out.println("\n--- Femmes mariées deux fois ou plus ---");
        femmeService.femmesMarieesAuMoinsDeuxFois().forEach(f -> System.out.printf("%-5s %-25s %-25s%n", f.getCin(), f.getNom(), f.getPrenom()));

        System.out.println("\n--- Hommes mariés à 4 femmes entre 1980 et 2025 ---");
        hommeService.afficherNombreHommesAvecQuatreFemmes(LocalDate.of(1980, 1, 1), LocalDate.of(2025, 12, 31));

        if (h1 != null) {
            System.out.println("\n--- Mariages de l'homme " + h1.getNom() + " avec détails ---");
            mariageService.afficherMariagesAvecDetails(h1.getId());
        }

        context.close();
    }
}
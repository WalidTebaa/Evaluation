package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TestProgram implements CommandLineRunner {

    @Autowired
    private HommeService hommeService;

    @Autowired
    private FemmeService femmeService;

    @Autowired
    private MariageService mariageService;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== DÉBUT DU PROGRAMME DE TEST ===\n");

        // 1. Créer 10 femmes et 5 hommes
        creerPersonnes();

        // 2. Afficher la liste des femmes
        //afficherListeFemmes();

        // 3. Afficher la femme la plus âgée
        //afficherFemmeLaPlusAgee();

        // 4. Afficher les épouses d'un homme donné
        //afficherEpousesHomme();

        // 5. Afficher le nombre d'enfants d'une femme entre deux dates
        //afficherNombreEnfantsFemme();

        // 6. Afficher les femmes mariées deux fois ou plus
        //afficherFemmesMarieesDeuxFois();

        // 7. Afficher les hommes mariés à quatre femmes entre deux dates
        //afficherHommesQuatreFemmes();

        // 8. Afficher les mariages d'un homme avec tous les détails
        afficherDetailsMariagesHomme();

        System.out.println("\n=== FIN DU PROGRAMME DE TEST ===");
    }

    private void creerPersonnes() throws ParseException {
        System.out.println("1. Création de 10 femmes et 5 hommes...\n");


        List<Femme> femmesExistantes = femmeService.findAll();
        if (!femmesExistantes.isEmpty()) {
            System.out.println("✓ Données déjà présentes dans la base");
            System.out.println("✓ " + femmesExistantes.size() + " femmes trouvées");

            List<Homme> hommesExistants = hommeService.findAll();
            System.out.println("✓ " + hommesExistants.size() + " hommes trouvés\n");
            return;
        }


        Femme f1 = new Femme("RAMI", "SALIMA", "0611111111", "Casablanca", sdf.parse("15/03/1970"));
        Femme f2 = new Femme("ALI", "AMAL", "0622222222", "Rabat", sdf.parse("20/05/1972"));
        Femme f3 = new Femme("ALAOUI", "WAFA", "0633333333", "Fès", sdf.parse("10/08/1975"));
        Femme f4 = new Femme("ALAMI", "KARIMA", "0644444444", "Marrakech", sdf.parse("25/12/1968"));
        Femme f5 = new Femme("BENNANI", "FATIMA", "0655555555", "Tanger", sdf.parse("05/01/1980"));
        Femme f6 = new Femme("IDRISSI", "AMINA", "0666666666", "Agadir", sdf.parse("18/07/1965"));
        Femme f7 = new Femme("CHAKIR", "LAILA", "0677777777", "Meknès", sdf.parse("12/11/1978"));
        Femme f8 = new Femme("TAZI", "KHADIJA", "0688888888", "Oujda", sdf.parse("30/04/1973"));
        Femme f9 = new Femme("FASSI", "NADIA", "0699999999", "Tétouan", sdf.parse("22/09/1976"));
        Femme f10 = new Femme("SQALLI", "SAMIRA", "0610101010", "Kenitra", sdf.parse("08/02/1982"));

        femmeService.create(f1);
        femmeService.create(f2);
        femmeService.create(f3);
        femmeService.create(f4);
        femmeService.create(f5);
        femmeService.create(f6);
        femmeService.create(f7);
        femmeService.create(f8);
        femmeService.create(f9);
        femmeService.create(f10);

        // Création de 5 hommes
        Homme h1 = new Homme("SAFI", "SAID", "0711111111", "Casablanca", sdf.parse("10/01/1965"));
        Homme h2 = new Homme("AMRANI", "MOHAMMED", "0722222222", "Rabat", sdf.parse("15/06/1970"));
        Homme h3 = new Homme("BERRADA", "YOUSSEF", "0733333333", "Fès", sdf.parse("20/03/1968"));
        Homme h4 = new Homme("KADIRI", "AHMED", "0744444444", "Marrakech", sdf.parse("25/11/1972"));
        Homme h5 = new Homme("FILALI", "HASSAN", "0755555555", "Tanger", sdf.parse("05/08/1975"));

        hommeService.create(h1);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);

        // Création de mariages pour SAFI SAID (h1) - Exemple de l'énoncé
        Mariage m1 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4); // KARIMA - échoué
        Mariage m2 = new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1); // SALIMA - en cours
        Mariage m3 = new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2); // AMAL - en cours
        Mariage m4 = new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3); // WAFA - en cours

        mariageService.create(m1);
        mariageService.create(m2);
        mariageService.create(m3);
        mariageService.create(m4);

        // Mariages pour h2
        Mariage m5 = new Mariage(sdf.parse("10/05/1992"), null, 2, h2, f5);
        Mariage m6 = new Mariage(sdf.parse("15/08/1998"), sdf.parse("20/06/2005"), 1, h2, f6);
        mariageService.create(m5);
        mariageService.create(m6);

        // Mariages pour h3
        Mariage m7 = new Mariage(sdf.parse("20/03/1990"), null, 5, h3, f7);
        mariageService.create(m7);

        // Mariages pour h4 - Femme mariée deux fois
        Mariage m8 = new Mariage(sdf.parse("01/01/1995"), sdf.parse("01/01/2000"), 2, h4, f8);
        Mariage m9 = new Mariage(sdf.parse("01/06/2001"), null, 3, h5, f8); // f8 mariée 2 fois
        mariageService.create(m8);
        mariageService.create(m9);

        // Mariages pour h5 avec 4 femmes entre 2000 et 2010
        Mariage m10 = new Mariage(sdf.parse("15/01/2002"), null, 1, h5, f9);
        Mariage m11 = new Mariage(sdf.parse("20/03/2004"), null, 1, h5, f10);
        Mariage m12 = new Mariage(sdf.parse("10/07/2006"), null, 2, h4, f9);
        Mariage m13 = new Mariage(sdf.parse("05/11/2008"), null, 1, h4, f10);
        mariageService.create(m10);
        mariageService.create(m11);
        mariageService.create(m12);
        mariageService.create(m13);

        System.out.println("✓ 10 femmes créées");
        System.out.println("✓ 5 hommes créés");
        System.out.println("✓ Mariages créés\n");
    }

    private void afficherListeFemmes() {
        System.out.println("2. Liste de toutes les femmes :");
        System.out.println("================================");
        List<Femme> femmes = femmeService.findAll();
        for (Femme f : femmes) {
            System.out.println("- " + f.getPrenom() + " " + f.getNom() +
                    " (Née le : " + sdf.format(f.getDateNaissance()) + ")");
        }
        System.out.println();
    }

    private void afficherFemmeLaPlusAgee() {
        System.out.println("3. Femme la plus âgée :");
        System.out.println("========================");
        Femme femmeLaPlusAgee = femmeService.getFemmeLaPlusAgee();
        if (femmeLaPlusAgee != null) {
            System.out.println("Nom : " + femmeLaPlusAgee.getPrenom() + " " + femmeLaPlusAgee.getNom());
            System.out.println("Date de naissance : " + sdf.format(femmeLaPlusAgee.getDateNaissance()));
            System.out.println("Âge : " + calculerAge(femmeLaPlusAgee.getDateNaissance()) + " ans");
        }
        System.out.println();
    }

    private void afficherEpousesHomme() throws ParseException {
        System.out.println("4. Épouses de SAFI SAID entre 01/01/1990 et 31/12/2000 :");
        System.out.println("==========================================================");
        List<Homme> hommes = hommeService.findAll();
        Homme homme = hommes.stream()
                .filter(h -> h.getNom().equals("SAFI") && h.getPrenom().equals("SAID"))
                .findFirst()
                .orElse(null);

        if (homme != null) {
            Date dateDebut = sdf.parse("01/01/1990");
            Date dateFin = sdf.parse("31/12/2000");
            List<Femme> epouses = hommeService.getEpousesBetweenDates(homme, dateDebut, dateFin);

            for (Femme f : epouses) {
                System.out.println("- " + f.getPrenom() + " " + f.getNom());
            }
        }
        System.out.println();
    }

    private void afficherNombreEnfantsFemme() throws ParseException {
        System.out.println("5. Nombre d'enfants de SALIMA RAMI entre 01/01/1990 et 31/12/2023 :");
        System.out.println("=====================================================================");
        List<Femme> femmes = femmeService.findAll();
        Femme femme = femmes.stream()
                .filter(f -> f.getNom().equals("RAMI") && f.getPrenom().equals("SALIMA"))
                .findFirst()
                .orElse(null);

        if (femme != null) {
            Date dateDebut = sdf.parse("01/01/1990");
            Date dateFin = sdf.parse("31/12/2023");
            long nombreEnfants = femmeService.countEnfantsBetweenDates(femme.getId(), dateDebut, dateFin);
            System.out.println("Nombre d'enfants : " + nombreEnfants);
        }
        System.out.println();
    }

    private void afficherFemmesMarieesDeuxFois() {
        System.out.println("6. Femmes mariées deux fois ou plus :");
        System.out.println("======================================");
        List<Femme> femmesMarieesDeuxFois = femmeService.getFemmesMarieesDeuxFois();

        if (femmesMarieesDeuxFois.isEmpty()) {
            System.out.println("Aucune femme mariée deux fois ou plus.");
        } else {
            for (Femme f : femmesMarieesDeuxFois) {
                System.out.println("- " + f.getPrenom() + " " + f.getNom());
            }
        }
        System.out.println();
    }

    private void afficherHommesQuatreFemmes() throws ParseException {
        System.out.println("7. Nombre d'hommes mariés à quatre femmes entre 2000 et 2010 :");
        System.out.println("===============================================================");
        Date dateDebut = sdf.parse("01/01/2000");
        Date dateFin = sdf.parse("31/12/2010");
        long nombre = hommeService.countHommesMariesQuatreFemmes(dateDebut, dateFin);
        System.out.println("Nombre d'hommes : " + nombre);
        System.out.println();
    }

    private void afficherDetailsMariagesHomme() {
        System.out.println("8. Détails des mariages de SAFI SAID :");
        System.out.println("=======================================");
        List<Homme> hommes = hommeService.findAll();
        Homme homme = hommes.stream()
                .filter(h -> h.getNom().equals("SAFI") && h.getPrenom().equals("SAID"))
                .findFirst()
                .orElse(null);

        if (homme != null) {
            hommeService.afficherMariagesHomme(homme.getId());
        }
        System.out.println();
    }

    private int calculerAge(Date dateNaissance) {
        Date aujourdhui = new Date();
        long diff = aujourdhui.getTime() - dateNaissance.getTime();
        return (int) (diff / (1000L * 60 * 60 * 24 * 365));
    }
}
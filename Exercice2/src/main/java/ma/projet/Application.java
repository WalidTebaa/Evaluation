package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(EmployeService employeService,
                             ProjetService projetService,
                             TacheService tacheService,
                             EmployeTacheService employeTacheService) {
        return args -> {

            // Format date : dd/MM/yyyy et format français
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter moisLettre = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);

            // === Données de test ===
            Employe chef = employeService.save(new Employe("Tebaa", "Walid", "0600000001"));
            Employe dev = employeService.save(new Employe("Baba", "Saïd", "0600000002"));

            Projet p = new Projet("Gestion de stock",
                    LocalDate.of(2013, 1, 14),
                    LocalDate.of(2013, 6, 30));
            p.setChefDeProjet(chef);
            p = projetService.save(p);

            Tache t1 = new Tache("Analyse", LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20), 900);
            Tache t2 = new Tache("Conception", LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15), 1200);
            Tache t3 = new Tache("Développement", LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25), 2000);

            t1.setProjet(p);
            t2.setProjet(p);
            t3.setProjet(p);

            tacheService.save(t1);
            tacheService.save(t2);
            tacheService.save(t3);

            employeTacheService.save(new EmployeTache(dev, t1, LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20)));
            employeTacheService.save(new EmployeTache(dev, t2, LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15)));
            employeTacheService.save(new EmployeTache(dev, t3, LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25)));

            // === Affichage formaté ===
            System.out.println();
            System.out.println("==================== RAPPORT PROJET ====================");
            System.out.printf("Projet : %-5d Nom : %-20s Date début : %s%n",
                    p.getId(),
                    p.getNom(),
                    p.getDateDebut().format(moisLettre)
            );
            System.out.println("Chef de projet : " + chef.getPrenom() + " " + chef.getNom());
            System.out.println("Liste des tâches réalisées par : " + dev.getPrenom() + " " + dev.getNom());
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-4s %-15s %-18s %-18s%n",
                    "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");

            List<EmployeTache> ets = employeTacheService.findByEmployeId(dev.getId());
            for (EmployeTache et : ets) {
                System.out.printf("%-4d %-15s %-18s %-18s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        et.getDateDebutReelle().format(dateFormatter),
                        et.getDateFinReelle().format(dateFormatter)
                );
            }

            System.out.println("------------------------------------------------------------");
            System.out.println("=== Fin du rapport ===");
            System.out.println("============================================================");
        };
    }
}

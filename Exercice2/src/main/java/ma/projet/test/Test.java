package ma.projet.test;

import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        EmployeService employeService = context.getBean(EmployeService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);
        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);

        // Nouveaux employés
        Employe emp1 = new Employe();
        emp1.setNom("Samir");
        emp1.setPrenom("Kouachi");
        employeService.create(emp1);

        Employe emp2 = new Employe();
        emp2.setNom("Leila");
        emp2.setPrenom("Mansouri");
        employeService.create(emp2);

        Employe emp3 = new Employe();
        emp3.setNom("Omar");
        emp3.setPrenom("Rassoul");
        employeService.create(emp3);

        Employe emp4 = new Employe();
        emp4.setNom("Fatima");
        emp4.setPrenom("Zahidi");
        employeService.create(emp4);

        // Nouveaux projets
        Projet proj1 = new Projet();
        proj1.setNom("Nebula");
        proj1.setChef(emp4);
        proj1.setDateDebut(LocalDate.of(2024, 11, 1));
        proj1.setDateFin(LocalDate.of(2025, 2, 28));
        projetService.create(proj1);

        Projet proj2 = new Projet();
        proj2.setNom("Aurora");
        proj2.setChef(emp2);
        proj2.setDateDebut(LocalDate.of(2025, 1, 5));
        proj2.setDateFin(LocalDate.of(2025, 6, 30));
        projetService.create(proj2);

        // Tâches différentes
        Tache t1 = new Tache();
        t1.setNom("Spec-Alpha");
        t1.setProjet(proj1);
        t1.setPrix(420f);
        t1.setDateDebut(LocalDate.of(2024, 11, 5));
        t1.setDateFin(LocalDate.of(2024, 12, 15));
        tacheService.create(t1);

        Tache t2 = new Tache();
        t2.setNom("Integration-Beta");
        t2.setProjet(proj1);
        t2.setPrix(980f);
        t2.setDateDebut(LocalDate.of(2024, 12, 20));
        t2.setDateFin(null);
        tacheService.create(t2);

        Tache t3 = new Tache();
        t3.setNom("Refactor-X");
        t3.setProjet(proj2);
        t3.setPrix(1500f);
        t3.setDateDebut(LocalDate.of(2025, 1, 10));
        t3.setDateFin(LocalDate.of(2025, 3, 1));
        tacheService.create(t3);

        Tache t4 = new Tache();
        t4.setNom("Monitoring-Z");
        t4.setProjet(proj2);
        t4.setPrix(650f);
        t4.setDateDebut(LocalDate.of(2025, 4, 1));
        t4.setDateFin(null);
        tacheService.create(t4);

        // Assignations
        EmployeTache et1 = new EmployeTache();
        et1.setEmploye(emp4);
        et1.setTache(t1);
        employeTacheService.create(et1);

        EmployeTache et2 = new EmployeTache();
        et2.setEmploye(emp1);
        et2.setTache(t2);
        employeTacheService.create(et2);

        EmployeTache et3 = new EmployeTache();
        et3.setEmploye(emp2);
        et3.setTache(t3);
        employeTacheService.create(et3);

        EmployeTache et4 = new EmployeTache();
        et4.setEmploye(emp3);
        et4.setTache(t4);
        employeTacheService.create(et4);

        // Vérifications
        System.out.println("--- Tâches de Samir ---");
        employeService.afficherTachesParEmploye(emp1.getId());

        System.out.println("--- Projets gérés par Fatima ---");
        employeService.afficherProjetsGeresParEmploye(emp4.getId());

        System.out.println("--- Tâches planifiées pour Nebula ---");
        projetService.afficherTachesPlanifieesParProjet(proj1.getId());

        System.out.println("--- Tâches réalisées pour Aurora ---");
        projetService.afficherTachesRealiseesParProjet(proj2.getId());

        System.out.println("--- Tâches coûtant plus de 1000 ---");
        tacheService.afficherTachesPrixSuperieurA1000();

        System.out.println("--- Tâches entre 2024-11-01 et 2025-06-30 ---");
        LocalDate debut = LocalDate.of(2024, 11, 1);
        LocalDate fin = LocalDate.of(2025, 6, 30);
        tacheService.afficherTachesEntreDates(debut, fin);

        context.close();
    }
}

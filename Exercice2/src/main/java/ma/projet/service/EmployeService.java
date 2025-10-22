package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;

import java.util.List;

public interface EmployeService {
    Employe save(Employe e);
    List<Tache> listeTachesParEmploye(Long employeId);
    List<Projet> listeProjetsGeresParEmploye(Long employeId);
}

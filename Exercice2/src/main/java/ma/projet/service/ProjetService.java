package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;

import java.util.List;

public interface ProjetService {
    Projet save(Projet p);
    List<Tache> listeTachesPlanifiees(Long projetId);
    List<Tache> listeTachesRealiseesAvecDates(Long projetId);
    List<Projet> findAll();
}

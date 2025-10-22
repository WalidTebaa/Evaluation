package ma.projet.service;

import ma.projet.classes.Tache;

import java.time.LocalDate;
import java.util.List;

public interface TacheService {
    Tache save(Tache t);
    List<Tache> findAll();
    List<Tache> trouverTachesPrixSuperieur(double montant);
    List<Tache> trouverTachesRealiseesEntre(LocalDate d1, LocalDate d2);
}

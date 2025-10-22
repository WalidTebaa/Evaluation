package ma.projet.service.impl;

import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.dao.ProjetRepository;
import ma.projet.dao.TacheRepository;
import ma.projet.service.ProjetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepo;
    private final TacheRepository tacheRepo;

    public ProjetServiceImpl(ProjetRepository projetRepo, TacheRepository tacheRepo) {
        this.projetRepo = projetRepo;
        this.tacheRepo = tacheRepo;
    }

    @Override
    public Projet save(Projet p) { return projetRepo.save(p); }

    @Override
    public List<Tache> listeTachesPlanifiees(Long projetId) {
        // tâches liées au projet (prévisionnelles = dateDebut/dateFin définies dans tache)
        return tacheRepo.findByProjetId(projetId);
    }

    @Override
    public List<Tache> listeTachesRealiseesAvecDates(Long projetId) {
        // les dates réelles sont stockées dans EmployeTache, mais ici on retourne
        // les taches du projet (on récupèrera les EmployeTache via EmployeTacheService si besoin)
        return tacheRepo.findByProjetId(projetId);
    }

    @Override
    public List<Projet> findAll() { return projetRepo.findAll(); }
}

package ma.projet.service.impl;

import ma.projet.classes.Tache;
import ma.projet.dao.TacheRepository;
import ma.projet.service.TacheService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TacheServiceImpl implements TacheService {

    private final TacheRepository tacheRepo;

    public TacheServiceImpl(TacheRepository tacheRepo) {
        this.tacheRepo = tacheRepo;
    }

    @Override
    public Tache save(Tache t) { return tacheRepo.save(t); }

    @Override
    public List<Tache> findAll() { return tacheRepo.findAll(); }

    @Override
    public List<Tache> trouverTachesPrixSuperieur(double montant) {
        return tacheRepo.findByPrixGreaterThan(montant);
    }

    @Override
    public List<Tache> trouverTachesRealiseesEntre(LocalDate d1, LocalDate d2) {
        return tacheRepo.findTachesRealiseesBetween(d1, d2);
    }
}

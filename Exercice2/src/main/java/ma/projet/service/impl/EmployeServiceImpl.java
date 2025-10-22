package ma.projet.service.impl;

import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.EmployeRepository;
import ma.projet.dao.EmployeTacheRepository;
import ma.projet.service.EmployeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepo;
    private final EmployeTacheRepository employeTacheRepo;

    public EmployeServiceImpl(EmployeRepository employeRepo, EmployeTacheRepository employeTacheRepo) {
        this.employeRepo = employeRepo;
        this.employeTacheRepo = employeTacheRepo;
    }

    @Override
    public Employe save(Employe e) { return employeRepo.save(e); }

    @Override
    public List<Tache> listeTachesParEmploye(Long employeId) {
        List<EmployeTache> ets = employeTacheRepo.findByEmployeId(employeId);
        return ets.stream().map(EmployeTache::getTache).collect(Collectors.toList());
    }

    @Override
    public List<Projet> listeProjetsGeresParEmploye(Long employeId) {
        return employeRepo.findById(employeId)
                .map(Employe::getProjetsGeres)
                .map(set -> set.stream().collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
    }
}

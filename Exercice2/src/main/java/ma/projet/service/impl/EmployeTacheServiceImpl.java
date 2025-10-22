package ma.projet.service.impl;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.EmployeTacheRepository;
import ma.projet.service.EmployeTacheService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeTacheServiceImpl implements EmployeTacheService {

    private final EmployeTacheRepository repo;

    public EmployeTacheServiceImpl(EmployeTacheRepository repo) { this.repo = repo; }

    @Override
    public EmployeTache save(EmployeTache et) { return repo.save(et); }

    @Override
    public List<EmployeTache> findByEmployeId(Long id) { return repo.findByEmployeId(id); }

    @Override
    public List<EmployeTache> trouverEntreDates(LocalDate d1, LocalDate d2) {
        return repo.findBetweenDates(d1, d2);
    }
}

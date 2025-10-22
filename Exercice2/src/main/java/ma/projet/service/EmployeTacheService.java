package ma.projet.service;

import ma.projet.classes.EmployeTache;

import java.time.LocalDate;
import java.util.List;

public interface EmployeTacheService {
    EmployeTache save(EmployeTache et);
    List<EmployeTache> findByEmployeId(Long id);
    List<EmployeTache> trouverEntreDates(LocalDate d1, LocalDate d2);
}

package ma.projet.dao;

import ma.projet.classes.EmployeTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface EmployeTacheRepository extends JpaRepository<EmployeTache, Long> {
    @Query("SELECT et FROM EmployeTache et WHERE et.employe.id = :employeId")
    List<EmployeTache> findByEmployeId(Long employeId);

    @Query("SELECT et FROM EmployeTache et WHERE et.tache.projet.id = :projetId")
    List<EmployeTache> findByProjetId(Long projetId);

    @Query("SELECT et FROM EmployeTache et WHERE et.dateDebutReelle >= :d1 AND et.dateFinReelle <= :d2")
    List<EmployeTache> findBetweenDates(LocalDate d1, LocalDate d2);
}

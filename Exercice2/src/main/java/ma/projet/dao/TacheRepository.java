package ma.projet.dao;

import ma.projet.classes.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    // Utilise la NamedQuery définie dans l'entité
    @Query(name = "Tache.findByPrixGreaterThan")
    List<Tache> findByPrixGreaterThan(@Param("prix") double prix);

    @Query("SELECT t FROM Tache t WHERE t.dateFin BETWEEN :d1 AND :d2")
    List<Tache> findTachesRealiseesBetween(@Param("d1") LocalDate d1, @Param("d2") LocalDate d2);

    @Query("SELECT t FROM Tache t WHERE t.projet.id = :projetId")
    List<Tache> findByProjetId(@Param("projetId") Long projetId);
}

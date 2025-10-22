package ma.projet.service;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class HommeService implements IDao<Homme> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean create(Homme o) {
        try {
            em.persist(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Homme o) {
        try {
            em.remove(em.contains(o) ? o : em.merge(o));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Homme o) {
        try {
            em.merge(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Homme findById(int id) {
        return em.find(Homme.class, id);
    }

    @Override
    public List<Homme> findAll() {
        TypedQuery<Homme> query = em.createQuery("SELECT h FROM Homme h", Homme.class);
        return query.getResultList();
    }

    /**
     * Affiche les épouses d'un homme entre deux dates
     */
    public List<Femme> getEpousesBetweenDates(Homme homme, Date dateDebut, Date dateFin) {
        TypedQuery<Femme> query = em.createQuery(
                "SELECT DISTINCT m.femme FROM Mariage m WHERE m.homme = :homme " +
                        "AND m.dateDebut BETWEEN :dateDebut AND :dateFin",
                Femme.class
        );
        query.setParameter("homme", homme);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.getResultList();
    }

    /**
     * Affiche le nombre d'hommes mariés à quatre femmes entre deux dates (API Criteria)
     */
    public long countHommesMariesQuatreFemmes(Date dateDebut, Date dateFin) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Homme> homme = cq.from(Homme.class);
        Join<Homme, Mariage> mariage = homme.join("mariages");

        cq.select(cb.count(homme));
        cq.where(
                cb.between(mariage.get("dateDebut"), dateDebut, dateFin)
        );
        cq.groupBy(homme.get("id"));
        cq.having(cb.equal(cb.count(mariage), 4));

        try {
            List<Long> results = em.createQuery(cq).getResultList();
            return results.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Affiche les mariages d'un homme avec tous les détails
     */
    public void afficherMariagesHomme(int hommeId) {
        Homme homme = findById(hommeId);
        if (homme == null) {
            System.out.println("Homme non trouvé");
            return;
        }

        // Charger les mariages avec les femmes
        TypedQuery<Mariage> query = em.createQuery(
                "SELECT m FROM Mariage m JOIN FETCH m.femme WHERE m.homme.id = :hommeId ORDER BY m.dateDebut",
                Mariage.class
        );
        query.setParameter("hommeId", hommeId);
        List<Mariage> mariages = query.getResultList();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());

        // Mariages en cours
        List<Mariage> mariagesEnCours = mariages.stream()
                .filter(m -> m.getDateFin() == null)
                .collect(Collectors.toList());

        if (!mariagesEnCours.isEmpty()) {
            System.out.println("Mariages En Cours :");
            int count = 1;
            for (Mariage m : mariagesEnCours) {
                System.out.printf("%d. Femme : %-20s Date Début : %-12s Nbr Enfants : %d%n",
                        count,
                        m.getFemme().getPrenom() + " " + m.getFemme().getNom(),
                        sdf.format(m.getDateDebut()),
                        m.getNbrEnfant()
                );
                count++;
            }
        }

        // Mariages échoués
        List<Mariage> mariagesEchoues = mariages.stream()
                .filter(m -> m.getDateFin() != null)
                .collect(Collectors.toList());

        if (!mariagesEchoues.isEmpty()) {
            System.out.println("\nMariages échoués :");
            int count = 1;
            for (Mariage m : mariagesEchoues) {
                System.out.printf("%d. Femme : %-20s Date Début : %-12s Date Fin : %-12s Nbr Enfants : %d%n",
                        count,
                        m.getFemme().getPrenom() + " " + m.getFemme().getNom(),
                        sdf.format(m.getDateDebut()),
                        sdf.format(m.getDateFin()),
                        m.getNbrEnfant()
                );
                count++;
            }
        }
    }
}
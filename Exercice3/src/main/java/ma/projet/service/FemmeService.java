package ma.projet.service;

import jakarta.persistence.*;
import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FemmeService implements IDao<Femme> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean create(Femme o) {
        try {
            em.persist(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Femme o) {
        try {
            em.remove(em.contains(o) ? o : em.merge(o));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Femme o) {
        try {
            em.merge(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Femme findById(int id) {
        return em.find(Femme.class, id);
    }

    @Override
    public List<Femme> findAll() {
        TypedQuery<Femme> query = em.createQuery("SELECT f FROM Femme f", Femme.class);
        return query.getResultList();
    }

    /**
     * Requête native nommée : Retourne le nombre d'enfants d'une femme entre deux dates
     */
    public long countEnfantsBetweenDates(int femmeId, Date dateDebut, Date dateFin) {
        try {
            Query query = em.createNamedQuery("Femme.countEnfantsBetweenDates");
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            Object result = query.getSingleResult();
            return result != null ? ((Number) result).longValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Requête nommée : Retourne les femmes mariées au moins deux fois
     */
    public List<Femme> getFemmesMarieesDeuxFois() {
        TypedQuery<Femme> query = em.createNamedQuery("Personne.findFemmesMarieesDeuxFois", Femme.class);
        return query.getResultList();
    }

    /**
     * Trouve la femme la plus âgée
     */
    public Femme getFemmeLaPlusAgee() {
        TypedQuery<Femme> query = em.createQuery(
                "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC",
                Femme.class
        );
        query.setMaxResults(1);
        List<Femme> femmes = query.getResultList();
        return femmes.isEmpty() ? null : femmes.get(0);
    }
}
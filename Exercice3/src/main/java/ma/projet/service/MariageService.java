package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MariageService implements IDao<Mariage> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean create(Mariage o) {
        try {
            em.persist(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Mariage o) {
        try {
            em.remove(em.contains(o) ? o : em.merge(o));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Mariage o) {
        try {
            em.merge(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Mariage findById(int id) {
        return em.find(Mariage.class, id);
    }

    @Override
    public List<Mariage> findAll() {
        TypedQuery<Mariage> query = em.createQuery("SELECT m FROM Mariage m", Mariage.class);
        return query.getResultList();
    }
}
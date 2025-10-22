package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.LigneCommande;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommande> {

    @Override
    public void create(LigneCommande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(o);
        t.commit();
        s.close();
    }

    @Override
    public LigneCommande getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        LigneCommande lc = s.get(LigneCommande.class, id);
        s.close();
        return lc;
    }

    @Override
    public List<LigneCommande> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommande> list = s.createQuery("FROM LigneCommande").list();
        s.close();
        return list;
    }

    @Override
    public void update(LigneCommande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.update(o);
        t.commit();
        s.close();
    }

    @Override
    public void delete(LigneCommande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.delete(o);
        t.commit();
        s.close();
    }
}

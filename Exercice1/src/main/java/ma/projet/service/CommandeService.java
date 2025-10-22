package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Commande;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public void create(Commande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(o);
        t.commit();
        s.close();
    }

    @Override
    public Commande getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Commande c = s.get(Commande.class, id);
        s.close();
        return c;
    }

    @Override
    public List<Commande> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Commande> list = s.createQuery("FROM Commande").list();
        s.close();
        return list;
    }

    @Override
    public void update(Commande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.update(o);
        t.commit();
        s.close();
    }

    @Override
    public void delete(Commande o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.delete(o);
        t.commit();
        s.close();
    }
}

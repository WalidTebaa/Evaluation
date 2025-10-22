package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Categorie;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public void create(Categorie o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(o);
        t.commit();
        s.close();
    }

    @Override
    public Categorie getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Categorie c = s.get(Categorie.class, id);
        s.close();
        return c;
    }

    @Override
    public List<Categorie> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> list = s.createQuery("FROM Categorie").list();
        s.close();
        return list;
    }

    @Override
    public void update(Categorie o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.update(o);
        t.commit();
        s.close();
    }

    @Override
    public void delete(Categorie o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.delete(o);
        t.commit();
        s.close();
    }
}

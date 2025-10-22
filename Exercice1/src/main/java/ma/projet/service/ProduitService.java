package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public void create(Produit o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(o);
        t.commit();
        s.close();
    }

    @Override
    public Produit getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Produit p = s.get(Produit.class, id);
        s.close();
        return p;
    }

    @Override
    public List<Produit> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createQuery("FROM Produit").list();
        s.close();
        return list;
    }

    @Override
    public void update(Produit o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.update(o);
        t.commit();
        s.close();
    }

    @Override
    public void delete(Produit o) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.delete(o);
        t.commit();
        s.close();
    }

    // ✅ 1. Produits par catégorie
    public List<Produit> getProduitsParCategorie(int idCategorie) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createQuery(
                        "FROM Produit p WHERE p.categorie.id = :idCat", Produit.class)
                .setParameter("idCat", idCategorie)
                .list();
        s.close();
        return list;
    }

    // ✅ 2. Produits commandés entre deux dates
    public List<Produit> getProduitsEntreDates(Date date1, Date date2) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createQuery(
                        "SELECT DISTINCT lc.produit FROM LigneCommande lc " +
                                "WHERE lc.commande.date BETWEEN :d1 AND :d2", Produit.class)
                .setParameter("d1", date1)
                .setParameter("d2", date2)
                .list();
        s.close();
        return list;
    }

    // ✅ 3. Produits d’une commande donnée
    public List<Object[]> getProduitsParCommande(int idCommande) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> list = s.createQuery(
                        "SELECT p.reference, p.prix, lc.quantite " +
                                "FROM LigneCommande lc JOIN lc.produit p " +
                                "WHERE lc.commande.id = :idCmd", Object[].class)
                .setParameter("idCmd", idCommande)
                .list();
        s.close();
        return list;
    }

    // ✅ 4. Produits dont le prix > 100 (avec requête nommée)
    public List<Produit> getProduitsPrixSup100() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createNamedQuery("Produit.findPrixSup100", Produit.class).list();
        s.close();
        return list;
    }
}

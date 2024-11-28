package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.entity.Payment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean save(Payment entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object payment = session.save(entity);
        System.out.println(payment);

        if (payment != null) {
            transaction.commit();
            session.close();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean update(Payment entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<Payment> getAll() {
        return List.of();
    }

    @Override
    public boolean savePayment(Payment entity, Session session) {
        try {
            session.save(entity);
            return true;
        } catch (Exception e) {
             new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
             return false;
        }
    }

    @Override
    public int getCurrentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Integer id = 0;
        try {
            String hql = "SELECT p.id FROM Payment p ORDER BY p.id DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(1); // Limit the result to only 1 item
            id = (Integer) query.uniqueResult();
            session.close();
            if(id != null){
                System.out.println(id);
                return id;

            }

        } catch (HibernateException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        System.out.println(id);
        return 0;
    }
}

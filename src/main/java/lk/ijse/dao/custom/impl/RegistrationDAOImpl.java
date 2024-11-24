package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.entity.Registration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public boolean saveRegistration(List<Registration> registrationList, Session session) {

        try {
            for (Registration registration:registrationList){
                boolean issaved = save(registration,session);
            }
            return true;
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public int getCurrentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        int id = 0;
        try {
            String hql = "SELECT r.rid FROM Registration r ORDER BY r.rid DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(1); // Limit the result to only 1 item
            id = (Integer) query.uniqueResult();
            session.close();
            if(id != 0){
                System.out.println(id);
                return id;

            }

        } catch (HibernateException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        System.out.println(id);
        return id;
    }


    public boolean save(Registration entity, Session session) throws SQLException, ClassNotFoundException {
        try {
            session.save(entity);
            return true;
        } catch (HibernateException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean save(Registration entity) {
        return false;
    }

    @Override
    public boolean update(Registration entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<Registration> getAll() {
        return List.of();
    }
}

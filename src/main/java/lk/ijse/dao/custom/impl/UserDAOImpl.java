package lk.ijse.dao.custom.impl;

import jakarta.persistence.NoResultException;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import org.hibernate.Session;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean ifHaveAdmins() {
        boolean ishaveadmins = false;
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "from User where role='admin'";
        try {
            ishaveadmins = session.createQuery(hql).list().size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ishaveadmins;
    }

    @Override
    public boolean saveUser(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public User getdatas(String username) {
        System.out.println("User name DAO - "+username);
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = null;

        try {
            String hql = "FROM User WHERE username = :userName";
            user = session.createQuery(hql, User.class)
                    .setParameter("userName", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found
            System.out.println("No user found with userid: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public boolean deleteUser(String username) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            session.delete(getdatas(username));
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }



    @Override
    public List<User> getAllUsers() {
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "from User";

        return session.createQuery(hql, User.class).list();
    }

    @Override
    public boolean changePassword(String username, String password) {
        System.out.println("User Name - "+username);
        System.out.println(password);

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            String hql = "UPDATE User SET password = :password WHERE username = :userName";
            session.createQuery(hql)
                    .setParameter("password", password)
                    .setParameter("userName", username)
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean checkemail(String email) {
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "FROM User WHERE email = :email";
        try {
            User user = session.createQuery(hql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean save(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();
            String hql = "UPDATE User SET username = :userName, email = :email, password = :password, role = :role WHERE email = :email";
            session.createQuery(hql)
                    .setParameter("userName", entity.getUsername())
                    .setParameter("password", entity.getPassword())
                    .setParameter("email", entity.getEmail())
                    .setParameter("role", entity.getRole())
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}

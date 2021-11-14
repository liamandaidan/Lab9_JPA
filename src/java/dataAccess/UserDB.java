package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.Role;
import models.User;

/**
 * This class interacts directly with the database in order to create, delete, and manipulate user data
 *
 * @author bdavi
 */
public class UserDB {

    /**
     * Create a list of User objects from every user in the database
     *
     * @return list of User objects
     * @throws SQLException
     */
    public List<User> getAllUsers() throws SQLException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<User> allUsers = em.createNamedQuery("User.findAll").getResultList();
            //has all users inside of it. Just need to unpackage later.
            return allUsers;
        } finally {
            em.close();
        }

    }

    /**
     * Retrieve a single user from the database based on the email primary key
     *
     * @param email the primary key to search for
     * @return the User object matching the email passed to it
     * @throws SQLException
     */
    public User getUser(String email) throws SQLException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            User user = em.find(User.class, email);
            System.out.println("The user found is: " + user.getFirstName());
            return user;
        } finally {
            em.close();
        }

    }

    /**
     * Add a User object to the database as a new row in the Users table
     *
     * @param user the User object to add
     * @throws SQLException
     */
    public void addUser(User user) throws SQLException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            //take the user and insert
            trans.begin();
            System.out.println(user.getEmail() + " has been persisted!");
            em.persist(user);
            trans.commit();

        } catch (Exception e) {
            System.out.println("There was an error adding user");
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * Change data for a user in the users table
     *
     * @param user the User object to edit
     * @throws SQLException
     */
    public void updateUser(User user) throws SQLException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println("There was an error updating user");
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * Remove a row (a user) from the database users table
     *
     * @param email the primary key of the User to remove
     * @throws SQLException
     */
    public void deleteUser(String email) throws SQLException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            //remove a user by their email.
            List<User> userToRemove = em.createNamedQuery("User.findByEmail").setParameter("email", email).getResultList();
            //we grab the first user since its a primary key
            User user = userToRemove.get(0);
            System.out.println("The user being removed: " + user.getEmail());
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception ex) {
            System.out.println("There was an error deleting user");
            trans.rollback();
        } finally {
            em.close();
        }
    }
}

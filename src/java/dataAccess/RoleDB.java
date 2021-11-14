package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;
import models.User;

/**
 * This class interacts directly with the database, and the roles table
 *
 * @author bdavi
 */
public class RoleDB {

    /**
     * Return a single role name based on its ID number
     *
     * @param roleID
     * @return
     * @throws SQLException
     */
    public Role getRole(int roleID) throws SQLException {

     EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            Role role = em.find(Role.class, roleID);
            System.out.println("The Role found is: " + role.getRoleName());
            return role;
        } finally {
            em.close();
        }
    }

    /**
     * Returns a list of all role names from the roles table
     *
     * @return
     * @throws SQLException
     */
    public List<Role> getAllRoles() throws SQLException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Role> allRoles = em.createNamedQuery("Role.findAll").getResultList();
            //has all users inside of it. Just need to unpackage later.
             System.out.println("The Role List found is: " + allRoles);
            return allRoles;
        } finally {
            em.close();
        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataAccess.RoleDB;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Role;

/**
 *
 * @author bdavi
 */
public class RoleService implements Serializable{

    static RoleDB roleGetter = new RoleDB();

    // Get a list of all roles from the DB.
    public List<Role> getAllRoles() {
        try 
        {
            return roleGetter.getAllRoles();
        } 
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
       
    }
    
    // Get a single role from the db by its primary key.
    public Role getRoleByID(int roleID) {
        try 
        {
            return roleGetter.getRole(roleID);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        
    }  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.User;
import dataAccess.*;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Brett
 */
public class UserService
{
    static UserDB userGetter = new UserDB();
    public static List<User> getAllUsers()
    {
        List<User> users;
        try
        {
            users = userGetter.getAllUsers();
            if(users.isEmpty())
            {
                System.out.println("There are no recorded users.");
                return null;
            }
            return users;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static User getUser(String email)
    {
        try
        {
            User theUser = userGetter.getUser(email);
            if(theUser == null)
            {
                System.out.println("That user doesn't exist!");
                return null;
            }
            return theUser;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static boolean updateUser(User user)
    {
        if(user.getEmail() != null && user.getFirstName() != null && user.getLastName() != null && user.getPassword() != null && user.getRole() != null)
        {
            try
            {
                userGetter.updateUser(user);
                return true;
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
        } else 
        {
            System.out.println("One or more fields are null. That won't work!");
            return false;
        }
    }
    
    public static boolean createUser(User user)
    {
        if(user.getEmail() != null && user.getFirstName() != null && user.getLastName() != null && user.getPassword() != null && user.getRole() != null)
        {
            try
            {
                userGetter.addUser(user);
                return true;
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
        } else
        {
            System.out.println("One or more fields are null. That won't work!");
            return false;
        }
    }
    
    public static boolean delete(String userEmail)
    {
        try
        {
            userGetter.deleteUser(userEmail);
            return true;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

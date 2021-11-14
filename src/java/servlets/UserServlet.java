/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.*;

/**
 *
 *
 */
public class UserServlet extends HttpServlet {

    private static final String errCreateUserMsg = "Failed to create user. Please check that all fields are filled out.";
    private static final String errEditUserMsg = "Failed to edit user. Please check that all fields are filled out and are accurate";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the role information and the current users as attributes for initial display of the page.
        UserService us = new UserService();
        RoleService rs = new RoleService();
        //TODO come back and fix
        request.setAttribute("roles", rs.getAllRoles());
        request.setAttribute("userList", us.getAllUsers());
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();
        RoleService rs = new RoleService();
        
        // Set the roles for the drop down box and create vars to hold each parameter.
        //TODO come fix. Returns arrayList<Roles>
        request.setAttribute("roles", rs.getAllRoles());
        String requestedAction = request.getParameter("submitBtn");
        User user;
        String email;
        String firstName;
        String lastName;
        String password;
        String role;
        String isActive;
        
        // Make sure we dont try to check values that are null.
        if (requestedAction == null) {
            requestedAction = "";
        }

        // Pull the attributes from the JSP forms
        if (requestedAction.equals("addUser")) { // If user is adding a new user.
            email = request.getParameter("addEmail");
            firstName = request.getParameter("addFirst");
            lastName = request.getParameter("addLast");
            password = request.getParameter("addPassword");
            role = request.getParameter("addRole");
            isActive = request.getParameter("addActive");

            // Instantiate a new user and make sure the factory function did not return a null user in failure.
            user = instantiateUserObject(email, firstName, lastName, password, role, isActive);
            if (user == null) {
                request.setAttribute("invalidAttribute", errCreateUserMsg);
            } else {
                // Check if the user service accepts the user object. If not display error message.
                if (!us.createUser(user)) {
                    request.setAttribute("invalidAttribute", errCreateUserMsg);
                } else {
                    request.setAttribute("invalidAttribute", "");
                }
            }
        } 
        // Pull attributes from the edit form if that submit button was pressed. This block then sends that
        // update to the user service.
        else if (requestedAction.equals("editUser")) {
            email = request.getParameter("editEmail");
            firstName = request.getParameter("editFirst");
            lastName = request.getParameter("editLast");
            password = request.getParameter("editPassword");
            role = request.getParameter("editRole");
            isActive = request.getParameter("editActive");
            
            // Instantiate a new user and make sure the factory function did not return a null user in failure.
            user = instantiateUserObject(email, firstName, lastName, password, role, isActive);
            if (user == null) {
                request.setAttribute("invalidAttribute", errEditUserMsg);
            } else {
                // Check if the user service accepts the user object. If not display error message.
                if (!us.updateUser(user)) {
                    request.setAttribute("invalidAttribute", errEditUserMsg);
                } else {
                    request.setAttribute("invalidAttribute", "");
                }
            }
        }

        // If the edit or delete user buttons were selected then fill in the user info into the edit fields or delete the user.
        String editOrDelete = request.getParameter("editOrDelete");
        if (editOrDelete != null) {
            email = request.getParameter("hiddenEmail");

            if (editOrDelete.equals("Edit")) {
                User userInfo = us.getUser(email);
                
                // Pull the role name from the DB.
                userInfo.setRole(rs.getRoleByID(userInfo.getRole().getRoleId()));
                request.setAttribute("userInfo", userInfo);
                
            } else if (editOrDelete.equals("Delete")) {
                us.delete(email);
            }
        }

        request.setAttribute("userList", us.getAllUsers());
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    User instantiateUserObject(String email, String first, String last, String password, String role, String isActive) {
        User user = new User();
        RoleService rs = new RoleService();
        Role roleName = rs.getRoleByID(Integer.parseInt(role));
        // Check if any of the fields are null or are empty.
        if (Stream.of(email, first, last, password).anyMatch(x -> x == null)
                || Stream.of(email, first, last, password).anyMatch(x -> x.equals(""))) {
            return null;
        } else {
            
            user.setEmail(email);
            user.setFirstName(first);
            user.setLastName(last);
            user.setPassword(password);
            user.setRole(roleName);
            
            // Determine the state of the textbox. If it is not selected it is retrieved as false.
            if (isActive == null) {
                user.setActive(false);
            } else {
                user.setActive(true);
            }
        }
        return user;
    }
}

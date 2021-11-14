<!DOCTYPE html>
<%-- 
    Document   : users.jsp
    Created on : 23-Oct-2021, 4:36:22 PM
    Author     : Liam McLaughlin
    Comments   : The jsp will display the UI for the assignment. It communcates with the styles.css, and
                 UserServelet(/user).
--%>
<html lang="en">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/res/styles.css">
        <title>Document</title>
    </head>

    <body>

        <div class="column-wrap">
            <div class="column one-third">
                <div class="col-group">
                    <h1>Add User</h1>
                    <form action="user" method="post">
                        <input type="email" name="addEmail" placeholder="Email"><br>
                        <input type="text" name="addFirst" placeholder="FirstName"><br>
                        <input type="text" name="addLast" placeholder="LastName"><br>
                        <input type="text" name="addPassword" placeholder="Password"><br>
                        <select name="addRole" id="role">
                            <c:forEach var="roleList" items="${roles}">
                                <option name="addRole" value="${roleList.getRoleId}">${roleList.getRoleName}</option>
                            </c:forEach>

                        </select><br>
                        <input type="checkbox" name="addActive" id="active" value="true"> 
                        <label for="active">is Active</label><br>
                        <input type="submit" value="addUser" name="submitBtn">
                    </form>
                    <c:if test="${invalidAttribute != null}">
                        <div>
                            <p>${invalidAttribute}</p>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="column one-third">
                <div class="col-group">
                    <h1>Manage User</h1>
                    <table>
                        <tr>
                            <th>Email</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.email}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                            <form action="user" method="post">
                                <td><input type="submit" name="editOrDelete" value="Edit"></td>
                                <input type="hidden" name="hiddenEmail" value="${user.email}">
                                <td><input type="submit" name="editOrDelete" value="Delete"></td>
                            </form>
                            </tr>

                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="column one-third">
                <div class="col-group">
                    <h1>Edit User</h1>
                    <form action="user" method="post">
                        <input type="email" name="editEmail" placeholder="Email" value="${userInfo.email}" readonly><br>
                        <input type="text" name="editFirst" placeholder="FirstName" value="${userInfo.firstName}"><br>
                        <input type="text" name="editLast" placeholder="LastName" value="${userInfo.lastName}"><br>
                        <input type="text" name="editPassword" placeholder="Password" value="${userInfo.password}"><br>
                        <select name="editRole" id="role">
                            <c:forEach var="roleList" items="${roles}">
                                <c:if test="${userInfo.role == roleList}">
                                    <option name="editRole" value="${roleList}" selected>${roleList}</option>
                                </c:if>
                                <c:if test="${userInfo.role != roleList}">
                                    <option name="editRole" value="${roleList}">${roleList}</option>
                                </c:if>
                            </c:forEach>
                        </select><br>
                        <c:if test="${userInfo.isActive}">
                            <input type="checkbox" name="editActive" id="active" value="true" checked>
                        </c:if>
                        <c:if test="${!userInfo.isActive}">
                            <input type="checkbox" name="editActive" id="active" value="true">
                        </c:if>

                        <label for="active">is Active</label><br>
                        <input type="submit" value="editUser" name="submitBtn">
                    </form>
                </div>
            </div>
        </div>

        <video autoplay muted loop id="video">
            <source src="${pageContext.request.contextPath}/res/bg.mp4" type="video/mp4">
            <!-- Video BG -->
            <!-- https://www.pexels.com/video/mixture-of-different-colors-of-ink-in-liquid-2421545/ -->
        </video>

    </body>

</html>
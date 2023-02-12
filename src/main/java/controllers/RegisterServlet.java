package controllers;

import dao.*;

import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "controllers.RegisterServlet", urlPatterns = "/DeLaCruz_Zhang_Cui_Adlister_war_exploded/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("confirm_password");
        long zip_code = Long.parseLong(request.getParameter("zip_code"));
        long phone_number = Long.parseLong(request.getParameter("phone_number"));
        // username should not be able to be registered twice
        // validate input
        boolean inputHasErrors = username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || (! password.equals(passwordConfirmation));

        try {
            boolean existingUser = DaoFactory.getUsersDao().validateUsername(username);
            if (existingUser){
                request.setAttribute("invalidAttempt", true);
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request,response);
            }

        } catch (ServletException | SQLException e) {
            e.printStackTrace();
        }


        if (inputHasErrors || password.length() <= 7) {
            response.sendRedirect("/DeLaCruz_Zhang_Cui_Adlister_war_exploded/register");
            return;
        }

        // create and save a new user
        User user = new User(username, email, password, zip_code, phone_number);
        DaoFactory.getUsersDao().insert(user);
        response.sendRedirect("/DeLaCruz_Zhang_Cui_Adlister_war_exploded/login");
    }
}

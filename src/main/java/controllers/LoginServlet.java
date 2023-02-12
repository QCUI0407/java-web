package controllers;

import dao.DaoFactory;
import models.User;
import util.Password;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "controllers.LoginServlet", urlPatterns = "/DeLaCruz_Zhang_Cui_Adlister_war_exploded/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("/DeLaCruz_Zhang_Cui_Adlister_war_exploded/profile");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = DaoFactory.getUsersDao().findByUsername(username);

        request.getSession().setAttribute("newName", request.getParameter("username"));

        if(user == null) {
            request.getSession().setAttribute("invalidAttempt", false);
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        boolean validAttempt = Password.check(user.getPassword(), Password.hash(password));
        if (validAttempt) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/DeLaCruz_Zhang_Cui_Adlister_war_exploded/ads");
        } else {

            response.sendRedirect("/DeLaCruz_Zhang_Cui_Adlister_war_exploded/login");

        }
    }
}
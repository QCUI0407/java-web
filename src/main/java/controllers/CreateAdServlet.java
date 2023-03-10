package controllers;

import dao.DaoFactory;
import models.Ad;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateAdServlet", urlPatterns = "/DeLaCruz_Zhang_Cui_Adlister_war_exploded/ads/create")
public class CreateAdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/ads/create.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassCastException{
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }


        User user = new User();

        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser != null){
            String titleRetrieval = request.getParameter("title");
            String descriptionRetrieval = request.getParameter("description");
            Ad ad = new Ad(
                    currentUser.getId(),
                    titleRetrieval,
                    descriptionRetrieval
            );


            DaoFactory.getAdsDao().insert(ad);
            response.sendRedirect("/ads");
        }
    }
}
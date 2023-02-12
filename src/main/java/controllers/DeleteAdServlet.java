package controllers;

import dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "DeleteAdServlet", urlPatterns = "/DeLaCruz_Zhang_Cui_Adlister_war_exploded/delete")
public class DeleteAdServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("deleteAd");
        DaoFactory.getAdsDao().delete(id);;
//        dao.delete(id);

        response.sendRedirect("/DeLaCruz_Zhang_Cui_Adlister_war_exploded/ads");
    }

}

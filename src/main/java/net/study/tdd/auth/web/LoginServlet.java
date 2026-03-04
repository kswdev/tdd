package net.study.tdd.auth.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.study.tdd.auth.service.AuthenticationService;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected AuthenticationService getAuthenticationService() {
        return null;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("j_username");
        String pass = req.getParameter("j_password");

        if (getAuthenticationService().isValidLogin(user, pass)) {
            req.getSession().setAttribute("j_username", user);
            req.getSession().setAttribute("j_password", pass);
            resp.sendRedirect("/frontpage");
        } else
            resp.sendRedirect("/invalidlogin");
    }
}

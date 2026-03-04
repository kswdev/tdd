package net.study.tdd.auth.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.study.tdd.auth.web.constant.Authentication;
import net.study.tdd.auth.service.AuthenticationService;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected AuthenticationService getAuthenticationService() {
        return null;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter(Authentication.USERNAME);
        String pass = req.getParameter(Authentication.PASSWORD);

        if (getAuthenticationService().isValidLogin(user, pass)) {
            req.getSession().setAttribute(Authentication.USERNAME, user);
            req.getSession().setAttribute(Authentication.PASSWORD, pass);
            resp.sendRedirect("/frontpage");
        } else
            resp.sendRedirect("/invalidlogin");
    }
}

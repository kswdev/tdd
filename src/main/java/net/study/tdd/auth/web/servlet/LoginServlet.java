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
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter(Authentication.USERNAME);
        String pass = request.getParameter(Authentication.PASSWORD);

        if (getAuthenticationService().isValidLogin(user, pass)) {
            request.getSession().setAttribute(Authentication.USERNAME, user);
            response.sendRedirect("/frontpage");
        } else
            response.sendRedirect("/invalidlogin");
    }
}

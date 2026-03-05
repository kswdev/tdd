package net.study.tdd.auth.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.study.tdd.auth.service.AuthenticationService;
import net.study.tdd.auth.web.constant.Authentication;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class LoginController implements Controller {

    private AuthenticationService authenticationService;

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Nullable
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String user = request.getParameter(Authentication.USERNAME);
        String pass = request.getParameter(Authentication.PASSWORD);

        if (authenticationService.isValidLogin(user, pass)) {
            request.getSession().setAttribute(Authentication.USERNAME, user);
            return new ModelAndView("/frontpage");
        }
        return new ModelAndView("/invalidlogin");
    }
}

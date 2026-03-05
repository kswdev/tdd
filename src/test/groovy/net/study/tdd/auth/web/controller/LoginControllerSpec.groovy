package net.study.tdd.auth.web.controller

import net.study.tdd.auth.service.AuthenticationService
import net.study.tdd.auth.service.FakeAuthenticationService
import net.study.tdd.auth.web.constant.Authentication
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.Controller
import spock.lang.Specification

class LoginControllerSpec extends Specification {

    private static final String VALID_USERNAME = "validUser"
    private static final String VALID_PASSWORD = "validPassword"

    private Controller loginController
    private MockHttpServletRequest request
    private MockHttpServletResponse response
    private FakeAuthenticationService authenticator

    def setup() {
        request = new MockHttpServletRequest("GET", "/login")
        response = new MockHttpServletResponse()
        authenticator = new FakeAuthenticationService()

        authenticator.addUser(VALID_USERNAME, VALID_PASSWORD)

        loginController = new LoginController()
        loginController.setAuthenticationService(authenticator)
    }

    def "패스워드가 틀리면 에러 페이지로 리다이렉트한다."() {
        given:
        request.addParameter(Authentication.USERNAME, VALID_USERNAME)
        request.addParameter(Authentication.PASSWORD, "wrongpassword")

        when:
        ModelAndView mv = loginController.handleRequest(request, response)

        then:
        mv.viewName == "/invalidlogin"
    }

    def "패스워드가 맞으면 프론트 페이지로 이동하고 사용자 이름을 저장한다."() {
        given:
        request.addParameter(Authentication.USERNAME, VALID_USERNAME)
        request.addParameter(Authentication.PASSWORD, VALID_PASSWORD)

        when:
        ModelAndView mv = loginController.handleRequest(request, response)

        then:
        mv.viewName == "/frontpage"
        request.getSession().getAttribute(Authentication.USERNAME) == VALID_USERNAME
    }
}

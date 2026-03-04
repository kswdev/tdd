package net.study.tdd.auth.web

import net.study.tdd.auth.service.AuthenticationService
import net.study.tdd.auth.service.FakeAuthenticationService
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import spock.lang.Specification

class LoginServletSpec extends Specification {

    private static final String VALID_USERNAME = "validUser"
    private static final String VALID_PASSWORD = "validPassword"

    private LoginServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FakeAuthenticationService authenticator;

    def setup() {
        request = new MockHttpServletRequest("GET", "/login")
        response = new MockHttpServletResponse()
        authenticator = new FakeAuthenticationService()

        authenticator.addUser(VALID_USERNAME, VALID_PASSWORD)

        servlet = new LoginServlet() {

            @Override
            protected AuthenticationService getAuthenticationService() {
                return authenticator
            }
        }
    }

    def "패스워드가 틀리면 에러 페이지로 리다이렉트한다."() {
        given:
        request.addParameter("j_username", VALID_USERNAME)
        request.addParameter("j_password", "wrongpassword")

        when:
        servlet.service(request, response)

        then:
        response.redirectedUrl == "/invalidlogin"
    }

    def "패스워드가 맞으면 프론트 페이지로 이동하고 사용자 이름을 저장한다."() {
        given:
        request.addParameter("j_username", VALID_USERNAME)
        request.addParameter("j_password", VALID_PASSWORD)

        when:
        servlet.service(request, response)

        then:
        response.redirectedUrl == "/frontpage"
        request.getSession().getAttribute("j_username") == "validuser"
    }
}

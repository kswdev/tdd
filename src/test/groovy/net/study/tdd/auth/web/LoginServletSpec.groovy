package net.study.tdd.auth.web


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import spock.lang.Specification

class LoginServletSpec extends Specification {

    def "패스워드가 틀리면 에러 페이지로 리다이렉트한다."() {
        given:
        def servlet = new LoginServlet()
        def request = new MockHttpServletRequest("GET", "/login")
        request.addParameter("j_username", "nosuchuser")
        request.addParameter("j_password", "wrongpassword")
        def response = new MockHttpServletResponse()

        when:
        servlet.service(request, response)

        then:
        response.redirectedUrl == "/invalidlogin"
    }

    def "패스워드가 맞으면 프론트 페이지로 이동하고 사용자 이름을 저장한다."() {
        given:
        def servlet = new LoginServlet()
        def request = new MockHttpServletRequest("GET", "/login")
        request.addParameter("j_username", "validuser")
        request.addParameter("j_password", "correctpassword")
        def response = new MockHttpServletResponse()

        when:
        servlet.service(request, response)

        then:
        response.redirectedUrl == "/frontpage"
        request.getSession().getAttribute("j_username") == "validuser"
    }
}

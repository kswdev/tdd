package net.study.tdd.common.log

import jakarta.servlet.http.HttpServletRequest
import net.study.tdd.common.time.SystemTime
import spock.lang.Specification

import java.text.DateFormat

class HttpRequestLogFormatterSpec extends Specification {

    private HttpServletRequest request = Mock()
    private HttpRequestLogFormatter formatter;

    def setup() {
        formatter = new HttpRequestLogFormatter()
    }

    def cleanup() {
        SystemTime.reset()
    }

    def "Http 요청을 받으면 지정된 포맷으로 로그를 출력한다."() {
        given:
        long time = SystemTime.asMillis()
        SystemTime.setTimeSource({time})
        DateFormat dateFormat = HttpRequestLogFormatter.dateFormat
        String timestamp = dateFormat.format(SystemTime.asDate())
        String expected = new StringBuilder()
                .append("1.2.3.4 - bob [${timestamp}] ")
                .append('"GET /ctx/resource HTTP/1.1" ')
                .append("200 2326")

        when:
        String result = formatter.format(request, 200, 2326)

        then:
        1 * request.getRemoteAddr() >> '1.2.3.4'
        1 * request.getRemoteUser() >> 'bob'
        1 * request.getMethod() >> 'GET'
        1 * request.getRequestURI() >> '/ctx/resource'
        1 * request.getProtocol() >> 'HTTP/1.1'

        result == expected
    }
}

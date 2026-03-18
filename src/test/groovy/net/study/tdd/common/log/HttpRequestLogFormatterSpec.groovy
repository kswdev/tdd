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

    def "timestamp는 특정 로그 포맷을 따라야 한다"() {
        given:
        // 19/Mar/2026
        String date = "\\d{2}/[\\w가-힣]{2}/\\d{4}"
        // 02:28:42
        String time = "\\d{2}:\\d{2}:\\d{2}"
        // +0900
        String timezone = "(-|\\+)\\d{4}"
        String regex = date + " " + time + " " + timezone

        DateFormat dateFormat = HttpRequestLogFormatter.dateFormat
        String timestamp = dateFormat.format(new Date())

        expect:
        timestamp.matches(regex)
    }

    def "고정된 시간 기준으로 로그 포맷을 정확히 생성한다"() {
        given:
        String fixed = "09/3월/2006 20:55:59 +0900"
        DateFormat df = HttpRequestLogFormatter.dateFormat
        Date date = df.parse(fixed)

        SystemTime.setTimeSource({ date.time })

        String expected = "1.2.3.4 - bob [${fixed}] \"GET /ctx/resource HTTP/1.1\" 200 2326"

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

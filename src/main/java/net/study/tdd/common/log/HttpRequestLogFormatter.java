package net.study.tdd.common.log;

import jakarta.servlet.http.HttpServletRequest;
import net.study.tdd.common.time.SystemTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HttpRequestLogFormatter {

    public static DateFormat dateFormat = new SimpleDateFormat( "dd/MMM/yyyy HH:mm:ss Z");

    public String format(HttpServletRequest request, int status, long timeTaken) {
        return String.format(
                "%s - %s [%s] \"%s %s %s\" %s %s",
                request.getRemoteAddr(),
                request.getRemoteUser(),
                dateFormat.format(SystemTime.asDate()),
                request.getMethod(),
                request.getRequestURI(),
                request.getProtocol(),
                status,
                timeTaken
        );
    }
}

package net.study.tdd.common.time;

import java.util.Date;

public class SystemTime {

    private static final TimeSource defaultSrc = System::currentTimeMillis;

    private static TimeSource source = null;

    public static long asMillis() {
        return getTimeSource().millis();
    }

    public static Date asDate() {
        return new Date(asMillis());
    }

    public static void reset() {
        setTimeSource(null);
    }

    public static void setTimeSource(TimeSource source) {
        SystemTime.source = source;
    }
    private static TimeSource getTimeSource() {
        return (source != null ? source : defaultSrc);
    }
}

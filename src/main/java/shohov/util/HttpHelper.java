package shohov.util;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {

    private HttpHelper() {}

    private static final String X_FORWARDED_FOR = "X-Forwarded-For";

    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",", 1)[0];
        }
        return request.getRemoteAddr();
    }
}

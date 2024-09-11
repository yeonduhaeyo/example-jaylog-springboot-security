package org.jaybon.jaylog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UtilFunction {

    public static String generateAccessJwtByCustomUserDetails(CustomUserDetails customUserDetails) {
        return JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.Jwt.ACCESS_EXPIRATION_TIME))
                .withClaim("username", customUserDetails.getUsername())
                .withClaim("roleList", customUserDetails.getUser().getRoleList())
                .withClaim("loginType", customUserDetails.getUser().getLoginType())
                .withClaim("timestamp", Timestamp.valueOf(LocalDateTime.now()).getTime())
                .sign(Algorithm.HMAC512(Constants.Jwt.SECRET));
    }

    public static String generateRefreshJwtByCustomUserDetails(CustomUserDetails customUserDetails) {
        return JWT.create()
                .withSubject("refreshToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.Jwt.REFRESH_EXPIRATION_TIME))
                .withClaim("username", customUserDetails.getUsername())
                .withClaim("timestamp", Timestamp.valueOf(LocalDateTime.now()).getTime())
                .sign(Algorithm.HMAC512(Constants.Jwt.SECRET));
    }

    public static boolean isWindows() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.toLowerCase().contains("win");
    }

    public static boolean isMac() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.toLowerCase().contains("mac");
    }

    public static boolean isUnix() {
        String OS = System.getProperty("os.name").toLowerCase();
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }

    public static Map<String, String> parseQueryString(String url) {
        Map<String, String> queryPairs = new HashMap<>();
        try {
            URL urlObj = new URL(url);
            String query = urlObj.getQuery();
            String[] pairs = query != null ? query.split("&") : new String[0];
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryPairs;
    }

}

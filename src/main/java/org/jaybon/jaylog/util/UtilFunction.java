package org.jaybon.jaylog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.exception.BadRequestException;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.jaybon.jaylog.model.article.repository.ArticleRepository;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.jaybon.jaylog.model.user.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilFunction {

    public static String convertImageToBase64(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        if (!isValidImageExtension(multipartFile.getOriginalFilename())) {
            throw new BadRequestException("jpeg, jpg, png, gif 파일만 업로드 가능합니다.");
        }
        String imageBase64;
        try {
            imageBase64 = "data:" + multipartFile.getContentType() + ";base64,"
                    + Base64.getEncoder().encodeToString(multipartFile.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("프로필 이미지 변환에 실패했습니다." + e.getMessage());
        }
        return imageBase64;
    }

    public static boolean isValidImageExtension(String fileName) {
        return Pattern.compile(Constants.Regex.VALID_IMAGE_EXTENSION).matcher(fileName).matches();
    }

    public static ArticleEntity getArticleEntityBy(
            ArticleRepository articleRepository,
            Long id
    ) {
        Optional<ArticleEntity> articleEntityOptional = articleRepository.findByIdAndDeleteDateIsNull(id);
        if (articleEntityOptional.isEmpty()) {
            throw new BadRequestException("해당 게시글이 존재하지 않습니다.");
        }
        return articleEntityOptional.get();
    }

    public static UserEntity getUserEntityBy(
            UserRepository userRepository,
            CustomUserDetails customUserDetails
    ) {
        Optional<UserEntity> userEntityOptional = userRepository.findByIdAndDeleteDateIsNull(customUserDetails.getUser().getId());
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("사용자 정보를 찾을 수 없습니다.");
        }
        return userEntityOptional.get();
    }

    public static String getFirstUrlFromMarkdown(String markdown) {
        List<String> urlList = getUrlListFromMarkdown(markdown);
        return urlList.isEmpty() ? null : urlList.get(0);
    }

    public static List<String> getUrlListFromMarkdown(String markdown) {
        Pattern pattern = Pattern.compile(Constants.Regex.MARKDOWN_IMAGE);
        Matcher matcher = pattern.matcher(markdown);
        List<String> urlList = new ArrayList<>();
        while (matcher.find()) {
            urlList.add(matcher.group(1));
        }
        return urlList;
    }

    public static String generateAccessJwtBy(CustomUserDetails customUserDetails) {
        return JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.Jwt.ACCESS_EXPIRATION_TIME))
                .withClaim("username", customUserDetails.getUsername())
                .withClaim("roleList", customUserDetails.getUser().getRoleList())
                .withClaim("loginType", customUserDetails.getUser().getLoginType().toString())
                .withClaim("timestamp", Timestamp.valueOf(LocalDateTime.now()).getTime())
                .sign(Algorithm.HMAC512(Constants.Jwt.SECRET));
    }

    public static String generateRefreshJwtBy(CustomUserDetails customUserDetails) {
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

    public static Map<String, String> parseQueryStringOf(String url) {
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

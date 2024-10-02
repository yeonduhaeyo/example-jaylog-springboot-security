package org.jaybon.jaylog.common.constants;

public class Constants {

    // 상수 클래스

    public static class Jwt {
        public static final String SECRET = "sweetsalt"; // 서버만 알고 있는 비밀값
        public static final int ACCESS_EXPIRATION_TIME = 30 * 60 * 1000; // 만료기간 30분 (1/1000초)
        public static final int REFRESH_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 만료기간 7일 (1/1000초)
        public static final String ACCESS_HEADER_NAME = "Authorization";
        public static final String HEADER_PREFIX = "Bearer ";
    }

    public static class Regex {
        public static final String MARKDOWN = "(\\*\\*|\\*|_|#|`{1,3}|~{2}|-{3,}|>{1,}|\\[.*?\\]\\(.*?\\)|!\\[.*?\\]\\(.*?\\)|\\n)";
        public static final String MARKDOWN_IMAGE = "!\\[[^\\]]*\\]\\(([^)]+)\\)";
        public static final String VALID_IMAGE_EXTENSION = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif))$)";
    }

    public static class ResCode {
        public static final int ENTITY_ALREADY_EXIST_EXCEPTION = 1;
        public static final int OK = 0;
        public static final int BAD_REQUEST_EXCEPTION = -1;
        public static final int MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = -2;
        public static final int BIND_EXCEPTION = -3;
        public static final int CONSTRAINT_VIOLATION_EXCEPTION = -3;
        public static final int HTTP_MESSAGE_NOT_READABLE_EXCEPTION = -4;
        public static final int HTTP_REQUEST_METHOD_NOT_SUPPORT_EXCEPTION = -5;
        public static final int METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = -6;
        public static final int CONVERSION_FAILED_EXCEPTION = -7;
        public static final int AUTHENTICATION_EXCEPTION = -9;
        public static final int AUTHORITY_EXCEPTION = -10;
        public static final int EXCEPTION = -99;
    }

}

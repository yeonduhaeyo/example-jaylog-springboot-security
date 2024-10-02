package org.jaybon.jaylog.common.exception;

public class AuthenticationException extends RuntimeException {

    // 인증에 문제가 생겼을 때 발생
    public AuthenticationException(String message) {
        super(message);
    }

}

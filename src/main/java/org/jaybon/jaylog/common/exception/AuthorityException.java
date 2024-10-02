package org.jaybon.jaylog.common.exception;

public class AuthorityException extends RuntimeException {

    // 권한에 문제가 생겼을 때 발생
    public AuthorityException(String message) {
        super(message);
    }

}

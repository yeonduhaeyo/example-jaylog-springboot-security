package org.jaybon.jaylog.common.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super("잘못된 요청입니다.");
    }
}

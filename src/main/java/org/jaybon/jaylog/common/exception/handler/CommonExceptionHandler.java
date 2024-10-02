package org.jaybon.jaylog.common.exception.handler;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.common.exception.AuthenticationException;
import org.jaybon.jaylog.common.exception.AuthorityException;
import org.jaybon.jaylog.common.exception.BadRequestException;
import org.jaybon.jaylog.common.exception.EntityAlreadyExistException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@RestControllerAdvice // @ControllerAdvice와 @ResponseBody를 합쳐놓은 어노테이션
                      // @ExceptionHandler, @ModelAttribute, @InitBinder 가 적용된 메서드들에
                      // AOP를 적용해 Controller 단에 적용
                      // @ResponseBody를 통해 객체를 리턴
public class CommonExceptionHandler {

        // 스프링부트 익셉션 핸들러 예시

        // 익셉션 핸들러 작동 순서 (public class BadRequestException extends RuntimeException)

        // 1.BadRequestException의 핸들러를 찾는다.

        // 2.없다면 BadRequestException의 부모 익셉션 핸들러를 찾는다.
        // (RuntimeException, Exception)

        // 3.없다면 BadRequestException의 부모 익셉션 핸들러의 다른 자식 핸들러를 찾는다.
        // (RuntimeException, Exception을 상속한 다른 자식 핸들러, 의도하지 않은 작동을 할 수 있음)

        @ExceptionHandler(EntityAlreadyExistException.class) // @ExceptionHandler는 Controller계층에서 발생하는 에러를 잡아서 메서드로
                                                             // 처리해주는 기능, Service, Repository에서 발생하는 에러는 제외한다.

        public ResponseEntity<ResDTO<Object>> handleEntityAlreadyExistException(Exception e) {
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.ENTITY_ALREADY_EXIST_EXCEPTION)
                                                .message(e.getMessage())
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ResDTO<Object>> handleBadRequestException(Exception e) {
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.BAD_REQUEST_EXCEPTION)
                                                .message(e.getMessage())
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ResDTO<Object>> handleMissingServletRequestParameterException(Exception e) {
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION)
                                                .message(e.getMessage())
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(BindException.class)
        public ResponseEntity<ResDTO<Object>> handleBindException(BindException e) {
                Map<String, String> errorMap = new HashMap<>();

                e.getBindingResult().getFieldErrors().forEach(fieldError -> {
                        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                });

                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.BIND_EXCEPTION)
                                                .message("요청한 데이터가 유효하지 않습니다.")
                                                .data(errorMap)
                                                .build(),
                                HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ResDTO<Object>> handleBindException(ConstraintViolationException exception) {
                Map<String, String> errorMap = new HashMap<>();
                exception.getConstraintViolations().forEach(constraintViolation -> {
                        List<Path.Node> pathNodeList = StreamSupport
                                        .stream(constraintViolation.getPropertyPath().spliterator(), false)
                                        .toList();
                        errorMap.put(pathNodeList.get(pathNodeList.size() - 1).getName(),
                                        constraintViolation.getMessage());
                });
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.CONSTRAINT_VIOLATION_EXCEPTION)
                                                .message("요청한 데이터가 유효하지 않습니다.")
                                                .data(errorMap)
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ResDTO<Object>> handleHttpMessageNotReadableException(Exception e) {
                if (e.getMessage().contains("Required request body is missing")) {
                        return new ResponseEntity<>(
                                        ResDTO.builder()
                                                        .code(Constants.ResCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION)
                                                        .message("RequestBody가 없습니다.")
                                                        .build(),
                                        HttpStatus.BAD_REQUEST);
                }
                if (e.getMessage().contains("Enum class: ")) {
                        return new ResponseEntity<>(
                                        ResDTO.builder()
                                                        .code(Constants.ResCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION)
                                                        .message("Type 매개변수를 확인하세요.")
                                                        .build(),
                                        HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION)
                                                .message("RequestBody를 형식에 맞추어 주세요.")
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ResDTO<Object>> handleHttpRequestMethodNotSupportedException(Exception e) {
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.HTTP_REQUEST_METHOD_NOT_SUPPORT_EXCEPTION)
                                                .message("엔드포인트가 요청하신 메소드에 대해 지원하지 않습니다. 메소드, 엔드포인트, PathVariable을 확인하세요.")
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ResDTO<Object>> handleMethodArgumentTypeMismatchException(Exception e) {
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION)
                                                .message("PathVariable, QueryString의 타입을 확인하세요.")
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConversionFailedException.class)
        public ResponseEntity<ResDTO<Object>> handleConversionFailedException(Exception e) {
                if (e.getMessage().contains("persistence.Enumerated")) {
                        return new ResponseEntity<>(
                                        ResDTO.builder()
                                                        .code(Constants.ResCode.CONVERSION_FAILED_EXCEPTION)
                                                        .message("status를 정확히 입력해주세요.")
                                                        .build(),
                                        HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.CONVERSION_FAILED_EXCEPTION)
                                                .message("PathVariable, QueryString, ResponseBody를 확인하세요.")
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ResDTO<Object>> handleAuthenticationException(Exception e) {
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.AUTHENTICATION_EXCEPTION)
                                                .message(e.getMessage())
                                                .build(),
                                HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(AuthorityException.class)
        public ResponseEntity<ResDTO<Object>> handleAuthorityException(Exception e) {
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.AUTHORITY_EXCEPTION)
                                                .message("권한이 없습니다.")
                                                .build(),
                                HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ResDTO<Object>> handleException(Exception e) {
                e.printStackTrace();
                // ---
                // TODO: 로깅
                // ---
                return new ResponseEntity<>(
                                ResDTO.builder()
                                                .code(Constants.ResCode.EXCEPTION)
                                                .message(e.getMessage())
                                                .build(),
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

}

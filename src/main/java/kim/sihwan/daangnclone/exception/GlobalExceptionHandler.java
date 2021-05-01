package kim.sihwan.daangnclone.exception;

import kim.sihwan.daangnclone.dto.exception.ErrorResponseDto;
import kim.sihwan.daangnclone.exception.customException.UsernameDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponseDto> badCredentialsException(BadCredentialsException e){
        log.info("BadCredentialsException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.INVALID_LOGIN_INFO.getCode(), ErrorCode.INVALID_LOGIN_INFO.getDescription()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameDuplicatedException.class)
    protected ResponseEntity<ErrorResponseDto> usernameDuplicatedException(UsernameDuplicatedException e){
        log.info("UsernameDuplicatedException"+ e.getMessage());
        return new ResponseEntity<>(errorResponseDto(ErrorCode.DUPLICATED_USERNAME.getCode(),ErrorCode.DUPLICATED_USERNAME.getDescription()),HttpStatus.BAD_REQUEST);
    }
    private ErrorResponseDto errorResponseDto(int code, String message){
        return new ErrorResponseDto(false,code,message);

    }
}

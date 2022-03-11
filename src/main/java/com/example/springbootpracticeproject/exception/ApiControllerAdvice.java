package com.example.springbootpracticeproject.exception;

import com.example.springbootpracticeproject.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
// 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler, @InitBinder 기능을 부여해주는 역할을 한다.
// 즉, 클래스를 분리해서 따로 관리할 수 있다
@RestControllerAdvice(assignableTypes = UserController.class) // 지정하지 않으면 글로벌
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExResolver(HttpServletRequest request,
                                         HttpServletResponse response,
                                         IllegalArgumentException e) {
        log.info("illegalExResolver Start!");
        //System.out.println(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        return new ErrorResult(HttpStatus.BAD_REQUEST,
                e.getMessage());
    }


    //@ExceptionHandler를 사용할 때는 @ResponseStatus는 BasicErrorController로
    // 넘어가게 처리를 하지는 않는다.
    //이유는 @ExceptionHandler가 스프링이 등록해주는 HandlerExceptionResolver 중에서
    // 더 높은 우선순위를 가지고 동작하기 때문이다.
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExResolver(HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      UserException exception) {
        ErrorResult errorResult = new ErrorResult(
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    // messages.properties에서 reason과 동일하게 맵핑되는 값을 찾는다. → 있으면 출력
    // 없으면 error.bad 그 자체를 출력한다.
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "error.bad")
    public class BadRequestException extends RuntimeException{
    }
}

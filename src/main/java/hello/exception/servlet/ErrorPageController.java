package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//error page처리를 위한 controller
@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    //404 error 발생시 해당 page로 이동
    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    //505 error 발생시 해당 page로 이동
    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    /*
        error가 was까지 전달되고, 해당 error에 errorPage가 등록되어 있는 경우 was는 해당 path로 다시 reqeust 요청을 하는데, 이때
        was는 reqeust의 attribute정보에 error에 해당하는 정보를 셋팅해놓는다. 따라서 errorPage를 처리하는 Controller는 해당 request 정보를 통해
        발생했던 error의 정보를 확인할 수 있다.

        http://localhost:8080/error-ex로 접속했을때 확인할 수 있는 errorInfo는 다음과 같다.
        ERROR_EXCEPTION_TYPE: class java.lang.RuntimeException
        ERROR_MESSAGE: Request processing failed; nested exception is java.lang.RuntimeException: 예외 발생
        ERROR_REQUEST_URI: /error-ex
        ERROR_SERVLET_NAME: dispatcherServlet
        ERROR_STATUS_CODE: 500
        dispatchType=ERROR

        http://localhost:8080/error-404로 접속했을때 확인할 수 있는 errorInfo는 다음과 같다.
        ERROR_EXCEPTION: null
        ERROR_EXCEPTION_TYPE: null
        ERROR_MESSAGE: 404 오류!
        ERROR_REQUEST_URI: /error-404
        ERROR_SERVLET_NAME: dispatcherServlet
        ERROR_STATUS_CODE: 404
        dispatchType=ERROR
     */
    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }
}

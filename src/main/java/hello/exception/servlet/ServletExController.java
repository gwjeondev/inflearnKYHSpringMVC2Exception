package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {

    /* 일부러 was(서블릿 컨테이너)까지 예외를 던지고 어떻게 was가 처리하는지 확인해보면 Http 500 Error가 발생한다.
    * 서버내에서 Exception이 터질경우, WAS는 무조건 해당 Error를 Http 500 Error로 취급한다. */
    @GetMapping("/error-ex")
    public void errorEx() {
        log.info("errorEx");
        throw new RuntimeException("예외 발생");
    }

    /* response.sendError()는 예외를 터트렸을 경우와 다르게 Error 코드를 지정할 수 있다.
    * response.sendError()를 호출하면 response 내부에는 오류가 발생했다는 상태를 저장해둔다.
    * 그리고 서블릿 컨테이너는 고객에게 응답 전에 response에 sendError()가 호출되었는지 확인한다.
    * 그리고 호출이 되었다면 설정한 오류 코드에 맞추어 기본 오류 페이지를 보여준다.
    * WAS(sendError 호출 기록 확인) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(response.sendError()) */
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        //기본적으로 error Msg는 default로 숨김처리 되어있으나, 이것을 꺼내서 사용할 수 있는 방법도 있다고 한다.
        response.sendError(404, "404 오류!");
    }
    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}

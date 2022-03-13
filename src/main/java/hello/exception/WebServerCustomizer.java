package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//ErrorPage 등록
@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        //HttpStatus.NOT_FOUND Error가 발생하면 path[/error-page/400]를 호출.
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");

        //HttpStatus.INTERNAL_SERVER_ERROR Error가 발생하면 path[/error-page/500]를 호출.
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        //RuntimeException Error가 발생하면 path[/error-page/500]를 호출.
        //RuntimeException을 지정했지만, RuntimeException 뿐만 아니라 자식 예외까지 모두 포함됨.
        ErrorPage runtimeExErrorPage = new ErrorPage(RuntimeException.class, "/error-page/500");

        //factory에 errorPage 등록.
        factory.addErrorPages(errorPage404, errorPage500, runtimeExErrorPage);
    }
}

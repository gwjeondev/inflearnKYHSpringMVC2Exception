package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//스프링 부트는 아래와 같은 클래스를 생성하고, errorPage를 추가해주지 않아도 기본적으로 error 페이지를 제공한다.
/* ErrorPage 를 자동으로 등록한다. 이때 /error 라는 경로로 기본 오류 페이지를 설정한다.
        new ErrorPage("/error") , 상태코드와 예외를 설정하지 않으면 기본 오류 페이지로 사용된다.
        서블릿 밖으로 예외가 발생하거나, response.sendError(...) 가 호출되면 모든 오류는 /error 를
        호출하게 된다.
        BasicErrorController 라는 스프링 컨트롤러를 자동으로 등록한다.
        ErrorPage 에서 등록한 /error 를 매핑해서 처리하는 컨트롤러다.

        BasicErrorController 는 기본적인 로직이 모두 개발되어 있다.
        개발자는 오류 페이지 화면만 BasicErrorController 가 제공하는 룰과 우선순위에 따라서 등록하면
        된다. 정적 HTML이면 정적 리소스, 뷰 템플릿을 사용해서 동적으로 오류 화면을 만들고 싶으면 뷰 템플릿
        경로에 오류 페이지 파일을 만들어서 넣어두기만 하면 된다.
        뷰 선택 우선순위
        BasicErrorController 의 처리 순서
        1. 뷰 템플릿
            1. resources/templates/error/500.html
            2. resources/templates/error/5xx.html
        2. 정적 리소스( static , public )
            1. resources/static/error/400.html
            2. resources/static/error/404.html
            3. resources/static/error/4xx.html
        3. 적용 대상이 없을 때 뷰 이름( error )
            1. resources/templates/error.html
        */


//ErrorPage 등록
//@Component
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

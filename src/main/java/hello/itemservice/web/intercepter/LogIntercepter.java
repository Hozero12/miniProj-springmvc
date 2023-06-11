package hello.itemservice.web.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *   인터셉터 : 스프링 mvc에서 제공
 *   필터 : 자바에서 제공
 *
 *   필터 - 서블릿 - 인터셈터 - 컨트롤 순서
 */
@Slf4j
public class LogIntercepter implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        StringBuffer requestURI = request.getRequestURL();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;   //호출할 컨트롤러 메서드의 모든 정포가 포함되어 있음
        }

        log.info("1111 request [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("1111 postHandler = [{}]", modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String)request.getAttribute(LOG_ID);
        log.info("1111 request [{}][{}][{}]", uuid, requestURI, handler);

        if (ex != null) {  //에러가 있을경우 발생
            log.error("after Completion error!!", ex);
        }


    }
}

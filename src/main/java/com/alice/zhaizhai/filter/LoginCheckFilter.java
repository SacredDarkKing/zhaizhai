package com.alice.zhaizhai.filter;

import com.alice.zhaizhai.common.R;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年01月17日 14:47
 */
@WebFilter("/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    private final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    String[] uris = new String[]{
            "/employee/login",
            "/backend/**",
            "/front/**",
            "/user/login",
            "/user/sendMsg"
    };

    /**
     * 检查uri是否是白名单中的uri
     *
     * @param uri
     */
    public boolean checkURI(String uri) {
        for (String white : uris) {
            if (PATH_MATCHER.match(white, uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        log.info("正在拦截URI：{}", requestURI);

        if (checkURI(requestURI)) {//uri白名单，无论登录与否，以下请求都放行
            log.info("  放行：白名单{}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        //todo: 1.如果是访问的是管理端，则检查employee属性，决定放行与否


        Object employee = request.getSession().getAttribute("employee");
        if (employee != null) {//登录，直接放行
            log.info("  放行，管理用户{}已登录，", employee);
            filterChain.doFilter(request, response);
            return;
        }

        Object user = request.getSession().getAttribute("user");
        if (user != null) {//登录，直接放行
            log.info("  放行，客户用户{}已登录，", user);
            filterChain.doFilter(request, response);
            return;
        }


        //未登录，也不是白名单，通过response的输出流返回结果
        log.info("  不放行：用户未登录，{}需要拦截", requestURI);
        Gson gson = new Gson();
        String notlogin = gson.toJson(R.error("NOTLOGIN"));
        response.getWriter().write(notlogin);


    }
}

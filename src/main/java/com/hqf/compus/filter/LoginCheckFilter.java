package com.hqf.compus.filter;


import com.alibaba.fastjson.JSON;
import com.hqf.compus.common.BaseContext;
import com.hqf.compus.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截器
 */
@WebFilter(filterName = "LoginCheckFilter" , urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {


    //路径匹配器，支持通配符
    private static final AntPathMatcher Path_Matcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取本次请求的uri
        String requestURI = request.getRequestURI();

        //判断本次请求是否拦截

        String[] uri = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        Boolean check = check(uri, requestURI);

        //无需处理直接放行
        if (check){

            log.info("本次请求 {} 不需要处理"+ request.getRequestURI());
            filterChain.doFilter(request,response);
            return;
        }

        //判断登录状态，如果已登录直接放行。

       if (request.getSession().getAttribute("employee")!=null){

           log.info("用户已登录，用户id为{}"+ request.getSession().getAttribute("employee"));

           long id = Thread.currentThread().getId();
           log.info("当前线程id为 {} ",id);

           Long empid = (Long) request.getSession().getAttribute("employee");
           BaseContext.setCurrentId(empid);

           filterChain.doFilter(request,response);
           return;
       }

       //如果未登录则返回登录结果，通过输出流向客户端返回数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
       return;







    }


    /**
     * 判断是否拦截
     * @param uris
     * @param requestURI
     * @return
     */
    public Boolean check(String[] uris, String requestURI ){
        for (String url : uris) {
            boolean match = Path_Matcher.match(url, requestURI);
            if (match){

                return true;
            }
        }
        return false;
    }
}

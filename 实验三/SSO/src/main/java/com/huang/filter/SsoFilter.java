package com.huang.filter;

/**
 * @author Huang_ruijie
 * @version 1.0
 */
import com.huang.utils.JwtUtil;
import com.huang.utils.UserManager;

import java.io.IOException;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class SsoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * 过滤器
     * @param request 请求
     * @param response 响应
     * @param chain 过滤链
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 检查请求路径，放行登录页面
        String path = httpRequest.getRequestURI();
        if (path.endsWith("/login") || path.endsWith("/sign")) {
            chain.doFilter(request, response);
            return;
        }
        // 检查 Cookie 中是否有 JWT 令牌
        String jwtToken = null;
        boolean isValid = false;
        boolean disabled = false;//没有被禁
        Set<String> disabledUsers = UserManager.getDisabledUser();

        if (httpRequest.getCookies() != null) {
            //先判断Cookie是否过期
            for (Cookie cookie : httpRequest.getCookies()) {
                if("time".equals(cookie.getName())) {
                    isValid = UserManager.checkCookieValidity(cookie);
                }
            }
            //拿到用户名
            String username = "";
            for (Cookie cookie : httpRequest.getCookies()) {
                if("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
            }
            //判断用户是否被禁用
            disabled = disabledUsers.contains(username);//true 为被禁用
            //如果过期或者被禁用，就从在线人员里面删除
            if(!isValid || disabled){
                for (Cookie cookie : httpRequest.getCookies()) {
                    if("username".equals(cookie.getName())) {
                        UserManager.removeUser(cookie.getValue());
                    }
                }
            }
            for (Cookie cookie : httpRequest.getCookies()) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        // 验证 JWT 令牌，并且没有被禁用才可以进入系统
        if (jwtToken != null && JwtUtil.verifyToken(jwtToken) && !disabled && isValid) {
            chain.doFilter(request, response);
        } else {
            String url = "/login";
            if(path.endsWith("/web1")){
                url = "login?target=web1";
            }else if(path.endsWith("/web2")){
                url = "login?target=web2";
            }
            httpResponse.sendRedirect(url);
        }
    }

    @Override
    public void destroy() {
    }
}


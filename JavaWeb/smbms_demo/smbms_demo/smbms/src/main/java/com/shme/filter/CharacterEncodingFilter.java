package com.shme.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: CharacterEncodingFilter
 * @Desc:  字符编码过滤器
 * @package com.shme.filter
 * @project smbms
 * @date 2020/6/26 20:30
 */
public class CharacterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter初始化");
    }

    /**
     * 设置网页字符集编码为 UTF-8
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
//        resp.setContentType("text/html");
        System.out.println("CharacterEncodingFilter已过滤");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("CharacterEncodingFilter已销毁");
    }
}

package com.shme.filter;

import com.shme.pojo.User;
import com.shme.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: SystemFilter
 * @Desc:
 * @package com.shme.filter
 * @project smbms
 * @date 2020/6/26 21:44
 */
public class SystemFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("SystemFilter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);

        System.out.println(user);
        if (user == null){ //用户未登录
            resp.sendRedirect(req.getContextPath()+"/error/403.jsp");
            System.out.println("用户未登录");
        }else{ //用户以登陆
            System.out.println("用户已登录");
            chain.doFilter(req,resp);
        }
        System.out.println("SystemFilter已过滤");
    }

    @Override
    public void destroy() {
        System.out.println("SystemFilter已销毁");
    }
}

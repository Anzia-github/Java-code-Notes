package com.shme.servlet;

import com.shme.pojo.User;
import com.shme.service.UserService;
import com.shme.service.UserServiceImpl;
import com.shme.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: Login
 * @Desc:  用户登陆验证
 * @package com.shme.servlet
 * @project smbms
 * @date 2020/6/26 16:15
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取网页端传入的用户名和信息
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        System.out.println("前端:"+userCode+":"+userPassword);
        //获取数据库的用户信息
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode);
        System.out.println(user);
        System.out.println("数据库:"+user.getUserCode()+":"+user.getUserPassword());

        //判断用户
        if (user.getId() != null) {
            if (userPassword.equals(user.getUserPassword())) {
                //将用户信息添加到Session中
                req.getSession().setAttribute(Constants.USER_SESSION, user);
                resp.sendRedirect("jsp/frame.jsp");
                System.out.println("登陆成功");
            } else {
                req.setAttribute("error", "密码错误");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                System.out.println("密码错误");
            }
        } else {
            req.setAttribute("error", "用户不存在");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            System.out.println("用户不存在");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

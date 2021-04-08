package com.shme.servlet;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.shme.pojo.Role;
import com.shme.pojo.User;
import com.shme.service.RoleService;
import com.shme.service.RoleServiceImpl;
import com.shme.service.UserService;
import com.shme.service.UserServiceImpl;
import com.shme.util.Constants;
import com.shme.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author ShmeBluk
 * @version 1.0
 * @ClassName: UserServlet
 * @Desc: 处理用户的Servlet请求
 * @package com.shme.servlet
 * @project smbms
 * @date 2020/6/27 19:06
 */
public class UserServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && "savepwd".equals(method)) { //更新密码
            updatePassword(req, resp);
        } else if (method != null && "pwdmodify".equals(method)) { //请求匹配验证旧密码
            modifyPassword(req, resp);
        } else if (method !=null && "query".equals(method)){ //用户管理页面查询数据
            query(req, resp);
        }

    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 更新密码
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newPassword = req.getParameter("newpassword");

        boolean flag = false;

        if (o != null && newPassword != null && newPassword.length() != 0) {
            UserService userService = new UserServiceImpl();
            flag = userService.updatePassword(newPassword, ((User) o).getId());
            if (flag) {
                req.setAttribute(Constants.MSG, "密码修改成功,请重新登陆");
                req.getSession().removeAttribute(Constants.USER_SESSION);
                resp.sendRedirect(req.getContextPath() + "/reLogin.jsp");
                System.out.println("密码修改成功");
                return;
            } else {
                req.setAttribute(Constants.MSG, "密码修改失败,请使用原密码登陆");
            }
        } else {
            req.setAttribute(Constants.MSG, "输入的新密码有误");
        }
        req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
        System.out.println("密码修改失败");
    }

    /**
     * 请求匹配验证旧密码
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    private void modifyPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User o = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        System.out.println("Session中的密码:" + o.getUserPassword());
        System.out.println("用户输入的旧密码:" + oldpassword);

        HashMap<String, String> resultMap = new HashMap<>();

        if (o == null) {
            resultMap.put("result", "errorSession");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {
            resultMap.put("result", "error");
        } else {
            if (o.getUserPassword().equals(oldpassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

        //设置返回JSON字符串
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write(JSONArray.toJSONString(resultMap));
        out.flush();
        out.close();
    }

    /**
     * 用户管理页面查询数据
     * @param req
     * @param resp
     */
    private void query(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        //从请求中获取参数
        String queryName = req.getParameter("queryName");
        String pageIndex = req.getParameter("pageIndex");

        String temp = req.getParameter("queryUserRole");
        int queryUserRole = 0; //初始化赋值,避免查询时出现异常

        //页面大小
        int pageSize = Constants.PAGE_SIZE;
        //当前页码
        int currentPageCode = 1;

        //打印输出获取到的参数
        System.out.println("queryName -------->"+queryName);
        System.out.println("queryUserRole -------->"+queryUserRole);
        System.out.println("pageIndex ---------> " + pageIndex);

        //判断是否传入指定用户名
        if(queryName == null){
            //未传入则赋值为空字符串,避免查询报错
            queryName = "";
        }
        //判断是否限定了用户角色条件
        if(temp != null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);
        }
        //判断传入页码是否正确
        if (pageIndex != null){
            try {
                currentPageCode = Integer.parseInt(pageIndex);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                resp.sendRedirect("error.jsp");
            }
        }

        //获取符合条件的用户个数
        UserService userService = new UserServiceImpl();
        int totalDataNumber = userService.getUserCount(queryName, queryUserRole);

        //计算数据显示的总页数
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageCode(currentPageCode);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalDataNumber(totalDataNumber);

        int pageNumber = pageSupport.getPageNumber(); //总页数

        //控制首页和尾页
        if(currentPageCode < 1){ //如果页码小于1,则显示第一页
            currentPageCode = 1;
        }else if(currentPageCode > pageNumber){ //如果页码大于最大页码数.则显示最后一页
            currentPageCode = pageNumber;
        }

        //将查到的用户数据存到Attribute中
        List<User> userList = userService.getUserList(queryName, queryUserRole, currentPageCode, pageSize);
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            System.out.println("用户:"+user);
        }
        req.setAttribute("userList", userList);

        //将查询到的角色数据存到Attribute中
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        for(Role role : roleList){
            System.out.println("用户角色:"+role);
        }
        req.setAttribute("roleList", roleList);

        //将需要的参数存到Attribute中
        req.setAttribute("queryUserName", queryName);
        req.setAttribute("queryUserRole", queryUserRole);
        req.setAttribute("totalPageCount", pageNumber);
        req.setAttribute("totalCount", totalDataNumber);
        req.setAttribute("currentPageNo", currentPageCode);

        //转发:url不变
        req.getRequestDispatcher("userlist.jsp").forward(req, resp);

    }
}

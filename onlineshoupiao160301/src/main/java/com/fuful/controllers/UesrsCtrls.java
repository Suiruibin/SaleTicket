package com.fuful.controllers;
import com.fuful.domain.User;
import com.fuful.service.UsersService;
import com.fuful.utils.CommonsUtils;

import com.fuful.utils.MailUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UesrsCtrls {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/FLoginServlet", method = POST)
    public String test(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        String checkcode = request.getParameter("checkCode");
        String checkcode_session = (String) session.getAttribute("checkcode_session");

        int active=usersService.getState(username);

        if(active>0){
            if (checkcode.equals(checkcode_session)) {
                User msg = usersService.login(username, password);
                if (msg != null) {
                    session.setAttribute("user", msg);
                    return "redirect:/front/index.jsp";
                } else {
                    return "redirect:/front/login.jsp";
                }
            }
            else
            {
                return "redirect:/front/login.jsp";
            }
        }
        else
        {
            return "redirect:/front/login.jsp";
        }





//        User msg = usersService.login(username, password);
//        if (msg != null) {
//            session.setAttribute("user", msg);
//            return "redirect:/front/index.jsp";
//        } else {
//            return "redirect:/front/login.jsp";
//        }

    }

    @RequestMapping(value = "/register", method = POST)
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> res=new HashMap<>();
        //获得表单数据
        Map<String, String[]> properties = request.getParameterMap();
        User user = new User();
        try {
            //自己指定一个类型转换器（将String转成Date）
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class clazz, Object value) {
                    //将string转成date
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = null;
                    try {
                        parse = format.parse(value.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return parse;
                }
            }, Date.class);
            //映射封装
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //private String uid;
        user.setUid(CommonsUtils.getUUID());
        //private String telephone;
        user.setTelephone(null);
        //private int state;//是否激活
        user.setState(0);
        //private String code;//激活码
        String activeCode = CommonsUtils.getUUID();
        user.setCode(activeCode);
        user.setAmount(0);
        user.setAddress(null);


        String checkcode = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        String checkcode_session = (String) session.getAttribute("checkcode_session");

        if (checkcode_session.equals(checkcode)) {


            //将user传递给service层
            User userexist = usersService.JudgeUserExist(user.getUsername());

            if (userexist == null) {

                //是否注册成功
                int isRegisterSuccess = usersService.regist(user);
                System.out.println("isRegisterSuccess="+isRegisterSuccess);
                if (isRegisterSuccess > 0) {
                    //发送激活邮件
                    String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
                            + "<a href='http://localhost:8020/active?activeCode=" + activeCode + "'>"
                            + "http://localhost:8020/active?activeCode=" + activeCode + "</a>";
                    try {
                        MailUtils.sendMail(user.getEmail(), emailMsg);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    response.sendRedirect(request.getContextPath() + "/front/registerSuccess.jsp");
                } else {
                    //跳转到失败的提示页面
                    response.sendRedirect(request.getContextPath() + "/front/registerFail.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/front/register.jsp");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/front/register.jsp");
        }

    }

    @RequestMapping(value = "/active",method = GET)
    public String activeUser(HttpServletRequest request){
        String activecode=request.getParameter("activeCode");
        int active=usersService.updateState(activecode);
        return "redirect:/front/login.jsp";
    }

    @RequestMapping(value = "/UserLogoutServlet",method = GET)
    public void UserLogoutServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("cart");
        // 还要将存储在客户端的cookie删除掉，因为设置了自动登录
        //将存储在客户端的cookie删除掉
        Cookie cookie_username = new Cookie("cookie_username","");
        cookie_username.setMaxAge(0);
        Cookie cookie_password = new Cookie("cookie_password","");
        cookie_password.setMaxAge(0);
        response.addCookie(cookie_username);
        response.addCookie(cookie_password);
        response.sendRedirect(request.getContextPath()+"/front/login.jsp");
    }


    @RequestMapping(value = "/backuser", method = POST)
    public String backuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        String email = request.getParameter("email");

        HttpSession session = request.getSession();

        String checkcode = request.getParameter("checkcode");

        String checkcode_session = (String) session.getAttribute("checkcode_session");

        System.out.println("checocode="+checkcode+"checosession="+checkcode_session);
        if(checkcode.equals(checkcode_session)){
            if (usersService.backuser(username,name,email)) {
                if(usersService.edituser(username,password)){
                    return "redirect:/front/login.jsp";
                }
                else{
                    //Ro ro=new Ro("ERROR","修改失败，请重新输入");
                    System.out.println("找到了3");
                    return "redirect:/front/backuser.jsp";
                }
            }
            else {
                //Ro ro=new Ro("ERROR","修改信息错误，请重新输入");

                return "redirect:/front/backuser.jsp";
            }
        }
        else {
            //Ro ro=new Ro("ERROR","验证码错误，请重新输入");
            return "redirect:/front/backuser.jsp";
        }
    }
}






package com.fuful.controllers.admin;

import com.fuful.domain.Book;
import com.fuful.domain.Mark;
import com.fuful.service.admin.AMarkService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by SunRuiBin on 2019/11/17.
 */
@Controller
public class AMarkCtrls {

    @Autowired
    AMarkService aMarkService;


    @RequestMapping(value = "/MarkListServlet",method = GET)
    public  void MarkListServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int page;
        if(request.getParameter("curPage")==null)
        {
            page=1;
        }
        else
        {
            page=Integer.parseInt(request.getParameter("curPage"));
        }

        PageInfo<Mark> markList =aMarkService.getMarkList(page,7);

        HttpSession session = request.getSession();
        session.setAttribute("splitemarklist", markList.getList());
        session.setAttribute("curPage", markList.getPageNum());
        session.setAttribute("totalPage",markList.getPages());
        session.setAttribute("totalCount",markList.getTotal());

        session.removeAttribute("marksearchflag");
        session.removeAttribute("currentkeyword");





        //重定向是客户端 要加项目名
        response.sendRedirect("/admin/operate-mark.jsp");
    }

    @RequestMapping(value = "/AdminDeleteMark",method = POST)
    public void DeleteMark(HttpServletResponse response,HttpServletRequest request) throws IOException {
        String mid=request.getParameter("mid");

        boolean msg=aMarkService.deletemark(mid);
        String message="";
        if(msg==true)
        {
            message="恭喜您:删除成功!";
        }
        else
        {
            message="恭喜您:删除失败!";
        }

        Gson gson=new Gson();
        String json=gson.toJson(message);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }


    @RequestMapping(value = "/QueryMark",method = POST)
    public void QueryMark(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String markkeyword=request.getParameter("markkeyword");
        System.out.println("markkeyword"+markkeyword);
        if(markkeyword.length()==0)
        {
            markkeyword=(String) request.getSession().getAttribute("currentkeyword");
        }
        int page;
        if(request.getParameter("curPage")==null)
        {
            page=1;
        }
        else
        {
            page=Integer.parseInt(request.getParameter("curPage"));
        }

        PageInfo<Mark> markList =aMarkService.getSearchMarkList(page,7,markkeyword);



        HttpSession session = request.getSession();
        session.setAttribute("splitemarklist",markList.getList());
        session.setAttribute("curPage", markList.getPageNum());
        session.setAttribute("totalPage", markList.getPages());
        session.setAttribute("totalCount", markList.getTotal());
        session.setAttribute("marksearchflag", "true");
        session.setAttribute("currentkeyword", markkeyword);


        System.out.println("投票搜索结果。。。。。");

        System.out.println("评论搜索。。。。。。");
        String message="success";
        Gson gson=new Gson();
        String json=gson.toJson(message);

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(json);

    }

}

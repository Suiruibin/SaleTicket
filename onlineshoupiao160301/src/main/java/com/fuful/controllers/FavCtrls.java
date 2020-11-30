package com.fuful.controllers;

import com.fuful.domain.Book;
import com.fuful.domain.BookLove;
import com.fuful.domain.Ticket;
import com.fuful.domain.User;
import com.fuful.service.TicketService;
import com.fuful.service.FavService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by SunRuiBin on 2019/11/16.
 */
@Controller
public class FavCtrls {

    @Autowired
    FavService favService;
    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/MyLoveListServlet",method = GET)
    public void favget(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();

        User user=(User)session.getAttribute("user");

        if(user==null)
        {
            response.sendRedirect("/front/login.jsp");
            return ;
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
        PageInfo<BookLove> loveList=favService.getTicketLoveList(page,7,user.getUid());

        List<BookLove> lovelist=loveList.getList();
        List<BookLove> new_lovelist=new ArrayList<>();

        for(BookLove p: lovelist){
            Ticket b= ticketService.findTicketInfoById(p.getPid());
            p.setBookInfo(b);
            new_lovelist.add(p);
        }
        session.setAttribute("spliteMyLovelist", new_lovelist);
        session.setAttribute("curPage", loveList.getPageNum());
        session.setAttribute("totalPage", loveList.getPages());
        session.setAttribute("totalCount", loveList.getTotal());

        response.sendRedirect("/front/my_love.jsp");
    }

    @RequestMapping(value = "/DeleteoffLove",method = GET)
    public void deletelove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");

        String pid=request.getParameter("pid");


        favService.updateState(user.getUid(), pid, 0);

        request.getRequestDispatcher("/MyLoveListServlet").forward(request, response);

    }

    @RequestMapping(value ="/AddBookToLove",method = POST)
    public void addBookLove(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();

        User user=(User)session.getAttribute("user");

        if(user==null)
        {
            response.sendRedirect(request.getContextPath()+"/front/index.jsp");
            return;
        }
//	获得图书的ID
        String pid=request.getParameter("pid");
//		图书表收藏数+1;
        String message="";
//		先判断数据库里有没有数据
        BookLove msg=favService.queryState(user.getUid(),pid);

        if(msg!=null)
        {
//			存在数据

//			查询用户与图书之间的收藏关系
//			查询用户与图书之间的收藏关系
//			0未收藏 1收藏
            if(msg.getState()==0)
            {
                message="收藏";
//				更新收藏状态

                boolean msgg=favService.updateState(user.getUid(),pid,1);
            }
            else if(msg.getState()==1)
            {
                message="取消收藏";
                boolean msgg=favService.updateState(user.getUid(),pid,0);
            }


        }
        else
        {
            message="收藏";
            boolean msggeeee=favService.addLove(user.getUid(), pid);
            System.out.println("msggeee"+msggeeee);
        }

        Gson gson=new Gson();
        String json=gson.toJson(message);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }
}

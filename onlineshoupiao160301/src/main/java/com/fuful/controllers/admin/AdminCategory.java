package com.fuful.controllers.admin;

import com.fuful.domain.BookSubType;
import com.fuful.domain.BookSuperType;
import com.fuful.domain.Ticket;
import com.fuful.service.TicketService;
import com.fuful.service.admin.AdminCateGoryService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by SunRuiBin on 2019/12/9.
 */
@Controller
public class AdminCategory {

    @Autowired
    AdminCateGoryService aCateGoryService;

    @Autowired
    TicketService ticketService;
    @RequestMapping(value = "/getCategoryType.do",method = GET)
    public void getCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("...点击栏目了");
        int page = Integer.parseInt(request.getParameter("curPage"));


        List<BookSuperType> bookSuperTypeList= ticketService.findAllSuperType();
        PageInfo<BookSuperType> list= aCateGoryService.getCateGory(page,6);


        HttpSession session = request.getSession();
        session.setAttribute("splitecategoryplist", list.getList());
        session.setAttribute("curPage",list.getPageNum());
        session.setAttribute("totalPage", list.getPages());
        session.setAttribute("totalCount", list.getTotal());
        session.setAttribute("categoryplist", bookSuperTypeList);
//		request.getRequestDispatcher("/admin/category.jsp").forward(request, response);

        //重定向是客户端 要加项目名
        response.sendRedirect("/admin/category.jsp");
    }

    @RequestMapping(value = "/AdminAddCategoryServlet",method = POST)
    public void AdminAddCategoryServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String name=request.getParameter("name");
        String alias=request.getParameter("alias");
        String keywords=request.getParameter("keywords");
        String describe=request.getParameter("describe");
        String message="";
        if(name==null||name.equals(""))
        {
            message="栏目名称不能为空!";
        }
        else
        {



                BookSuperType p=new BookSuperType();

                p.setTname(name);
                p.setAname(alias);
                p.setKeyword(keywords);
                p.setSupdesc(describe);


                if(aCateGoryService.querySuperTypeExistByName(name))
                {
                    message="父栏目添加失败!请检查输入信息";
                }
                else
                {

                    boolean msg=aCateGoryService.saveCategorySuperType(p);
                    if(msg==true)
                    {
                        message="恭喜您:父栏目添加成功!";
                    }
                    else
                    {
                        message="父栏目添加失败!请检查输入信息";
                    }
                    System.out.println(msg);
                }


        }

        Gson gson=new Gson();
        String json=gson.toJson(message);
        System.out.println(json);
        response.setContentType("text/html;charset=UTF-8");


        response.getWriter().write(json);

    }

    @RequestMapping(value = "/categorytypedetail.do",method = GET)
    public void  operatesubtype(HttpServletRequest request,HttpServletResponse response) throws IOException {

        int Id=Integer.parseInt(request.getParameter("ID"));


        System.out.println(Id);

        HttpSession session=request.getSession();


        BookSuperType updatesupertypedetail=null;
        List<BookSubType> updatesubtypedetail=null;



        updatesupertypedetail=(BookSuperType)aCateGoryService.findSuperTypeDetailByid(Id);

        session.setAttribute("updatesupertypedetail", updatesupertypedetail);
        session.setAttribute("updateById", Id);
        response.sendRedirect("/admin/update-category.jsp");


    }

    @RequestMapping(value = "/UpdateCategoryDetail",method = POST)
    public void UpdateCategoryDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int Id=Integer.parseInt(request.getParameter("ID"));
        String name=request.getParameter("name");
        String alias=request.getParameter("alias");
        int  fid=Integer.parseInt(request.getParameter("fid"));
        String keywords=request.getParameter("keywords");
        String describe=request.getParameter("describe");


        System.out.println(Id+" "+name+" "+alias+" "+fid+" "+keywords+" "+describe);
//		创建栏目增删改查对象
        String message="";


        if(name==null||name.equals(""))
        {
            message="栏目名称不能为空!";
        }
        else
        {
            BookSuperType p=new BookSuperType();
            p.setTname(name);
            p.setAname(alias);
            p.setKeyword(keywords);
            p.setSupdesc(describe);
            p.setID(Id);
            boolean msg=aCateGoryService.updateSuperType(p);

            if(msg==true)
            {

                message="恭喜您:父栏目更新成功!";
            }
            else
            {
                message="父栏目更新失败!请检查输入信息";
            }
            System.out.println(msg);


        }

        Gson gson=new Gson();
        String json=gson.toJson(message);
        System.out.println(json);
        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(json);

    }

    @RequestMapping(value = "/deleteSuperType.do",method = POST)
    public void deleteSuperType(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int id=Integer.parseInt(request.getParameter("ID"));
        System.out.println("delete want to is:"+id);



        boolean msg_supertype=aCateGoryService.deleteSuperTypeById(id);

        String message="";

        if(msg_supertype==true)
        {
            message="删除成功!";
        }
        else
        {
            message="删除失败,请重试!";
        }
        Gson gson=new Gson();
        String json=gson.toJson(message);


        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(json);
    }
}

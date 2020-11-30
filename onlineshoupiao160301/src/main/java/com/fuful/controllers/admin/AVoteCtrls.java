package com.fuful.controllers.admin;

import com.fuful.domain.Book;
import com.fuful.domain.Vote;
import com.fuful.domain.VoteCount;
import com.fuful.service.VoteService;
import com.fuful.service.admin.ATicketService;
import com.fuful.service.admin.AVoteService;
import com.fuful.utils.CommonsUtils;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by SunRuiBin on 2019/11/18.
 */
@Controller
public class AVoteCtrls {

    @Autowired
    AVoteService aVoteService;

    @Autowired
    VoteService voteService;

    @RequestMapping(value = "/InitVoteListServlet",method = GET)
    public void InitVoteListServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int page;
        if(request.getParameter("curPage")==null)
        {
            page=1;
        }
        else
        {
            page=Integer.parseInt(request.getParameter("curPage"));
        }
        PageInfo<Vote> voteList =aVoteService.getVoteList(page,7);
        HttpSession session = request.getSession();
        session.setAttribute("splitevotelist", voteList.getList());
        session.setAttribute("curPage",voteList.getPageNum());
        session.setAttribute("totalPage",voteList.getPages());
        session.setAttribute("totalCount", voteList.getTotal());
        session.removeAttribute("updateflag");
        session.removeAttribute("votesearchflag");
        session.removeAttribute("currentkeyword");





        //重定向是客户端 要加项目名
        response.sendRedirect("/admin/operate-vote.jsp");
    }

    @RequestMapping(value = "/AddVoteServlet",method = POST)
    public String AddVoteServlet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String votename=request.getParameter("votename");
        String stime=request.getParameter("stime");
        String etime=request.getParameter("etime");

        Vote vote=new Vote();

        vote.setVid(CommonsUtils.getUUID());

        vote.setStime(stime);
        vote.setEtime(etime);
        vote.setVname(votename);
        vote.setState(0);
        vote.setUsercount(0);
        int items = Integer.parseInt(request.getParameter("items"));
        System.out.println(items);
        for (int i = 1; i <= items; i++) {
            String name = request.getParameter("item"+i);
            String vname="item"+i;
            System.out.println(vname);
            if(vname.equals("item1"))
            {
                vote.setItem1(name);
            }
            else if(vname.equals("item2"))
            {
                vote.setItem2(name);
            }
            else if(vname.equals("item3"))
            {
                vote.setItem3(name);
            }
            else if(vname.equals("item4"))
            {
                vote.setItem4(name);
            }
            else if(vname.equals("item5"))
            {
                vote.setItem5(name);
            }
            else if(vname.equals("item6"))
            {
                vote.setItem6(name);
            }
            else if(vname.equals("item7"))
            {
                vote.setItem7(name);
            }
            else if(vname.equals("item8"))
            {
                vote.setItem8(name);
            }
            System.out.println("item" + i + name);

        }
//		保存投票信息
//        boolean judge=vote.queryExistName(votename);
        boolean judge=false;

        if(judge)
        {
            request.getRequestDispatcher("/InitVoteListServlet").forward(request, response);
//            return;
        }
        else
        {

            boolean msg=aVoteService.saveVote(vote);
            System.out.println("投票保存成功");
//		保存投票的统计信息

            VoteCount votecount=new VoteCount();
            votecount.setVid(vote.getVid());
            votecount.setItem1(0);
            votecount.setItem2(0);
            votecount.setItem3(0);
            votecount.setItem4(0);
            votecount.setItem5(0);
            votecount.setItem6(0);
            votecount.setItem7(0);
            votecount.setItem8(0);

            //判断是否有相同的数据
            boolean msgcount=aVoteService.saveVoteCount(votecount);


        }

        HttpSession session=request.getSession();


//		开始保存信息用于显示
//        request.getRequestDispatcher("/InitVoteListServlet").forward(request, response);
        return "redirect:/InitVoteListServlet";
    }

    @RequestMapping(value = "/chart",method = GET)
    public void chart(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String vid=request.getParameter("vid");
        String vname=request.getParameter("vname");

        HttpSession session=request.getSession();

        session.setAttribute("vid", vid);
        session.setAttribute("vname", vname);

        String [] name= {"法国","德国","克罗地亚","法国"};
        String [] value= {"1","2","3","4"};

        session.setAttribute("name", name);
        session.setAttribute("value", value);
        response.sendRedirect("/admin/chart.jsp");

    }

    @RequestMapping(value = "/ChartDataGetServlet",method = POST)
    public void ChartDataGetServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        session.removeAttribute("updateflag");
        String vid=request.getParameter("vid");
        String vname=request.getParameter("vname");
        System.out.println("vid;;;;;;"+vid+" vname::::"+vname);

//

        List<Map<String,Object>> votechartlist=new ArrayList();

//		现根据VID查找每个选项的选择数目



        VoteCount  votecount=voteService.findVoteCountByVid(vid);
        System.out.println("votecount.item1"+votecount.getItem1());

//		保存用于修改的投票项名称  显示在修改页面上
        List<String> voteitemdetail=new ArrayList();
        int voteitemlen=0;
        Vote vote=voteService.findVoteItemsByVid(vid);

        if(vote.getItem1()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem1());
            map.put("itemvalue", votecount.getItem1());
            System.out.println("itemname"+vote.getItem1()+"  "+"itemvalue"+votecount.getItem1());
            voteitemdetail.add(vote.getItem1());
            voteitemlen++;
            votechartlist.add(map);
        }
        if(vote.getItem2()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem2());
            map.put("itemvalue", votecount.getItem2());
            System.out.println("itemname"+vote.getItem2()+"  "+"itemvalue"+votecount.getItem2());
            voteitemdetail.add(vote.getItem2());
            voteitemlen++;
            votechartlist.add(map);
        }
        if(vote.getItem3()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem3());
            map.put("itemvalue", votecount.getItem3());
            System.out.println("itemname"+vote.getItem3()+"  "+"itemvalue"+votecount.getItem3());
            voteitemdetail.add(vote.getItem3());
            voteitemlen++;
            votechartlist.add(map);
        }
        if(vote.getItem4()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem4());
            map.put("itemvalue", votecount.getItem4());
            System.out.println("itemname"+vote.getItem4()+"  "+"itemvalue"+votecount.getItem4());
            voteitemdetail.add(vote.getItem4());
            voteitemlen++;
            votechartlist.add(map);
        }
        if(vote.getItem5()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem5());
            map.put("itemvalue", votecount.getItem5());
            System.out.println("itemname"+vote.getItem5()+"  "+"itemvalue"+votecount.getItem5());
            voteitemdetail.add(vote.getItem5());
            voteitemlen++;
            votechartlist.add(map);
        }
        if(vote.getItem6()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem6());
            map.put("itemvalue", votecount.getItem6());
            System.out.println("itemname"+vote.getItem6()+"  "+"itemvalue"+votecount.getItem6());
            voteitemdetail.add(vote.getItem6());
            voteitemlen++;
            votechartlist.add(map);
        }

        if(vote.getItem7()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem7());
            map.put("itemvalue", votecount.getItem7());
            System.out.println("itemname"+vote.getItem7()+"  "+"itemvalue"+votecount.getItem7());
            voteitemdetail.add(vote.getItem7());
            voteitemlen++;
            votechartlist.add(map);
        }

        if(vote.getItem8()!=null)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemname", vote.getItem8());
            map.put("itemvalue", votecount.getItem8());
            System.out.println("itemname"+vote.getItem8()+"  "+"itemvalue"+votecount.getItem8());
            voteitemdetail.add(vote.getItem8());
            voteitemlen++;
            votechartlist.add(map);
        }

        System.out.println("votechartlist:::"+votechartlist);
//		再根据是否不为空再选择名字



        session.setAttribute("voteitemdetail", voteitemdetail);
        session.setAttribute("voteitemlen", voteitemlen);
        Gson gson=new Gson();
        String json=gson.toJson(votechartlist);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);

//		组成JSON数据 [{name:xxx,value:xxx}]
    }



    @RequestMapping(value = "/QueryVote",method = POST)
    public void QueryVote(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String votekeyword=request.getParameter("votekeyword");
        if(votekeyword.length()==0)
        {
            votekeyword=(String) request.getSession().getAttribute("currentkeyword");
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



        PageInfo<Vote> voteList =aVoteService.getSearchVoteList(page,7,votekeyword);

        HttpSession session = request.getSession();
        session.setAttribute("splitevotelist", voteList.getList());
        session.setAttribute("curPage", voteList.getPageNum());
        session.setAttribute("totalPage", voteList.getPages());
        session.setAttribute("totalCount", voteList.getTotal());
        session.setAttribute("votesearchflag", "true");
        session.setAttribute("currentkeyword", votekeyword);
        session.removeAttribute("updateflag");

        System.out.println("投票搜索结果。。。。。");


        String message="success";
        Gson gson=new Gson();
        String json=gson.toJson(message);

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(json);

    }


    @RequestMapping(value = "/DeleVoteServlet",method = POST)
    public void DeleVoteServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String vid=request.getParameter("vid");

        System.out.println("删除页面的vid："+vid);



        boolean msg=aVoteService.deleteVoteByVid(vid);
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

    @RequestMapping(value = "/InitUpdateServlet",method = POST)
    public void InitUpdateServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String vid=request.getParameter("vid");

        System.out.println("初始化更新时的vid::"+vid);


        String vname=request.getParameter("vname");
        System.out.println("初始化更新时的vname::"+vname);



        List<String> voteitemdetail=new ArrayList();
        int voteitemlen=0;
        Vote vote=voteService.findVoteItemsByVid(vid);

        if(vote.getItem1()!=null)
        {

            voteitemdetail.add(vote.getItem1());
            voteitemlen++;

        }
        if(vote.getItem2()!=null)
        {

            voteitemdetail.add(vote.getItem2());
            voteitemlen++;

        }
        if(vote.getItem3()!=null)
        {

            voteitemdetail.add(vote.getItem3());
            voteitemlen++;

        }
        if(vote.getItem4()!=null)
        {

            voteitemdetail.add(vote.getItem4());
            voteitemlen++;

        }
        if(vote.getItem5()!=null)
        {

            voteitemdetail.add(vote.getItem5());
            voteitemlen++;

        }
        if(vote.getItem6()!=null)
        {

            voteitemdetail.add(vote.getItem6());
            voteitemlen++;

        }

        if(vote.getItem7()!=null)
        {

            voteitemdetail.add(vote.getItem7());
            voteitemlen++;

        }

        if(vote.getItem8()!=null)
        {


            voteitemdetail.add(vote.getItem8());
            voteitemlen++;

        }



        HttpSession session=request.getSession();
        session.setAttribute("voteitemstime", vote.getStime());
        System.out.println("voteitemstime::"+vote.getStime());
        session.setAttribute("voteitemetime", vote.getEtime());
        session.setAttribute("voteitemestate", vote.getState());
        session.setAttribute("voteitemdetail", voteitemdetail);
        session.setAttribute("voteitemvid", vid);
        session.setAttribute("voteitemvname", vname);
        session.setAttribute("voteitemlen", voteitemlen);
        session.setAttribute("updateflag", "true");
//		清除警示框更新成功时
        session.removeAttribute("updateIsSuccessFlag");

        Gson gson=new Gson();
        String json=gson.toJson("succsess");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
//		组成JSON数据 [{name:xxx,value:xxx}]
    }

    @RequestMapping(value = "/UpdateVoteServlet",method = POST)
    public String UpdateVoteServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String votename = request.getParameter("votename");
        String stime = request.getParameter("stime");
        String etime = request.getParameter("etime");


        String vid = request.getParameter("vid");
        String state = request.getParameter("state");
        int votelen = Integer.parseInt(request.getParameter("votelen"));

        Vote vote = new Vote();


        vote.setVid(vid);
        vote.setStime(stime);
        vote.setEtime(etime);
        vote.setVname(votename);
        vote.setState(Integer.parseInt(state));

//		int items = Integer.parseInt(request.getParameter("voteitems"));
        System.out.println("votelen" + votelen);
        for (int i = 1; i <= votelen; i++) {
            String name = request.getParameter("item" + i);
            String vname = "item" + i;
            System.out.println(vname);
            if (vname.equals("item1")) {
                vote.setItem1(name);
                System.out.println(name);
            } else if (vname.equals("item2")) {
                vote.setItem2(name);
                System.out.println(name);
            } else if (vname.equals("item3")) {
                vote.setItem3(name);
                System.out.println(name);
            } else if (vname.equals("item4")) {
                vote.setItem4(name);
                System.out.println(name);
            } else if (vname.equals("item5")) {
                vote.setItem5(name);
                System.out.println(name);
            } else if (vname.equals("item6")) {
                vote.setItem6(name);
                System.out.println(name);
            } else if (vname.equals("item7")) {
                vote.setItem7(name);
                System.out.println(name);
            } else if (vname.equals("item8")) {
                vote.setItem8(name);
                System.out.println(name);
            }
            System.out.println("updateitem" + i + name);

        }
//		保存投票信息


        boolean msg = aVoteService.updateVoteByVid(vote);


        HttpSession session = request.getSession();


//		用于更新完成之后谈个警示框


//		开始保存信息用于显示

        Gson gson = new Gson();
        String json = gson.toJson("success");
        response.getWriter().write(json);
        return "redirect:/InitVoteListServlet";
    }

}

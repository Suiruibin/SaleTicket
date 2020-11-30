package com.fuful.controllers;

import com.fuful.domain.*;
import com.fuful.service.VoteService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by SunRuiBin on 2019/11/16.
 */
@Controller
public class VoteCtrls {
    @Autowired
    VoteService voteService;


    @RequestMapping(value = "/FVoteListIndexServlet",method = GET)
    public void fvoteList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        if(session.getAttribute("user")==null)
        {
            response.sendRedirect(request.getContextPath()+"/front/login.jsp");
            return;
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
//        FSpliteVoteList sp=new FSpliteVoteList();
        List<Vote> voteList=voteService.getVoteList();
        Vote vote=voteList.get(page-1);

//		保存每个的详细信息
        int voteitemlen=0;
        List<String> voteitemdetail=new ArrayList<String>();
        System.out.println("VOTE="+vote);
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
        System.out.println("voteitemdetail:::"+voteitemdetail);
        System.out.println("voteitemlen:::"+voteitemlen);





//		前台投票列表
        session.setAttribute("spliteVoteplist", vote);
        session.setAttribute("curPage",page);
        session.setAttribute("totalPage", voteList.size());
        session.setAttribute("totalCount", voteList.size());


        session.setAttribute("voteitemstime", vote.getStime());
        session.setAttribute("voteitemetime", vote.getEtime());
        session.setAttribute("voteitemdetail", voteitemdetail);

        session.setAttribute("voteitemlen", voteitemlen);

        response.sendRedirect("/front/vote_list.jsp");
    }



    @RequestMapping(value = "/AddClickVote",method = POST)
    public void AddClickVote(HttpServletResponse response,HttpServletRequest request) throws IOException {
        String itemname=request.getParameter("itemname");
        String vid=request.getParameter("vid");
//		 查询投票是否过期
        Vote  vote=voteService.findVoteItemsByVid(vid);
        String endtime=vote.getEtime();

        String newendtime=endtime.replace("T", " ");
        System.out.println("newendtime:"+newendtime);

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate=format.format(new Date());
        System.out.println("nowtime:"+currentDate);

        int i=currentDate.compareTo(newendtime);
        String message="";
        if(i>0)
        {
            message="该投票已经过期,请选择其他投票项目!";
        }
        else
        {
//		查询用户是否已经投票了

            VoteUserlog voteuserlog=new VoteUserlog();
            User user=(User)request.getSession().getAttribute("user");
            voteuserlog.setUser(user.getUid());
            voteuserlog.setVid(vid);

            boolean msge=voteService.findUserHasVote(voteuserlog);



            if(msge)
            {

                message="您已经投过票了!请勿重复投票!";

            }
            else
            {


                VoteCount votecount=voteService.findVoteCountByVid(vid);

//		新投票数
                int newclick=0;


                boolean msg = false;


                if(itemname.equals("item1"))
                {
                    newclick=votecount.getItem1()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);

                }
                if(itemname.equals("item2"))
                {
                    newclick=votecount.getItem2()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);
                }
                if(itemname.equals("item3"))
                {
                    newclick=votecount.getItem3()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);
                }
                if(itemname.equals("item4"))
                {
                    newclick=votecount.getItem4()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);
                }
                if(itemname.equals("item5"))
                {
                    newclick=votecount.getItem5()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);
                }
                if(itemname.equals("item6"))
                {
                    newclick=votecount.getItem6()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);
                }
                if(itemname.equals("item7"))
                {
                    newclick=votecount.getItem7()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);
                }
                if(itemname.equals("item8"))
                {
                    newclick=votecount.getItem8()+1;
                    msg=voteService.updateVotecountByVid1(vid,itemname,newclick);
                }
//		更新投票信息


//		更新投票的参与人数

                Vote  voteitem=voteService.findVoteItemsByVid(vid);
                int newusercount=voteitem.getUsercount()+1;

                int res=voteService.updateVoteUsercount(newusercount,vid);


//		更新投票的用户防止他再次投票

                VoteUserlog votelog=new VoteUserlog();
                voteuserlog.setUser(user.getUid());
                voteuserlog.setVid(vid);

                int msgee=voteService.addUserHasVote(voteuserlog);



                if(msgee>0)
                {
                    message="投票成功!感谢您的参与";
                }
                else
                {
                    message="投票失败!请稍后再试";
                }

                System.out.println(message);

            }
        }
        Gson gson=new Gson();
        String json=gson.toJson(message);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }


}

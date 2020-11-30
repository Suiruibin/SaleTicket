package com.fuful.service;

import com.fuful.dao.VoteDao;
import com.fuful.domain.Mark;
import com.fuful.domain.Vote;
import com.fuful.domain.VoteCount;
import com.fuful.domain.VoteUserlog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SunRuiBin on 2019/11/16.
 */
@Service
public class VoteService {

    @Autowired
    VoteDao voteDao;

    public List<Vote> getVoteList(){
        List<Vote>  voteList = voteDao.findVoteList();
        return voteList;
    }
    public Vote findVoteItemsByVid(String vid) {
        // TODO Auto-generated method stub

        Vote p = voteDao.findVoteByVid(vid);
//        try {
//            PreparedStatement ps = conn.prepareStatement("select * from tb_vote  where vid=? ");

        return p;
    }

    public boolean findUserHasVote(VoteUserlog voteuserlog) {
        // TODO Auto-generated method stub

        VoteUserlog plist =voteDao.findUserVoteLog(voteuserlog.getUser(),voteuserlog.getVid());
//        try {
//            PreparedStatement ps = conn.prepareStatement("select * from tb_voteuserlog where user=? and vid=? ");
//            ps.setString(1, voteuserlog.getUser());
//            ps.setString(2, voteuserlog.getVid());
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                return true;
//            }
//
//            JNDISessionFactory.close(rs, ps, conn);
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        if(plist!=null) {
            return true;
        }else {
            return false;
        }

    }
    public VoteCount findVoteCountByVid(String vid) {
        // TODO Auto-generated method stub
        VoteCount  p = voteDao.findVoteCount(vid);
//        try {
//            PreparedStatement ps = conn.prepareStatement("select * from tb_votecount where vid=?  ");
        return p;
    }

    public boolean updateVotecountByVid1(String vid,String item,int newcount) {
        // TODO Auto-generated method stub
        boolean res=false;
        if(item.equals("item1")){
            res=voteDao.updateVoteCount1(newcount,vid,item);
           
        }
        else if(item.equals("item2")){
            res=voteDao.updateVoteCount2(newcount,vid,item);

        }else if(item.equals("item3")){
            res=voteDao.updateVoteCount3(newcount,vid,item);

        }
        else if(item.equals("item4")){
            res=voteDao.updateVoteCount4(newcount,vid,item);

        }
        else if(item.equals("item5")){
            res=voteDao.updateVoteCount5(newcount,vid,item);

        }
        else if(item.equals("item6")){
            res=voteDao.updateVoteCount6(newcount,vid,item);

        }
        else if(item.equals("item7")){
            res=voteDao.updateVoteCount7(newcount,vid,item);

        }

//        try {
//            PreparedStatement ps = conn.prepareStatement("update  tb_votecount set item1=? where vid=?");
//
        return res;

    }

    public int updateVoteUsercount(int newusercount, String vid) {

       int res=voteDao.UpdateVoteUserCount(newusercount,vid);

//        try {
//            PreparedStatement ps = conn.prepareStatement("update  tb_vote set usercount=? where vid=?");
//            ps.setInt(1, newusercount);
//            ps.setString(2, vid);
//
//            int i=ps.executeUpdate();
//
//            if(i>0)
//            {
//                System.out.println("用户投票参与数量已经更新");
//            }
//
//            JNDISessionFactory.close( ps, conn);
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return res;
    }

    public int addUserHasVote(VoteUserlog voteuserlog) {
        // TODO Auto-generated method stub

        int res=voteDao.AddUserHasVote(voteuserlog.getUser(),voteuserlog.getVid());
//        List<Vote> plist = new ArrayList();
//        try {
//            PreparedStatement ps = conn.prepareStatement("insert into   tb_voteuserlog  values(?,?,?) ");
//            ps.setString(1, voteuserlog.getUser());
//            ps.setString(2, voteuserlog.getVid());
//            ps.setInt(3,1);
//
//            int i=ps.executeUpdate();
//            if(i>0)
//            {
//                return true;
//            }
//            JNDISessionFactory.close( ps, conn);
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return res;

    }


}

package com.fuful.service.admin;

import com.fuful.dao.admin.AVoteDao;
import com.fuful.domain.Vote;
import com.fuful.domain.VoteCount;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by SunRuiBin on 2019/11/18.
 */
@Service
public class AVoteService {
    @Autowired
    AVoteDao aVoteDao;
    public PageInfo<Vote> getVoteList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Vote> ticketList = aVoteDao.findAllVote();
        PageInfo<Vote> data = new PageInfo<>(ticketList);
        return data;
    }

    public PageInfo<Vote> getSearchVoteList(int pageNum, int pageSize,String word){
        PageHelper.startPage(pageNum,pageSize);
        List<Vote> ticketList = aVoteDao.findSearchAllVote(word);
        PageInfo<Vote> data = new PageInfo<>(ticketList);
        return data;
    }

    public boolean  saveVote(Vote vote) {
        // TODO Auto-generated method stub
        int res=aVoteDao.addVote(vote);
        if(res>0) return true;
        else
            return false;
    }


    public boolean saveVoteCount(VoteCount votecount) {
        int res=aVoteDao.InsertVoteCount(votecount);
        if(res>0){
            return true;
        }
        else
        {
            return false;
        }
    }


    public boolean deleteVoteByVid(String vid){
        boolean res=aVoteDao.deleteVoteByVid(vid);
        return res;
    }

    public boolean updateVoteByVid(Vote vote) {
        // TODO Auto-generated method stub
     int  res=aVoteDao.updateVote(vote);

     if(res>0)
        return true;
     else
         return false;
    }

}

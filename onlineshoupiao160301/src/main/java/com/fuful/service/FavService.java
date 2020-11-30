package com.fuful.service;

import com.fuful.dao.FavDao;
import com.fuful.domain.BookLove;
import com.fuful.domain.Mark;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by SunRuiBin on 2019/11/16.
 */
@Service
public class FavService {
    @Autowired
    FavDao favDao;

    public PageInfo<BookLove> getTicketLoveList(int pageNum, int pageSize, String  uid){
        PageHelper.startPage(pageNum,pageSize);
        List<BookLove> loveList = favDao.findMyLoveList(uid);
        PageInfo<BookLove> data = new PageInfo<>(loveList);
        return data;
    }

    public BookLove queryState(String uid, String pid){
        BookLove p = favDao.getBookLove(uid,Integer.parseInt(pid));
        return p;
    }

    public boolean updateState(String uid, String pid, int state) {
        // TODO Auto-generated method stub
        boolean res=favDao.UpdateFavState(uid,Integer.parseInt(pid),state);
        return res;
    }


    public boolean addLove(String uid, String pid) {
        // TODO Auto-generated method stub

        int p = favDao.addNewFav(uid,Integer.parseInt(pid));
//        try {
//            PreparedStatement ps = conn.prepareStatement("insert into  tb_love  values(?,?,?) ");
        if(p>0){
            return true;
        }
        else {
            return false;
        }
    }

}

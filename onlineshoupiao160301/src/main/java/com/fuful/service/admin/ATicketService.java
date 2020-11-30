package com.fuful.service.admin;

import com.fuful.dao.admin.ATicketDao;
import com.fuful.domain.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by SunRuiBin on 2019/11/17.
 */
@Service
public class ATicketService {
    @Autowired
    ATicketDao ticketDao;

    public boolean deleteTicketPrice(int tid,double price){
        boolean res=ticketDao.DeleteTicketPrice(tid,price);
        return res;
    }
    public boolean deleteRound(int id){
        boolean res=ticketDao.DeleteRound(id);
        return res;
    }
    public boolean deleteTicketPlace(String tid,String place){
        boolean res=ticketDao.DeleteTicketPlace(tid,place);
        return res;
    }

    public List<Price> getPriceList(String tid){
        List<Price> list=ticketDao.findAllPriceByTid(tid);
        return list;
    }

    public List<Round> getRoundList(String tid){
        List<Round> list=ticketDao.findAllRoundByTid(tid);
        return list;
    }

    public List<City> getPlaceList(String tid){
        List<City> list=ticketDao.findAllPlaceByTid(tid);
        return list;
    }
    public int addTicketPrice(String tid,Double price){
        int res=ticketDao.AddTicketPrice(tid,price);
        return res;
    }

    public int addPlaceTime(String cityId,String  time,String tid,String amount,String row,String col){
        int res=ticketDao.AddPlaceTime(cityId,time,tid,Integer.parseInt(amount),Integer.parseInt(row),Integer.parseInt(col));
        return res;
    }
    public int addTicketPlace(String tid,String  place,String location,String town){
        int res=ticketDao.AddTicketPlace(tid,place,location,town);
        return res;
    }

    public Ticket findTicketInfoById(int id) {
//		// TODO Auto-generated method stub
        Ticket p = ticketDao.getTicketInfoById(id);
        return p;

    }
    public List<Ticket> getAllTicket(){
        List<Ticket> tickerList=ticketDao.findAllTicket();
        return tickerList;
    }
    public PageInfo<Ticket> getTicketList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Ticket> ticketList = ticketDao.findAllTicket();
        PageInfo<Ticket> data = new PageInfo<>(ticketList);
        return data;
    }
    public PageInfo<Ticket> getSearchTicketListByBID(int pageNum, int pageSize,String word){
        PageHelper.startPage(pageNum,pageSize);
        List<Ticket> ticketList = ticketDao.findSearchAllTicketByBID(word);
        PageInfo<Ticket> data = new PageInfo<>(ticketList);
        return data;
    }
    public PageInfo<Ticket> getSearchTicketList(int pageNum, int pageSize,String word){
        PageHelper.startPage(pageNum,pageSize);
        List<Ticket> ticketList = ticketDao.findSearchAllTicket(word);
        PageInfo<Ticket> data = new PageInfo<>(ticketList);
        return data;
    }

    public int updateBookInfo(Ticket p) {
        // TODO Auto-generated method stub

        int res=ticketDao.UpdateTicketInfo(p);
        return res;
    }
    public boolean deleteBookInfoByID(int id) {
        // TODO Auto-generated method stub
        boolean res=ticketDao.DeleteTicketInfo(id);

        return res;
    }

    public int saveBookInfo(Ticket p) {
        // TODO Auto-generated method stub
        int res=ticketDao.addTicketDao(p);
       return res;

    }

}

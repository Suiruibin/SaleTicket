package com.fuful.service;



import com.fuful.dao.TicketDao;
import com.fuful.domain.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {


    @Autowired
    TicketDao ticketDao;

    public boolean deleteSeatlog(int rid,int row,int col,String itemid){
        boolean res=ticketDao.DeleteSeatlog(rid,row,col,itemid);
        return res;
    }

    public List<SeatLog> getAllSeatLog(String oid){
        List<SeatLog> seatLogList=ticketDao.findAllSeatlog(oid);
        return seatLogList;
    }
    public List<SeatLog> findTmpSeatLog(String oid){
        List<SeatLog> seatLogList=ticketDao.findAllTmpSeatlog(oid);
        return seatLogList;
    }
    public  int addSeatLog(int rid,int row,int col,String itemid){
        int res=ticketDao.AddSeatLog(rid,row,col,itemid);
        return res;
    }
    public int addTempSeatLog(int rid,int row,int col,String itemid,String oid ){
        int res=ticketDao.AddTmpSeatLog(rid,row,col,itemid,oid);
        return res;
    }

    public List<SeatLog> getSeatLog(int id){
        List<SeatLog> seatLogList=ticketDao.findAllSeatLogById(id);
        return seatLogList;
    }

    public PageInfo<City> getTicketListByTown(int pageNum, int pageSize, String town){
        PageHelper.startPage(pageNum,pageSize);
        List<City> ticketList = ticketDao.findTicketListByTown(town);
        PageInfo<City> data = new PageInfo<>(ticketList);
        return data;
    }



    public List<TownInfo> getTownInfoList(){
        List<TownInfo> townInfoList=ticketDao.findTownInfoList();
        return townInfoList;
    }

    public List<Price> getPriceByTid(String tid){
        List<Price> priceList=ticketDao.findAllPriceByTid(tid);
        return priceList;
    }
    public List<Round> getRoundByPlace(String tid,String city){
        List<Round> roundList=ticketDao.findRoundByPlace(tid,city);
        return roundList;
    }

    public List<City> getPlaceByTid(String tid){
        List<City> cityList=ticketDao.findAllPlaceByTid(tid);
        return cityList;
    }

    public List<Round> getPriceByLRT(String tid,String city,String round){
        List<Round> priceList=ticketDao.findAllPriceByLRT(tid,city,round);
        return priceList;
    }


    //	获得热门图书
    public List<Book> getHot() {
        // TODO Auto-generated method stub
        List<Book> plist =new ArrayList<>();
        plist= ticketDao.findHotBook();
        return plist;
    }
//  获得最新商品
	public List<Book> getNew() {
//		// TODO Auto-generated method stub
//		Connection conn = JNDISessionFactory.getConnection();
		List<Book> plist = ticketDao.findAllNewBook();
		return plist;
	}


    //	获得所有的栏目superType
    public List<BookSuperType> findAllSuperType() {
        // TODO Auto-generated method stub
        List<BookSuperType> plist = ticketDao.findSuperType();
        return plist;
    }

    public PageInfo<Ticket> getProductList(int pageNum, int pageSize, int cid){
        PageHelper.startPage(pageNum,pageSize);
        List<Ticket> ticketList = ticketDao.findProductByCid(cid);
        PageInfo<Ticket> data = new PageInfo<>(ticketList);
        return data;
    }

	public Ticket findTicketInfoById(int id) {
//		// TODO Auto-generated method stub
        Ticket p = ticketDao.getTicketInfoById(id);
        return p;
    }
	public List<Book> getDiscount() {
//		// TODO Auto-generated method stub
		List<Book> plist = ticketDao.findAllDiscountBook();
		return plist;
	}

	public void addTicketInfoHit(int hit,int id) {
//		// TODO Auto-generated method stub
        int res= ticketDao.addTicketHit(hit,id);

	}

    public String findSuperTypeName(int superType) {
        // TODO Auto-generated method stub
        String  p = ticketDao.getSuperTypeName(superType);
        return p;
    }


    public PageInfo<Mark> getMarkList(int pageNum,int pageSize,int pid){
        PageHelper.startPage(pageNum,pageSize);
        List<Mark> markList = ticketDao.findMarkList(pid);
        PageInfo<Mark> data = new PageInfo<>(markList);
        return data;
    }

    public boolean deletemark(String mid){
        boolean res= ticketDao.DeleteMark(mid);
        return res;
    }
    public PageInfo<Mark> getMyMarkList(int pageNum,int pageSize,String  username){
        PageHelper.startPage(pageNum,pageSize);
        List<Mark> markList = ticketDao.findMyMarkList(username);
        PageInfo<Mark> data = new PageInfo<>(markList);
        return data;
    }


    public Ticket findTicketInfoByName(String name){
        Ticket res=ticketDao.findTIcketInfoByName(name);
        return res;
    }

    public List<String> findTicketByword(String word){
        List<String> res=ticketDao.findTickByWord(word);
        return res;
    }
}

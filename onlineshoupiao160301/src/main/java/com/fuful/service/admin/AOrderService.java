package com.fuful.service.admin;

import com.fuful.dao.admin.AOrderDao;
import com.fuful.domain.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunRuiBin on 2019/11/17.
 */
@Service
public class AOrderService {
    @Autowired
    AOrderDao aOrderDao;

    public int updateRefuseOrder(String oid){
        int res=aOrderDao.UpdateRefuseOrder(oid);
        return res;

    }
    public int updateConfirmCancel(String oid){
        int res=aOrderDao.UpdateConfirmCancel(oid);
        return res;
    }
    public int addTransportInfo(String oid,String transport,String transportNum){
        int res=aOrderDao.AddTransInfo(oid,transport,transportNum);
        return res;
    }
    public int updateOrderSend(String oid){
        int res=aOrderDao.UpdateOrderSend(oid);
        return res;
    }

    public PageInfo<Order> getSearchOrderResult(int pageNum,int pageSize,String keyword) {

        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = aOrderDao.findAllSearchOrder(keyword);
        PageInfo<Order> data = new PageInfo<>(orders);
        return data;

    }

    public List<SeatLog> getSeatLogByItemid(String itemid) {

        List<SeatLog> seatLogList = aOrderDao.findSeatLogByItemid(itemid);
        return seatLogList;

    }




    public PageInfo<Order> findAllOrdersNoById(int pageNum,int pageSize) {

            PageHelper.startPage(pageNum,pageSize);
            List<Order> orders = aOrderDao.findAllOrder();
            PageInfo<Order> data = new PageInfo<>(orders);
            return data;

    }

    public Round getRoundInfoByRid(int rid){
         Round round=aOrderDao.findRoundInfoByRid(rid);
         return round;
    }

    public List<OrderProductDetail> findOrderInfoByOid(String oid) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> plist = new ArrayList();
        List<OrderProductDetail> orderProductDetails=aOrderDao.findOrderInfoByOid(oid);
        List<OrderProductDetail> new_orderProductDetails=new ArrayList<>();
        for(OrderProductDetail p:orderProductDetails){
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("count", p.getCount());
//            map.put("subtotal", p.getSubtotal());
//            map.put("itemid",p.getItemid());
//            map.put("picture", p.getPicture());
//            map.put("bookName", p.getBookName());
//            map.put("price", p.getPrice());
            List<SeatLog> seatLogs=getSeatLogByItemid(p.getItemid());
            p.setSeatLogList(seatLogs);

            if(seatLogs.size()>0){
                Round round=getRoundInfoByRid(seatLogs.get(0).getRid());
                p.setPlace(round.getCity());
                p.setRound(round.getRound());
            }

            new_orderProductDetails.add(p);
//            plist.add(map);
        }
        return new_orderProductDetails;
    }

    public boolean deleteOrderByOid(String oid) {
        // TODO Auto-generated method stub

        boolean res=aOrderDao.DeleteOrder(oid);
        return res;
    }


}

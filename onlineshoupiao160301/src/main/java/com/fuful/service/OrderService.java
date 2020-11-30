package com.fuful.service;

import com.fuful.dao.OrderDao;
import com.fuful.db.JNDISessionFactory;
import com.fuful.domain.*;
import com.github.pagehelper.Page;
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
 * Created by SunRuiBin on 2019/11/14.
 */
@Service
public class OrderService {


    @Autowired
    OrderDao orderDao;

    public int updateRefuseOrder(String oid){
        int res=orderDao.UpdateRefuseOrder(oid);
        return res;
    }

    public boolean deleteOrder(String oid){
        boolean res=orderDao.DeleteOrder(oid);
        return res;
    }
    public List<SeatLog> getAllSeatLogByItemid(String itemid){
        List<SeatLog> seatLogList=orderDao.findAllSeatlogByItemid(itemid);
        return seatLogList;
    }
    public List<Transport> getTransportInfo(String oid){
        List<Transport> transportList=orderDao.findTransportInfo(oid);
        return transportList;
    }
    public int updateOrderCancel(String oid){
        int res=orderDao.UpdateOrderCancel(oid);
        return res;
    }
    public int updateOrderConfirm(String oid){
        int res=orderDao.UpdateOrderConfirm(oid);
        return res;

    }

    public int updateOrderAdrr(Order order){
        int res=orderDao.UpdateOrderAdrr(order);
        return res;
    }

    public int submitOrder(Order p) {
        // TODO Auto-generated method stub
        int res=orderDao.addOrder(p);
        return res;

    }
    public void submitOrderItem(List<OrderItem> orderItems){
// TODO Auto-generated method stub
        Connection conn = JNDISessionFactory.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("insert into tb_orderitem values(?,?,?,?,?,?,?)");

            for(OrderItem item: orderItems)
            {

                ps.setString(1,item.getItemid());
                ps.setInt(2, item.getCount());
                ps.setDouble(3, item.getSubtotal());
                ps.setInt(4, item.getProduct().getID());
                ps.setString(5, item.getOrder().getOid());
                ps.setString(6,item.getLocation());
                ps.setString(7,item.getRound());
                int i = ps.executeUpdate();
            }
            JNDISessionFactory.close(ps, conn);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public PageInfo<Order> getAllOrders(int pageNum, int pageSize, String uid) {
        // TODO Auto-generated method stub
        List<Order> plist = orderDao.findAllOrders(uid);
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderDao.findAllOrders(uid);
        PageInfo<Order> data = new PageInfo<>(orders);
        return data;
    }
    public List<Map<String, Object>> findAllOrderItemByOid(String oid) {
        // TODO Auto-generated method stub

//		select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from orderitem i,product p where i.pid=p.pid and i.oid=?
        List<Map<String, Object>> plist = new ArrayList();
        List<OrderProductDetail> orderProductDetails=orderDao.findOrderItemByOid(oid);

        for(OrderProductDetail p:orderProductDetails){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("count", p.getCount());
            map.put("subtotal", p.getSubtotal());
            map.put("itemid",p.getItemid());
            map.put("picture", p.getPicture());
            map.put("ticketName", p.getTicketName());
            map.put("price", p.getPrice());
            map.put("location",p.getLocation()) ;

            map.put("round",p.getRound()) ;

            plist.add(map);
        }
//        try {
//            PreparedStatement ps = conn.prepareStatement("select i.count,i.subtotal,p.picture,p.bookName,p.price from tb_orderitem i,tb_book p where i.pid=p.ID and i.oid=?");
        return plist;
    }

    public int updateOrderState(String oid) {
        int res=orderDao.UpdateOrderState(oid);
        return res;

    }




}

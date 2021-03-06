package com.fuful.controllers;

import com.fuful.domain.*;
import com.fuful.service.OrderService;
import com.fuful.service.TicketService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by SunRuiBin on 2019/11/16.
 */
@Controller
public class OrderCtrls {


    @Autowired
    OrderService orderService;

    @Autowired
    TicketService ticketService;
//    @RequestMapping(value = "/ProductMyOrders",method = GET)
////    @ResponseBody
//    public String  productOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//
//        User user = (User) session.getAttribute("user");
//        if(user==null)
//        {
//            return "redirect:/front/login.jsp";
//        }
//        //查询该用户的所有的订单信息(单表查询orders表)
//        //集合中的每一个Order对象的数据是不完整的 缺少List<OrderItem> orderItems数据
//        List<Order> orderList = orderService.getAllOrders(user.getUid());
//        //循环所有的订单 为每个订单填充订单项集合信息
//        if(orderList!=null){
//            for(Order order : orderList){
//                //获得每一个订单的oid
//                String oid = order.getOid();
//                //查询该订单的所有的订单项---mapList封装的是多个订单项和该订单项中的商品的信息
//                List<Map<String, Object>> mapList = orderService.findAllOrderItemByOid(oid);
//                //将mapList转换成List<OrderItem> orderItems
//                for(Map<String,Object> map : mapList){
//
//                    try {
//                        //从map中取出count subtotal 封装到OrderItem中
//                        OrderItem item = new OrderItem();
//                        //item.setCount(Integer.parseInt(map.get("count").toString()));
//                        BeanUtils.populate(item, map);
//                        //从map中取出pimage pname shop_price 封装到Product中
//                        Ticket product = new Ticket();
//                        BeanUtils.populate(product, map);
//                        //将product封装到OrderItem
//                        item.setProduct(product);
//                        //将orderitem封装到order中的orderItemList中
//                        order.getOrderItems().add(item);
//                    } catch (IllegalAccessException | InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//            }
//        }
//        //orderList封装完整了
//        session.setAttribute("orderList", orderList);
//
////        return orderList;
//        return "redirect:/front/order_list.jsp";
//
//    }

    @RequestMapping(value = "/ProductMyOrders",method = GET)
    public String  productOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int page;
        if(request.getParameter("currentPage")==null)
        {
            page=1;
        }
        else
        {
            page=Integer.parseInt(request.getParameter("currentPage"));
        }
        User user = (User) session.getAttribute("user");
        if(user==null)
        {
            return "redirect:/front/login.jsp";
        }
        //查询该用户的所有的订单信息(单表查询orders表)
        //集合中的每一个Order对象的数据是不完整的 缺少List<OrderItem> orderItems数据

        PageInfo<Order> orderListPageinfo = orderService.getAllOrders(page,5,user.getUid());


        List<Order> orderList=orderListPageinfo.getList();
        //循环所有的订单 为每个订单填充订单项集合信息
        if(orderList!=null){
            for(Order order : orderList){
                //获得每一个订单的oid
                String oid = order.getOid();
                //查询该订单的所有的订单项---mapList封装的是多个订单项和该订单项中的商品的信息
                List<Map<String, Object>> mapList = orderService.findAllOrderItemByOid(oid);
                //将mapList转换成List<OrderItem> orderItems
                for(Map<String,Object> map : mapList){

                    try {
                        //从map中取出count subtotal 封装到OrderItem中
                        OrderItem item = new OrderItem();
                        //item.setCount(Integer.parseInt(map.get("count").toString()));
                        BeanUtils.populate(item, map);
                        //从map中取出pimage pname shop_price 封装到Product中
                        Ticket product = new Ticket();
                        BeanUtils.populate(product, map);
                        //将product封装到OrderItem
                        item.setProduct(product);
                        //将orderitem封装到order中的orderItemList中
                        order.getOrderItems().add(item);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }

            }
        }
        //orderList封装完整了
        session.setAttribute("orderList", orderList);

        session.setAttribute("currentPage", orderListPageinfo.getPageNum());
        session.setAttribute("totalPage", orderListPageinfo.getPages());
        session.setAttribute("totalCount", orderListPageinfo.getTotal());
//        return orderList;
        return "redirect:/front/order_list.jsp";

    }



    @RequestMapping(value = "/ProductPayforServlet",method = POST)
    public void ProductPayforServlet(HttpServletResponse response,HttpServletRequest request) throws IOException {
        //1、更新收货人信息
        Map<String, String[]> properties = request.getParameterMap();
        Order order = new Order();
        try {
            BeanUtils.populate(order, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        String oid=request.getParameter("oid");
        order.setOid(oid);
        int res=orderService.updateOrderAdrr(order);
//        return "redirect:/front/ChoiceBank.jsp";
        Gson gson=new Gson();
        String json=gson.toJson("success");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    @RequestMapping(value = "/BankPay",method =POST )
    public void bankpay(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String id=request.getParameter("bankId");
        String password=request.getParameter("bankPassword");

        HttpSession session=request.getSession();
        String oid= (String) session.getAttribute("oid");

//        List<SeatLog> seatLogList= (List<SeatLog>) session.getAttribute("order_seatlog");
        List<SeatLog> seatLogList=ticketService.findTmpSeatLog(oid);

        for(SeatLog p:seatLogList){
            int res=ticketService.addSeatLog(p.getRid(),p.getRow(),p.getCol(),p.getItemid());
        }
        session.removeAttribute("cart");

        int flag= orderService.updateOrderState(oid);

        System.out.println("Flag="+flag);
        Gson gson=new Gson();
        String json=gson.toJson("success");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }


    @RequestMapping(value = "/RBankPay",method =POST )
    public void rbankpay(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String id=request.getParameter("bankId");
        String password=request.getParameter("bankPassword");

        String oid=  request.getParameter("oid");

        List<SeatLog> seatLogList=ticketService.findTmpSeatLog(oid);
        for(SeatLog p:seatLogList){
            int res=ticketService.addSeatLog(p.getRid(),p.getRow(),p.getCol(),p.getItemid());
        }
        int flag= orderService.updateOrderState(oid);
        System.out.println("Flag="+flag);
        Gson gson=new Gson();
        String json=gson.toJson("success");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }


    @RequestMapping(value = "/RePayforServlet",method = GET)
    public String repayfor(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        String orderid = request.getParameter("oid");
        session.setAttribute("oid",orderid);
        return "redirect:/front/ChoiceBank.jsp";
    }


    @RequestMapping(value = "/ConfirmOrder",method = POST)
    public void ConfirmOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String oid=request.getParameter("orderId");
        String password=request.getParameter("password");

        int res=orderService.updateOrderConfirm(oid);

        String message="success";
        Gson gson=new Gson();
        String json=gson.toJson(message);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);


    }

    @RequestMapping(value = "/requestOrderCancel",method = GET)
    public String requestOrderCancel(HttpServletRequest request,HttpServletResponse response){
        String oid=request.getParameter("oid");
        String curpage=request.getParameter("curPage");
        int res=orderService.updateOrderCancel(oid);

        return "redirect:/ProductMyOrders?currentPage="+curpage;
    }

    @RequestMapping(value = "/RerequestOrderCancel",method = POST)
    public void RerequestOrderCancel(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String oid=request.getParameter("oid");
//        重新设置confirmCancel为0;
        int res=orderService.updateOrderCancel(oid);
        int res_2=orderService.updateRefuseOrder(oid);

        Gson gson=new Gson();
        String json=gson.toJson("success");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);

    }


    @RequestMapping(value = "/showTransport",method = POST)
    public void showTransport(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String oid=request.getParameter("oid");
        List<Transport> res=orderService.getTransportInfo(oid);
        HttpSession session=request.getSession();

        Gson gson=new Gson();
        String json=gson.toJson(res.get(0));
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    @RequestMapping(value = "/FindAllSeatLogByItemid",method = POST)
    public void FindAllSeatLogByItemid(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String itemid=request.getParameter("itemid");
        List<SeatLog> seatLogList=orderService.getAllSeatLogByItemid(itemid);

        System.out.println("SEATLOGLIST==="+seatLogList);
        System.out.println("ITEMID==="+itemid);

        Gson gson=new Gson();
        String json=gson.toJson(seatLogList);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }
    @RequestMapping(value = "/DelteOrder",method = POST)
    public void DelteOrder(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String oid=request.getParameter("oid");

        boolean res=orderService.deleteOrder(oid);

        Gson gson=new Gson();
        String json=gson.toJson("success");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

}

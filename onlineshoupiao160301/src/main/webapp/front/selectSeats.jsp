<%--
  Created by IntelliJ IDEA.
  User: SunRuiBin
  Date: 2019/11/23
  Time: 11:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isELIgnored="false" %>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="icon" href="img/logo2.png">
    <title>在线选座</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <link href="css/selectSeat/nav/bootstrap.css" rel='stylesheet' type='text/css' />
    <link href="css/selectSeat/nav/style.css" rel="stylesheet" type="text/css" media="all" />

    <link href='css/selectSeat/googleAPIStylesheet.css' rel='stylesheet' type='text/css'>
    <link href="css/selectSeat/style.css" rel="stylesheet" type="text/css" media="all" />
    <script src="js/selectSeat/jquery-1.11.0.min.js"></script>
    <script src="js/selectSeat/jquery.seat-charts.js"></script>
    <script src="js/selectSeat/jquery.nicescroll.js"></script>
    <script src="js/selectSeat/scripts.js"></script>

    <script type="text/javascript">
        function addCart(){
            //获得购买的商品的数量
            var  seatlog=new Array();
            var ul = $("#selected_seat").children();
            for(var i=0;i<ul.length;i++){
                console.log(ul[i].innerHTML)
                seatlog[i]=ul[i].innerHTML;
            }

            var buyNum = $("#counter").text();
            var location="${round.city}";
            var round="${round.round}";
            var roundID="${round.id}";
            var price="${price}";

            console.log(seatlog)
            window.location.href="/AddProductToCart?pid=${product.ID}&buyNum="+buyNum+"&location="+location+"&round="+round+"&price="+price+"&seatlog="+seatlog+"&roundID="+roundID;
            <%--window.location.href="/AddProductToCart?pid=${product.ID}&buyNum="+buyNum;--%>
        }

    </script>

</head>


<body>




<div class="container" >


    <div class="container_wrap">
        <div class="header_top">
            <div class="col-sm-3 logo"><a href="/frontindex"><img src="img/logo.png" style="width: 120px;height: 70px" alt=""/></a></div>
            <div class="col-sm-6 nav">
                <%--<ul>--%>
                    <%--<li> <span class="simptip-position-bottom simptip-movable" data-tooltip="comic"><a href="../movie.html"> </a></span></li>--%>
                    <%--<li><span class="simptip-position-bottom simptip-movable" data-tooltip="movie"><a href="../movie.html"> </a> </span></li>--%>
                    <%--<li><span class="simptip-position-bottom simptip-movable" data-tooltip="video"><a href="../movie.html"> </a></span></li>--%>
                    <%--<li><span class="simptip-position-bottom simptip-movable" data-tooltip="game"><a href="../movie.html"> </a></span></li>--%>
                    <%--<li><span class="simptip-position-bottom simptip-movable" data-tooltip="tv"><a href="../movie.html"> </a></span></li>--%>
                    <%--<li><span class="simptip-position-bottom simptip-movable" data-tooltip="more"><a href="../movie.html"> </a></span></li>--%>
                <%--</ul>--%>
            </div>
            <div class="col-sm-3 header_right">
                <ul class="header_right_box">
                    <%--<li><img src="images/selectSeat/nav/p1.png" alt=""/></li>--%>
                    <li><p><a href="/ProductInfoServlet?pid=${pid}&cid=${cid}&currentPage=${currentPage}&typeID=null">返回票务详情页面</a></p></li>
                    <li class="last"><i class="edit"> </i></li>
                    <div class="clearfix"> </div>
                </ul>
            </div>
            <div class="clearfix"> </div>
        </div>
        <div class="content">
            <div class="main">
                <div class="demo">
                    <div id="seat-map">
                        <div class="front">场馆</div>
                    </div>
                    <div class="booking-details">
                        <ul class="book-left">
                            <li>票名 :</li>
                            <li>场次 :</li>
                            <li>已选座位数 :</li>
                            <li>价格 :</li>
                        </ul>
                        <ul class="book-right">
                            <li style="text-overflow:ellipsis;
white-space:nowrap;
overflow:hidden;">${ticketName}</li>
                            <li>
                                <%--<input style="display: inline;width: 116%;border: none" class="input-group" value="${round.round}" type="datetime-local" readonly>--%>
                                    ${round.round}
                            </li>
                            <li> <span id="counter">0</span></li>
                            <li><b>¥<span id="total">0</span></b></li>
                        </ul>
                        <div class="clear"></div>
                        <ul id="selected-seats" class="scrollbar scrollbar1"></ul>

                        <ul id="selected_seat" style="display: none"></ul>
                        <button class="checkout-button" onclick="addCart()">加入购物车</button>
                        <div id="legend"></div>
                    </div>
                    <div style="clear:both"></div>
                </div>

                <script type="text/javascript">
                    var price = "${price}"; //price
                    price= parseFloat(price);


                    $(document).ready(function() {
                        var $cart = $('#selected-seats'), //Sitting Area
                            $counter = $('#counter'), //Votes
                            $total = $('#total'); //Total money

                        var row_string=null;
                        var col_string=null;
                        var row="${row}";
                        var col="${col}";

                        var p="";
                        for(var i=0;i<col;i++){
                            p+='a';
                        }
                        var mymap=new Array();
                        for(var i=0;i<row;i++){
                            mymap[i]=p;
                        }
                        var soldticket=new Array();

                        var sc = $('#seat-map').seatCharts({
                            map: mymap,
                            naming : {
                                top : false,
                                getLabel : function (character, row, column) {
                                    return column;
                                }
                            },
                            legend : { //Definition legend
                                node : $('#legend'),
                                items : [
                                    [ 'a', 'available',   '可选' ],
                                    [ 'a', 'unavailable', '已售'],
                                    [ 'a', 'selected', '选中']
                                ]
                            },
                            click: function () { //Click event
                                if (this.status() == 'available') { //optional seat
                                    $('<li>Row'+(this.settings.row+1)+' Seat'+this.settings.label+'</li>')
                                        .attr('id', 'cart-item-'+this.settings.id)
                                        .data('seatId', this.settings.id)
                                        .appendTo($cart);

                                    $('<li>'+(this.settings.row+1)+','+this.settings.label+'</li>')
                                        .attr('id', 'cart-item-1'+this.settings.id)
                                        .data('seatId', this.settings.id)
                                        .appendTo($("#selected_seat"));

                                    $counter.text(sc.find('selected').length+1);
                                    $total.text(recalculateTotal(sc)+price);

                                    return 'selected';
                                } else if (this.status() == 'selected') { //Checked
                                    //Update Number
                                    $counter.text(sc.find('selected').length-1);
                                    //update totalnum
                                    $total.text(recalculateTotal(sc)-price);
                                    $('#cart-item-1'+this.settings.id).remove();
                                    //Delete reservation
                                    $('#cart-item-'+this.settings.id).remove();
                                    //optional
                                    return 'available';
                                } else if (this.status() == 'unavailable') { //sold
                                    return 'unavailable';
                                } else {
                                    return this.style();
                                }
                            }
                        });

                        $.post(
                            "/GetSeatLogServlet",
                            {"rid":${round.id}},
                            function(data)
                            {
                                for(var i=0;i<data.length;i++){
                                    /* 传递该子栏目的ID */
                                    soldticket[i]=data[i].row+"_"+data[i].col
                                }
                                sc.get(soldticket).status('unavailable');
                            },
                            "json"

                        )
                        //sold seat


                    });
                    //sum total money
                    function recalculateTotal(sc) {
                        var total = 0;
                        sc.find('selected').each(function () {
                            total += price;
                        });

                        return total;
                    }
                </script>
            </div>
        </div>
    </div>

</body>
</html>
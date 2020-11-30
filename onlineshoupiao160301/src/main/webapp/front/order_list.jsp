<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isELIgnored="false" %>
<base href="http://localhost:8080/front/">
<!DOCTYPE html>
<html>

<head>
	<link rel="icon" href="img/logo2.png">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>订单列表</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>我的订单</strong>
				<table class="table table-bordered">

					<c:if test="${!empty orderList }">
						<c:forEach items="${orderList }" var="order">
							<tbody>
								<tr class="success">
									<th colspan="7">
										订单编号:${order.oid }&nbsp;&nbsp;
											<%--${order.state==0?"未付款":"已付款" }--%>

										<%--<c:if test="${order.state==1}">--%>
											<%--<c:if test="${order.send==0}">--%>
													<%--未发货--%>
											<%--</c:if>--%>


											<%--<c:if test="${order.send==1}">--%>

													<%--<button type="button" class="btn btn-default">--%>
														<%--<a href="/RePayforServlet?oid=${order.oid }">--%>
															<%--查看物流--%>
														<%--</a>--%>
													<%--</button>--%>

												<%--<c:if test="${order.confirmOrder==1}">--%>
													<%--已收货--%>
												<%--</c:if>--%>


												<%--<c:if test="${order.confirmOrder==0}">--%>
													<%--<button type="button" class="btn btn-default">--%>
														<%--&lt;%&ndash;<a href="/ConfirmOrder?oid=${order.oid }">&ndash;%&gt;--%>
															<%--&lt;%&ndash;确认收货&ndash;%&gt;--%>
														<%--&lt;%&ndash;</a>&ndash;%&gt;--%>
														<%--<a   onclick="preConfirm('${order.oid}')" >确认收货</a>--%>
													<%--</button>--%>
												<%--</c:if>--%>


												<%--<script type="text/javascript">--%>
													<%--function preConfirm(oid) {--%>

                                                        <%--$("#orderId").val(oid);--%>
                                                        <%--$('#confirmPay').modal('show');--%>
                                                    <%--}--%>
												<%--</script>--%>

											<%--</c:if>--%>
									    <%--</c:if>--%>


									</th>
									<%--<c:if test="${order.state==0}">--%>
										<%--<th>--%>
											<%--<button type="button" class="btn btn-default">--%>
												<%--<a href="/RePayforServlet?oid=${order.oid }">--%>
													<%--去付款--%>
												<%--</a>--%>
											<%--</button>--%>
										<%--</th>--%>
									<%--</c:if>--%>

									<th style="text-align: center">
										<a onclick="deleteOrder('${order.oid}')">删除</a>
										<%--删除--%>
									</th>


										<script type="text/javascript">
											function deleteOrder(oid) {
											    var res=confirm("确定要删除订单吗？")
												if(res) {
                                                    $.ajax({
                                                        type: "POST",
                                                        url: "/DelteOrder",
                                                        data: "oid=" + oid,
                                                        cache: false, //不缓存此页面
                                                        success: function (data) {
                                                            window.location.href = "/ProductMyOrders";
                                                        }
                                                    });
                                                }

                                            }
										</script>

								</tr>
								<tr class="warning">
									<th>图片</th>
									<th>商品</th>
									<th>地点</th>
									<th>场次</th>
									<th>数量</th>
									<th>小计</th>
									<th>查看座位</th>
									<th>
										操作
									</th>
								</tr>
							<c:forEach items="${order.orderItems }" var="orderItem"  varStatus="p">
								<tr class="active">
											<td width="60" width="40%"><input type="hidden" name="id"
											value="22">
											<img src="${orderItem.product.picture}" width="70" height="60">
											</td>
									<td width="20%"> ${orderItem.product.ticketName }</td>
									<td width="20%"> ${orderItem.location }</td>
									<td width="20%">

											<%--${orderItem.round }--%>
												<span class="article-time-display">
						 							 <input style="border: none;"  name="INTime" readonly type="datetime-local" value="${orderItem.round}" id="INTime"/>
												</span>
									</td>
									<td width="10%">${orderItem.count }</td>
									<td width="10%"><span class="subtotal">￥${orderItem.subtotal }</span></td>
									<td>
										<button class="btn btn-default" style="margin-top: 10px">
											<a onclick="showSeats('${orderItem.itemid}')">查看</a>
										</button>
									</td>

									<script type="text/javascript">
										function showSeats(itemid) {
                                            $.post(
                                                "/FindAllSeatLogByItemid",
                                                {"itemid":itemid},
                                                function(data){

                                                    var content = "";
													for(var j=0;j<data.length;j++){
//						    console.log(data[i].seatLogList[j].row+" "+data[i].seatLogList[j].col)
                                                            content+="<tr style='text-align: left;height: 30px;margin-top: 20px'>"+
                                                                "<td>"+"选择的座位号"+
                                                                "</td>"+
                                                                "<td>row:</td>"+
                                                                "<td></td>"+
                                                                "<td>"+data[j].row+"排</td>"+
                                                                "<td>col:</td>"+
                                                                "<td></td>"+
                                                                "<td>"+data[j].col+"座</td>"+
                                                                "</tr>";
													}


                                                    $("#showorderinfo").html(content);

                                                    //订单编号
                                                    $("#shodDivOid").html(itemid);
                                                    $('#seeorder').modal('show');

                                                },
                                                "json"
                                            );
                                        }
									</script>

									<td style="text-align: center" width="20%">
										<c:if test="${p.count==1}">

											<c:if test="${order.confirmCancel==1}">
												<p style="color: #b7b7aa">订单关闭</p>
											</c:if>

											<c:if test="${order.confirmCancel==0}">

												<c:if test="${order.state==1}">
													<c:if test="${order.send==0}">
														<p>未发货</p>
														<hr>
														<p >

															<c:if test="${order.cancel==0}">
																<a href="/requestOrderCancel?oid=${order.oid}&curPage=${currentPage}">
																	关闭订单
																</a>
															</c:if>

															<c:if test="${order.cancel==1}">
																<c:if test="${order.refuse==1}">
																	<button class="btn btn-default">
																		<p onclick="reReuqest('${order.oid}')">
																			商家拒绝
																		</p>
																	</button>

																</c:if>

																<c:if test="${order.refuse==0}">
																	请求关闭订单中..
																</c:if>


															</c:if>

														</p>


													</c:if>


													<script type="text/javascript">
														function reReuqest(oid) {
														    var res=confirm("继续请求取消订单吗？");
														    if(res){
                                                                $.post(
                                                                    "/RerequestOrderCancel",
                                                                    {"oid":oid},
                                                                    function(data)
                                                                    {
                                                                        window.location.href="/ProductMyOrders?currentPage="+${currentPage};
                                                                    },
                                                                    "json"
                                                                )
															}
															else
															{

															}
                                                        }
													</script>

													<c:if test="${order.send==1}">

														<button type="button" class="btn btn-default" onclick="showTransport('${order.oid}')">
															<%--<a href="/ShowDelivery?oid=${order.oid }">--%>
																<%--查看物流--%>
															<%--</a>--%>
																查看物流
														</button>

														<script type="text/javascript">
															function showTransport(oid) {
																$("#order_id").val(oid);

                                                                $.post(
                                                                    "/showTransport",
                                                                    {"oid":oid},
                                                                    function(data)
                                                                    {
                                                                        console.log(data);
                                                                        $("#transport").val(data.transport);
                                                                        $("#transportNum").val(data.transportNum);
                                                                        $("#showTrans").modal("show");

                                                                    },
                                                                    "json"
                                                                )


															}
														</script>
														<c:if test="${order.confirmOrder==1}">
															<p>
																已收货
															</p>
														</c:if>


														<c:if test="${order.confirmOrder==0}">
															<button type="button" class="btn btn-default">
																	<%--<a href="/ConfirmOrder?oid=${order.oid }">--%>
																	<%--确认收货--%>
																	<%--</a>--%>
																<a   onclick="preConfirm('${order.oid}')" >确认收货</a>
															</button>
															<hr>
															<%--<p >--%>
																<%--<c:if test="${order.cancel==0}">--%>
																	<%--<a href="/requestOrderCancel?oid=${order.oid}">--%>
																		<%--关闭订单--%>
																	<%--</a>--%>
																<%--</c:if>--%>

																<%--<c:if test="${order.cancel==1}">--%>
																	<%--请求关闭订单中..--%>
																<%--</c:if>--%>
															<%--</p>--%>
														</c:if>




														<script type="text/javascript">
                                                            function preConfirm(oid) {

                                                                $("#orderId").val(oid);
                                                                $('#confirmPay').modal('show');
                                                            }
														</script>

													</c:if>
												</c:if>

												<c:if test="${order.state==0}">

													<button type="button" class="btn btn-default">
														<a onclick="goPayFor('${order.oid}')">
															去付款
														</a>
													</button>

													<script type="text/javascript">
														function goPayFor(oid) {
                                                            $("#orderNumber").val(oid);
                                                            $('#PayforOrder').modal('show');
                                                        }
													</script>
													<hr>
													<p >
														<c:if test="${order.cancel==0}">
															<a href="/requestOrderCancel?oid=${order.oid}&curPage=${currentPage}">
																关闭订单
															</a>
														</c:if>

														<c:if test="${order.cancel==1}">
															<c:if test="${order.refuse==1}">
																<button class="btn btn-default">
																<p onclick="reReuqest('${order.oid}')">
																	商家拒绝
																</p>
																</button>

															</c:if>

															<c:if test="${order.refuse==0}">
																请求关闭订单中..
															</c:if>
														</c:if>
													</p>

												</c:if>
											</c:if>


										</c:if>



									</td>
								</tr>
							</c:forEach>
							</tbody>

						</c:forEach>
					</c:if>
					
				</table>
			</div>
		</div>

		<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
			<ul class="pagination" style="text-align: center; margin-top: 10px;">

				<!-- 上一页 -->
				<c:if test="${currentPage==1 }">
					<li class="disabled">
						<a href="javascript:void(0);" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${currentPage!=1 }">
					<li>
						<a href="/ProductMyOrders?currentPage=${currentPage-1 }" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>


				<!-- 显示每一页 -->
				<c:forEach begin="1" end="${totalPage }" var="page">
					<!-- 判断是否是当前页 -->
					<c:if test="${page==currentPage }">
						<li class="active"><a href="javascript:void(0);">${page }</a></li>
					</c:if>
					<c:if test="${page!=currentPage }">
						<li><a href="/ProductMyOrders?currentPage=${page }">${page }</a></li>
					</c:if>
				</c:forEach>


				<!-- 下一页 -->
				<c:if test="${currentPage==totalPage }">
					<li class="disabled">
						<a href="javascript:void(0);" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${currentPage!=totalPage }">
					<li>
						<a href="${pageContext.request.contextPath}/ProductMyOrders?currentPage=${currentPage+1 }" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>

					</li>
				</c:if>

			</ul>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	
	<jsp:include page="footer.jsp"></jsp:include>


	<%--查看座位--%>

	<div class="modal fade" id="seeorder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<!--<h4 class="modal-title" >订单变哈皮</h4>-->
					<h2 style="color:#3399CC;">订单编号：<span id="shodDivOid" style="font-size: 13px;color: #999"></span></h2>
				</div>
				<div class="modal-body">
					<table class="table" style="margin-bottom:0px;">

						<tbody  id="showorderinfo">


						</tbody>

					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>




	<div class="modal fade" id="confirmPay" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="margin-top: 80px"  >
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" >确认收货</h4>
				</div>
				<div class="modal-body" style="margin-top: 10px">

					<table class="table" style="margin-bottom:0px;">
						<thead>
						<tr>
							<td wdith="20%">订单编号:</td>
							<td width="80%">
								<input type="text" value="" readonly  id="orderId" class="form-control" name="votename" maxlength="10" autocomplete="off" />
							</td>
						</tr>


						</thead>
						<tbody >

						<tr>
							<td wdith="20%">输入支付密码:</td>
							<td width="80%"><input type="password" value="" id="payPassword"  class="form-control" name="ticketPrice" maxlength="10" autocomplete="off" /></td>
						</tr>

						</tbody>
						<tfoot>
						<tr></tr>
						</tfoot>
					</table>



				</div>
				<div class="modal-footer">
					<button  onclick="addPlace()" type="button" class="btn btn-default">确认收货</button>
				</div>



				<script type="text/javascript">

                    function addPlace()
                    {
                        var orderId=$("#orderId").val();
                        var password=$("#payPassword").val();
                        if(password == '' || password == undefined || password == null){
                            return
                        }

                        $.post(
                            "/ConfirmOrder",
                            {"orderId":orderId,"password":password},
                            function(data)
                            {

                                window.location.href="/ProductMyOrders";

                            },
                            "json"
                        )
                    }

				</script>

			</div>

		</div>
	</div>


	<div class="modal fade" id="showTrans" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="margin-top: 80px"  >
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" >查看物流信息</h4>
				</div>
				<div class="modal-body" style="margin-top: 10px">

					<table class="table" style="margin-bottom:0px;">
						<thead>
						<tr>
							<td wdith="20%">订单编号:</td>
							<td width="80%">
								<input type="text" value="" readonly  id="order_id" class="form-control" name="votename" maxlength="10" autocomplete="off" />
							</td>
						</tr>


						</thead>
						<tbody >

						<tr>
							<td wdith="20%">物流名称:</td>
							<td width="80%"><input type="text" value="" readonly id="transport"  class="form-control" name="transport" maxlength="10" autocomplete="off" /></td>
						</tr>
						<tr>
							<td width="20%">物流单号:</td>
							<td width="80%"><input type="text"  readonly value="${transport.transportNum}" id="transportNum"  class="form-control" name="transportNum" maxlength="10" autocomplete="off" /></td>

						</tr>

						</tbody>
						<tfoot>
						<tr></tr>
						</tfoot>
					</table>



				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>



			</div>

		</div>
	</div>

	<div class="modal fade" id="PayforOrder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="margin-top: 80px"  >
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" >支付订单</h4>
				</div>
				<div class="modal-body" style="margin-top: 10px">

					<table class="table" style="margin-bottom:0px;">
						<thead>
						<tr>
							<td wdith="20%">订单编号:</td>
							<td width="80%">
								<input type="text" value=""   id="orderNumber" class="form-control" name="votename" maxlength="10" autocomplete="off" />
							</td>
						</tr>


						</thead>
						<tbody >

						<tr>
							<td wdith="20%">输入银行卡号:</td>
							<td width="80%"><input type="text" value="" id="bankNumber"  class="form-control" name="ticketPrice" maxlength="16" autocomplete="off" /></td>
						</tr>
						<tr>
							<td wdith="20%">输入支付密码:</td>
							<td width="80%"><input type="password" value="" id="bankPassword"  class="form-control" name="ticketPrice" maxlength="10" autocomplete="off" /></td>
						</tr>

						</tbody>
						<tfoot>
						<tr></tr>
						</tfoot>
					</table>



				</div>
				<div class="modal-footer">
					<button  onclick="Pay()" type="button" class="btn btn-default">确认支付</button>
				</div>



				<script type="text/javascript">

                    function Pay()
                    {
                        var orderId=$("#orderNumber").val();
                        var bankId=$("#bankNumber").val();
                        var bankPassword=$("#bankPassword").val();
                        if(bankId == '' || bankId == undefined || bankId == null){
                            return
                        }

                        alert(orderId+" "+bankId+" "+bankPassword);

                        $.post(
                            "/RBankPay",
                            {"bankId":bankId,"bankPassword":bankPassword,"oid":orderId},
                            function(data)
                            {
                                window.location.href="/ProductMyOrders?currentPage=1";
                            },
                            "json"
                        )
                    }

				</script>

			</div>

		</div>
	</div>




</body>






</html>
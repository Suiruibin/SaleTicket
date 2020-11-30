<%--
  Created by IntelliJ IDEA.
  User: SunRuiBin
  Date: 2019/11/16
  Time: 10:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="/BankPay" method="POST">

    <input placeholder="输入银行卡号" type="text" name="bankId">
    <input placeholder="输入密码" type="password" name="bankPassword">
    <input placeholder="订单" type="text" name="oid" style="display: none">
    <button type="submit">支付</button>
</form>

<A href="/frontindex">返回首页</A>
</body>
</html>

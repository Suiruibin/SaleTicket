<%--
  Created by IntelliJ IDEA.
  User: Scarlet
  Date: 2019/11/28
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>找回密码</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function mysubmit() {
            var uname=document.getElementById("username").value;
            var passw=document.getElementById("password").value;
            var passw1=document.getElementById("repassword").value;
            var name=document.getElementById("name").value;
            var email=document.getElementById("email").value;
            var checkcode=document.getElementById("checkcode").value;
            if(uname==""){
                document.getElementById("ermag").innerHTML="对不起，用户名不能为空";
                return false;
            }
            else if(passw==""){
                document.getElementById("ermag").innerHTML="对不起，密码不能为空";
                return false;
            }
            else if(passw1!=passw){
                document.getElementById("ermag").innerHTML="对不起，密码不一致";
                return false;
            }
            else if(name==""){
                document.getElementById("ermag").innerHTML="对不起，姓名不能为空";
                return false;
            }
            else if(email==""){
                document.getElementById("ermag").innerHTML="对不起，邮箱不能为空";
                return false;
            }
            else if(checkcode==""){
                document.getElementById("ermag").innerHTML="对不起，验证码不能为空";
                return false;
            }
            else{
                return true;
            }
        }
    </script>
</head>

<style>
    body {
        margin-top: 20px;
        margin: 0 auto;
    }

    .carousel-inner .item img {
        width: 100%;
        height: 300px;
    }

    font {
        color: #3164af;
        font-size: 18px;
        font-weight: normal;
        padding: 0 10px;
    }
</style>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="container"
     style="width: 100%; background: url('image/regist_bg.jpg');">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"
             style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
            <font>找回密码</font>
            <form id="backform" class="form-horizontal" action="/backuser" method="post" onsubmit="return mysubmit()" style="margin-top: 5px;">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="username" name="username"
                               placeholder="请输入用户名">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">重置密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="请输入密码">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="repassword" name="repassword"
                               placeholder="请输入确认密码">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-6">
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="Email">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="name" name="name"
                               placeholder="请输入姓名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="checkcode" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="checkcode" id="checkcode">

                    </div>
                    <div class="col-sm-2">
                        <img src="/checkImg" />
                    </div>

                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" width="100" value="确认找回" name="submit"
                               style="background: url('./images/login.jpg') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
                    </div>
                    <%--<div style="width: 300px;height:50px">
                        <c:if test="${dataedit.status=='ERROR'}">
                            <div style="margin-top: 10px;color: red">${dataedit.status}：${dataedit.msg}</div>
                        </c:if>
                    </div>--%>
                    <div style="width: 300px;height:50px">
                        <div><front id="ermag" ></front></div>

                    </div>
                </div>
            </form>
        </div>

        <div class="col-md-2"></div>

    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>

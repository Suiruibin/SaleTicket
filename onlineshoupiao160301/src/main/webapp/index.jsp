<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="UTF-8">
    <title>ajax</title>
</head>
<body>

<button type="button" class="btn btn-primary" data-toggle="collapse"
        data-target="#demo">
    简单的可折叠组件
</button>

<div id="demo" class="collapse in">
    Nihil anim keffiyeh helvetica, craft beer labore wes anderson
    cred nesciunt sapiente ea proident. Ad vegan excepteur butcher
    vice lomo.
</div>


<input type="text" placeholder="请输出你的地址" id="tel"/>
<button id="ajax">确定</button>
<p><span id="reslut"></span></p>
<script type="text/javascript" src="admin/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
    $(function(){
        $('#ajax').on('click',function(){
//            var $telValue=$('#tel').val();
//            if($telValue=="") {
//                alert('不能为空！');
//                return;
//            }
            $.ajax({
                type: 'GET',
                dataType:'jsonp',
                url: 'https://restapi.amap.com/v3/geocode/geo?address=北京市朝阳区阜通东大街6号&output=json&key=da3f89f7c685285905e53d03ba5e4482',
                success: function(data){
                    var reslutData=data;
                    alert(reslutData)
                    console.log(reslutData.geocodes[0].location);
//                    $('#reslut').text("你查询的是:"+reslutData.city+","+"明天的天气是:"+reslutData.weather[0].weather);
                }
            })
        })
    })
</script>
</body>
</html>

<%--https://restapi.amap.com/v3/staticmap?key=da3f89f7c685285905e53d03ba5e4482&size=1024*1024&location=116.483038,39.990633&zoom=11&markers=mid,0xFF0000,B:116.37359,39.92437--%>
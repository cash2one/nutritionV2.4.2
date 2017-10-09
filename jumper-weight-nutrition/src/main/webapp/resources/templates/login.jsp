<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>天使医生 社交后台</title>
    <link type="text/css" rel="stylesheet" href="resources/styles/logincss.css">
    <script src="resources/scripts/jquery.min.js" type="text/javascript" ></script>
    <script src="resources/scripts/login.js" type="text/javascript" ></script>
</head>
<body style="background: #f0f2f5">
    <div id="head">
        <div class="imgBox">
            <p><img src="resources/images/logo2.png">天使医生 社交后台</p>
        </div>
    </div>
    <form id="main">
        <p>用户登录</p>
        <!-- <span>验证码填写错误</span> -->
        <div class="login-item">
           <div class="list">
               <label>
                   <i class="iconfont">&#xe602;</i>
                   <input type="text" class="listInput1" name="username" placeholder="请输入你的账户名">
                   <i class="iconfont fr inputDe" >&#xe604;</i>
               </label>
           </div>
        </div>
        <div class="login-item">
            <div class="list">
                <label>
                    <i class="iconfont">&#xe601;</i>
                    <input type="text" class="listInput2" name="password" placeholder="请输入你的密码">
                    <i class="iconfont fr inputDe" >&#xe604;</i>
                </label>
            </div>
        </div>
        <div class="login-item yz">
            <div class="list">
                <label>
                    <i class="iconfont">&#xe600;</i>
                    <input style="width: 200px;" type="text" class="listInput3"  placeholder="请输入验证码">
                    <i class="iconfont fr inputDe" >&#xe604;</i>
                </label>
            </div>
        </div>
        <input type="button" id="btn" value="免费获取验证码"/>
        <input type="button" class="loginBtn" value="登录"/>
    </form>
    <div id="flooder">
    </div>
    
</body>

<script>
    $(document).ready(function(){
        $(".listInput1,.listInput2,.listInput3").focus(function() {
            $(this).siblings("label i").css("color","#ff669e");
            $(this).parent().parent(".list").css("border-bottom","1px solid #ff669e")
        });
        $(".listInput1,.listInput2,.listInput3").blur(function() {
            $(this).siblings("label i").css("color","#999999");
            $(this).parent().parent(".list").css("border-bottom","1px solid #cccccc")
        });
        $(".listInput1").focus(function(){$(this).removeAttr("placeholder");});
        $(".listInput1").blur(function(){$(this).attr("placeholder","请输入你的账号");});
        $(".listInput2").focus(function(){$(this).removeAttr("placeholder");});
        $(".listInput2").blur(function(){$(this).attr("placeholder","请输入密码");});
        $(".listInput3").focus(function(){$(this).removeAttr("placeholder");});
        $(".listInput3").blur(function(){$(this).attr("placeholder","请输入验证码");});

        $(".inputDe").click(function(){
            $(this).siblings("input").attr("value","");
        })
    });
    $(function() {
        var wait = 60;
        var btn = document.getElementById("btn");
        function time(o){
            if(wait==0){
                o.removeAttribute("disabled");
                o.value = "免费获取验证码";
                wait = 60;
            }else{
                o.setAttribute("disabled",true);
                o.value="重新发送("+wait+")";
                wait--;
                setTimeout(function(){
                    time(o);
                },1000);
                btn.style.backgroundColor = "#e0e0e0";
                btn.style.color="#999"
            }
        }
        document.getElementById("btn").onclick = function(){
            time(this);
        }
    })
</script>
</html>
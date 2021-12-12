<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
<link href="css.css" rel="stylesheet" type="text/css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>index page</title>
<script>
$(function() {        
    if (navigator.geolocation) {
        //위치 정보를 얻기
        navigator.geolocation.getCurrentPosition (function(pos) {
            $('#latitude').html(pos.coords.latitude);     // 위도
            $('#longitude').html(pos.coords.longitude); // 경도
            $("input[name=latitude]").val(pos.coords.latitude);
            $("input[name=longitude]").val(pos.coords.longitude);
            $('#latitude2').html(pos.coords.latitude);     // 위도
            $('#longitude2').html(pos.coords.longitude); // 경도
            $("input[name=latitude2]").val(pos.coords.latitude);
            $("input[name=longitude2]").val(pos.coords.longitude);
        });
    } else {
        alert("이 브라우저에서는 Geolocation이 지원되지 않습니다.")
    }
});
</script>

</head>
<body>
	<%
		String user = request.getParameter("user_id");
	%>

	<%@ include file="header.jsp"%>
	<%
	if(user == null || user.equals("")){
	%>
	<div class="container" style="text-align: center;">
		<form action="Controller?type=searchPro" method="post">
			<button type="button" class="btn btn-primary btn-lg btn-block" style="margin-top:100px;" onclick="user_check()">병원찾기</button>
		</form>	
		
		<form action="Controller?type=searchPro2" method="post">
			<button type="button" class="btn btn-primary btn-lg btn-block" style="margin-top:100px;" onclick="user_check()">약국찾기</button>
		</form>	
	</div>
	<%} else{%>
		<div class="container" style="text-align: center;">
		<form action="Controller?type=searchPro" method="post">
			<button type="submit" class="btn btn-primary btn-lg btn-block" style="margin-top:100px;">병원찾기</button>
			<input type="hidden" name="latitude" id="latitude" value=""/>
			<input type="hidden" name="longitude" id="longitude" value=""/>
		</form>	
		
		<form action="Controller?type=searchPro2" method="post">
			<button type="submit" class="btn btn-primary btn-lg btn-block" style="margin-top:100px;">약국찾기</button>
			<input type="hidden" name="latitude2" id="latitude2" value=""/>
			<input type="hidden" name="longitude2" id="longitude2" value=""/>
		</form>	
	</div>
	<%} %>
</body>

	<script type="text/javascript">	
		function user_check(){
			alert("로그인을 해주세요.");
			/* history.back(); */
		}
	</script>

</html>
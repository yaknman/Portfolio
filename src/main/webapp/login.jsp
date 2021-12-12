<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<form action="Controller?type=loginPro" method="post">
		<div class="login_form" style="text-align: center;">
			<div class="login_text">
				<h3 style="margin-top : 30px;">Login</h3>
			</div>
			<div>
				<input type="text" name="user_id" style="margin-top : 30px;" placeholder="아이디">
			</div>
			<div>
				<input type="password" name="user_pw" style="margin-top : 30px;" placeholder="비밀번호">
			</div>
			<div>
				<button type="submit" style="margin-top : 30px;" class="btn btn-primary">로그인</button>
			</div>
		</div>
	</form>
</body>
</html>
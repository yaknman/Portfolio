<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/design.css" rel="stylesheet" type="text/css">
<meta charset="UTF-8">
<title>Join us</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<div class="join_form" style="text-align: center;">
		<h3 style="margin-top:30px;">회원가입</h3>
		<form name="frm" id="frm" method="post"
			action="Controller?type=joinPro">
			<div class="join">
				<input type="text" name="name" id="name" style="margin-top:30px;" placeholder="이름">
			</div>
			<div class="join">
				<input type="text" name="user_id" id="user_id" style="margin-top:30px;" placeholder="아이디">
			</div>
			<div class="join">
				<input type="password" name="user_pw" id="user_pw" style="margin-top:30px;"
					placeholder="비밀번호">
			</div>
			<div class="join">
				<input type="password" name="user_pw_check" id="user_pw_check" style="margin-top:30px;"
					placeholder="비밀번호 확인">
			</div>
			<div class="join">
				<input type="text" name="phone" id="phone" style="margin-top:30px;" placeholder="전화번호">
			</div>
			<div class="join">
				<input type="text" name="email" id="email" style="margin-top:30px;" placeholder="이메일">
			</div>
			<div class="join">
				<button type="button" style="margin-top:30px;" onclick="check()" class="btn btn-primary">회원가입</button>
				<button type="reset" class="btn btn-primary" style="margin-top:30px;">다시작성</button>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript">
	function check() {
		var frm = document.getElementById("frm")
		var name = document.getElementById("name");
		var user_id = document.getElementById("user_id");
		var user_pw = document.getElementById("user_pw");
		var user_pw_check = document.getElementById("user_pw_check");
		var email = document.getElementById("email");
		var phone = document.getElementById("phone");

		if (name.value == "") {
			alert("이름을 입력하세요.");
			name.focus();
		} else if (user_id.value == "") {
			alert("아이디를 입력하세요.");
			user_id.focus();
		} else if (user_pw.value == "") {
			alert("비밀번호를 입력하세요.");
			user_pw.focus();
		} else if (user_pw.value !== user_pw_check.value) {
			alert("비밀번호가 일치하지 않습니다.")
			user_pw_check.focus();
		} else if (email.value == "") {
			alert("이메일을 입력하세요.");
			email.focus();
		} else if (phone.value == "") {
			alert("전화번호를 입력하세요.");
			phone.focus();
		} else {
			alert("회원가입 완료");
			frm.submit();
		}
	}
</script>

</html>
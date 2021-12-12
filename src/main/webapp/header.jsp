<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">

<title>Insert title here</title>
<link href="css/design.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%
	String user_id = (String) session.getAttribute("user_id");
	%>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Y.Doc</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarColor01"
				aria-controls="navbarColor01" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarColor01">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link active" href="Controller?type=index">Home
							<span class="visually-hidden">(current)</span>
					</a></li>
				</ul>
				<form class="d-flex" method="get">
					<ul>
						<%
						if (user_id == null) {
						%>
						<li><a href="Controller?type=login">login</a></li>
						<%}%>
						<%
						if (user_id != null) {
						%>
						<li><a href="Controller?type=logOut">logOut</a></li>
						<%
						}
						%>
						<li><a href="Controller?type=join">join us</a></li>
					</ul>
				</form>
			</div>
		</div>
	</nav>
</body>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
	crossorigin="anonymous"></script>
</html>
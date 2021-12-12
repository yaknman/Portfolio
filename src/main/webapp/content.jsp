<%@page import="model.HospInfo"%>
<%@page import="java.util.List"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a7d5c442fe39c2f174448e11241ea4dd"></script>
<title>Insert title here</title>
</head>
<body style="text-align: center;">
	<%@ include file="header.jsp"%>

	<%
	List<HospInfo> list = (List<HospInfo>) request.getAttribute("hospList");
	String hospName = request.getParameter("hospName");
	String latitude = request.getParameter("lat");
	String longitude = request.getParameter("logt");
	String subject = request.getParameter("subject");
	String address = request.getParameter("address");
	%>

	<div style="text-align: center;">
		<h3><%=hospName%></h3>
			
			<b>위치</b>
			<br>
			<br>
			<div id="map" style="width: 250px; height: 250px; margin:auto !important;"></div>
			<br>
			<b>진료과목</b> <br>
			<br>
			<a><%=subject%></a> <br>
			<br>
			<b>주소</b> <br>
			<br> 
			<a><%=address%></a>
	</div>

	<script>
		var latitude =
	<%=latitude%>
		;
		var longitude =
	<%=longitude%>
		;

		var container = document.getElementById('map');
		options = {
			center : new kakao.maps.LatLng(latitude, longitude),
			level : 3
		};

		var map = new kakao.maps.Map(container, options);

		//지도에 마커 표시

		var markerPosition = new kakao.maps.LatLng(latitude, longitude);

		/* console.log(latitude); */
		/* console.log(longitude); */

		var marker = new kakao.maps.Marker({
			position : markerPosition
		});

		marker.setMap(map);

		/* map.relayout(); */
	</script>
</body>
</html>
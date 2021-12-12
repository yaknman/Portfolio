<%@page import="model.ParmacyInfo"%>
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
<link href="css.css" rel="stylesheet" type="text/css">
<title>병원찾기</title>
<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a7d5c442fe39c2f174448e11241ea4dd"></script>
</head>
<body>
	<%@ include file="header.jsp"%>
	
	<%
	List<ParmacyInfo> list = (List<ParmacyInfo>) request.getAttribute("ParList");
	%>
	
	<%
	for (int i = 0; i < list.size(); i++) {
		ParmacyInfo ParList = list.get(i);
		
	%>
	<form action="Controller?type=content2&ParName=<%=list.get(i).getParName() %>&lat=<%=list.get(i).getLat() %>&logt=<%=list.get(i).getLogt() %>&address=<%=list.get(i).getAddress() %>" method="post">
	<div class="d-grid gap-2">
		<button class="btn-lg btn-light"><%=list.get(i).getParName()%><br><%=list.get(i).getDistance()%>km</button>
		<input type="hidden" id="latitude" value="<%=list.get(i).getLat()%>">
		<input type="hidden" id="longitude" value="<%=list.get(i).getLogt()%>">
	</div>	
	
	</form>
	<%}%>
	
</body>


</html> 
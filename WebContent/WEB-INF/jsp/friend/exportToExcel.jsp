<%@ page language="java"
	contentType="application/vnd.ms-excel; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String filename = request.getAttribute("filename").toString();
	response.setHeader("Content-Type", "application/vnd.ms-xls");
	response.setHeader("Content-Disposition", "inline; filename=FriendList_" + filename + ".xls");
%>​
</head>
<body>
	<table border="1">
		<tr>
			<th>이름</th>
			<th>나이</th>
			<th>성별</th>
			<th>지역</th>
			<th>그룹</th>
		</tr>
		<c:forEach items="${list}" var="detail" varStatus="status">
			<tr>
				<td>${detail.cus_name}</td>
				<td>${detail.cus_age}</td>
				<td>${detail.cus_gender}</td>
				<td>${detail.cus_locale}</td>
				<td>${detail.group_name}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<%@include file="/WEB-INF/jsp/common/left.jsp"%>

<!-- 본문내용 작성 -->
<%@ page import="twitter4j.auth.*, java.util.*, twitter4j.*"%>
<%
Twitter twitter = (Twitter)request.getAttribute("twitter");
long myId = twitter.getId();
ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
%>

<div id="content">

<%for(Status ht : homeTimeline){
	if(ht.getUser().getId() != myId){%>
		<p><%=ht.getUser().getName()%></p>
		<p><%=ht.getUser().getScreenName()%></p>
		<p><%=ht.getUser().getId()%></p>
		<p><%=ht.getText()%></p>
		<p><%=ht.getId()%></p>
		<hr>
		<!-- System.out.println("==timeline.do============================================================================");
		System.out.println("글쓴이 이름 : " + ht.getUser().getName());
		System.out.println("타임라인 스크린네임 : " + ht.getUser().getScreenName());
		System.out.println("글쓴이 아이디 : " + ht.getUser().getId());
		System.out.println("타임라인 내용 : " + ht.getText());
		System.out.println("타임라인 아이디 : " + ht.getId()); -->
	<%}

} %>

</div>
<!-- 본문 내용 끝 -->

<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
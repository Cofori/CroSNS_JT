<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="side">
	<div id="sns">
		<div>
			<!-- <p class="heart">♥</p> -->
			<p class="name">${sysId}様</p>
			<p class="wellcom">ようこそ</p>
			<!-- <p class="heart">♥</p> -->
		</div>
		<%-- <div class="fb_btn">
			<c:if test="${facebookLoginInfo == null}">
				<a href="/signin.do">Facebook Login</a>
			</c:if>
			<c:if test="${facebookLoginInfo != null}">
				<a href="/facebookLogOut.do">Facebook 連動中</a>
			</c:if>
		</div> --%>
		<div class="fb_btn">
			<%-- <%if(request.getSession().getAttribute("facebookLogin") == null){ %>
				<a href="/twitterSignin.do">Facebook Login</a>
			<% }%>
			<%if(request.getSession().getAttribute("facebookLogin") != null){ %>
				<a href="/twitterLogOut.do">Facebook 連動中</a>
			<% }%> --%>
			<%if(request.getSession().getAttribute("twitterLogin") == null){ %>
				<a href="/twitterSignin.do">Twitter Login</a>
			<% }%>
			<%if(request.getSession().getAttribute("twitterLogin") != null){ %>
				<a href="/twitterLogOut.do">Twitter 連動中</a>
			<% }%>
		</div>
	</div>
	
	<div>
		<div>
			<a href=""></a>
			<a href=""></a>
			<a href=""></a>
			<a href=""></a>
		</div>
		<div>
			<a href=""></a>
			<a href="/group.do">グループ登録</a>
			<a href=""></a>
			<a href=""></a>
		</div>
		<div>
			<a href=""></a>
			<a href=""></a>
			<a href=""></a>
		</div>
		
	</div>
</div>

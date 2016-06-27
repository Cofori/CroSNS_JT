<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<%@include file="/WEB-INF/jsp/common/left.jsp"%>

<!-- 본문 내용 시작 -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	
</script>



<style type="text/css">
table {
	width: 100%;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #BDBDBD;
	padding: 10px;
}

table tr th {
	background: #EAEAEA;
	padding: 10px;
}

.serch {
	float: left;
}

.excel {
	float: right;
}

/* 그룹팝업 ============================================================= */
.button {
	cursor: pointer;
	transition: all 0.3s ease-out;
}

.button:hover {
	background: #06D85F;
}

.overlay {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background: rgba(0, 0, 0, 0.7);
	transition: opacity 500ms;
	visibility: hidden;
	opacity: 0;
}

.overlay:target {
	visibility: visible;
	opacity: 1;
}

.popup {
	margin: 70px auto;
	padding: 20px;
	background: #fff;
	border-radius: 5px;
	width: 30%;
	position: relative;
	transition: all 5s ease-in-out;
}

.popup .close {
	position: absolute;
	top: 20px;
	right: 30px;
	transition: all 200ms;
	font-size: 30px;
	font-weight: bold;
	text-decoration: none;
	color: #333;
}

.popup .close:hover {
	color: #06D85F;
}

.popup .content {
	max-height: 30%;
	overflow: auto;
}

@media screen and (max-width: 700px) {
	.box {
		width: 70%;
	}
	.popup {
		width: 70%;
	}
}

#friendSerch, #submit {
	padding : 10px;
}
</style>

<!-- <a href="/friend2.do">페이스북 친구</a>
<a href="/friend.do">트위터 친구</a> -->

<div id=content>

	<!-- 友達検索　 -->
	<form action="/friendSerch.do" method="get" class="serch">
		<input type="text" name="friendSerch" id="friendSerch"
			placeholder="IDを入力してください。" /> <input type="submit" name="submit"
			id="submit" value="検索" />
	</form>

	<!-- エクセル出力　 -->
	<form name="searchForm" method="POST" action="/exportToExcel.do" class="excel">
		<!-- <input .....> -->
		<!-- <button type="button" onclick="exportToExcel();">엑셀출력</button> -->
		<input type="submit" id="submit" name="submit" value="Excel出力">
	</form>

	<div style="clear: both; width: 100%; height: 10px;"></div>

	<!-- 友達リスト　 -->
	<table border="1">
		<tr>
			<th>名前</th>
			<th>年齢</th>
			<th>性別</th>
			<th>地域</th>
			<th>グループ</th>
			<th>機能</th>
		</tr>
		<c:forEach var="fl" items="${list}">
			<tr>
				<td>${fl.cus_name}</td>
				<td>${fl.cus_age}</td>
				<td>${fl.cus_gender}</td>
				<td>${fl.cus_locale}</td>
				<td>${fl.group_name}</td>
				<td>
					<div class="box">
						<a class="button" href="#${fl.cus_no}">Group</a>
					</div>

					<div id="${fl.cus_no}" class="overlay">
						<div class="popup">
							<a class="close" href="#">&times;</a>
							<div>${fl.cus_name}님의
								현제 그룹은 「
								<c:if test="${fl.group_no != 0}">${fl.group_name}</c:if>
								<c:if test="${fl.group_no == 0}">없음</c:if>
								」입니다. 변경 하실 그룹을 선택 해 주세요.
							</div>
							<div class="content">
								<form action="/setGroup.do" method="POST">
								<input type="hidden" name="cusNo" value="${fl.cus_no}"/>
									<select class=box name='area'>
										<c:forEach var="gr" items="${group}">
											<option value="${gr.group_no}">
												<c:if test="${gr.group_no != 0}">${gr.group_name}</c:if>
												<c:if test="${gr.group_no == 0}">그룹없음</c:if>
											</option>
										</c:forEach>
									</select> <input type="submit" value="확인" />
								</form>
							</div>
						</div>
					</div>
					
					
					<div class="box">
						<a class="button" href="#${fl.cus_no}_memo">Memo</a>
					</div>

					<div id="${fl.cus_no}_memo" class="overlay">
						<div class="popup">
							<a class="close" href="#">&times;</a>
							<div class="content">
								<form action="/setMemo.do" method="GET">
								<input type="hidden" name="cusNo" value="${fl.cus_no}"/>
								<textarea rows="" cols="" name="setMemo">${fl.cus_memo}</textarea>
									 <input type="submit" value="확인" />
								</form>
							</div>
						</div>
					</div>
					
				</td>
			</tr>
		</c:forEach>
	</table>

</div>

<!-- 본문 내용 끝 -->

<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
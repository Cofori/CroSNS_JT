<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<%@include file="/WEB-INF/jsp/common/left.jsp"%>


<style type="text/css">
form {
	position: relative;
}



a:link {text-decoration: none; color: #696969}
a:vistied{text-decoration: none; color:#330066}
a:hover{text-decoration: none; color: #66ccff}


.tbcss1, .tbcss1 td, .tbcss1 th{
		border-collapse: collapse;
		border: 1px solid;
		font-size: 9pt;
		font-family: tahoma,굴림,돋움,verdana;
}

.tbcss2,.tbcss2 td, .tbcss2 th{
	border-collapse: collapse;
	border-top: 1px solid;
	border-bottom: 1px solid;
	border-left: 0px;
	border-right: 0px;
	font-size: 9pt;
	font-family: tohama,굴림,돋움,verdana;
}

textarea {
	background: #E4F7BA;
	border: solid #BCE55C 1px;
	border-radius: 5px;
	color: #6B9900;
}

.tabs {
	width: 908px;
	float: none;
	list-style: none;
	margin: 20px 0;
}

.tabs li {
	display: block;
	float: left;
}

.labels:after {
	content: '';
	display: table;
	clear: both;
}

.tabs label {
	display: inline-block;
	float: left;
	padding: 10px 20px;
	margin: 0 20px 0 0;
	color: #CCA63D;
	background: #FAECC5;
	cursor: pointer;
	border: solid #F2CB61 1px;
	border-radius: 5px;
}

.tabs label:hover {
	background: #F2CB61;
	color: #FAECC5;
}

.tab-content {
	display: none;
}

.tab-content>input {
	width: 120%;
	height: 43px;
	border: solid #F2CB61 1px;
	border-radius: 5px;
}

.tabs input[type=radio] {
	display: none;
}

.tabs input[type=radio]:checked+label {
	background: red;
}

[id^=tab]:checked ~ div[id^=tab-content] {
	display: block;
}

[id^=tab]:checked ~ [id^=label] {
	background: red;
	color: white;
}

#contents_submit {
	background: #FFD9EC;
	color: #F361A6;
	padding: 10px 20px;
	display: inline-block;
	text-align: center;
	font-size: 50px;
	line-height: 50px;
	border: solid #F361A6 1px;
	border-radius: 5px;
}

#contents_submit:HOVER {
	background: #F361A6;
	color: #FFD9EC;
}

#abtn {
	float: right;
	margin: 20px auto;
}
</style>

<script>
function Showhide(){
	var div = document.all.content;
	if(div.style.visibility == "hidden"){
		div.style.visibility = "visible"; 
	}else{
		div.style.visibility = "hidden";
	}
}


function move_box(an, box) {
	  var cleft = 0;
	  var ctop = 0;
	  var obj = an;
	  while (obj.offsetParent) {
	    cleft += obj.offsetLeft;
	    ctop += obj.offsetTop;
	    obj = obj.offsetParent;
	  }
	  box.style.left = cleft + 'px';
	  ctop += an.offsetHeight + 8;
	  if (document.body.currentStyle &&
	    document.body.currentStyle['marginTop']) {
	    ctop += parseInt(
	      document.body.currentStyle['marginTop']);
	  }
	  box.style.top = ctop + 'px';
	}
	 
	function show_hide_box(an, width, height, borderStyle) {
	  var href = an.href;
	  var boxdiv = document.getElementById(href);
	 
	  if (boxdiv != null) {
	    if (boxdiv.style.display=='none') {
	      move_box(an, boxdiv);
	      boxdiv.style.display='block';
	    } else
	      boxdiv.style.display='none';
	    return false;
	  }
	 
	  boxdiv = document.createElement('div');
	  boxdiv.setAttribute('id', href);
	  boxdiv.style.display = 'block';
	  boxdiv.style.position = 'absolute';
	  boxdiv.style.width = width + 'px';
	  boxdiv.style.height = height + 'px';
	  boxdiv.style.border = borderStyle;
	  boxdiv.style.backgroundColor = '#fff';
	 
	  var contents = document.createElement('iframe');
	  contents.scrolling = 'no';
	  contents.frameBorder = '0';
	  contents.style.width = width + 'px';
	  contents.style.height = height + 'px';
	  contents.src = href;
	 
	  boxdiv.appendChild(contents);
	  document.body.appendChild(boxdiv);
	  move_box(an, boxdiv);
	 
	
		
		return false;
	}

</script>



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<html>
<body>




<script type="text/javascript">
function groupCreate() {
	
   if(document.getElementById('preStat').value == "" || document.getElementById('preStat').value == 'none' ){
		document.getElementById('preStat').value = 'block';
		document.getElementById('nowStat').value = 'none';
		
	}
    else if(document.getElementById('preStat').value != "" || document.getElementById('preStat').value == 'block'){
		document.getElementById('preStat').value = 'none';
		document.getElementById('nowStat').value = 'block';
	} 
  
	 
	return document.getElementById('create').submit();
} 

function groupDelete(){
	/* var groupNO = document.getElementById('selectedGroup').value; */
	/*
	유효성체크 필요
	*/
	if(document.getElementById('preStat').value == "" || document.getElementById('preStat').value == 'none' ){
		document.getElementById('preStat').value = 'block';
		document.getElementById('nowStat').value = 'none';
		
	}
    else if(document.getElementById('preStat').value != "" || document.getElementById('preStat').value == 'block'){
		document.getElementById('preStat').value = 'none';
		document.getElementById('nowStat').value = 'block';
	} 
	if (confirm("グループを削除しますか？"))
	{return document.getElementById('create').submit();}
	return false;
}
</script>




<!-- return show_hide_box(this,300,200,'1px solid'); -->
<style type="text/css">
	#content2, #content2 input{
		display: ;
	}
	#content, #content input{
		display: ;
	}
</style>



	<form action="/group.do" id="create" name="create" method="post">
			<input type="hidden" name="preStat" id="preStat" value="${status}">
			<input type="hidden" name="nowStat" id="nowStat">
			<input type="hidden" name="selectedGroup" id="selectedGroup">
			
		<div id="content" style="display:${status}">
			<input type="button" onClick="groupCreate()" value="グループ生成" >
			<div>
			<table cellpadding=5  class="tbcss2"　> 	
				<th><tr><td>&nbsp;グループ番号&nbsp;<td>&nbsp;グループ名&nbsp;<td>&nbsp;グループ管理&nbsp;
				<c:if test="${name!=null}">
					<c:forEach var="name" items="${name}" varStatus="status">
						<c:forEach var="no" items="${no}" varStatus="noStatus">
		  					<c:if test="${noStatus.index==status.index}">
		  						<tr align="center"	style="cursor:hand" <%-- onclick="location.replace('/main.do?no=${no}')" --%>>
		  							<input type="hidden" id="${name}" value="${no}" >
								   <td>${status.index+1} <td>${name} <td><input type="button" onclick="document.getElementById('selectedGroup').value=${no};groupDelete();" value="削除">
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:if>
			</table>
			</div>
		</div>
	
		<div id="content2" style="display:${status2}">
			 <input type="text" value="" name="newGroupName">
			 <input type="button" onClick="groupCreate()" value="グループ生成">
			 <br>${message}
		</div>
	</form>




<!-- 본문 내용 끝 -->
</body>
</html>

<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
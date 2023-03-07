<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.3.js" ></script>
</head>
<body>
<h1>게시판 글목록</h1>
<table border="1">
		<tr>
			<td>글번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>
<c:forEach items="${boardlist }" var="board">
		<tr>
			<td>${board.boardNum }</td>
			<td><a href="<%=request.getContextPath()%>/board/read?boardNum=${board.boardNum }">${board.boardTitle }</a></td>
			<td>${board.boardWriter }</td>
			<td>${board.boardDate }</td>
			<td>${board.boardReadCount }</td>
		</tr>
</c:forEach>
</table>
 <%-- [${boardlist }]

 	${board.boardNum }<br>--%>
	<%-- ${boardlist.boardNum }<br> --%>
<hr>
	<c:forEach begin="${pageInfo.startpage }" end ="${pageInfo.endpage }" var="page">
	${page }
	<c:if test="${pageInfo.endpage != page}">
	,
	</c:if>
	</c:forEach>
<hr>	
</body>
</html>
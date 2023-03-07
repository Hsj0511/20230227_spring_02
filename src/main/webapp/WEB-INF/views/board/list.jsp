<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
[${boardlist }]
<c:forEach items="${boardlist }" var="board">
	<%-- ${boardlist.boardNum }<br> --%>

	${board.boardNum }<br>
</c:forEach>


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
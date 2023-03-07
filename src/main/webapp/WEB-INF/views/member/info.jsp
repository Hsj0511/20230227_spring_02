<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내정보</title>
 <script src="https://code.jquery.com/jquery-3.6.3.js" ></script>
</head>
<body>

<form id="frmInfo" method="get">
<input value="${membervo.id }" type="text" name="id" readonly="readonly" ><br>
		<input  value="${membervo.passwd }"type="password" name="passwd" placeholder="pass"><br>
		<input value="${membervo.name }"type="text" name="name" placeholder="name" readonly="readonly"><br>
		<input value="${membervo.email }"type="text" name="email" placeholder="email" readonly="readonly"><br>
		
		<button type="button" onclick="frmSubmit('update');">수 정</button>
		<button type="button" onclick="frmSubmit('delete');">탈 퇴</button>

</form>

<script>
	console.log($("button").eq(2).text());
	console.log($("button").get(2).innertext());

	$("botton").eq(0).click('update', frmSubmit2);
	$("botton").eq(1).click('delete', frmSubmit2);
	function frmSubmit2(event) {
		console.log(this); // this: click이 발생한 시점에 element
		frmInfo.action = event.data;
		frmInfo.submit();
	}
	
	function frmSubmit(targetEle) {
		console.log(this); //this: window 객체 전부
		
//			form.action = teargetEle;
//			document.querySelector(".aaa.bbb[type=text]").action = targetEle;
//			document.querySelectorAll(".aaa")[0].action = targetEle;
//			$("#frmInfo").attr("action",targetEle);
//			$("frmInfo").submit();
			frmInfo.action = targetEle;
			frmInfo.submit();
	}

</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mLogin</title>
<script src="jquery/jquery-3.6.1.min.js"></script>
<script>
	


</script>
</head>
<body>

	<div align="center">
		<h3>로그인</h3>
		<table border="1">
			<tr>
				<td>아이디</td>
				<td><input type="text" name="memberId" autofocus required></td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td>
					<input type="password" name="passwd" required><br>
					<span id="resultMsg"></span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="button" id="loginBtn" value="로그인"></td>
			</tr>
		</table>
	</div>
	
</body>
</html>
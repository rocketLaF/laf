<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<!DOCTYPE html>
		<html lang="en">

		<head>
			<meta charset="UTF-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>Document</title>

			<script src='../resources/js/login.js'></script>
			<script>
				// 요놈!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				let param = "${param.error}";
			</script>

		</head>


		<body>
			<jsp:include page="../UI/topMenu.jsp" flush="true" />

			<div class="login-box">
				<h2>Login</h2>
				<form method="post" onsubmit="return chkBlank()">
					<div class="user-box">
						<label>Username</label>
						<br>
						<input type="text" name="username" id="sublogin">
					</div>
					<div class="user-box">
						<label>Password</label>
						<br>
						<input type="password" name="password">
					</div>
					<input type="submit" value="로그인">
					<input type="button" value="회원가입" onclick="location.href='/user/signUp'">
				</form>
			</div>

			<jsp:include page="../UI/sideMenu.jsp" flush="true" />

		</body>

		</html>
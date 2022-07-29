<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- security teglibrary -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="../resources/css/reviewWrite.css" type="text/css">

    <title>Document</title>
</head>
<body>
    <!-- security tags starts-->
    <sec:authorize access="isAnonymous()">
        <!-- <button class="btn" sec:authorize="isAnonymous()"
            onclick="location.href='/user/login'">로그인</button> -->
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="name" var="loginUserName" />
        <!-- <span class="item">${loginUserName}님 환영합니다</span>
        <button class="btn" onclick="location.href='/lostWrite'">글쓰기</button>
        <button class="btn" onclick="location.href=''">마이페이지</button>
        <button class="btn" onclick="location.href='/user/logout'">로그아웃</button> -->
    </sec:authorize>
    <!-- security tags ends-->

    <div class="userReview-box">
 <!-- 임시 --><h2>후기 작성  페이지</h2>
		<form action="/review/saveReview" method="get" onsubmit="return submitBtn()" id="formTag">
 <!-- 임시 --><div>${loginUserName}</div> 
            <input type="hidden" name="rUserId" value="${loginUserName}">
            <input type="hidden" name="rRevieweeId" value="${revieweeId}">
            <br>
			<div class="reviewee-id-text">
				<label>후기준 아이디</label> &nbsp; <span>${revieweeId}</span>
			</div>
            <br>
			<div class="content-box">
				<label>후기내용</label>
				<br>
                <textarea rows="10" cols="80" name="rContent"></textarea>
			</div>
			<div>
                <input type="radio" name="rOption" value="option1"> 응답이 빨라요! 
                <br>
                <input type="radio" name="rOption" value="option2"> 물건이 무사히 돌아왔어요! 
                <br>
                <input type="radio" name="rOption" value="option3"> 약속 시간을 잘 지켜요!
                <br>
            </div>
            <div class="star-rating space-x-4 mx-auto">
                <input type="radio" id="5-stars" name="rScore" value="5" v-model="ratings"/>
                <label for="5-stars" class="star pr-4">★</label>
                <input type="radio" id="4-stars" name="rScore" value="4" v-model="ratings"/>
                <label for="4-stars" class="star">★</label>
                <input type="radio" id="3-stars" name="rScore" value="3" v-model="ratings"/>
                <label for="3-stars" class="star">★</label>
                <input type="radio" id="2-stars" name="rScore" value="2" v-model="ratings"/>
                <label for="2-stars" class="star">★</label>
                <input type="radio" id="1-star" name="rScore" value="1" v-model="ratings" />
                <label for="1-star" class="star">★</label>
                <p id="starlabel">별점주기</p>
            </div>
            <br>
			<input type="submit" value="확인">
		</form>
	</div>

    
</body>
</html>
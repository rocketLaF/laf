<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../resources/css/reviewWrite.css" type="text/css">
    <script src="../resources/js/review.js"></script>
    <script>
        let option = "${reviewDetail.ROption}";
        let score = "${reviewDetail.RScore}";
    </script>

    <title>Document</title>
</head>
<body>
        <sec:authentication property="name" var="loginUserName" />
    <div class="userReview-box">
 <!-- 임시 --><h2>리뷰 디테일  페이지</h2>
            <input type="hidden" name="rUserId" value="${loginUserName}">
            <input type="hidden" name="rRevieweeId" value="${revieweeId}">
            <br>
			<div class="reviewee-id-text">
				<label>후기준 아이디</label> &nbsp; <span>${reviewDetail.RRevieweeId}</span>
			</div>
            <br>
			<div class="content-box">
				<label>후기내용</label>
				<br>
                <textarea rows="10" cols="80" name="rContent">${reviewDetail.RContent}</textarea>
			</div>
			<div>
                <input type="radio" name="rOption" value="option1" id="fast" onclick="return(false);"> 응답이 빨라요! 
                <br>
                <input type="radio" name="rOption" value="option2" id="safe" onclick="return(false);"> 물건이 무사히 돌아왔어요! 
                <br>
                <input type="radio" name="rOption" value="option3" id="timely" onclick="return(false);"> 약속 시간을 잘 지켜요!
                <br>
            </div>
            <div class="star-rating space-x-4 mx-auto">
                <input type="radio" id="5-stars" name="rScore" value="5" v-model="ratings" onclick="return(false);"/>
                <label for="5-stars" class="star pr-4">★</label>
                <input type="radio" id="4-stars" name="rScore" value="4" v-model="ratings" onclick="return(false);"/>
                <label for="4-stars" class="star">★</label>
                <input type="radio" id="3-stars" name="rScore" value="3" v-model="ratings" onclick="return(false);"/>
                <label for="3-stars" class="star">★</label>
                <input type="radio" id="2-stars" name="rScore" value="2" v-model="ratings" onclick="return(false);"/>
                <label for="2-stars" class="star">★</label>
                <input type="radio" id="1-star" name="rScore" value="1" v-model="ratings" onclick="return(false);"/>
                <label for="1-star" class="star">★</label>
                <p id="starlabel">별점주기</p>
            </div>
            <br>
			<input type="button" value="뒤로가기" onclick="location.href='/review'">
	</div>

    
</body>
</html>
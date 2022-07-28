<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- security teglibrary -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>LAF커뮤니티</title>


    <link rel="stylesheet" href="/resources/css/button.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/comBoard.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer_btn.css" type="text/css">
    <link rel="stylesheet"
          href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <script src='/resources/js/main_sidebar.js'></script>

</head>

<body class="body_container">
<div class="wrapper">
    <jsp:include page="../UI/topMenu.jsp" flush="true"/>


    <div class="contents_container">
        <div>
            <c:choose>
                <c:when test="${empty pDetail}">
                    <div><img width="300" height="169" src="/resources/img/woo.png"></div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${pDetail}" var="pd">
                        <article class="location-listing">
                            <div class="location-image">
                                <img width="300" height="169" src="/src/main/resources/static/img/profile/songokuf.gif" alt="">
                            </div>
                        </article>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <div>${uDetail.userName}유저네임</div>
        <div>${cbDetail.CLocation}</div>
        <div>${cbDetail.CTitle}</div>
        <div>${cbDetail.CCategory}</div>
        <div>${cbDetail.CCreateDate}</div>
        <div>${cbDetail.CContent}</div>
        <div>${hDetail.hashKeyword}</div>
        <div>
            <input type="button" value="목록" onclick="location.href='/cBoard'">
            <input type="button" value="수정" onclick="location.href='/cBoard/update/${cbDetail.CBoardNo}'">
            <input type="button" value="삭제" onclick="location.href='/cBoard/delete/${cbDetail.CBoardNo}'">
        </div>
    </div>


    <jsp:include page="../UI/sideMenu.jsp" flush="true"/>

</div>
</body>

</html>
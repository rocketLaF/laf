<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ReviewDetail</title>
    
    <link rel="stylesheet" href="/resources/css/comBoard.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer_btn.css" type="text/css">
    <link rel="stylesheet"
          href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <script src='/resources/js/main_sidebar.js'></script>

</head>
<body class="body_container">
    <div class="wrapper">
        <div class="section">
            <div class="top_navbar">
                <div class="hamburger">
                    <a href="#">
                        <i class="fi fi-rr-menu-burger"></i>
                    </a>
                </div>
                <div class="right_nav">
                    <button class="btn" onclick="location.href='/Lostwrite'">글쓰기</button>
                    <button class="btn" onclick="location.href='/user/login'">로그인/마이페이지</button>
                </div>
            </div>
        </div>
             <!-- SIDE menu -->
    <jsp:include page="../UI/sideMenu.jsp" flush="true"/>
    <script src='../resources/js/readImage.js'></script>
    <script src='../resources/js/main_sidebar.js'></script>
    <script src='../resources/js/naverMapApiTest3.js'></script>
            </ul>
        </div>
    </div>
</html>
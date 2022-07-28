<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mypage</title>

    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <link rel="stylesheet" href="/resources/css/comBoard.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer_btn.css" type="text/css">
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="/resources/css/myPage.css" type="text/css">
    <script type="text/javascript"
                src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=0dxd3s19ri"></script>
            <script type="text/javascript"
                src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=0dxd3s19ri&submodules=geocoder"></script>
    <script src='/resources/js/main_sidebar.js'></script>
    <script src='../resources/js/myPage.js'></script>

</head>
<style>
    .mypage_container{
     margin-left: 300px;
    }
</style>
<body class="body_container">
    <jsp:include page="../UI/topMenu.jsp" flush="true" />
    <!-- security tags starts-->
    <sec:authorize access="isAnonymous()">
        <button class="btn" sec:authorize="isAnonymous()"
            onclick="location.href='/user/login'">로그인</button>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal.username" var="loginUserName" />
        
    </sec:authorize>
    <!-- security tags ends-->
    
    <div class = "mypage_container" id="mypage">
            <!-- post -->
            <form action = "" method="post" enctype="multipart/form-data">
                <input type="file" name = "file">
                <button type="submit">프로필 업데이트</button>
            </form>
            <h3>------ GET POST 경계 -------</h3>
            <h3>프로필</h3>
            <form action = "" method="get" enctype="multipart/form-data">
                <img src = "resources/img/profile/${img.originalFileName}">
            </form>
            <!-- get -->
            <ul>
                <li>
                    <span class="item">${file}</span>
                </li>
                <li>
                    <span class="item">No = ${dto.userNo}</span>
                    <span class="item">ID = ${loginUserName}</span>
                </li>
                <li>
                    <span class="item">NAME = ${dto.userName}</span>
                </li>
                <li>
                    <span class="item">00동 = ${dto.userLocation}</span>
                </li>
                <li>
                    <span class="item">▼ 〓</span>
                </li>
                <li>
                    <span class="item">동네설정</span>
                </li>
                <li>
                    <span class="item">누적사례금 | 000000 원 =</span>
                </li>
                <li>
                    <span class="item">사례 횟수 | 000 번</span>
                </li>
                <li>
                    <span class="item">사례금 잔액 | 000000 원</span>
                </li>
                <li>
                    <a href = "/myPage/founddetail">
                        <span class="item">내가 찾아준 내역 ▷</span>
                    </a>
                </li>
                <li>
                    <a href = "/myPage/reviewdetail">
                        <span class="item">내 후기 모아보기 ▷</span>
                    </a>
                </li>
            </ul>
        </div>
        <jsp:include page="../UI/sideMenu.jsp" flush="true" />


            <script src='resources/js/readImage.js'></script>
            <script src='resources/js/main_sidebar.js'></script>
            <script src='resources/js/naverMapApiTest3.js'></script>

        
    
</body>

</html>
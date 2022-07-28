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
    <script src='../resources/js/main_sidebar.js'></script>
    <script src='../resources/js/myPage.js'></script>

</head>
<style>
    .mypage_container{
     margin-left: 300px;
    }
</style>
<body class="body_container">
    <jsp:include page="../UI/topMenu.jsp" flush="true" />
    
    <div class = mypage_container>
    <table border = "1">
    <c:choose>
        <c:when test="${empty list}">
            <tr>
                <td colspan="4">-------작성된 글이 존재하지 않습니다.-------</td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach items="${list}" var="dto">
                <tr>
                    <td><img src = ../resources/img/profile/songokug.jpg style = "width : 50px; height :50px;"></td>
                    <td>${dto.userNo }</td>
                    <td>${dto.userName }</td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table> 
</div>   
    







    <jsp:include page="../UI/sideMenu.jsp" flush="true" />
            <script src='../resources/js/readImage.js'></script>
            <script src='../resources/js/main_sidebar.js'></script>
            <script src='../resources/js/naverMapApiTest3.js'></script>
        
</body>
</html>
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

    <title>채팅리스트</title>


    <link rel="stylesheet" href="/resources/css/button.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/comBoard.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer_btn.css" type="text/css">
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">

</head>

<body class="body_container">
<div class="wrapper">
    <jsp:include page="../UI/topMenu.jsp" flush="true"/>
    <div class="contents_container">
        <div class="row">
            <div class="col-md-12">
                <h3>채팅방리스트</h3>
                <c:choose>
                    <c:when test="${empty roomList}">
                        아직 채팅방이 존재하지 않습니다.
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${roomList}" var="rL">
                            <div class="chatList" onclick="location.href='/comm/room/enter/${rL.roomId}'">
                                ${rL.roomId}
                                ${rL.boardNo}
                                ${rL.title}
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div><!-- <%-- contents_container --%> -->
</div><!-- <%--wrapper--%> -->
</body>
</html>
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
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <script src='/resources/js/main_sidebar.js'></script>
    <%-- ajax를 위한 script START--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <%-- ajax를 위한 script END--%>
    <script type="text/javascript">
        let boardNo = "${cbDetail.boardNo}";
    </script>
    <script type="text/javascript">
        function getClickId(clickId) {
            let picNo = clickId;
            $.ajax({
                type: "get",
                data: "picNo=" + picNo,
                dataType: "json",
                url: "/picture/delete/" + picNo,
                success: function (pictureList) {
                    let tags = '';
                    for (let i = 0; i < pictureList.length; i++) {
                        tags += "<article class='location-listing'>";
                        tags += "<div class='location-image'>";
                        tags += "<img width='300' height='169' src='" + pictureList[i].storedFilePath + "' alt='사진을 불러올수가 엄써' class='img' />";
                        tags += "<input type='button' value='삭제' id='" + pictureList[i].picNo + "' class='imgDeleteBtn' onclick='getClickId(this.id)'>";
                        tags += "</div>";
                        tags += "</article>";
                        //엘리먼트만 지운다.
                    }
                    $("#imageList").html();
                    $("#imageList").html(tags);
                }
            })
        }
    </script>
</head>


<body class="body_container">
<jsp:include page="../UI/comTopMenu.jsp" flush="true"/>

<div class="contents_container">
    <form action="/cBoard/update/${cbDetail.boardNo}" method="post" enctype="multipart/form-data">
        <div id="imageList">
            <c:choose>
                <c:when test="${empty pDetail}">
                    <td>등록된 사진이 없습니다</td>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${pDetail}" var="pd">
                        <article class="location-listing">
                            <div class="location-image">
                                <img width="300" height="169" src="${pd.storedFilePath}"
                                     alt="사진을 불러올수가 엄써" class="img"/>
                                <input type="button" value="삭제" id="${pd.picNo}" class="imgDeleteBtn" onclick="getClickId(this.id)">
                            </div>
                        </article>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

        </div>
        <input type="hidden" name="boardNo" value="${cbDetail.boardNo}">
        <table>
            <tr>
                <div id="image_container"></div>

                <td><input type="file" name="pictureUpload" id="pictureUpload" multiple="multiple"
                           accept="image/*" onchange="setThumbnail(event);"></td>
            </tr>
            <tr>
                <td><input type="text" name="title" value="${cbDetail.title}"></td>
            </tr>
            <tr>
                <td>
                    <textarea name="content">${cbDetail.content}</textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" value="취소" onclick="location.href='/cBoard'">
                    <input type="submit" value="확인">
                </td>
            </tr>
        </table>
    </form>
</div>


<jsp:include page="../UI/sideMenu.jsp" flush="true"/>
<script src='/resources/js/readImage.js'></script>

</body>

</html>
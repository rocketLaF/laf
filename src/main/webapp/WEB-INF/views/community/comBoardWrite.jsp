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


    <link rel="stylesheet" href="/resources/css/comBoard.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/header_footer_btn.css" type="text/css">
    <link rel="stylesheet"
          href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <script src='/resources/js/main_sidebar.js'></script>

</head>

<body class="body_container">

<jsp:include page="../UI/topMenu.jsp" flush="true"/>


<div class="contents_container">
    <form action="/cBoard/write" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <div id="image_container"></div>
                <div><input type="file" name="pictureUpload" id="pictureUpload" multiple="multiple"
                            accept="image/*" onchange="setThumbnail(event);"/>
                </div>
            </tr>
            <tr>
                <div class="uploadResult"></div>
            </tr>
            <tr>
                <td><input type="text" name="cTitle" placeholder="글 제목을 입력해주세요"></td>
            </tr>
            <tr>
                <td><input type="text" name="cCategory" placeholder="카테고리 입력"></td>
            </tr>
            <tr>
                <td>
                    <textarea name="cContent" placeholder="글 내용을 입력해주세요"></textarea>
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

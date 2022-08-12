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
                <title>LaF</title>


                <link rel="stylesheet"
                    href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">

                <link rel="stylesheet" href="resources/css/main.css" type="text/css">
                <link rel="stylesheet" media="screen and (max-width:390px)" href="resources/css/main_mobile.css"
                    type="text/css">



                <script src='resources/js/main_sidebar.js'></script>



                <link rel="stylesheet" href="resources/css/header_footer.css" type="text/css">
                <link rel="stylesheet" href="resources/css/header_footer_btn.css" type="text/css">


            </head>

            <body class="body_container">


                <jsp:include page="UI/topMenu.jsp" flush="true" />

                <div></div>
                <!-- 컨텐츠 삽입부분-->
                <div class="contents_container">

                    <div class="child-page-listing">

                        <h2 style="text-align: center;">실시간베너 만들것 </h2>

                        <div class="grid-container">

                            <c:choose>
                                <c:when test="${empty lostlist }">
                                    <td colspan="4">----작성된 글이 존재하지 않습니다----</td>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${lostlist}" var="lostboard">
                                        <article class="location-listing">
                                            <a class="location-title" href="${lostboard.LBoardNo}" name="lBoardNo">
                                                ${lostboard.LTitle} </a>
                                            <div class="location-image">
                                                <a href="#">
                                                    <img width="300" height="169" src="${lostboard.storedFilePath}"
                                                        alt="${lostboard.LTitle}">
                                                </a>
                                            </div>
                                        </article>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <jsp:include page="UI/sideMenu.jsp" flush="true" />


            </body>

            </html>
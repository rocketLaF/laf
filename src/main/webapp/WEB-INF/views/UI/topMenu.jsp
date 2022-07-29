<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!-- security teglibrary -->
        <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


            <!DOCTYPE html>
            <html lang="en">

            <head>


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
                                <!-- security tags starts-->
                                <sec:authorize access="isAnonymous()">
                                    <button class="btn" sec:authorize="isAnonymous()"
                                        onclick="location.href='/user/login'">로그인</button>
                                </sec:authorize>
                                <sec:authorize access="isAuthenticated()">
                                    <sec:authentication property="principal.username" var="loginUserName" />
                                    <sec:authentication property="principal.userNo" var="principal" />
                                    <span class="item">${loginUserName}님 환영합니다</span>
                                    <!-- <span class="item">${principal}번호님 환영합니다</span> -->
                                    <button class="btn" onclick="location.href='/write'">글쓰기</button>
                                    <button class="btn" onclick="location.href=''">마이페이지</button>
                                    <button class="btn" onclick="location.href='/user/logout'">로그아웃</button>
                                </sec:authorize>
                                <!-- security tags ends-->
                            </div>
                        </div>
                    </div>

            </body>

            </html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <sec:authentication property="name" var="loginUserName" />
</head>
<body>
        <table border="1">
            <colgroup>
                <col width="50">
                <col width="200">
                <col width="300">
            </colgroup>
            <tr>
                <th>번호
                </th>
                <th>내가 남긴 후기
                </th>
                <th>일시
                </th>
            </tr>
            <c:choose>
                <c:when test="${empty reviewList }">
                    <tr>
                        <td colspan="2" align="center"> --------- 작성된 글이 없습니다 ----------
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${reviewList }" var="list" varStatus="number">
                        <tr>
                            <td>
                                ${number.count }
                            </td>
                            <td>
                                <a href="/review/details?reviewNo=${list.reviewNo }">${list.RContent }
                                </a>
                            </td>
                            <td>${list.RDate }
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </table>

    
</body>
</html>
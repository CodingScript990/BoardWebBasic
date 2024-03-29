<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${requestScope.title}</title> <!-- 받은 타이틀값 -->
    <link rel="stylesheet" href="/res/css/common.css?ver=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <%-- 브라우저에서 실행할때 다운로드 되어서 실행해주는 것 --%>
</head>
<body>
    <div class="container">
        <div class="header">
            <!--
                로그아웃 상태면 메뉴 : 로그인, 회원가입
                로그인 상태면 메뉴 : 로그아웃
             -->
            <ul class="topMenu">
                <li><a href="/board/list">게시판</a></li>
                <li><a href="/board/rank?type=1">조회수 Top 10</a></li>
                <li><a href="/board/rank?type=2">댓글수 Top 10</a></li>
                <li><a href="/board/rank?type=3">좋아요 Top 10</a></li>

                    <c:if test="${sessionScope.loginUser != null}">
                        <li><a href="/board/regmod">글쓰기</a></li>
                        <li><a href="/user/profile">마이페이지</a></li>
                        <li>${sessionScope.loginUser.nm}(${sessionScope.loginUser.uid})님 환영합니다.</li>
                        <li><a href="/user/logout">로그아웃</a></li>
                    </c:if>

                    <c:if test="${sessionScope.loginUser == null}">
                        <li style="flex-grow: 1; text-align: right;"><a href="/user/login">로그인</a></li>
                        <li style="flex-grow: 0; text-align: right"><a href="/user/join">회원가입</a></li>
                    </c:if>

            </ul>
        </div>
        <div class="body">
            <jsp:include page="/WEB-INF/view/${requestScope.page}.jsp"></jsp:include>
        </div>
        <!-- jsp파일을 넣을 곳!(포함을 시키겠다는 의미) -->
        <div class="footer">
            footer
        </div>
    </div>
    <c:if test="${requestScope.err != null}">
        <script>
            let body = document.querySelector('body');

            body.onload = () => {
                setTimeout( () => {
                    alert(<c:out value="${requestScope.err}"/>);
                }, 300);
            }
        </script>
    </c:if>
</body>
</html>
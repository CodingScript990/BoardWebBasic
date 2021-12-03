<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/res/css/board/detail.css">
<%-- 글번호, 글제목, 글내용, 조회수, 작성자(이름), 등록일시 --%>
<div>
<%-- 내가 쓴 글이면 버튼이 나타낙 해주기! --%>
    <c:if test="${sessionScope.loginUser.iuser == requestScope.data.writer}">
        <div>
            <a href="/board/del?iboard=${requestScope.data.iboard}"><button>삭제</button></a>
            <a href="/board/regmod?iboard=${requestScope.data.iboard}"><button>수정</button></a>
        </div>
    </c:if>
    <div class="counting">조회수 : <c:out value="${requestScope.data.hit}"/></div>
    <div>글번호 : ${requestScope.data.iboard}</div>
    <div>글제목 :<c:out value="${requestScope.data.title}"/></div>
    <div>글내용 : <c:out value="${requestScope.data.ctnt}"/></div>
    <div>작성자(이름) : <c:out value="${requestScope.data.writerNm}"/></div>
    <div>등록일시 : <c:out value="${requestScope.data.mdt}"/></div>

</div>
<script src="/res/js/board/detail.js"></script>
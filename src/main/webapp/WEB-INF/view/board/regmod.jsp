<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <form action="/board/regmod" method="post">
        <input type="hidden" name="iboard" value="${param.iboard}">
        <%-- param을 사용해서 데이터 값을 담아온다 --%>
        <div><label>제목: <input type="text" name="title" value="<c:out value="${requestScope.data.title}"/>"></label></div>
        <div><label>내용: <textarea name="ctnt"><c:out value="${requestScope.data.ctnt}"/></textarea></label></div>
        <div>
            <input type="submit" value="${title}">
            <input type="reset" value="초기화">
        </div>
    </form>
</div>
<c:if test="${err != null}">
    <script>
        let body = document.querySelector('body');
        body.onload = function () {
            setTimeout(function () {
                alert('<c:out value="${requestScope.err}"/>')
            }, 300);
        }
    </script>
</c:if>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="/res/css/board/detail.css?ver=2">
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

        <c:if test="${sessionScope.loginUser.iuser != null}">
            <div>
                <form action="/board/cmt/reg" method="post">
                    <input type="hidden" name="iboard" value="${requestScope.data.iboard}">
                    <div>
                        댓글 : <input type="text" name="ctnt" id="ctnt" placeholder="댓글 내용">
                        <input type="submit" value="댓글작성">
                    </div>
                </form>
            </div>
        </c:if>

    <div>
        <table class="commentTable">
            <colgroup>
                <col width="60%">
                <col>
                <col>
            </colgroup>
            <tr>
                <th>댓글</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>비고</th>
            </tr>
            <c:forEach items="${requestScope.cmtList}" var="item">
                <tr class="comments">
                    <td><c:out value="${item.ctnt}"/></td>
                    <td>${item.writerNm}</td>
                    <td>${item.mdt}</td>
                    <c:if test="${sessionScope.loginUser.iuser == requestScope.data.writer}">
                        <td>
                            <div>
                                <button onclick="openModForm(${item.icmt}, '${item.ctnt}');">수정</button>
                                <button onclick="isDelCmt(${requestScope.data.iboard}, ${item.icmt});">삭제</button>
                            </div>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>

<div class="cmtModContainer">
    <div class="cmtModBody">
        <form action="/board/cmt/mod" method="post" id="cmtModFrm">
            <input type="hidden" name="iboard" value="${requestScope.data.iboard}">
            <input type="hidden" name="icmt">
            <div>
                <input type="text" name="ctnt" placeholder="댓글 내용">
                <input type="submit" value="수정">
                <input type="button" value="취소" id="btnCancel">
            </div>
        </form>
    </div>
</div>

<script src="/res/js/board/detail.js?ver=2"></script>
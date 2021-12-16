<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/res/css/board/list.css?ver=2">

<div class="search">
    <form action="/board/list" method="get" id="searchFrm">
        <diV>
            <select name="searchType">
                <option value="1" ${param.searchType == 1 ? 'selected' : ''}>제목</option>
                <option value="2" ${param.searchType == 2 ? 'selected' : ''}>내용</option>
                <option value="3" ${param.searchType == 3 ? 'selected' : ''}>제목/내용</option>
                <option value="4" ${param.searchType == 4 ? 'selected' : ''}>글쓴이</option>
                <option value="5" ${param.searchType == 5 ? 'selected' : ''}>전체</option>
            </select>
            <input type="search" name="searchText" value="${param.searchText}">
            <input type="submit" value="검색">
            <%-- 나타나는 행 수  --%>
            <select name="rowCnt">
                <c:forEach var="cnt" begin="5" end="30" step="5">
                    <option value="${cnt}" ${cnt == param.rowCnt ? 'selected' : ''}>${cnt}개</option>
                </c:forEach>
            </select>
        </diV>
    </form>
</div>

<c:choose>
    <%-- when : if역할 --%>
    <c:when test="${requestScope.maxPageNum == 0}">
        <div>글이 없습니다.</div>
    </c:when>
    <%-- otherwise else역할 --%>
    <c:otherwise>
        <div>
            <table class="boardTable">
                <colgroup>
                    <col width="20%">
                    <col>
                    <col>
                    <col width="100px">
                    <col>
                </colgroup>
                <tr>
                    <th>no</th>
                    <th>title</th>
                    <th>hits</th>
                    <th>writer</th>
                    <th>reg-datetime</th>
                </tr>
                <c:forEach items="${requestScope.list}" var="item">
                    <%-- servlet에 해놓으면 굳이 이렇게 하지 않아도 된다! --%>
                    <%-- set에 사용한건 script조작을 방지하기 위함, c:out을 대신해서 작업! --%>
                    <c:set var="eachTitle" value="${fn:replace(fn:replace(item.title, '>', '&gt;'), '<', '&lt;')}" />
                    <%-- page 안에(BoardVO) title 주소값을 받아온다. --%>
                    <c:if test="${param.searchText != null && param.searchType == 1 || param.searchType == 3 || param.searchType == 5}">
                        <c:set var="eachTitle" value="${fn:replace(eachTitle, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
                    </c:if>

                    <%-- 이름도 script방지를 해주면 좋다! --%>
                    <c:set var="eachWriterNm" value="${item.writerNm}" />
                    <%-- page 안에(BoardVO) title 주소값을 받아온다. --%>
                    <c:if test="${param.searchText != null && param.searchType == 4 || param.searchType == 5}">
                        <c:set var="eachWriterNm" value="${fn:replace(eachWriterNm, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
                    </c:if>

                    <c:set var="pImg" value="defaultProfile.jpg"/>
                    <c:if test="${item.profileImg != null}">
                        <c:set var="pImg" value="profile/${item.writer}/${item.profileImg}"/>
                    </c:if>

                    <tr class="record" onclick="moveToDetail(${item.iboard});">
                        <%-- (BoardVO) info 주소값을 받아온다. --%>
                        <td>${item.iboard}</td>
                        <td>${eachTitle}</td>
                        <td>${item.hit}</td>
                        <td>${eachWriterNm}<img class="profile-sm" src="/res/img/${pImg}"></td>
                        <td>${item.mdt}</td>
                    </tr>
                </c:forEach>
            </table>
            <div class="pageContainer">
                <%-- set : 세팅한다. 값을 받는다! --%>
                <c:set var="selectedPage" value="${param.page == null ? 1 : param.page}"/>
                <%-- selectedPage값이 page값과 null이면 1 아니면 param.page --%>
                <c:forEach var="page" begin="1" end="${maxPageNum}">
                    <div><a href="/board/list?page=${page}&searchType=${param.searchType}&searchText=${param.searchText}&rowCnt=${param.rowCnt}"><span class="${selectedPage == page ? 'selected' : ''}" ><c:out value="${page}"></c:out></span></a></div>
                </c:forEach>
            </div>
        </div>
    </c:otherwise>

</c:choose>
<script src="/res/js/board/list.js?ver=1"></script>
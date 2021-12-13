<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div>
    <h1>${requestScope.title}</h1>
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

                <tr class="record" onclick="moveToDetail(${item.iboard});">
                        <%-- (BoardVO) info 주소값을 받아온다. --%>
                    <td>${item.iboard}</td>
                    <td>${eachTitle}</td>
                    <td>${item.hit}</td>
                    <td>${eachWriterNm}</td>
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
</div>
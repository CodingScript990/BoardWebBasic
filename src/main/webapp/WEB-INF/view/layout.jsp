<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${requestScope.title}</title> <!-- 받은 타이틀값 -->
    <link rel="stylesheet" href="/res/css/common.css">
    <%-- 브라우저에서 실행할때 다운로드 되어서 실행해주는 것 --%>
</head>
<body>
    <div class="container">
        <div class="header">
            header
        </div>
        <div class="body"><jsp:include page="/WEB-INF/view/${requestScope.page}.jsp"></jsp:include></div>
        <!-- jsp파일을 넣을 곳!(포함을 시키겠다는 의미) -->
        <div class="footer">
            footer
        </div>
    </div>
</body>
</html>
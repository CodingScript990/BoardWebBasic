<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <c:set var="pImg" value="defaultProfile.jpg"/>
    <c:if test="${sessionScope.loginUser.profileImg != null}">
        <c:set var="pImg" value="profile/${sessionScope.loginUser.iuser}/${requestScope.data.profileImg}"/>
    </c:if>
    <div><img class="profile-img" src="/res/img/${pImg}"></div>
    <div>
        <div>아이디 : ${sessionScope.loginUser.uid}</div>
        <div>이름 : ${sessionScope.loginUser.nm}</div>
        <div>성별 : ${sessionScope.loginUser.gender == 1 ? '남자' : '여자'}</div>
        <div>가입일 : ${sessionScope.loginUser.rdt}</div>
    </div>
    <div>
        <form action="/user/profile" method="post" enctype="multipart/form-data">
        <%-- 인코딩 할때 필수임! enctype --%>
            <div><label>이미지 : <input type="file" name="profileImg" accept="image/*"></label></div>
            <div><input type="submit" value="이미지 업로드"></div>
        </form>
    </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div>프로필 이미지</div>
    <div>
        <div>이름 : </div>
        <div>성별 : </div>
    </div>
    <div>
        <form action="/user/profile" method="post" enctype="multipart/form-data">
        <%-- 인코딩 할때 필수임! enctype --%>
            <div><label>이미지 : <input type="file" name="profileImg"></label></div>
            <div><input type="submit" value="프로필 이미지 변경"></div>
        </form>
    </div>
</div>
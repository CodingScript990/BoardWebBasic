<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <form action="/user/join" method="post" id="frm" onsubmit="return joinChk();">
        <div><input type="text" name="uid" placeholder="id" required></div>
        <div><input type="password" name="upw" placeholder="password" required></div>
        <div><input type="password" id="reupw" placeholder="password comfirm" required></div>
        <div><input type="text" name="nm" placeholder="name" required></div>
        <div>
            <label>woman <input type="radio" value="1" name="gender" checked></label>
            <label>man <input type="radio" value="2" name="gender"></label>
        </div>
        <div>
            <input type="submit" value="join">
            <input type="reset" value="reset">
        </div>
    </form>
</div>

<script src="/res/js/user/join.js"></script>
<%-- 무조건 script태그는 있어야함!! --%>
<%-- jsp파일을 읽을때를 위해서 jsp파일 다 작성하고 마지막에 script를 작성하는게 좋다! --%>
<%-- defer은 나중에 작업을 한다는 소리임! --%>
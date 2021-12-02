<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <form action="/user/login" method="post" id="frm">
        <div><input type="text" name="uid" placeholder="id" value="jinx1"></div>
        <div><input type="password" name="upw" placeholder="password" value="121212"></div>
        <div><input type="submit" value="login"></div>
    </form>
    <div>
        <input type="button" value="upw show" id="btnShowPw">
    </div>
    <div>
        <a href="/user/join">join</a>
    </div>
</div>
<script src="/res/js/user/login.js"></script>
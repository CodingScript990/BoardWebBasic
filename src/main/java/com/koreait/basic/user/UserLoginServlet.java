package com.koreait.basic.user;

import com.koreait.basic.Utils;
import com.koreait.basic.dao.UserDAO;
import com.koreait.basic.user.model.LoginResult;
import com.koreait.basic.user.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Utils.displayView("로그인", "user/login", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uid = req.getParameter("uid");
        String upw = req.getParameter("upw");
        UserEntity entity = new UserEntity();

        entity.setUid(uid);
        entity.setUpw(upw);

        System.out.println(entity);

        LoginResult lr = UserDAO.login(entity);

        switch (lr.getResult()) {
            case 1:
                // 세션에 loginUser값 등록
                HttpSession session = req.getSession();
                session.setAttribute("loginUser", lr.getLoginUser());
                // 이동은 마음대로!
                res.sendRedirect("/board/list");
                break;
            default:
                break;
        }

        System.out.println("result : " + lr.getResult());
        System.out.println("user : " + lr.getLoginUser());
    }
}

package com.koreait.basic.user;

import com.koreait.basic.Utils;
import com.koreait.basic.dao.UserDAO;
import com.koreait.basic.user.model.UserEntity;
import com.mysql.cj.util.Util;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/join")
public class UserJoinServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        req.setAttribute("title", "회원가입"); // 기본으로 깔고 시작
//        req.setAttribute("page", "user/join"); // 페이지 경로(layout.jsp에 body안에 page에 들어갈 곳!)
//        String jsp = "/WEB-INF/view/layout.jsp";
//        req.getRequestDispatcher(jsp).forward(req, res);
        Utils.displayView("회원가입", "user/join", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uid = req.getParameter("uid");
        String upw = req.getParameter("upw");
        String nm = req.getParameter("nm");
        int gender = Utils.getParameterInt(req, "gender");

        String hashPw = BCrypt.hashpw(upw, BCrypt.gensalt()); // 암호화 시켜주는 작업!(즉, 생성될때마다 로그인 할때마다 값이 바뀜!)
        // 비밀번호는 무조건 알 수 없게 복호화 시켜줘야함! (feat : 개인정보 보호법!)

        UserEntity entity = new UserEntity(); // 객체화
        entity.setUid(uid);
        entity.setUpw(hashPw); // 암호화!
        entity.setNm(nm);
        entity.setGender(gender);
        // 단 방향은 복호화를 할 수 없다!

        System.out.println(entity);

        int result = UserDAO.join(entity);

        switch (result) {
            case 1:
                res.sendRedirect("/user/login");
                break;
            default:
                req.setAttribute("err", "회원가입에 실패하였습니다!");
                doGet(req, res);
                break;
        }
    }
}

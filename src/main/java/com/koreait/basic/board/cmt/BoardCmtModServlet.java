package com.koreait.basic.board.cmt;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardCmtEntity;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/cmt/mod")
public class BoardCmtModServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard");

        int icmt = Utils.getParameterInt(req,"icmt");

        String ctnt = req.getParameter("ctnt");
        ctnt = ctnt.replace("<", "&lt;").replace(">", "&gt;");

        int writer = Utils.getLoginUserPk(req);

        BoardCmtEntity entity = new BoardCmtEntity();

        entity.setIboard(iboard);
        entity.setIcmt(icmt);
        entity.setCtnt(ctnt);
        entity.setWriter(writer);

        int result = BoardCmtDAO.upBoardCmt(entity);

        if (iboard == 0) {
            result = BoardCmtDAO.upBoardCmt(entity);
            req.setAttribute("err", "댓글 수정등록에 실패했습니다.");
        } else {
            entity.setIboard(iboard);
            req.setAttribute("err", "댓글 수정에 실패했습니다.");
        }

        req.setAttribute("cmt", entity);
        res.sendRedirect("/board/detail?iboard=" + iboard);
    }
}

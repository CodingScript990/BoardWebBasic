package com.koreait.basic.board.cmt;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardCmtEntity;
import com.koreait.basic.board.model.BoardCmtVO;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/cmt/reg")
public class BoardCmtRegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 댓글 insert 하고 "/board/detail화면으로 이동!"

        int iboard = Utils.getParameterInt(req, "iboard");

        String ctnt = req.getParameter("ctnt");
        ctnt = ctnt.replace("<", "&lt;").replace(">", "&gt;");

        int writer = Utils.getLoginUserPk(req); // 로그인 유저!

        BoardCmtEntity entity = new BoardCmtEntity();

        entity.setIboard(iboard);
        entity.setCtnt(ctnt);
        entity.setWriter(writer);

        int result = BoardCmtDAO.insBoardCmt(entity);

        if (iboard == 0) {
            result = BoardCmtDAO.insBoardCmt(entity);
            req.setAttribute("err", "댓글등록에 실패했습니다.");
        } else {
            entity.setIboard(iboard);
//            result = BoardCmtDAO.upBoardCmt();
        req.setAttribute("err", "댓글 수정에 실패하였습니다.");
        }

        req.setAttribute("cmt", entity);
        res.sendRedirect("/board/detail?nohits=1&iboard=" + iboard);

    }
}

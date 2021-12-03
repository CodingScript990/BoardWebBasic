package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/del")
public class BoardDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard"); // iboard값을 들고온다
        int writer = Utils.getLoginUserPk(req); // login session값 들고온다

        BoardEntity entity = new BoardEntity(); // 객체
        entity.setIboard(iboard); // iboard값
        entity.setWriter(writer); // writer값

        int result = BoardDAO.delBoard(entity); // 비교해서

        switch (result) { // 결과값들은 예외처리 해줌!
            case 1:
                res.sendRedirect("/board/list");
                return;
            default:
                req.setAttribute("err", "글 삭제를 실패하였습니다.");
                req.getRequestDispatcher("/board/detail?iboard=" + iboard).forward(req, res);
                return;
        }
    }
}

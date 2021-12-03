package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BoardDTO dto = new BoardDTO();

        dto.setIboard(Integer.parseInt(req.getParameter("iboard")));

        BoardVO vo = BoardDAO.selBoardDetail(dto);
        // 로그인 한 사람의 pk값과 data에 들어있는 writer 값이 다르다면 혹은
        // 로그인이 안되어 있으면 hit값을 올려주면 된다.
        int loginUserPk = Utils.getLoginUserPk(req);
        if (vo.getWriter() != loginUserPk) { // 로그인 안되어 있으면 0, 로그인 되어있으면 pk값
            BoardDAO.updBoardHitUp(dto);
        }

        req.setAttribute("data", vo);

        Utils.displayView("상세페이지", "board/detail", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}

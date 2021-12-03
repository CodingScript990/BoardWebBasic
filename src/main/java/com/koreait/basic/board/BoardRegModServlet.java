package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardDAO;
import com.koreait.basic.user.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/regmod")
public class BoardRegModServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard");

        String title = "글등록";

        // 수정 or 등록
        if(iboard > 0) { // 수정 iboard > 0, iboard != 0
            title = "글수정";

            if (req.getAttribute("data" ) == null ) {
                BoardDTO dto = new BoardDTO(); // DTO 객체화

                dto.setIboard(iboard); // iboard의 값을 불러오고
                BoardVO data = BoardDAO.selBoardDetail(dto);
                req.setAttribute("data", data); // 게시판에 있는 iboard값을 보여줘!
            }
        }
        Utils.displayView(title, "board/regmod", req, res); // 경로 이동!!
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req);
        if(loginUserPk == 0) {
            res.sendRedirect("/user/login");
            return;
        }

        int iboard = Utils.getParameterInt(req, "iboard");
        String title = req.getParameter("title");
        String ctnt = req.getParameter("ctnt");

        int result = 0;
        BoardEntity entity = new BoardEntity();
        entity.setTitle(title);
        entity.setCtnt(ctnt);
        entity.setWriter(loginUserPk);

        // 등록
        if (iboard == 0) {
            result = BoardDAO.insBoard(entity); // 글작성
            req.setAttribute("err", "등록에 실패하였습니다!");
        } else { // 수정
            entity.setIboard(iboard); // 작성한 iboard값 불러오고
            result = BoardDAO.updBoard(entity); // 글 수정!
            req.setAttribute("err", "수정에 실패하였습니다!");
        }

        switch (result) {
            case 1:
                if (entity.getIboard() != 0) {
                    res.sendRedirect("/board/detail?iboard=" + entity.getIboard());
                    return;
                }
                break;
            default:
                req.setAttribute("data", entity);
                doGet(req, res);
                break;
        }
        res.sendRedirect("/board/list");
    }
}

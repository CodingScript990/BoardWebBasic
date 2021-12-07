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
import java.util.List;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // search
        int searchType = Utils.getParameterInt(req, "searchType", 0);
        String searchText = req.getParameter("searchText");

        // param setting
        int page = Utils.getParameterInt(req, "page", 1);

        // Record page
        BoardDTO param = new BoardDTO(); // DTO에서 설정한 rowCnt를 불러오는 작업

        param.setRowCnt(5); // 설정 값 5
        param.setPage(page); // page 값;

        param.setSearchType(searchType); // type 값
        param.setSearchText(searchText); // text 값

        int startIdx = (param.getPage() - 1) * param.getRowCnt(); // 바로 page 값 계산!!
        param.setStartIdx(startIdx); // list value 값 불러오고!

        int maxPageNum = BoardDAO.getMaxPageNum(param); // DAO 에서 불러온다

        req.setAttribute("maxPageNum", maxPageNum); // maxPageNum 을 담는다
//        if (maxPageNum > 0) { // 0 이상이면?
//            req.setAttribute("list", BoardDAO.selBoardList()); // DAO selBoardList를 불러온다!
//        }

        req.setAttribute("list", BoardDAO.selBoardList(param)); // DAO selBoardList를 불러온다!

        Utils.displayView("게시판", "board/list", req, res); // 경로!
    }
}

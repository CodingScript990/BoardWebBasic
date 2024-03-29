package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardRankDAO {

    // select
    public static List<BoardVO> procResultSet(String sql) {

        List<BoardVO> list = new ArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                BoardVO vo = BoardVO.builder()
                        .iboard(rs.getInt("iboard"))
                        .title(rs.getString("title"))
                        .writer(rs.getInt("writer"))
                        .rdt(rs.getString("rdt"))
                        .mdt(rs.getString("mdt"))
                        .writerNm(rs.getString("writerNm"))
                        .cnt(rs.getInt("cnt"))
                        .profileImg(rs.getString("profileImg"))
                        .build();
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return list;
    }

    // 조회수

    public static List<BoardVO> selBoardHitsRankList() {

        String sql = " SELECT A.iboard, A.title, A.writer, A.hit AS cnt, A.rdt, A.mdt " +
                " , B.nm AS writerNm, B.profileImg " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser " +
                " WHERE A.hit > 0 " +
                " ORDER BY A.hit DESC, A.iboard DESC" +
                " LIMIT 10 ";

        return procResultSet(sql);
    }

    // 댓글수

    public static List<BoardVO> selBoardCmtRankList() {

        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt, A.mdt, B.nm AS writerNm, B.profileImg, C.cnt " +
                    " FROM t_board A " +
                    " INNER JOIN t_user B " +
                    " ON A.writer = B.iuser " +
                    " INNER JOIN " +
                    " ( SELECT iboard, COUNT(icmt) AS cnt " +
                    " FROM t_board_cmt " +
                    " GROUP BY iboard ) C " +
                    " ON A.iboard = C.iboard " +
                    " ORDER BY C.cnt DESC " +
                    " LIMIT 10 ";

        return procResultSet(sql);
    }

    // 좋아요수

    public static List<BoardVO> selBoardHeartRankList() {

        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt, A.mdt, B.nm AS writerNm, B.profileImg, C.cnt " +
                    " FROM t_board A " +
                    " INNER JOIN t_user B " +
                    " ON A.writer = B.iuser " +
                     "INNER JOIN " +
                    " ( " +
                    " SELECT iboard, COUNT(iuser) AS cnt " +
                    " FROM t_board_like " +
                    " GROUP BY iboard " +
                    " ) C " +
                    " ON A.iboard = C.iboard " +
                    " ORDER BY C.cnt DESC " +
                    " LIMIT 10 ";

        return procResultSet(sql);
    }
}

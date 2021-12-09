package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardCmtDTO;
import com.koreait.basic.board.model.BoardCmtEntity;
import com.koreait.basic.board.model.BoardCmtVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardCmtDAO {

    // Comment

    // create
    public static int insBoardCmt(BoardCmtEntity entity) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO t_board_cmt (iboard, ctnt, writer) VALUES (?, ?, ?)";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entity.getIboard());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getWriter());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
    // select

    public static List<BoardCmtVO> selBoardCmtList(BoardCmtDTO entity) {

        List<BoardCmtVO> list = new ArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = " SELECT A.icmt, A.ctnt, A.writer, A.rdt, A.mdt, B.nm AS writerNm " +
                    " FROM t_board_cmt A " +
                    " INNER JOIN t_user B " +
                    " ON A.writer = B.iuser" +
                    " WHERE A.iboard = ? " +
                    " ORDER BY A.icmt DESC ";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entity.getIboard());

            rs = ps.executeQuery();

            while (rs.next()) {
                int icmt = rs.getInt("icmt");
                String ctnt = rs.getString("ctnt");
                int writer = rs.getInt("writer");
                String rdt = rs.getString("rdt");
                String mdt = rs.getString("mdt");
                String writerNm = rs.getString("writerNm");

                BoardCmtVO vo = BoardCmtVO.builder().
                        icmt(icmt).
                        ctnt(ctnt).
                        writer(writer).
                        rdt(rdt).
                        mdt(mdt).
                        writerNm(writerNm).
                        build();
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return list;
    }

    // update

    public static int upBoardCmt(BoardCmtEntity entity) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "UPDATE t_board_cmt SET ctnt = ?, mdt = now() WHERE icmt = ? AND writer = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setString(1, entity.getCtnt());
            ps.setInt(2, entity.getIcmt());
            ps.setInt(3, entity.getWriter());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }

        return 0;
    }

    // delete

    public static int delBoardCmt(BoardCmtEntity entity) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM t_board_cmt WHERE icmt = ? AND writer = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entity.getIcmt());
            ps.setInt(2, entity.getWriter());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
}

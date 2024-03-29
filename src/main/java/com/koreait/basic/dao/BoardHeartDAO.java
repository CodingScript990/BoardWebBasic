package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardHeartEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardHeartDAO {

    // insert

    public static int insBoardHeart(BoardHeartEntity param) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO t_board_like(iuser, iboard) VALUES (?, ?)";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, param.getIuser());
            ps.setInt(2, param.getIboard());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }

        return 0;
    }

    public static int delBoardHeart(BoardHeartEntity param) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM t_board_like WHERE  iuser = ? AND iboard = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, param.getIuser());
            ps.setInt(2, param.getIboard());

            return  ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }

        return 0;
    }

    // 좋아요 했다면 1, 아니면 0 // 로그인한 사람의 iuser 값, 글번호 iboard 값

    public static int selIsHeart(BoardHeartEntity param) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM t_board_like WHERE iuser = ? AND iboard = ?";

        try {
            // 1
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, param.getIuser());
            ps.setInt(2, param.getIboard());

            rs =  ps.executeQuery();

            if (rs.next()) {
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }

        return 0;
    }
}

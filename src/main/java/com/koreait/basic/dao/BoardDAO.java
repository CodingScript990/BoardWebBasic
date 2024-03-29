package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

//    // entity에 iboard값에 pk값 담기
//    // return int 값은 그대로
//    public static int insBoardWithPk(BoardEntity entity) {
//        int result = 0;
//        Connection con = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        String sql = "INSERT INTO t_board(title, ctnt, writer)" +
//                "VALUES (?, ?, ?)";
//        try {
//            con = DbUtils.getCon();
//            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, entity.getTitle());
//            ps.setString(2, entity.getCtnt());
//            ps.setInt(3, entity.getWriter());
//            result = ps.executeUpdate();
//            rs = ps.getGeneratedKeys();
//            if(rs.next()) {
//                int iboard = rs.getInt(1);
//                entity.setIboard(iboard);
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        } finally {
//            DbUtils.close(con, ps, rs);
//        }
//        return result;
//    }

    // create
    public static int insBoard (BoardEntity entity) { // title, ctnt, writer

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO t_board (title, ctnt, writer) VALUES (?, ?, ?)";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setString(1, entity.getTitle());
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

    // search
    private static  String getSearchWhereString(BoardDTO param) { // 검색
        if (param.getSearchText() != null && !"".equals(param.getSearchText())) { // null값이 아니라면!

            switch (param.getSearchType()) {
                // title
                case 1:
                    return  String.format(" WHERE A.title LIKE '%%%s%%'", param.getSearchText());
                // ctnt
                case 2:
                    return String.format(" WHERE A.ctnt LIKE '%%%s%%'", param.getSearchText());
                // title, ctnt
                case 3:
                    return String.format(" WHERE A.title LIKE '%%%s%%' OR A.ctnt LIKE '%%%s%%'",
                            param.getSearchText(), param.getSearchText());
                // writer
                case 4:
                    return String.format(" WHERE B.nm LIKE '%%%s%%'", param.getSearchText());
                // all
                case 5:
                    return String.format(" WHERE A.title LIKE '%%%s%%' OR A.ctnt LIKE '%%%s%%' OR B.nm LIKE '%%%s%%'",
                            param.getSearchText(), param.getSearchText(), param.getSearchText());
            }
        }
        return "";
    }

    public static int getMaxPageNum(BoardDTO param) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT CEIL(COUNT(*) / ?) FROM t_board A INNER JOIN t_user B ON  A.writer = B.iuser";

        sql += getSearchWhereString(param);

//        System.out.println("sql : " + sql);

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, param.getRowCnt());

            rs = ps.executeQuery();

            if (rs.next()) {
                int maxPageNum = rs.getInt(1);
                return maxPageNum;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }

        return 0;
    }

    public static List<BoardVO> selBoardList(BoardDTO param) {

        List<BoardVO> list = new ArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = " SELECT A.iboard, A.title, A.writer, A.hit, A.rdt, A.mdt, B.nm AS writerNm, B.profileImg " +
                    " FROM t_board A" +
                    " INNER JOIN t_user B" +
                    " ON A.writer = B.iuser";

                sql += getSearchWhereString(param);

                sql += " ORDER BY A.iboard DESC LIMIT ?, ? ";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, param.getStartIdx()); // idx
            ps.setInt(2, param.getRowCnt()); // rowcnt

            rs = ps.executeQuery();

            while (rs.next()) {
                int iboard = rs.getInt("iboard");
                String title = rs.getString("title");
                int writer = rs.getInt("writer");
                int hit = rs.getInt("hit");
                String rdt = rs.getString("rdt");
                String mdt = rs.getString("mdt");
                String writerNm = rs.getString("writerNm");
                String profileImg = rs.getString("profileImg");

                // 필요한 값을 받는 것!(빌드패턴 디자인!)
                BoardVO vo = BoardVO.builder().
                        iboard(iboard).
                        title(title).
                        writer(writer).
                        hit(hit).
                        rdt(rdt).
                        mdt(mdt).
                        writerNm(writerNm).
                        profileImg(profileImg).
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

    public static BoardVO selBoardDetail(BoardDTO param) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = " SELECT A.title, A.ctnt, A.writer, A.hit, A.rdt, A.mdt, B.nm AS writerNm " +
                    " FROM t_board A " +
                    " INNER JOIN t_user B" +
                    " ON A.writer = B.iuser " +
                    " WHERE A.iboard = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, param.getIboard());

            rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String ctnt = rs.getString("ctnt");
                int writer = rs.getInt("writer");
                int hit = rs.getInt("hit");
                String rdt = rs.getString("rdt");
                String mdt = rs.getString("mdt");
                String writerNm = rs.getString("writerNm");

                BoardVO vo = BoardVO.builder().
                            iboard(param.getIboard()).
                            title(title).
                            ctnt(ctnt).
                            writer(writer).
                            hit(hit).
                            rdt(rdt).
                            mdt(mdt).
                            writerNm(writerNm).
                            build();
                return vo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return null;
    }

    public static void updBoardHitUp(BoardDTO param) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "UPDATE t_board SET hit = hit + 1 WHERE iboard = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, param.getIboard());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
    }

    public static int updBoard(BoardEntity entity) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "UPDATE t_board SET title = ?, ctnt = ?, mdt = now() WHERE iboard = ? AND writer = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getIboard());
            ps.setInt(4, entity.getWriter());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }

        return 0;
    }

    public static int delBoard(BoardEntity entity) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM t_board WHERE iboard = ? AND writer = ?"; // writer 값까지 받아줘야함! 내글만 삭제하기 위함!

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entity.getIboard()); // iboard
            ps.setInt(2, entity.getWriter()); // writer

            return ps.executeUpdate(); // update!!

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }

        return 0;
    }
}

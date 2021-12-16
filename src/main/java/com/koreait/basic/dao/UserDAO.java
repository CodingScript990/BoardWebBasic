package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.user.model.LoginResult;
import com.koreait.basic.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    // 회원가입 create
    public static int join(UserEntity param) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO t_user (uid, upw, nm, gender) VALUES (?, ?, ?, ?)";

        try {
            // 회원가입 성공!
            con = DbUtils.getCon();

            ps = con.prepareStatement(sql);

            ps.setString(1, param.getUid());
            ps.setString(2, param.getUpw());
            ps.setString(3, param.getNm());
            ps.setInt(4, param.getGender());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }

        return 0; // 회원가입 실패!
    }

    // 무조건 LoginResult객체 주소값 리턴
    // result 값은 1이 로그인 성공! 0은 실패! 2는 아이디 없음 3은 비밀번호 틀림
    // result가 1 이었을때만 loginUser에 로그인 한 유저의 iuser, id, nm, gender값을 저장한 객체를 담는다.
    public static LoginResult login(UserEntity entity) {

        int result = 0; // 초기값

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        UserEntity loginUser = null; // 로그인시에 값을 비교하기 위함을 말함!

        String sql = "SELECT iuser, upw, nm, gender, profileImg FROM t_user WHERE uid = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setString(1, entity.getUid());

            rs = ps.executeQuery();

            if (rs.next()) {
                String dbPw = rs.getString("upw");
                if (BCrypt.checkpw(entity.getUpw(), dbPw)) { // true 비밀번호 맞으면!
                    result = 1; // 로그인 성공
                    loginUser = new UserEntity();
                    loginUser.setIuser(rs.getInt("iuser"));
                    loginUser.setUid(entity.getUid());
                    loginUser.setNm(rs.getString("nm"));
                    loginUser.setGender(rs.getInt("gender"));
                    loginUser.setProfileImg(rs.getString("profileImg"));
                } else {
                    result = 3; // 비밀번호 틀림
                }
            } else {
                result = 2; // 아이디 없음
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }

        return new LoginResult(result, loginUser);
    }

    // user search (select)

    public static UserEntity selUser(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT uid, upw, nm, gender, rdt, profileImg FROM t_user WHERE iuser = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entity.getIuser());

            rs = ps.executeQuery();

            if (rs.next()) {
                UserEntity vo = new UserEntity();
                vo.setUid(rs.getString("uid"));
                vo.setNm(rs.getString("nm"));
                vo.setGender(rs.getInt("gender"));
                vo.setRdt(rs.getString("rdt"));
                vo.setProfileImg(rs.getString("profileImg"));

                return vo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return null;
    }

    //t_user 테이블에서 iuser or uid 유저 정보 가져올 수 있는 메소드
    public static UserEntity selUser2(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT iuser, uid, upw, nm, gender, rdt, profileImg FROM t_user WHERE ";

        if(entity.getIuser() > 0) {
            sql += "iuser = " + entity.getIuser();
        } else {
            sql += "uid = '" + entity.getUid() + "'";
        }
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()) {
                UserEntity vo = new UserEntity();
                vo.setIuser(rs.getInt("iuser"));
                vo.setUid(rs.getString("uid"));
                vo.setUpw(rs.getString("upw"));
                vo.setNm(rs.getString("nm"));
                vo.setGender(rs.getInt("gender"));
                vo.setRdt(rs.getString("rdt"));
                vo.setProfileImg(rs.getString("profileImg"));
                return vo;
            }
        } catch (Exception e) { e.printStackTrace();
        } finally { DbUtils.close(con, ps, rs); }
        return null;
    }

    // 비밀번호 변경 > iuser, upw(암호화)가 넘어옴
    // 프로필 이미지 > iuser, profileImg 가 넘어온다.

    // 프로필 업데이트

    public static int updUser(UserEntity entity) {

        Connection con = null;
        PreparedStatement ps = null;

//        String sql = " UPDATE t_user SET "; // update t_user
//        String changeVal = null; // String value 값
//
//        if (entity.getUpw() != null && !"".equals(entity.getUpw())){ // 패스워드가 null값이 아니라면, "" 빈값이 아니라면
//            sql += " upw = ? "; // upw의 값은?
//            changeVal = entity.getUpw(); // 작성한 비밀번호의 값
//        } else if (entity.getProfileImg() != null && !"".equals(entity.getProfileImg())) { // 프로필 이미지의 값이 있다면, 값이 없다면
//            sql += " profileImg = ? "; // profileImg 값은?
//            changeVal = entity.getProfileImg(); // 업로드된 profileImg 값
//        }
//        sql += " WHERE iuser = ? "; // 해당 유저값

        String sql = " UPDATE t_user SET profileImg = ? WHERE iuser = ? ";
        String changeVal = entity.getProfileImg();

        if (entity.getUpw() != null && !"".equals(entity.getUpw())) {
            sql = sql.replace("profileImg", "upw");
            changeVal = entity.getUpw();
        }

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);

            ps.setString(1, changeVal); // 비밀번호
            ps.setInt(2, entity.getIuser()); // 유저 PK

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static int updUser2(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " UPDATE t_user SET profileImg = ? WHERE iuser = ? ";
        String changeVal = entity.getProfileImg();

        if(entity.getUpw() != null && !"".equals(entity.getUpw())) {
            sql = sql.replace("profileImg", "upw");
            changeVal = entity.getUpw();
        }
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, changeVal);
            ps.setInt(2, entity.getIuser());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace();
        } finally { DbUtils.close(con, ps); }
        return 0;
    }
}

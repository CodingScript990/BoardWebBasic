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

        String sql = "SELECT iuser, upw, nm, gender FROM t_user WHERE uid = ?";

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
}

package com.koreait.basic.user;

import com.koreait.basic.FileUtils;
import com.koreait.basic.Utils;
import com.koreait.basic.dao.UserDAO;
import com.koreait.basic.user.model.UserEntity;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        int loginUserPK = Utils.getLoginUserPk(req); // 로그인 세션값을 받고
        UserEntity entity = new UserEntity(); // 유저 객체화를 만들어주고
        entity.setIuser(loginUserPK); // 유저 로그인 세션값을 가져와서
        req.setAttribute("data", UserDAO.selUser(entity)); // 유저에 담긴 세션값을 요청한다.

        String title = "프로필"; // title name
        req.setAttribute("subPage", "user/profile"); // 열어야 될 jsp file name
        Utils.displayView(title, "user/myPage", req, res); // 경로

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req); // 로그인 유저 pk값;
        int maxSize = 10_485_760; // 이미지 허용 사이즈 값; 1024 * 1024 * 10 (10mb)

        ServletContext application = req.getServletContext();

        String targetPath = application.getRealPath("/res/img/profile/") + loginUserPk;
        // 폴더명(tomcat Path 경로)/img 폴더명/ profile 폴더명/ loginUser PK 값까지 폴더로 만들어줌(변경 할 수 도 있기 때문에 구분 짓기 위함)
        System.out.println("targetPath : " + targetPath); // image 저장 경로

        File targetFolder = new File(targetPath);

        if (targetFolder.exists()) {
            FileUtils.delFolderFiles(targetPath, false);
        } else {
            targetFolder.mkdirs();
        }

        MultipartRequest mr = new MultipartRequest(req, targetPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
        // 요청받고, 이미지파일 경로(유저가 로그인시에), 이미지 사이즈, 인코딩, 중복된 파일이 업로드 되지 않게
        // 이름을 바꿔줘서 저장을 함! 파일 업로드!
        // Enumeration files = mr.getFileNames(); // 파일 이름 업로드 == ResultSet

//        if(files.hasMoreElements()) { // file 이 있는지 없는지 체크(file 이 있으면 true : 없으면 false)
//            String originFileNm = (String) files.nextElement(); // 실제로 올라간 파일명!
//            System.out.println("fileNm : " + originFileNm);
//            String changeFileNm = mr.getFilesystemName(originFileNm); // 변경된 파일명!
//            System.out.println("changeFileNm : " + changeFileNm);
//        }
        String changeFileNm = mr.getFilesystemName("profileImg");

        UserEntity entity = new UserEntity();

        entity.setIuser(loginUserPk);
        entity.setProfileImg(changeFileNm);

        int result = UserDAO.updUser(entity);
//        doGet(req, res);
        res.sendRedirect("/user/profile");
    }
}

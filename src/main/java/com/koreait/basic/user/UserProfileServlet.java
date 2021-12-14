package com.koreait.basic.user;

import com.koreait.basic.Utils;
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

        String title = "프로필";
        req.setAttribute("subPage", "user/profile");
        Utils.displayView(title, "user/myPage", req, res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req); // 로그인 유저 pk값;
        int maxSize = 10_485_760; // 이미지 허용 사이즈 값; 1024 * 1024 * 10 (10mb)

        ServletContext application = req.getServletContext();

        String targetPath = application.getRealPath("/res/img/profile/") + loginUserPk;
        // 폴더명(tomcat Path 경로)/img 폴더명/ profile 폴더명/ loginUser
        System.out.println("basicPath : " + targetPath);
        File file = new File(targetPath);
        file.mkdirs(); // 업로드한 파일 지정한 경로에 저장

        MultipartRequest mr = new MultipartRequest(req, targetPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
        // 요청받고, 이미지파일 경로(유저가 로그인시에), 이미지 사이즈, 인코딩, 중복된 파일이 업로드 되지 않게
        // 이름을 바꿔줘서 저장을 함! 파일 업로드!
        Enumeration files = mr.getFileNames(); // 파일 이름 업로드
        
        String fileNm = (String) files.nextElement(); // 파일 이름
        System.out.println("fileNm : " + fileNm);
    }
}

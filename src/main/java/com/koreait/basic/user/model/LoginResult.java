package com.koreait.basic.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 생성자로만 값을 넣을 수 있다.
//@Builder // 생성자를 이용해서 값을 넣어주는 디자인 패턴!

public class LoginResult {
    private final int result;
    private final UserEntity loginUser;
}

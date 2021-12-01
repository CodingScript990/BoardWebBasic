package com.koreait.basic.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // getter 만들어줌
@Setter // getter 만들어줌
@ToString // 알아서 출력해줌
public class UserEntity {
    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private int gender;
    private String rdt;
}

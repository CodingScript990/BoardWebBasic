package com.koreait.basic.board.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //

public class BoardVO { // 불변형! jsp 에서만 사용하는 거라고 생각하면됨!!
    private int iboard;
    private String title;
    private String ctnt;
    private int writer;
    private int hit;
    private String rdt;
    private String mdt;

    private String writerNm;
}

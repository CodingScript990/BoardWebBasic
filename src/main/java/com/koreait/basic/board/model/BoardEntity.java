package com.koreait.basic.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoardEntity { // 개체!(DB랑 1:1 보장!! 절대로 건드리지 않음!!)
    private int iboard;
    private String title;
    private String ctnt;
    private int writer;
    private int hit;
    private String rdt;
    private String mdt;
}

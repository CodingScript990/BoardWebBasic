package com.koreait.basic.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 에노테이션
public class BoardDTO {
    private int iboard; // iboard
    private int page; // page
    private int startIdx; // index
    private int rowCnt; // record
    private int searchType; // type
    private String searchText; // text
    private int loginUserPk; // pk
}

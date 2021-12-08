function isDelCmt(iboard, icmt) {

    if (confirm('댓글을 삭제하시겠습니까?')) {// 내용만 적으면 됨!)
        location.href = '/board/cmt/del?iboard=' + iboard + '&icmt=' + icmt; // 삭제할 경로!!
    }

}
function isDelCmt(iboard, icmt) {

    if (confirm('댓글을 삭제하시겠습니까?')) {// 내용만 적으면 됨!)
        location.href = '/board/cmt/del?iboard=' + iboard + '&icmt=' + icmt; // 삭제할 경로!!
    }

}

// onclick 선택자들
let cmtModContainerElem = document.querySelector('.cmtModContainer');
let btnCancel = cmtModContainerElem.querySelector('#btnCancel');
let cmtModFrmElem = cmtModContainerElem.querySelector('#cmtModFrm');

// click event
btnCancel.addEventListener('click', () => {
    cmtModContainerElem.style.display='none';
});

// onclick
function openModForm(icmt, ctnt) {

    cmtModContainerElem.style.display='flex';

    cmtModFrmElem.icmt.value=icmt; // icmt접근하라
    cmtModFrmElem.ctnt.value=ctnt; // ctnt접근하라
}

// heart
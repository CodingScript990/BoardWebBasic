let frmElem = document.querySelector('#frm'); // 주소값
let submitBtnElem = document.querySelector('#submitBtn'); // 주소값

submitBtnElem.addEventListener('click', () => {

    if (frmElem.upw.value.length < 5) {
        alert('비밀번호를 확인해 주세요.');
    } else if (frmElem.changedUpw.value.length < 5 && frmElem.changedUpwConfirm.value.length < 5) {
        alert('변경 비밀번호를 확인해주세요.');
    } else if (frmElem.changedUpw.value !== frmElem.changedUpwConfirm.value) {
        alert('변경 비밀번호와 확인 비밀번호가 다릅니다.');
    } else {
        frmElem.submit();
    }
});
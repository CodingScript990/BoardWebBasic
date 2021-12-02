let frm = document.querySelector('#frm');

if (frm) {

    /*function proc() { // 1번 방법!
        alert('전송!!');
    }

    let proc2 = function () { // 2번 방법!
        alert('전송??');
    }

    frm.addEventListener('submit', proc);
    frm.addEventListener('submit', proc2);
    frm.addEventListener('submit', function (e) {
        alert('전송!??');
        e.preventDefault(); // 옳바르지 않는 것을 막는 방법!
    }); // 3번 방법!!(일반적으로 많이 사용함!)
    */
    /*
    frm.addEventListener('submit', function (e) {
        alert('전송?');
        e.preventDefault();
    });
    */
    function frmSubmitEvent(e) {
        // 아이디가 5글자 미만 혹은 20글자 초과가 되면 아이디는 5~20글자 입니다. alert 띄우기
        // 비밀번호는 5글자 미만일때 비밀번호를 확인해 주세요
        if (frm.uid.value.length < 5 || frm.uid.value.length > 20) {
            alert('아이디는 5~20글자 입니다.');
            e.preventDefault(); // 자기업무를 하지 않는다!
            return;
        } else if (frm.upw.value.length < 5) {
            alert('비밀번호를 확인해 주세요.');
            e.preventDefault();
            return;
        } else {
            alert('전송!');
        }
    }
    frm.addEventListener('submit', frmSubmitEvent);

    let btn = document.querySelector('#btnShowPw'); // 선택자

    // if (btn) { // 선택해서!
    //     btn.addEventListener('click', function () { // 클릭하면!
    //         if (frm.upw.type === 'password') { // 패스워드 보여주게하고!
    //             frm.upw.type = 'text'; // 텍스트는(버튼 텍스트!)
    //             btn.value = 'upw hide'; // 텍스트를 변화주기!
    //         } else { // 아니라면
    //             frm.upw.type = 'password'; // 패스워트 타입은 기존 타입처럼 만들고
    //             btn.value = 'upw show'; // 텍스트도 기존 텍스트대로 놔둬라!
    //         }
    //     });
    // }
    if (btn) {
        btn.addEventListener('click', function () {
            let btnEff = (frm.upw.type === 'password') ? btn.value='upw hide' : btn.value='upw show';
            let btnText = (frm.upw.type === 'password') ? frm.upw.type='text' : frm.upw.type='password';

            if (btnEff === 'click') {
                return btnText;
            } else {
                return btnText;
            }
        });
    }
}
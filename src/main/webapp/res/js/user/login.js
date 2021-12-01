let frm = document.querySelector('#frm');

if (frm) {

    function proc() { // 1번 방법!
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
}
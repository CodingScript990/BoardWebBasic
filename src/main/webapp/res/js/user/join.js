function joinChk() {
    // 분기는 무조건 if문!!

    // return false; // false 값만 무조건 값이 안날라감!

    let frm = document.querySelector('#frm'); // 선택자!

    if (frm.uid.value.length < 5 || frm.uid.value.length > 20) {
        alert('아이디를 5~20자 사이로 작성해 주세요!');
        return false; // 아이디만 안적혔을 때만 안날라가게!
    }
    else if (frm.upw.value.length < 5) {
        alert('비밀번호를 5자 이상 작성해 주세요!');
        return false; // 비밀번호 안적었을때 안날라가게!
    }
    else if (frm.upw.value !== frm.reupw.value) {
        alert('비밀번호를 확인해 주세요!');
        return false; // 2차 비밀번호가 1차랑 안맞으면 안날라가게!
    }
    else if (frm.nm.value.length > 5) {
        alert('이름을 확인해 주세요!');
        return false; // 이름이 5글자 초과시 안날라가게!
    }
    return true;
    // id, name 값을 같은 네이밍을 주면 안된다!!
    // id를 주는건 서버에서 들고 올 수 없기 때문에 중복 확인때
    // 많이 활용됨
}
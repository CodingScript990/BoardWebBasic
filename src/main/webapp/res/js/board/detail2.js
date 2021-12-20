let cmtListContainerElem = document.querySelector('#cmtListContainer');

const getList = () => {
    let url = '/board/cmt?iboard=' + cmtListContainerElem.dataset.iboard;
    fetch(url).then( (res) => {
        return res.json();
    }).then( (data) => {
        console.log(data);
    }).catch( (err) => {
        console.error(err);
    });
}
getList();
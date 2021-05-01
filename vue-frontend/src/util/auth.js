
function setStorage(tokenInfo){
    localStorage.setItem('access_token', 'Bearer '+tokenInfo.token);
    localStorage.setItem('username', tokenInfo.username);
}

function initStorage(){
    localStorage.clear();
}

export default {setStorage,initStorage}
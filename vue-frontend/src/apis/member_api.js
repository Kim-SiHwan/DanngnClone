import Send from "@/apis/common_api";

function requestJoin(member){
    return Send({
        url:'/api/member/join',
        method:'POST',
        data:member
    })
}

function requestLogin(member){
    return Send({
        url:'/api/member/login',
        method:'POST',
        data:member
    })
}

function requestLogout(){
    return Send({
        url:'/api/member/logout',
        method:'POST',
    })
}

export default {requestJoin,requestLogin,requestLogout};
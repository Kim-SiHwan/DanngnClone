import axios from "axios";
import store from "@/store/store";
import router from "@/routes/index";
const instance = axios.create();

instance.interceptors.request.use(
    async config=>{
        const token = localStorage.getItem("access_token");
        config.headers={'Authorization' : token}
        return config;
    },
    error => {
        console.log(error);
    }
)
instance.interceptors.response.use(
    response=>{
        return response;
    },
    async error=>{
        const code = error.response.data.code;
        const msg = error.response.data.message;
        const status = error.response.status;

        if(code === 1 ){
            store.commit('SET_SNACK_BAR',{
                msg:msg, color:'warning'
            })
        }else if(code === 2){
            store.commit('SET_SNACK_BAR',{
                msg:msg, color:'error'
            })
        }else if(code === 3){
            store.commit('SET_SNACK_BAR',{
                msg:msg, color:'error'
            })

        }else if(status===403){
            store.commit('SET_SNACK_BAR',{
                msg:"토큰이 만료되었으니 로그인을 다시 시도해주세요.",color:'info'
            })
            router.push('/login');
        }else if(status===401){
            store.commit('SET_SNACK_BAR',{
                msg:"로그인이 필요한 서비스입니다.",color:'info'
            })
            store.commit('LOGOUT');

            router.push('/login');
        }else if(status===405){
            store.commit('SET_SNACK_BAR',{
                msg:"로그인이 필요한 서비스입니다.",color:'info'
            })
            store.commit('LOGOUT');

            router.push('/login');

        }else if(code === 7 ){
            store.commit('SET_SNACK_BAR',{
                msg:msg,color:'error'
            })
        }else if(code === 8 ){
            store.commit('SET_SNACK_BAR',{
                msg:msg,color:'error'
            })
            router.push('/error');
        }else if(code === 9 ){
            store.commit('SET_SNACK_BAR',{
                msg:msg,color:'error'
            })
        }else if(code === 10 ){
            store.commit('SET_SNACK_BAR',{
                msg:msg,color:'error'
            })
        }




    }

)

/*function requestRefreshToken(accessToken){
    let form ={
        expiredToken : accessToken
    }
    console.log("펑셩ㄴ! " +accessToken);
    return axios.request({
        url:'/api/member/new_token',
        method:'POST',
        data:form,

    })
}*/


export default instance;
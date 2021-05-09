import Send from "@/apis/common_api";

function findAreaByDong(dong){
    return Send({
        url:'/api/area/'+dong,
        method:'GET'
    })
}

export default {findAreaByDong};
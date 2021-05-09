
import area_api from "@/apis/area_api";
const areaStore={
    state:{
        areas:[]
    },
    mutations:{
        SET_AREA(state,payload){
            state.areas = payload;
        }
    },
    actions:{
        async REQUEST_AREAS(context,payload){
          const areaResponse = await area_api.findAreaByDong(payload);
          if(areaResponse){
              console.log(areaResponse.data);
              context.commit("SET_AREA",areaResponse.data);
          }
        }
    }

}

export default areaStore
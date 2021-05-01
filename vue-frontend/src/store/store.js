import Vue from "vue";
import Vuex from "vuex";
import memberStore from "@/store/memberStore"
import commonStore from "@/store/commonStore";

Vue.use(Vuex);

export const store = new Vuex.Store({
    // ...
    modules:{
        memberStore,
        commonStore,

    }

});
export default store;
import { createStore } from "vuex";
import axios from 'axios';

const store = createStore({
    state:{
        isLoggedIn:false,

    },
    mutations:{
        SET_LOGIN_STATUS(state, status){
            state.isLoggedIn = status;
        },

    },
    getters:{
        isLoggedIn: state => state.isLoggedIn,

    },
    actions:{
        login({commit}, payload){
            return new Promise((resolve,reject) =>{
                axios.post("http://localhost:8080/api/auth/login",payload,{withXSRFToken: true, withCredentials:true}).then((res)=> {
                    if(res.status == 200){
                        commit('SET_LOGIN_STATUS', true);
                        resolve({invalidLogin:false});
                    }
                }).catch((err) => {
                    console.log(err)
                    reject({invalidLogin:true});
                });
            }
        )},
        authenticate({commit}){
            axios.get("http://localhost:8080/api/auth/authenticate",{withCredentials:true}).then((resp) =>{
                commit('SET_LOGIN_STATUS', resp.data.authentication);
            }).catch(() =>{
                commit('SET_LOGIN_STATUS', false);
            })
            
        },
        logout({commit}){
            commit('SET_LOGIN_STATUS',false);
            commit('SET_USER',null);
        }

    }
})

export default store
import { reject } from "core-js/fn/promise";
import { createStore } from "vuex";

const store = createStore({
    state:{
        isLoggedIn:false,
        user:null
    },
    mutations:{
        SET_LOGIN_STATUS(state, status){
            state.isLoggedIn = status;
        },
        SET_USER(state, user){
            state.user = user;
        }

    },
    getters:{
        isLoggedIn: state => state.isLoggedIn,
        user: state => state.user

    },
    actions:{
        login({commit}, payload){
            return new Promise((resolve,reject) =>{
                fetch("http://localhost:8080/api/auth/login",{
                    method:"POST",
                    credentials:"include",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify(payload)
                }).then((resp) => resp.json()).then((json) =>{
                    commit('SET_USER', jsong.username)
                    commit('SET_LOGIN_STATUS', json.authentication)
                    resolve({"loginSuccess":true})
                }).catch(()=>{
                    commit('SET_LOGIN_STATUS',false)
                    reject({"loginSuccess":false})
                })
            })
        },
        authenticate({commit}){
            fetch("http://localhost:8080/api/auth/authenticate",{
                method:"GET",
                credentials:"include",
                headers:{
                    "Content-Type":"application/json"
                }
            }).then((resp) => resp.json()).then((json) =>{
                commit('SET_LOGIN_STATUS',json.authentication)
                commit('SET_USER',json.username)
            }).catch(()=>{
                commit('SET_LOGIN_STATUS', false)
            })
        }

    }
})

export default store
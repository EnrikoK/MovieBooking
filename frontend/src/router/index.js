
import { createRouter, createWebHistory } from "vue-router";
import store from "../store/store"
//import authenticate from "./utils/authenticate"

import MoviesPageView from "@/views/MoviesPageView.vue";
import RegisterView from "@/views/RegisterView.vue";
import SingleMovieView from "@/views/SingleMovieView.vue";
import ProfileView from "@/views/ProfileView"
import LoginView from "@/views/LoginView"

/*
const authenticationGuard = async(req,res,next) =>{
    let auth = await authenticate();
    if(auth){
        next();
    }
    next("/login")
}
*/
const routes = [
    {path:'/movies', name:'movies', component:MoviesPageView},
    {path:'/profile',name:'profile', component:ProfileView},
    {path:'/screening/:id',name:'screening',component:SingleMovieView},
    {path:'/register',name:'register',component:RegisterView},
    {path:'/login',name:'login',component:LoginView}
]

const router = createRouter({
    history:createWebHistory(),
    routes
});
router.beforeEach((req,res,next)=>{
    store.dispatch('authenticate');
    next();
})
export default router;

import { createRouter, createWebHistory } from "vue-router";
import store from "../store/store"
import authenticate from "../utils/authenticate"

import MoviesPageView from "@/views/MoviesPageView.vue";
import RegisterView from "@/views/RegisterView.vue";
import SingleMovieView from "@/views/SingleMovieView.vue";
import ProfileView from "@/views/ProfileView"
import LoginView from "@/views/LoginView"
import PurchaseSuccessfulView from "@/views/PurchaseSuccessfulView.vue"

const authenticationGuard = async(req,res,next) =>{
    let auth = await authenticate.authenticated()
    if(auth){
        next();
    }
    next("/login")
}

const routes = [
    {path:'/', redirect:'/movies'},
    {path:'/movies', name:'movies', component:MoviesPageView},
    {path:'/profile',name:'profile', component:ProfileView, beforeEnter:authenticationGuard},
    {path:'/screening/:id',name:'screening',component:SingleMovieView},
    {path:'/register',name:'register',component:RegisterView},
    {path:'/login',name:'login',component:LoginView},
    {path:'/success', name:'purchase-success', component:PurchaseSuccessfulView, props:true}
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
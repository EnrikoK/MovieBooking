
import { createRouter, createWebHistory } from "vue-router";

import MoviesPageView from "@/views/MoviesPageView.vue";
import RegisterView from "@/views/RegisterView.vue";


const routes = [
    {path:'/movies', name:'movies', component:MoviesPageView},
    {path:'/profile',name:'profile', component:MoviesPageView},
    {path:'/screening/:id',name:'screening',component:MoviesPageView},
    {path:'/register',name:'register',component:RegisterView}
]

const router = createRouter({
    history:createWebHistory(),
    routes
});

export default router;
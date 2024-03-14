<template>
    <nav v-if="this.login == true">
        <ul>
            <li><RouterLink class="nav-link" to="/movies">Filmid</RouterLink></li>
            <li><RouterLink class="nav-link" to="/profile">Profiil</RouterLink></li> 
            <li @click="logout">Logi välja</li>              
             
        </ul>
    </nav>
    <nav v-else>
        <ul>
            <li><RouterLink class="nav-link" to="/movies">Filmid</RouterLink></li>
            <li><RouterLink class="nav-link" to="/login">Logi Sisse</RouterLink></li>  
            <li><RouterLink class="nav-link" to="/register">Loo Kasutaja</RouterLink></li>              
            
        </ul>
    </nav>
    
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex';
export default{
    computed:{
        ...mapState({
            login:['isLoggedIn']
        })
    },
    methods:{
        async logout(){
            try{
                await axios.get("http://localhost:8080/api/auth/logout",{withCredentials:true})
                this.$store.dispatch('logout');
            }catch (err){
                console.log(err);
            }
            
            

        }
    },

}

</script>

<style>
nav {
    padding: 2em; 
    border-bottom: 3px solid rgb(255, 126, 34);

}

/* Style the list items */
ul {
    list-style-type: none; 
    margin: 0; 
    padding: 0; 
    display: flex;
    justify-content: space-evenly;
} 
li {
    display: inline; /* Display list items horizontally */
    padding: 1em;
    font-size: 24px;

}
li:hover{
   text-decoration:  rgb(255, 126, 34) underline 3px;
}
li:active{
    background-color: #ccc;
}
.nav-link{
    text-decoration: none;
    color: black;
    padding: 2em;
}

</style>
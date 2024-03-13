<template>
    <nav v-if="login">
        <ul>
            <li><RouterLink class="nav-link" to="/movies">Filmid</RouterLink></li>
            <li><RouterLink class="nav-link" to="/profile">Profiil</RouterLink></li> 
            <li @click="logout()">Logi välja</li>              
             
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
            login:'isLoggedIn'
        })
    },
    methods:{
        logout(){
            axios.get("http://localhost:8080/api/auth/logout",{withCredentials:true}).then(()=>{
               
                this.$store.dispatch('logout');
                // Redirect to login page
                this.$router.push('/login');
                // Optionally, force page reload
                window.location.reload();

                
            }).catch((err) =>{
                console.log(err);
            })
        }
    }

}

</script>

<style>
nav {
    padding: 2em; 
    border-bottom: 3px solid #526D82;
}

/* Style the list items */
ul {
    list-style-type: none; 
    margin: 0; 
    padding: 0; 
} 
li {
    display: inline; /* Display list items horizontally */
    padding: 2em;

}
li:hover{
   text-decoration:  #526D82 underline 3px;
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
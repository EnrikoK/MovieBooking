<template>
    <h1>Sinu Piletid</h1>
    <div id="tickets-area">
        <tickets-component></tickets-component>  
    </div>
</template>
    
<script>
import UserTickets from '@/components/UserTickets.vue';
import { mapState } from 'vuex';
export default {
      
    components: {
        'tickets-component':UserTickets
    },
    computed:{
        ...mapState({
            login:['isLoggedIn']
        })
    },
    watch:{
        login(newValue){
            if(newValue == false){
                this.$router.push("/login")
            }
        }
    }
}
</script>
    
<style scoped>
#tickets-area{
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
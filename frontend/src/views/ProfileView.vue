<template>
    <h1>Sinu Piletid</h1>
    <div id="tickets-area">
        <tickets-component></tickets-component>  
    </div>
</template>
    
<script>
import UserTickets from '@/components/UserTickets.vue';
export default {
      
    components: {
        'tickets-component':UserTickets
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
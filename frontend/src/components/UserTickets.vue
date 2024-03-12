<template>
<div class="tickets-container">
    <div class="ticket" v-for="(ticket,index) in this.tickets" :key="index">
        <div>
            <h3>{{ ticket.screening.movie.title }}</h3>
            <h3>{{  new Date(ticket.screening.date).toLocaleString('en-UK',{year: 'numeric',month: 'numeric',day: 'numeric',hour: 'numeric',minute: 'numeric'})  }}</h3>            
        </div>
        <div>
            <p>Rida: {{ ticket.row }}</p>
            <p>Koht: {{ ticket.seat }}</p>
        </div>

    </div>

</div>
</template>

<script>
import axios from 'axios'

export default{
    data: function(){
        return{
            tickets:null,
        }
    },
    methods:{
        fetchUserTickets(){
            axios.get("http://localhost:8080/api/tickets/user-tickets",{withCredentials:true}).
            then((res) =>{
                this.tickets = res.data;
            }).catch((err) =>{
                console.log(err)
            })
        }
    },
    mounted(){
        this.fetchUserTickets();
    }
}
</script>

<style>
.tickets-container{
    display: flex;
    flex-wrap: wrap;
    max-width: 70vw;
    
}
.ticket{
    border: black solid 1px;
    width: 400px;
    margin: 1em;
}
</style>
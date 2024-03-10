<template>
<h1>siia tuleb film</h1>
<seat-select></seat-select>
</template>

<script>
import axios from 'axios'
import SeatSelectionComponent from '@/components/SeatSelectionComponent.vue';
export default{
    data:function(){
        return{
            screeningInfo:null,
        }
    },
    components:{
        'seat-select':SeatSelectionComponent
    },
    mounted(){
        axios.get("http://localhost:8080/api/screenings/screening/"+this.$route.params.id)
        .then((res) => {
            this.screeningInfo = res.data;
        }).catch((err) =>{
            console.log(err);
        })
        
    }
}
</script>

<style>
</style>
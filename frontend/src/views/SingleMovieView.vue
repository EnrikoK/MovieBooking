<template>
<div id="screening-content">
    <seat-select :taken-seats="this.screeningInfo==null?[]:this.screeningInfo.takenSeats"></seat-select>
    <movie-description :movie-data="this.screeningInfo==null?null:this.screeningInfo.screening"></movie-description>    
</div>

</template>

<script>
import axios from 'axios'
import SeatSelectionComponent from '@/components/SeatSelectionComponent.vue';
import MovieDescriptionComponent from '@/components/MovieDescriptionComponent.vue';
export default{
    data:function(){
        return{
            screeningInfo:null,
        }
    },
    components:{
        'seat-select':SeatSelectionComponent,
        'movie-description':MovieDescriptionComponent
    },
    beforeMount(){
        axios.get("http://localhost:8080/api/screenings/screening/"+this.$route.params.id)
        .then((res) => {
            this.screeningInfo = res.data;
            console.log(this.screeningInfo)
        }).catch((err) =>{
            console.log(err);
        })
        
    }
}
</script>

<style scoped>
#screening-content{
    padding-top: 2em;
    display: flex;
    justify-content: space-evenly;
    align-items: flex-start
}
</style>
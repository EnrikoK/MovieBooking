<template>
<div>
    
</div>
<div v-for="elem in this.upcomingScreenings" :key="elem.id" class="screening-item"> 
    <router-link :to="{name:'screening',params:{id:elem.id}}" @click.prevent="" >
        <div>
            <div class="screening-header">
                <h3>{{ elem.movie.title }}</h3>
                <h3>{{ new Date(elem.date).toLocaleString('en-UK',{year: 'numeric',month: 'numeric',day: 'numeric',hour: 'numeric',minute: 'numeric'}) }}</h3>
            </div>
        </div>
        <div>
            <label>Žanrid:</label>
            <ul>
                <li v-for="(genre, index) in elem.movie.genres" :key="index">{{ genre.genre }}</li>
            </ul>             
        </div>
    </router-link>
</div>
</template>

<script>
import axios from 'axios'
export default{
    data:function(){
        return{
            upcomingScreenings:null,
        }
    },
    mounted(){
        axios.get("http://localhost:8080/api/screenings/upcoming")
        .then((res) => {
            this.upcomingScreenings=res.data.upcoming
        }).catch((err) =>{
            console.log(err);
        })
    }
}
</script>

<style>
.screening-header{
    display: flex;
    justify-content: space-around;
}
.screening-item{
    box-shadow: 0 5px 5px rgba(0, 0, 0, 0.1);
    padding: 1em;
}
</style>
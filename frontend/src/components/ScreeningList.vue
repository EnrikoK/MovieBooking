<template>
<div id="component-container">
    <div class="filter-bar">
        <div>
            <label for="genre">Žanr: </label>
            <select v-model="this.selectedGenre" @change="applyFilters">
                <option value="">All</option>
                <option v-for="genre in genres" :key="genre" :value="genre">{{ genre }}</option>
            </select>

            <label for="language">Keel: </label>
            <select v-model="this.selectedLanguage" @change="applyFilters">
                <option value="">All</option>
                <option v-for="language in this.languages" :key="language" :value="language">{{ language }}</option>
            </select>

            <label for="rating">Vanus: </label>
            <select v-model="this.selectedRating" @change="applyFilters">
                <option value="">All</option>
                <option v-for="rating in this.contentRating" :key="rating" :value="rating">{{ rating }}</option>
            </select>
            <label for="time">Alates kellaajast: </label>
            <input type="time" name="time" v-model="this.selectedTime" @change="applyFilters">
            <div class="button-area">
                <button class="recomendations-button" @click="removeFilters">Eemalda filtrid</button>
                <div v-if="this.$store.state.isLoggedIn">
                    <button :class="this.showRecomendations?'recomendations-button active':'recomendations-button'" @click="toggleUserRecomendations()">Vaata enda soovitus!</button>
                </div>                
            </div>

        </div>


    </div>

    <div v-for="elem in this.visibleScreenings" :key="elem.screening.id" class="screening-item"> 
        <router-link :to="{name:'screening',params:{id:elem.screening.id}}" @click.prevent="" >
            <div>
                <div class="screening-header">
                    <h3>{{ elem.screening.movie.title }}</h3>
                    <h3>{{ new Date(elem.screening.date).toLocaleString('en-UK',{year: 'numeric',month: 'numeric',day: 'numeric',hour: 'numeric',minute: 'numeric'}) }}</h3>
                </div>
            </div>
            <div class="screening-content">
                <div>
                    <ul>
                        <p>Žanrid: </p>
                        <li v-for="(genre) in elem.screening.movie.genres" :key="genre"> {{ genre.genre }}</li>
                    </ul>                    
                </div>
                <p v-if="elem.score">Filmi IMDB skoor: {{ elem.score }}</p>
                <img v-if="elem.posterUrl !=null" class="movie-poster" :src="elem.posterUrl" >
                <img v-else class="movie-poster" src="../../public/generic.png">             
            </div>
        </router-link>
    </div>    
</div>

</template>

<script>
import axios from 'axios'
export default{
    data:function(){
        return{
            visibleScreenings:null,
            genres:new Set(),
            languages: new Set(),
            contentRating: new Set(),
            selectedGenre:"",
            selectedLanguage:"",
            selectedRating:"",
            selectedTime:"00:00",
            showRecomendations:false,
            recomendedScreenings:null,
            upcomingScreenings:null,
        }
    },
    methods:{
        getUserRecomendations(){
            axios.get("http://localhost:8080/api/screenings/user-recomendations",{withCredentials:true})
            .then((res)=>{
                this.recomendedScreenings= res.data;
                
            }).catch((err)=>{
                console.log(err);
            })
        },
        applyFilters(){
            let filters={
                genre:this.selectedGenre,
                language:this.selectedLanguage,
                rating:this.selectedRating,
                time:this.selectedTime
            };
            
            this.showRecomendations = false;
            this.visibleScreenings = this.upcomingScreenings.filter((item) =>{
                if(
                    ( filters.genre == "" || this.genreCheckerUtil(item.screening.movie.genres,filters.genre) ) &&
                    (filters.language == "" || item.screening.movie.language == filters.language ) &&
                    (filters.rating == "" || item.screening.movie.rating == filters.rating) &&
                    (filters.time == "" || this.dateCheckerUtil(item.screening.date,filters.time.split(":")))
                ){
                    return true;
                }
                else{
                    return false;
                }
                
            })
        },
        toggleUserRecomendations(){
            this.showRecomendations = !this.showRecomendations;
            this.selectedGenre = "";
            this.selectedLanguage = "";
            this.selectedRating = "";
            if(this.showRecomendations){
                this.visibleScreenings=this.recomendedScreenings;
            }else{
                this.visibleScreenings=this.upcomingScreenings;
            }
        },
        dateCheckerUtil(date,filter){
  
            let d = new Date(date);
            return d.getHours() > parseInt(filter[0]) || (d.getHours() == parseInt(filter[0]) && d.getMinutes() >= parseInt(filter[1]))       
        },
        genreCheckerUtil(genres, filter){
            for(let elem of genres){
                if(elem.genre == filter) return true;
            }
            return false;
        },
        removeFilters(){
            this.selectedGenre = "";
            this.selectedLanguage = "";
            this.selectedRating = "";
            this.selectedTime = "00:00";
            this.showRecomendations = false;
            this.applyFilters()
        }
    },
    mounted(){
        axios.get("http://localhost:8080/api/screenings/upcoming")
        .then((res) => {
            this.upcomingScreenings=res.data.upcoming;
            this.visibleScreenings = res.data.upcoming;
            for(let elem of res.data.upcoming){
                this.genres.add(elem.screening.movie.genres[0].genre);
                this.languages.add(elem.screening.movie.language);
                this.contentRating.add(elem.screening.movie.rating)
            }
            
        }).catch((err) =>{
            console.log(err);
        })
        if(this.$store.state.isLoggedIn) this.getUserRecomendations();
    }
}
</script>

<style scoped>
.filter-bar{
    display: flex;
    justify-content: space-evenly;
    font-size: large;
}
.filter-bar select,option{
    font-size: large;
    margin: 1em;
}
.button-area{
    display: flex;
    align-items: center;
    justify-content: center;
}
.recomendations-button{
    font-size: large;
    padding: 1em;
    margin-top: 0.5em;
}
.active{
    background-color: greenyellow;
}
#component-container{
    max-width: 60vw;
}
.screening-header{
    display: flex;
    justify-content: space-around;
}
.screening-content{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.screening-content ul,li {
    text-decoration:  none;
    font-size: large;
    font-weight: bold;

}
.screening-item{
    box-shadow: 0 5px 5px rgba(0, 0, 0, 0.5);
    padding: 1em;
    margin: 1em;
    min-width: 50vw;
    max-width: 60vw;
    
}

.screening-item:hover{
    background-color: rgb(237, 237, 237);
}
.movie-poster{
    max-width: 200px;
    margin: 0.75em;
}

</style>
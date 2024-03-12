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
        <select v-model="this.contentRating" @change="applyFilters">
            <option value="">All</option>
            <option v-for="rating in this.contentRating" :key="rating" :value="rating">{{ rating }}</option>
        </select>
        </div>
        <div v-if="this.$store.state.isLoggedIn">
            <button :class="this.showRecomendations?'recomendations-button active':'recomendations-button'" @click="toggleUserRecomendations()">Vaata enda soovitus!</button>
        </div>
    </div>

    <div v-for="elem in this.visibleScreenings" :key="elem.id" class="screening-item"> 
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
                <img class="movie-poster" src="../../public/generic.png">             
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
                rating:this.selectedRating
            };
            this.showRecomendations = false;
            this.visibleScreenings = this.upcomingScreenings.filter((item) =>{  
                console.log(item.movie.genres[0].genre)
                if(
                    ( filters.genre == "" ||item.movie.genres[0].genre == filters.genre) &&
                    (filters.language == "" || item.movie.language == filters.language ) &&
                    (filters.rating == "" || item.movie.rating == filters.rating)
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
            if(this.recomendedScreenings == null){
                this.getUserRecomendations();
            }
            if(this.showRecomendations){
                this.visibleScreenings=this.recomendedScreenings;
            }else{
                this.visibleScreenings=this.upcomingScreenings;
            }
            console.log(this.upcomingScreenings)
        }
    },
    mounted(){
        axios.get("http://localhost:8080/api/screenings/upcoming")
        .then((res) => {
            this.upcomingScreenings=res.data.upcoming;
            this.visibleScreenings = res.data.upcoming;
            for(let elem of res.data.upcoming){
                this.genres.add(elem.movie.genres[0].genre);
                this.languages.add(elem.movie.language);
                this.contentRating.add(elem.movie.rating)
            }
        }).catch((err) =>{
            console.log(err);
        })
    }
}
</script>

<style scoped>
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
.screening-item{
    
}
</style>
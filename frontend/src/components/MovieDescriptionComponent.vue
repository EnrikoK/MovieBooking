<template>
<div v-if="movieData!=null" class="description-container">
    <h1>{{ this.movieData.movie.title }}</h1>
    <p>{{ new Date(this.movieData.date).toLocaleString('en-UK',{year: 'numeric',month: 'numeric',day: 'numeric',hour: 'numeric',minute: 'numeric'}) }}</p>
    <div class="genres-container">
        <p>Žanr: </p>
        <p v-for="(genre,index) in this.movieData.movie.genres" :key="index" class="genre-list">{{ genre.genre }} </p>        
    </div>
    <h4>{{ this.movieData.movie.description }}</h4>

</div>
</template>

<script>
export default{
    props:{
        movieData: Object
    }
}
</script>

<style scoped>
.description-container{
    max-width: 20%;
    overflow:hidden;
    text-align: left;
    font-size: 20px;
}
.genres-container{
    display: flex;
}
.genre-list:not(:last-child)::after {
  content: ", ";
}
</style>
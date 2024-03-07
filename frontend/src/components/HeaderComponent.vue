<template>
    <nav>
        <ul>
            <li><RouterLink class="nav-link" to="/movies">Filmid</RouterLink></li>
            <li><RouterLink class="nav-link" to="/profile">Profiil</RouterLink></li>              
        </ul>
    </nav>
</template>

<script>
export default{

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
<template>
    <h1>Registreeri end kasutajaks!</h1>
    <signup-form></signup-form>
    <RouterLink to="/login">Kui omad juba kontot, siis logi sisse siit!</RouterLink>
</template>
    
<script>
import SignupFormComponent from "../components/SignupFormComponent.vue"
export default{
    components:{
        'signup-form':SignupFormComponent 
    }
}
</script>
    
<style scoped>
</style>
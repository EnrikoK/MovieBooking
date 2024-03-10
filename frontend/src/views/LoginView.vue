<template>
    <h1>Logi sisse siin!</h1>
    <login-form></login-form>
    <RouterLink to="/register">Ei oma kontot? Loo see siin!</RouterLink>
</template>
    
<script>
import LoginFormComponent from '@/components/LoginFormComponent';

export default{
    components:{
        'login-form':LoginFormComponent
    }
}
</script>
    
<style scoped>

</style>
<template>
    <div>
        <form>
            <label>Kasutajanimi</label>
            <input type="email" v-model="this.email">
            <label>Salasõna</label>
            <input type="password" v-model="this.password">
            <button @click.prevent="submitLogin()">Log In</button>        
        </form>
        <p v-if="this.invalidLogin" class="error-tooltip"> {{ this.errorMessage }} </p>
    </div>
</template>

<script>
export default{
    name:"login-form",
    data:function(){
        return{
            email:null,
            password:null,
            invalidLogin:false,
            errorMessage:null
        }
    },
    methods:{
        submitLogin(){
            var payload = {
                username:this.email,
                password:this.password
            }
            this.$store.dispatch('login',payload).then(()=>{
                this.$router.push("/movies");
            }).catch((err) => {
                this.invalidLogin = err.invalidLogin;
            })
        }
    }
}
</script>

<style scoped>
form {
    max-width: 400px;
    margin: 2em auto;
    padding: 2em;
    background: #f4f4f4;
    border: 1px solid #526D82;
    border-radius: 5px;
    
}

label {
    display: block;
    margin-bottom: 8px;
}

input {
    width: 100%;
    padding: 8px;
    margin-bottom: 12px;
    box-sizing: border-box;
}

button {
    background-color: #4caf50;
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #45a049;
}
.error-tooltip{
    margin:1em auto;
    background-color: rgb(214, 140, 140);
    max-width: 400px;
    padding: 1.5em;
    border-radius: 5px;
}
</style>
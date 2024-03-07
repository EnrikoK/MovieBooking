<template>
    <div>
        <form>
            <label>Kasutajanimi</label>
            <input type="text" v-model="this.username">
    
            <label>Salasõna</label>
            <input type="password" v-model="this.password">
    
            <label>Korda salasõna</label>
            <input type="password" v-model="this.repeatpassword">
    
            <button @click.prevent="createAccount()" >Submit</button>
        </form>
        <p v-if="this.error" class="error-tooltip">{{ errorMessage }}</p>
    </div>
</template>
    
<script>
export default{
    name:"SignupComponent",
    data(){
        return{
            username:null,
            password:null,
            repeatpassword:null,
            error:false,
            errorMessage:null
        }
    },
    methods:{
        async createAccount(){

                
        },
        validateInputs(){
            if(this.username == null){
                this.error = true;
                this.errorMessage = "Sisesta kasutajanimi"
            }else if(this.password != this.repeatpassword){
                this.error = true;
                this.errorMessage = "Salasõnad ei klapi! Kontrolli oma salasõnu!"
            }
            else{
                this.createAccount(); 
            }
   
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
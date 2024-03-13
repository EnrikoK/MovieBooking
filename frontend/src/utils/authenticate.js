export default {
    user: {authenticated:false},
    authenticated: async function(){
        await fetch("http://localhost:8080/api/auth/authenticate",
        {credentials:'include'})
        .then((response)=> response.json())
        .then((data)=>{
            this.user.authenticated = data.authentication;
        }).catch((err) => {
            console.log(err.message);
            this.user.authenticated = false;
        });
        return this.user.authenticated;
    }
}
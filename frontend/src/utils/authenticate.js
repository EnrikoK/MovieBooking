import axios from 'axios'

const authenticate = async function(){
    var result = new Promise((resolve,reject) => {
        axios.get("http://localhost:8080/api/auth/authenticate",{withCredentials:true}).then((res) => res.json()).then((json) =>{
            if(json.authenticate){
                console.log(json)
                resolve(true);
            }else{
                reject(false);
            }
        }).catch(() =>{
            reject(false);
        })
    })

    return result;
}
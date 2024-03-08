const authenticate = async function(){
    var result = new Promise((resolve,reject) => {
        fetch("http://localhost:8080/api/auth/authenticate",{
            method:"GET",
            credentials:"include"
        }).then((res) => res.json()).then((json) =>{
            if(json.authenticate){
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
<template>
<div :class="this.$store.state.isLoggedIn ? 'component-container':'component-container unclickable'">
    <div class="selector-container">
        <div >
            <h3 class="screen">Ekraan</h3>
            <div v-for="(row, indexRow) in this.seatsMatrix" :key="indexRow" class="seat-row">
                <p class="row-annotation">Rida:{{ indexRow +1}}</p>
                <div v-for="(seat, indexCol) in row" :key="indexCol">
                    <div v-if="seat==0" class="seat free" @click="selectSeat(indexRow,indexCol)" >{{ (indexCol+1)  }}</div>
                    <div v-else-if="seat==1" class="seat taken">{{ (indexCol+1)  }}</div>
                    <div v-else class="seat selected" @click="selectSeat(indexRow,indexCol)" >{{ (indexCol+1)  }}</div>
                </div>
            </div>
        </div>
        <div v-if="this.$store.state.isLoggedIn" class="ticket-selector">
            <button @click="handleIncrement()">+</button>
            <p>{{ this.activeSeats.length }}</p>
            <button @click="handleDecrement()">-</button>
        </div>
        <div v-else class="ticket-selector unclickable">
            <button @click="handleIncrement()">+</button>
            <p>{{ this.activeSeats.length }}</p>
            <button @click="handleDecrement()">-</button>
            <p>Logi sisse või loo kasutaja, et osta pileteid</p>
        </div>
    </div>
    <div class="card-details">
        <label>email:</label>
        <input :value="this.email">
        <label>Kaarti number:</label>
        <input :value="this.card">
        <label>CVC:</label>
        <input :value="this.cvc">
        
    </div> 
    <button id="purchase-button" @click="this.buyTickets()">Osta</button> 
    <p v-if="this.tooltip" class="tooltip">Kõrvuti kohti ei leitud. Vali ise sobivad kohad!</p>
</div>
</template>

<script>
import axios from 'axios'
export default{

    data: function(){
        return{
            seatsMatrix:[[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0]],
            activeSeats:[],
            tooltip:false,
            tooltipMessage:"Kõrvuti kohti ei leidunud, vali ise sobivad kohad!",
            screeningInfo:null,
            card:null,
            cvc:null,
            email:null
        }
    },
    props:{
        takenSeats: Array
    },
    methods:{
        selectSeat(row,col){

            let seat = this.seatsMatrix[row][col];
            if(seat == 2 ){
                this.seatsMatrix[row][col]=0
                this.activeSeats = this.activeSeats.filter(x =>!(x[0] == row && x[1] == col))

            }else if(seat == 0){
                this.seatsMatrix[row][col]=2
                this.activeSeats.push([row,col])
                

            }
        },
        recomendSeats(seats) {
            if(seats === undefined){
                seats = 2;
            }
            //Using the middle point of the matirx, find the seats closest to the middle
            let bestRow = 999;
            let bestCol = 999;
            //Row counter
            let r = 0;
            //Loop through each row of seats
            for (const row of this.seatsMatrix) {
                //In a row, find the needed amount of seats next to each other
                for (let i = 0; i < row.length - seats+1; i++) {
                    //Count the needed amount of seats
                    let counter = seats;
                    for (let j = 0; j < seats; j++) {
                        if (row[i + j] == 0) {
                        counter--;
                        }else{
                            break;
                        }
                    }
                    //If the distance to the middle is shorter than by the previous match update the best seats
                    if (counter == 0 && Math.abs(((2*i+seats)/2) - 3) + Math.abs(r - 2.5) < Math.abs(((2*bestCol+seats)/2) - 3) + Math.abs(bestRow - 2.5)) {
                        bestCol = i;
                        bestRow = r;
                    }
                }    
                r++;
            }
            //If consecutive seats were found, highlight them
            if(bestRow != 999){
                for(let i = 0; i<seats;i++){
                    this.selectSeat(bestRow,bestCol+i);
                    this.tooltip = false;
                }
            }else{
                //Show tooltip for user to select seats manually
                this.tooltip = true;
            }
        },
        generateRandomTakenSeats(){
            return new Promise((resolve)=>{
                let x = Math.random()*15;
                while (x > 0) {
                    let row = Math.floor(Math.random() * this.seatsMatrix.length);
                    let col = Math.floor(Math.random() * this.seatsMatrix[row].length);

                    if (this.seatsMatrix[row][col] == 0) {
                    this.seatsMatrix[row][col] = 1;
                    x--;
                    }
                }
                resolve();                
            })

        },
        handleIncrement(){
            if(this.activeSeats.length >= 30){
                return;
            }
            let seats = this.activeSeats.length;
            for (let seat of this.activeSeats){
                this.selectSeat(seat[0],seat[1]);
            }
            this.activeSeats=[];
            this.recomendSeats(seats+1);
        },
        handleDecrement(){
            if(this.activeSeats.length <= 1){
                return;
            }
            let seats = this.activeSeats.length;
            for (let seat of this.activeSeats){
               this.selectSeat(seat[0],seat[1]);

            }
            this.activeSeats=[];
            this.recomendSeats(seats-1); 
        },
        fillRealTakenSeats(takenSeats){
            return new Promise((resolve) =>{
                for(let seat of takenSeats){
                    if(seat.row > 5 || seat.seat >6){
                        continue;
                    }
                    if(this.seatsMatrix[seat.row-1][seat.seat-1]==0 || this.seatsMatrix[seat.row-1][seat.seat-1]==2){
                        this.seatsMatrix[seat.row-1][seat.seat-1]=1;
                    }
                }
                resolve();
            })

        },
        buyTickets(){
            let seatsPayload = [];
            for(let elem of this.activeSeats){
                seatsPayload.push([elem[0]+1,elem[1]+1])
            }
            let payload = {
                screeningID:this.$route.params.id,
                seats:seatsPayload
            }
            axios.post("http://localhost:8080/api/screenings/purchase-ticket",payload,{withXSRFToken: true, withCredentials:true})
            .then((res) =>{
                this.$router.push({name:'purchase-success', props:{tickets:res.data}})
            }).catch((err)=>{
                console.log(err);
            })
        } 
    },
    async beforeMount() {

        this.$store.dispatch('authenticate');
        this.fillRealTakenSeats(this.takenSeats)
        this.generateRandomTakenSeats();

       
        
    },
    watch:{
        takenSeats(){
            this.fillRealTakenSeats(this.takenSeats).then(this.recomendSeats())
        }
    }

    
}
</script>

<style scoped>
.card-details{
    font-size: large;
    margin-top: 1em;
}
.card-details > label{
    margin-right: 0.25em;
    margin-left: 0.25em;

}
.card-details > input{
    height: 1.5em;
}
#purchase-button{
    padding: 1em;
    font-size: large;
    margin: 1em;
    align-self: center;
    background-color: rgb(255, 126, 34);
    cursor: pointer;
}
#purchase-button:active{
   background-color: rgb(255, 142, 62);
}
.tooltip{
    margin:1em auto;
    background-color: rgb(255, 232, 115);
    max-width: 400px;
    padding: 1.5em;
    border-radius: 5px;
    font-size: large;
}
.component-container {
    max-width: 50vw;
    min-height: 60vh;
    max-height: 60vh;
    display: flex;
    flex-direction: column;
    margin: 1em;
}
.seat-row {
  display: flex;
  justify-content: center;
  align-items: center;
  
}
.seat {
  width: 30px;
  height: 30px;
  border: 1px solid black;
  margin: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.row-annotation {
  margin-right: 1em;
}
.screen{
    text-decoration: underline;
    border: 3px solid black;
    padding: 0.5em;
    border-radius: 5px;
    min-width: 400px;
}
.selector-container{

    display: flex;
    justify-content: space-around;
    align-items: center;
    box-shadow: 5px 5px 5px 5px rgba(0, 0, 0, 0.1);
    padding: 3em;
}

.selected{
    background-color: rgb(255, 232, 115);
}
.taken{
    background-color: red;
}
.free{
    background-color: green;
}
.unclickable{
    opacity: 0.5;
    pointer-events: none;
}
.ticket-selector{
    font-size: large;
    padding: 1em;

    
}
.ticket-selector > button{
    padding: 1em;
    font-size: large;
    background-color: rgb(255, 126, 34);
    border: 1px black solid;
    
}
.ticket-selector > button:hover{
    background-color: rgb(255, 142, 62);
    
}
</style>
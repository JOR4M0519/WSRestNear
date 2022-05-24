let response = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
let result = await response.json();

let response2 = await fetch(`./api/arts/${localStorage.getItem("id")}/price`);
let result2 = await response2.json();

if(result.fconis >= result2.price){

    

}
let response = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
let result = await response.json();

let response2 = await fetch(`./api/users/${localStorage.getItem("username")}/collections/`);
let result2 = await response2.json();

if(result.fconis >= ){

}
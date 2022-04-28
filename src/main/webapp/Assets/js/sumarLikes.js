

//document.getElementById('sumarLikes').addEventListener('onclick', btnLike);
//document.getElementById('restarLikes').addEventListener('click', btnLike);

//Registra cada vez que es oprimido el botón de likes de un NFT para a continuación de esto realizar la correspondiente acción solicitada
function btnLike(){
    const urlApi = "http://localhost:8080/WSRestNear-1.0-SNAPSHOT/api/likes";
    let data = fetch(urlApi+`/email=${localStorage.getItem("username")}`).then(response => response.json());
    alert("click!");
    if(document.getElementById("sumarLikes") != null){
        sumarLikes();
        document.getElementById("sumarLikes").id ="restarLikes";
        console.log(document.getElementById("restarLikes").textContent);
    }else{
        restarLikes();
        document.getElementById("restarLikes").id ="sumarLikes";
    }
}

//Suma los likes que tiene cada NFT
function sumarLikes(){
    var inputCantidad = document.getElementById("amountLikes");
    var cantidad = inputCantidad.textContent;
    inputCantidad.textContent = parseInt(cantidad) + parseInt(1);
}

//Resta los likes que tiene cada NFT
function restarLikes(){
    var inputCantidad = document.getElementById("amountLikes");
    var cantidad = parseInt(inputCantidad.value);

    if (cantidad>=1) {
        inputCantidad.value = cantidad - parseInt(1);
    }
}

//document.getElementById('sumarLikes').addEventListener('onclick', btnLike);
//document.getElementById('restarLikes').addEventListener('click', btnLike);

//Registra cada vez que es oprimido el botón de likes de un NFT para a continuación de esto realizar la correspondiente acción solicitada
const btnLike = async (id, type) => {
    if(!(localStorage.getItem("username") == null)) {
        let data = await fetch(`./api/users/${localStorage.getItem("username")}/arts/${id}`).then(response => response.json());
        console.log(data.email.toString())
        if (data.email.toString() != "") {
            restarLikes(id.toString(), type);
        } else {
            sumarLikes(id.toString(), type);
        }

        await fetch(`./api/users/${localStorage.getItem("username")}/arts/${id}/likes/like`, {
            method: "POST"
        }).then(response => response.json());
    }else {
        alert("Ingrese a una cuenta primero")
    }
}

//Suma los likes que tiene cada NFT
function sumarLikes(id, type) {
    var inputCantidad = document.getElementById("amountLikes" + type + id);
    var imgStatus = document.getElementById("heartStatus" + type + id);
    var cantidad = inputCantidad.textContent;

    imgStatus.src = "Assets/svg/heart-fill.svg";
    inputCantidad.textContent = parseInt(cantidad) + parseInt(1);

}

//Resta los likes que tiene cada NFT
function restarLikes(id, type) {
    var inputCantidad = document.getElementById("amountLikes" + type + id);
    var imgStatus = document.getElementById("heartStatus" + type + id);
    var cantidad = inputCantidad.textContent;

    imgStatus.src = "Assets/svg/heart-unfill.svg";
    inputCantidad.textContent = parseInt(cantidad) - parseInt(1);

}

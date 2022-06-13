var imagesDiv = document.getElementById("card");
var dataDiv = document.getElementById("data");
var cantidad = localStorage.getItem('cantidadCompras');

const getArtShopping = async () => {

    if (cantidad != null) {
        if(cantidad != 0){

        var item = [];
        for (var i = 1; i <= cantidad; i++) {
            item.push(JSON.parse(localStorage.getItem(`buy${i}`)));
        }

        for (const items of item) {
            const {id, collection, title, author, price} = items;

            imagesDiv.innerHTML += `
        <div class="card mb-3" style="max-width: 100%; border-radius: 10px;">
            <div class="row g-0" id="rowItemCart">
                <div class="col-md-4" id="groupArtCart">
                    <img src="${id}" class="img-fluid rounded-start" alt="..." id="imgArtCart">
                </div>
                <div class="col-md-8 bgbanner">
                    <div class="card-body" style="position: relative;">
                        <div style="margin-left: 10%; position: relative;">
                            <h3 class="card-title">${title}</h3><br>
                            <p class="card-text">$${new Intl.NumberFormat().format(price)} Fcoins</p><br>
                            <p class="card-text"><span class="text-muted">By: ${author}</span></p><br>
                            <p class="card-text"><small class="text-muted">Colección: ${collection}</small></p><br>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group btns"> 
                                  <button type="button" id="btnRemoveCart" onclick="removeItem('${id.toString().split("\\")[1]}')" class="btn btn-sm btn-outline-secondary">Remover del carro</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
          </div>
  `;
        }}
    }

}

const addData = async () =>{
        
    var totalPrice = 0;
    var restaFCoinsText = "";
    var restaFCoins = 0;
    var fcoins = 0;

    if((localStorage.getItem('username')!=null || localStorage.getItem('username')!=undefined) && localStorage.getItem('cantidadCompras')>0 ){
    let response = await fetch(`./api/users/${localStorage.getItem("username")}/wallet/fcoins`);
    let result = JSON.parse(await response.text());


   fcoins = result.fcoins;

    var dataArts = "[";
    var cantidad = parseFloat(localStorage.getItem('cantidadCompras'));

    for (var i=1; i<cantidad;i++){
        dataArts = dataArts+`${localStorage.getItem(`buy${i}`)},`;
    }
    dataArts = dataArts+`${localStorage.getItem(`buy${cantidad}`)}]`;


    var dataArtsJSON = JSON.parse(dataArts);
    

    for (const data1 of dataArtsJSON) {
        const {price} = data1;
        totalPrice += price;
    }
    restaFCoins = fcoins - totalPrice;
}
    if (fcoins<totalPrice){
        document.getElementById('btnBuyAllArts').disabled=true;
        document.getElementById('btnBuyAllArts').style.backgroundColor= "darkgray";
        restaFCoinsText="FCoins Faltantes";
    }else{
        document.getElementById('btnBuyAllArts').disabled=false;
        document.getElementById('btnBuyAllArts').style.backgroundColor= "#BA2737";
        restaFCoinsText="FCoins Restantes";
    }
    


    dataDiv.innerHTML =`
    <div class="resumenhead">
        <h4 style="padding: 1.5%;"><b> Tu Orden </b></h4>
    </div>
    <div class="cresumen" style="row-gap: 10%; height: 40vh; border: none;">
        
            <p class="itemLeft">Subtotal</p>
            <p>$${new Intl.NumberFormat().format(totalPrice)}</p>
            
            <p class="itemLeft" >Descuentos</p>
            <p>$0</p>
            
            <h4 class="itemLeft"><b> FCoins </b></h4>
            <h4 ><b> $${new Intl.NumberFormat().format(fcoins)} </b></h4>
            
            <h4><b></b></h4>
            <h4 style="color: #BA2737;"><b> -$${new Intl.NumberFormat().format(totalPrice)} </b></h4>
            
            <h3 class="itemLeft"><b> ${restaFCoinsText} </b></h3>
            <h3><b> $${new Intl.NumberFormat().format(restaFCoins)} </b></h3>

    </div>`;
}


const comprar = async () =>{

    let response = await fetch(`./api/users/${localStorage.getItem("username")}/wallet/fcoins`);
    let result = JSON.parse(await response.text());

    var dataArts = "[";
    var cantidad = parseFloat(localStorage.getItem('cantidadCompras'));

    for (var i=1; i<cantidad;i++){
        dataArts = dataArts+`${localStorage.getItem(`buy${i}`)},`;
    }
    dataArts = dataArts+`${localStorage.getItem(`buy${cantidad}`)}]`;


    var dataArtsJSON = JSON.parse(dataArts);
    var totalPrice = 0;

    for (const data1 of dataArtsJSON) {
        const {price} = data1;
        totalPrice += price;
    }



    if (result.fcoins<totalPrice){
        alert('Fondos Insuficientes');
    }else{
        for (const data2 of dataArtsJSON) {

            const {id,price} = data2;
            let idNFT = id.toString().split("\\")[1];

            //Propietario viejo del arte
            let {username} =  await fetch(`./api/owners/arts/${idNFT}`).then(response => response.json());

            //Cambia de propietario la pieza
            await fetch(`./api/owners/${localStorage.getItem('username')}/arts/${idNFT}`,
                {method: 'PUT'});

            //JSON Vendedor
            var historySeller = {  "username": username.toString(),
                "walletType": "Venta",
                "fcoins": price,
                "art": idNFT,
                "registeredAt": new Date(),
                "originProduct": localStorage.getItem('username').toString() };

            historySeller = JSON.stringify(historySeller);

            //JSON Customer
            var historyCustomer = {  "username":localStorage.getItem('username').toString(),
                "walletType": "Compra",
                "fcoins": (price * -1),
                "art": idNFT,
                "registeredAt": new Date(),
                "originProduct": username.toString()};

            historyCustomer = JSON.stringify(historyCustomer);

            //Se añade la compra al historial
            await fetch(`./api/users/${localStorage.getItem("username")}/wallet`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: historyCustomer
            });

            //Se añade la compra al historial del viejo propietario
            await fetch(`./api/users/${username}/wallet`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: historySeller
            });
        }


        for (var i=1; i<=localStorage.getItem('cantidadCompras');i++){
            localStorage.removeItem(`buy${i}`);
        }
        localStorage.removeItem('cantidadCompras');

        Swal.fire('Compra Exitosa!').then((result) =>{
            if (result.isConfirmed){
                top.location.href="./index.html"
            }
        })

    }
}

const test = async () =>{
    /*for (let i=0;i<100000;i++) {
    }*/
        setTimeout(5000);

}
function removeItem (idNFT) {

    for (var i = 1; i <= cantidad; i++) {
        if(JSON.parse(localStorage.getItem(`buy${i}`)).id.toString().split("\\")[1] == idNFT){

            Swal.fire({
                title: '¿Estás Seguro?',
                text: "",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, Borralo!'
            }).then((result) => {
                 if (result.isConfirmed) {
                    localStorage.removeItem(`buy${i}`);
                     if (localStorage.getItem('cantidadCompras')!=1) {
                         localStorage.setItem('cantidadCompras', parseInt(localStorage.getItem('cantidadCompras')) - parseInt(1))
                     }else {
                         localStorage.removeItem('cantidadCompras');
                     }
                     location.reload();
                }
            })
        }
    }


}
document.getElementById("btnBuyAllArts").addEventListener('click', comprar);

window.addEventListener("DOMContentLoaded", getArtShopping(), addData());
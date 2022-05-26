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

            imagesDiv.innerHTML += `<div class="card mb-3" style="max-width: 100%; border-radius: 10px;">
            <div class="row g-0" id="rowItemCart">
              <div class="col-md-4" id="groupArtCart">
                <img src="${id}" class="img-fluid rounded-start" alt="..." id="imgArtCart">
              </div>
              <div class="col-md-8 bgbanner">
                <div class="card-body" style="position: relative;">
                    <div style="margin-left: 10%; position: relaltive;">
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
    let response = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
    let result = await response.json();

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
    


    dataDiv.innerHTML =`<div class="resumenhead">
    <h4 style="padding: 1.5%;"><b> Tu Orden </b></h4>
  </div>
  <div class="cresumen">
    <div style="position: absolute; padding-top: 2.5%; left: 5%;">
      
      <p>Subtotal</p>
      <p>Descuentos</p>
      <h4><b> FCoins </b></h4>
      <h4 style="color: #BA2737;"><b> -</b></h4>
      <br>
      <h3><b> ${restaFCoinsText} </b></h3>
      
      
    </div>
    <div style="position: absolute; padding-top: 2.5%; right: 5%; text-align: right;">
      
      <p>$${new Intl.NumberFormat().format(totalPrice)}</p>
      <p>$0</p>
      <h4 ><b> $${new Intl.NumberFormat().format(fcoins)} </b></h4>
      <h4 style="color: #BA2737;"><b> -$${new Intl.NumberFormat().format(totalPrice)} </b></h4>
      <br>
      <h3><b> $${new Intl.NumberFormat().format(restaFCoins)} </b></h3>

      
    </div>
    
  </div>`;
}



const comprar = async () =>{

    let response = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
    let result = await response.json();


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

            const {id} = data2;
            let idNFT = id.toString().split("\\")[1];

            await fetch(`./api/owners/${localStorage.getItem('username')}/arts/${idNFT}`,
                {method: 'PUT'});

        }
        totalPrice= totalPrice * -1
        const fcoins = JSON.stringify({ "username":localStorage.getItem('username').toString(),"fcoins": parseFloat(totalPrice)});
        await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: fcoins

        });


        for (var i=1; i<=localStorage.getItem('cantidadCompras');i++){
            localStorage.removeItem(`buy${i}`);
        }
        localStorage.removeItem('cantidadCompras');
        Swal.fire('Compra Exitosa!').then((result) =>{
            if (result.isConfirmed){
                window.location.href="./index.html"
            }
        })

    }
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
window.addEventListener("DOMContentLoaded", getArtShopping())
window.addEventListener("DOMContentLoaded", addData())
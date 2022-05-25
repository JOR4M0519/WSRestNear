var imagesDiv = document.getElementById("card");
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
            <div class="row g-0">
              <div class="col-md-4">
                <img src="${id}" class="img-fluid rounded-start" style="border-top-left-radius: 10px; border-bottom-left-radius: 10px;" alt="...">
              </div>
              <div class="col-md-8 bgbanner">
                <div class="card-body" style="position: relative;">
                    <div style="left: 0%; position: absolute;">
                        <h3 class="card-title">${title}</h3><br>
                        <p class="card-text">${price} Fcoins</p><br>
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

const comprar = async () =>{

    let response = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
    let result = await response.json();


    var dataArts = "[";
    var cantidad = parseInt(localStorage.getItem('cantidadCompras'));

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
            const {collection, art} = data2;
            let response2 = await fetch(`users/${localStorage.getItem('username')}/collections/${collection}/arts/${art}/owner`, {
                method: "PUT",
            });
        }



    }

}

function removeItem (idNFT) {

    for (var i = 1; i <= cantidad; i++) {
        if(JSON.parse(localStorage.getItem(`buy${i}`)).id.toString().split("\\")[1] == idNFT){
            localStorage.removeItem(`buy${i}`);
            if (localStorage.getItem('cantidadCompras')!=1) {
                localStorage.setItem('cantidadCompras', parseInt(localStorage.getItem('cantidadCompras')) - parseInt(1))
            }else {
                localStorage.removeItem('cantidadCompras');
            }
            alert("Se eliminó correctamente");
        }
    }

    location.reload();
}
document.getElementById("btnBuyAllArts").addEventListener('click', comprar);
window.addEventListener("DOMContentLoaded", getArtShopping())
var imagesDiv = document.getElementById("card");

const getArtShopping = async () => {

        var cantidad = localStorage.getItem('cantidadCompras');

    
    if (cantidad != null) {
        if(cantidad != 0){

        var item = [];
        for (var i = 1; i <= cantidad && !exist; i++) {
            item.push(localStorage.getItem(`buy${i}`));
        }

        for (const items of item) {
            const {id, collection, title, author, price} = items;
            let idNFT = id.toString().split("\\")[1];

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
                              <button type="button" id="btnRemoveCart" onclick="removeItem('${id}')" class="btn btn-sm btn-outline-secondary">Remover del carro</button>
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

const removeItem = async (idNFT) => {

}

window.addEventListener("DOMContentLoaded", getArtShopping())
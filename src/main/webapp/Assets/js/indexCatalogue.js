var imagesDiv = document.getElementById("card");
var imagesCol = document.getElementById("cardcol")
var imagesLikesRank = document.getElementById("card_LikesRanking");

//Obtiene información sobre el sitio al cual se quiere ingresar y se genera una respuesta
const getDataNFTCatalogue = async () => {

    let data = null;
    let dataLikes = null;




    if (!window.location.toString().includes("artistAccount")) {

        data = await fetch("./api/arts").then(response => response.json());

        let innerhtml = "";

        for (const data1 of data) {
            const {id, collection, title, author, price} = data1;
            let idNFT = id.toString().split("\\")[1];
            let type = "";
            dataLikes = await fetch(`./api/users/arts/${idNFT}/likes`).then(response => response.json());
            let heartLikesStatus = await fetch(`./api/users/${localStorage.getItem("username")}/arts/${idNFT}/likes/like`).then(response => response.json());

            if (heartLikesStatus === 0) {
                heartLikesStatus = "Assets/svg/heart-unfill.svg";
            } else {
                heartLikesStatus = "Assets/svg/heart-fill.svg";
            }

            innerhtml += `
    <div class="col-md-4 card-position "> 
        <div class="card mb-4 shadow-sm card-dimensions" >
          <div class="imgBx">
            <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;"
                 src="${id}"
                 preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
              <title>Placeholder</title>
              <rect width="100%" height="100%" fill="#55595c"/>
            </img>
          </div>
          <div class="content card-content">
              <h3 class="card-text">Titulo: ${title}</h3>
              <p class="card-text">Autor: ${author}<br>
              Colección: ${collection}
              </p>
              <div class="card-body-price_likes">
                    <span class="text-muted">Precio: ${price}</span>
                    <span class="text-muted">Likes:
                        <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                            <img id="heartStatus${idNFT}" src="${heartLikesStatus}" width="15px">
                        </button>
                        <span id="amountLikes${idNFT}" aria-valuetext="" >${dataLikes}</span>
                    </span>
                </div>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group btns">
                 <form action="#" onsubmit="btnBuy(this)" >
                  <input type="hidden" name="dataBuy" value='${JSON.stringify(data1)}'>
                  <input type="submit" id="btnBuy/${idNFT}"  class="btn btn-sm btn-outline-secondary" value="Comprar">     
                </form> 
                <form action="#" onsubmit="btnAddCart(this)" >
                  <input type="hidden" name="dataCart" value='${JSON.stringify(data1)}'>
                  <input type="submit" id="btnAddCart"  class="btn btn-sm btn-outline-secondary" value="Añadir al Carro">
                </form> 
                </div>
              </div>
          </div>
        </div>
    </div>
  `;
        }
        imagesDiv.innerHTML += innerhtml;
    }
}

function btnBuy (form){

    var cantidad = localStorage.getItem('cantidadCompras');
    var data = form.dataBuy.value;
    var dataBuyJSON = JSON.parse(data);

    if (dataBuyJSON.email!=localStorage.getItem('username')) {
        if (cantidad == null) {

            localStorage.setItem('cantidadCompras', 1);
            localStorage.setItem('buy1', data);

        } else {
            let exist = false;
            for (var i = 1; i <= cantidad && !exist; i++) {
                if (localStorage.getItem(`buy${i}`) != form.dataBuy.value) {
                } else {
                    exist = true;
                    alert("ya esta en el carrito");
                }
            }
            if(!exist){
                cantidad = parseInt(cantidad) + parseInt(1);
                localStorage.setItem(`buy${cantidad}`, data);
                localStorage.setItem('cantidadCompras', cantidad);
            }

        }
    }else {
        alert('este nft ya es tuyo')
    }


}

const btnFinalCompra = async (id, collection) => {
    if(!(localStorage.getItem("username") == null)) {
        let data = await fetch(`./api/users/${localStorage.getItem("username")}/collection/${collection}/arts/${id}`).then(response => response.json());
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



const getDataCollection = async () => {
    if (window.location.toString().includes("artistAccount")) {
        dataCollection = await fetch(`./api/users/${localStorage.getItem("username")}/collections`).then(response => response.json());
    } else {
        dataCollection = await fetch("./api/collections").then(response => response.json());
    }


    for (const dataCollection1 of dataCollection) {
        const {username, collection} = dataCollection1;

        const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());

        const urlNfts = dataCollectionNFTs.map(collectionNft => ({id: collectionNft.id, author: collectionNft.author}))


        if (urlNfts.length == 0 && window.location.toString().includes("artistAccount")){
            imagesCol.innerHTML += `
       <div class="col-md-4 card-position">
    <div class="card mb-4 shadow-sm card-dimensions" id="modalNFTs" onclick="getDataModal('${collection.toString()}')"  data-toggle="modal" data-target=".bd-example-modal-lg">
        <div class="imgBx">
            <table>
                <tbody id="tableCollections${collection}">
                    <tr id="rowTable${collection}">
                    <p>Aún no has agregado ninguna pieza</p>
                    </tr>
                </table>
            </tbody>
        </div>
        <div class="content">
            <div class="card-body">
                <h3 class="card-text" id="collectionCollection">Colección: ${collection}</h3>
                <p class="card-text text-muted" id="collectionAuthor">Autor: ${document.getElementById("nameArtist").textContent}</p>
            </div>
        </div>
    </div>
</div>
   `;
        } else {

            imagesCol.innerHTML += `
       <div class="col-md-4 card-position">
    <div class="card mb-4 shadow-sm card-dimensions" id="modalNFTs" onclick="getDataModal('${collection.toString()}')"  data-toggle="modal" data-target=".bd-example-modal-lg">
        <div class="imgBx">
            <table>
                <tbody id="tableCollections${collection}">
                    <tr id="rowTable${collection}">
                    </tr>
                </table>
            </tbody>
        </div>
        <div class="content">
            <div class="card-body">
                <h3 class="card-text" id="collectionCollection">Colección: ${collection}</h3>
                <p class="card-text text-muted" id="collectionAuthor">Autor: ${urlNfts[0]?.author}</p>
            </div>
        </div>
    </div>
</div>
   `;
        }

        if (urlNfts.length != 0) {
            var tableCollection = document.getElementById("tableCollections" + collection);
            var rowTable = document.getElementById("rowTable" + collection);

            for (let i = 0; i < urlNfts.length; i++) {
                if (i == 2) {
                    tableCollection.innerHTML += `
            <tr id="rowTable${collection}2">    
            </tr>`
                    rowTable = document.getElementById("rowTable" + collection + "2");
                }
                rowTable.innerHTML += `
                    <td>
                        <img class="collection" width="100%" height="100%" style="border-top-left-radius: 3.5%;"
                            src="${urlNfts[i]?.id}" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"
                            aria-label="Placeholder: Thumbnail">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#55595c" />
                        </img>
                    </td>`;

            }
        }
    }
};


const getDataModal = async (collection) => {
    //Ventana emrgente modal
    var imagesModal = document.getElementById("cardsCollection");


    //llama la lista de colecciones
    const dataCollection = await fetch(`./api/collections`).then(response => response.json());
    const username = dataCollection.filter(data => (data.collection === collection));

    const dataCollectionNFTs = await fetch(`./api/users/${username[0].username}/collections/${collection}/arts`).then(response => response.json());
    const dataNFTs = dataCollectionNFTs.map(collectionNft => ({author: collectionNft.author}))

    imagesModal.innerHTML = `<h2 id="ofertas" class="card_tittle">Colección: ${collection} <p class="text-muted">By: ${dataNFTs[0].author}</p></h2>
            <!--Guia js-->
            <div class="card-group contenedor-social" id="socialcard">
              <section class="py-5">
                <div class="album py-5">
                  <div class="container">
                    <div class="row" id="cardNftCatologue">
                    </div>
                  </div>
                </div>
              </section>
            </div>`;

    let dataLikes = null;

    for (const dataCollectionNFTs1 of dataCollectionNFTs) {
        const {id, title, author, price} = dataCollectionNFTs1;

        let idNFT = id.toString().split("\\")[1];
        dataLikes = await fetch(`./api/users/arts/${idNFT}/likes`).then(response => response.json());
        let heartLikesStatus = await fetch(`./api/users/${localStorage.getItem("username")}/arts/${idNFT}/likes/like`).then(response => response.json());
        let type = "Modal";
        if (heartLikesStatus === 0) {
            heartLikesStatus = "Assets/svg/heart-unfill.svg";
        } else {
            heartLikesStatus = "Assets/svg/heart-fill.svg";
        }
        var cardNftCatalogue = document.getElementById("cardNftCatologue");

        cardNftCatalogue.innerHTML += `
                      <div class="col-md-4 card-position "> 
                        <div class="card mb-4 shadow-sm card-dimensions" >
                          <div class="imgBx">
                            <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;"
                                 src="${id}"
                                 preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
                              <title>Placeholder</title>
                              <rect width="100%" height="100%" fill="#55595c"/>
                            </img>
                          </div>
                          <div class="content card-content">
                              <h3 class="card-text">Titulo: ${title}</h3>
                              <p class="card-text">Autor: ${author}</p>
                              <div class="card-body-price_likes">
                                 <span class="text-muted">Precio: ${price}</span>
                                 <span class="text-muted">Likes:
                                    <button class="btn-like" onclick="btnLike('${idNFT}','${type}')" >
                                        <img id="heartStatusModal${idNFT}" src="${heartLikesStatus}" width="15px">
                                    </button>
                                    <span id="amountLikesModal${idNFT}" aria-valuetext="" >${dataLikes}
                                    </span>
                                </span>
                              </div>
                          </div>
                        </div>
                    </div>
                    `;
    }

}

const getDataRankingArts = async () => {

    let data = null;
    let dataLikes = null;

    if (!window.location.toString().includes("artistAccount")) {

        data = await fetch("./api/arts/likes").then(response => response.json());

        let innerhtml = "";

        for (const data1 of data) {
            const {id, collection, title, author, price} = data1;
            let idNFT = id.toString().split("\\")[1];
            let type = "";
            dataLikes = await fetch(`./api/users/arts/${idNFT}/likes`).then(response => response.json());
            let heartLikesStatus = await fetch(`./api/users/${localStorage.getItem("username")}/arts/${idNFT}/likes/like`).then(response => response.json());

            if (heartLikesStatus === 0) {
                heartLikesStatus = "Assets/svg/heart-unfill.svg";
            } else {
                heartLikesStatus = "Assets/svg/heart-fill.svg";
            }



            innerhtml += `
    <div class="col-md-4 card-position "> 
        <div class="cardTopRankLikes card mb-4 shadow-sm card-dimensionsTopLiked" >
          <div class="imgBx">
            <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;"
                 src="${id}"
                 preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
              <title>Placeholder</title>
              <rect width="100%" height="100%" fill="#55595c"/>
            </img>
          </div>
          <div class="content card-content">
              <h3 class="card-text">Titulo: ${title}</h3>
              <p class="card-text">Autor: ${author}<br>
              Colección: ${collection}
              </p>
              <div class="card-body-price_likes">
                    <span class="text-muted">Precio: ${price}</span>
                    <span class="text-muted">Likes:
                        <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                            <img id="heartStatus${idNFT}" src="${heartLikesStatus}" width="15px">
                        </button>
                        <span id="amountLikes${idNFT}" aria-valuetext="" >${dataLikes}</span>
                    </span>
                </div>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group btns">
                  <button type="button" id="btnBuy" onclick="btnBuy()" class="btn btn-sm btn-outline-secondary">Comprar</button>      
                  <button type="button" id="btnAddCart" onclick="" class="btn btn-sm btn-outline-secondary">Añadir al carro</button>
                </div>
              </div>
          </div>
        </div>
    </div>
  `;
        }

        imagesLikesRank.innerHTML += innerhtml;
    }
}


window.addEventListener("DOMContentLoaded", getDataNFTCatalogue(), getDataCollection(), getDataRankingArts());

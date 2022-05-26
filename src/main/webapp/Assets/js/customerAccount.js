var imagesOwner = document.getElementById("cardOwner");
var imagesfavorites = document.getElementById("cardfavorites");

const getOwnArts = async () => {

    let data = null;
    let dataLikes = null;

data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts`).then(response => response.json());

        let innerhtml = "";

        if(data.length != 0){
        
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
          </div>
        </div>
    </div>
  `;
        }
    }else{
        innerhtml = `<p>No has realizado compras!</p>`;
    }
        imagesOwner.innerHTML += innerhtml;
}

const getFavoritesArts = async () => {

    let data = null;
    let dataLikes = null;
data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts`).then(response => response.json());
data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());

        let innerhtml = "";

        if(data.length != 0){
        
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
          </div>
        </div>
    </div>
  `;
        }
    }else{
        innerhtml = `<p>No has dado like a ninun arte!</p>`;
    }
    imagesfavorites.innerHTML += innerhtml;
}

window.addEventListener("DOMContentLoaded", getOwnArts(), getFavoritesArts())
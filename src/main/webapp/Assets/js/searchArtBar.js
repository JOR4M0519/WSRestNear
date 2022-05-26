const getArtsFilter = async () => {
    let innerhtml = "";
    let dataFilter = params.filter; 

    var imagesDiv = document.getElementById("card");
    let data = null;
    let dataLikes = null;

        data = await fetch(`./api/arts/filter?data=${dataFilter}`).then(response => response.json());
        
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
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group btns">
                 <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Comprar" onclick="btnBuy(this,'buy')">     
                 <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Añadir al Carro" onclick="btnBuy(this,'add')">
                </div>
              </div>
          </div>
        </div>
    </div>
  `;
        }
    }else{
        innerhtml = `<p>No existe arte alguno por esta palabra</p>`;
    }
        imagesDiv.innerHTML += innerhtml;
}

const params = new Proxy(new URLSearchParams(window.location.search), {
    get: (searchParams, prop) => searchParams.get(prop),
  });
  
window.addEventListener("DOMContentLoaded", getArtsFilter());

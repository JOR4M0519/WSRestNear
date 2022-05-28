
const getDataArts = async (artsDiv) => {

  let data = null;
  let dataLikes = null;
  let heartLikesStatus = null;
  
  let styleClassCardTopRankLikes = "";

      //let url = window.location.href.split('/')[4].split('.')[0];
      let url = window.location.href
      
      //Realiza el llamado fetch de las artes dependiendo de la ubicación de la pagina
       if(url.includes("customerAccount")){
          data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts`).then(response => response.json());
          if(artsDiv.id != "cardfavorites"){
              getDataArts(document.getElementById("cardfavorites"));
          }else{
              data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());    
          }    
      }else if(url.includes("filterArts")){
          data = await fetch(`./api/arts/filter?data=${params.filter}`).then(response => response.json());
             
      }else if(url.includes("artistAccount")){
          data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());  
      } else{
        data = await fetch("./api/arts").then(response => response.json());
        if(artsDiv.id != "card_LikesRanking"){
            getDataArts(document.getElementById("card_LikesRanking"));
        }else{
            styleClassCardTopRankLikes = "cardTopRankLikes card-dimensionsTopLiked";
            data = await fetch("./api/arts/likes").then(response => response.json());
        }
      }

      // switch (url) {
      //     case "index":
      //         data = await fetch("./api/arts").then(response => response.json());
      //         break;
      //     case "customerAccount":
      //         data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts`).then(response => response.json());
      //         data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());    
      //         break;
      //     case "filterArts":
      //         data = await fetch(`./api/arts/filter?data=${dataFilter}`).then(response => response.json());
      //         break;
      //     case "artistAccount":
      //         data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());
      //         break;
      // }

      
      const listTotalLikes = await fetch("./api/arts/likes/list").then(response => response.json());
      const listTotalLikesByUser = await fetch(`./api/users/${localStorage.getItem('username')}/likes`).then(response => response.json());


      let innerhtml = "";

      for (const data1 of data) {
          const {id, collection, title, author, price} = data1;
          let idNFT = id.toString().split("\\")[1];
          let type = "";

          const artTotalLike = listTotalLikes.filter(data => (data.idImage === idNFT));

          if (artTotalLike.length!=0) {
              dataLikes = artTotalLike[0].likes;
         }else {
              dataLikes=0;
          }

          const likeByArt = listTotalLikesByUser.filter(data => (data.idImage === idNFT));
          if (likeByArt.length!=0) {
              heartLikesStatus = likeByArt[0].likes;
          }else {
              heartLikesStatus=0;
          }
          if (heartLikesStatus === 0) {
              heartLikesStatus = "Assets/svg/heart-unfill.svg";
          } else {
              heartLikesStatus = "Assets/svg/heart-fill.svg";
          }


          innerhtml += `
  <div class="col-md-4 card-position "> 
      <div class=" ${styleClassCardTopRankLikes} card mb-4 shadow-sm card-dimensions" >
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
                  <span class="text-muted">Precio: $${new Intl.NumberFormat().format(price)}</span>
                  <span class="text-muted">Likes:
                      <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                          <img id="heartStatus${idNFT}" src="${heartLikesStatus}" width="15px">
                      </button>
                      <span id="amountLikes${idNFT}" aria-valuetext="" >${dataLikes}</span>
                  </span>
              </div>

`;
  //No agrega los botones de compra y carrito de compras
  if(url.includes("customerAccount") && (artsDiv.id ==="cardOwner")|| url.includes("artistAccount")){
      innerhtml += `          
          </div>
      </div>
  </div>`;
  }
  //Agrega los botones de compra y carrito de compras
  else{
      innerhtml += `
          <div class="d-flex justify-content-between align-items-center" id="btnsShopping${idNFT}">
              <div class="btn-group btns" >
                  <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Comprar" onclick="btnBuy(this,'buy')">     
                  <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Añadir al Carro" onclick="btnBuy(this,'add')">
              </div>
          </div>
      </div>
  </div>
</div>`;
  }
      }
      artsDiv.innerHTML += innerhtml;
}

const getDataCollection = async () =>   {
    var imagesCol = document.getElementById("cardcol")
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
    <div class="card mb-4 shadow-sm card-dimensions" id="modalNFTs" onclick="getDataModal('${collection.toString()}','${username.toString()}')"  data-toggle="modal" data-target=".bd-example-modal-lg">
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

            for (let i = 0; i < urlNfts.length && i<=3; i++) {
                if (i == 2) {
                    tableCollection.innerHTML += `
            <tr id="rowTable${collection}2">    
            </tr>`
                    rowTable = document.getElementById("rowTable" + collection + "2");
                }
                rowTable.innerHTML += `
                    <td>
                        <img class="collection" width="100%" height="100%" style="border-top-left-radius: 3.5%; min-width: 135px; max-width: 135px;"
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

const getDataModal = async (collection,username) => {
    //Ventana emrgente modal
    var imagesModal = document.getElementById("cardsCollection");


    //llama la lista de colecciones
    // const dataCollection = await fetch(`./api/collections`).then(response => response.json());
    // const username = dataCollection.filter(data => (data.collection === collection));

    const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());
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
    const listTotalLikes = await fetch("./api/arts/likes/list").then(response => response.json());
    const listTotalLikesByUser = await fetch(`./api/users/${localStorage.getItem('username')}/likes`).then(response => response.json());

    
    for (const dataCollectionNFTs1 of dataCollectionNFTs) {
        const {id, title, author, price} = dataCollectionNFTs1;

        let idNFT = id.toString().split("\\")[1];
        //dataLikes = await fetch(`./api/users/arts/${idNFT}/likes`).then(response => response.json());
        //let heartLikesStatus = await fetch(`./api/users/${localStorage.getItem("username")}/arts/${idNFT}/likes/like`).then(response => response.json());
        let type = "Modal";

        const artTotalLike = listTotalLikes.filter(data => (data.idImage === idNFT));

        if (artTotalLike.length!=0) {
            dataLikes = artTotalLike[0].likes;
       }else {
            dataLikes=0;
        }

        const likeByArt = listTotalLikesByUser.filter(data => (data.idImage === idNFT));
        if (likeByArt.length!=0) {
            heartLikesStatus = likeByArt[0].likes;
        }else {
            heartLikesStatus=0;
        }
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
                                 <span class="text-muted">Precio: $${new Intl.NumberFormat().format(price)}</span>
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


const params = new Proxy(new URLSearchParams(window.location.search), {
    get: (searchParams, prop) => searchParams.get(prop),
  });
  

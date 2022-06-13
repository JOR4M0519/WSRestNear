const getDataArts = async (artsDiv, dataFetch, getBtnCallBack) => {

    let data = dataFetch;
    let dataLikes = null;
    let heartLikesStatus = null;

    let styleClassCardTopRankLikes = "";


    //Realiza el llamado fetch de las artes dependiendo de la ubicación de la pagina

    //puesto
    /*if(url.includes("customerAccount")){
          data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts`).then(response => response.json());
          if(artsDiv.id != "cardfavorites"){
              getDataArts(document.getElementById("cardfavorites"));
          }else{
              data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());    
          }
          //puesto
      }else*/

    //Puesto
    /*if(url.includes("filterArts")){
          data = await fetch(`./api/arts/filter?data=${params.filter}`).then(response => response.json());
    }
    else*/

    /*if(url.includes("artistAccount")){
            //puesto
          data = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());
      } else{*/

    //puesto index
    /*
            data = await fetch("./api/arts").then(response => response.json());

            //invierte la lista tomando los últimos creados
            data.reverse();

            //Toma solo las primeras 6 artes
           while(data.length>6 ){
               data.length = data.length - 1;
           }
           //modifica el id por la ruta de la imagen

           data.map(function(element) {
             element.id = "NFTS\\"+element.id;
           })
    */

    if (artsDiv.id == "card_LikesRanking") {
        styleClassCardTopRankLikes = "cardTopRankLikes card-dimensionsTopLiked";
    }
    //}

    //Get Likes Data of all images
    const listTotalLikes = await fetch("./api/arts/likes/list").then(response => response.json());
    const listTotalLikesByUser = await fetch(`./api/users/${localStorage.getItem('username')}/likes`).then(response => response.json());
    const listArtsOwner = await fetch(`./api/owners/${localStorage.getItem('username')}/arts`).then(response => response.json());

    let innerhtml = "";

    //Recorre cada arte
    for (const data1 of data) {
        const {id, collection, title, author, price, forSale,email} = data1;
        let idNFT = id.toString().split("\\")[1];
        let type = "";

        //Busca el numero de likes que posea el arte
        let artTotalLike = listTotalLikes.filter(data => (data.idImage === idNFT));
        if (artTotalLike.length != 0) {
            dataLikes = artTotalLike[0].likes;
        } else {
            dataLikes = 0;
        }

        //Busca si el usuario le dió click al Arte
        let likeByArt = listTotalLikesByUser.filter(data => (data.idImage === idNFT));
        if (likeByArt.length != 0) {
            heartLikesStatus = "Assets/svg/heart-fill.svg";
        } else {
            heartLikesStatus = "Assets/svg/heart-unfill.svg";
        }

        /*
        if (heartLikesStatus === 0) {
            heartLikesStatus = ;
        } else {
            heartLikesStatus = ;
        }*/

        innerhtml += `
<div class="col-md-4 card-position "> 
    <div class=" ${styleClassCardTopRankLikes} card mb-4 shadow-sm card-dimensions">
        <div class="imgBx">
            <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;" src="${id}" preserveaspectratio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
        </div>

        <div class="content card-content">
            <h3 class="card-text" id="item1">Titulo: ${title}</h3>
            <p class="card-text infoArt" id="item1">
            Autor: ${author}
            <br>
            Colección: ${collection}
            </p>
            <p class="text-muted">Precio: $${new Intl.NumberFormat().format(price)}</p>
            <p class="text-muted">Likes:
                <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                    <img id="heartStatus${idNFT}"" src="${heartLikesStatus}" width="15px">
                </button>
                <span id="amountLikes${idNFT}" aria-valuetext="">${dataLikes}</span>
            </p>`;
        let habilitar = "";

        //
        let ownerArt = listArtsOwner.filter(art => art.id === id);
        ownerArt = (ownerArt.length != 0);

/*
        //No agrega los botones de compra y carrito de compras
        if (url.includes("customerAccount") && (artsDiv.id === "cardOwner") || url.includes("artistAccount")) {


            if (forSale == true) {

                habilitar = "Deshabilitar";

            } else {
                habilitar = "Habilitar";
            }

            innerhtml += `
            <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary item1" value="${habilitar} Compra" onclick="btnDeshabilitar(this)">
            <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary item1" value="Editar" onclick="">
      </div>
    </div>
</div>`;
        }
        //Agrega los botones de compra y carrito de compras
        else {
            innerhtml += `
      <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Comprar" onclick="btnBuy(this,'buy')">
      <input type="submit" id='${JSON.stringify(data1)}'  class="btn btn-sm btn-outline-secondary" value="Añadir al Carro" onclick="btnBuy(this,'add')">

      </div>
    </div>
</div>`;
        }*/
        if(localStorage.getItem("role") === "Artista"){
            innerhtml += getBtnCallBack(data1,forSale,ownerArt,email)
        } else{
            innerhtml += getBtnCallBack(data1,forSale,ownerArt)
        }
    }
    artsDiv.innerHTML += innerhtml;
}

function getArtSale(dataArt,selling,owner,email){
    let description = "En venta"
    let sellStatus = "btnSell";

    //Muestra
    if(!selling){
        return getBtnNotEnable();
    }

    //Si no es propietario del arte y fue su creador carga el estilo del boton vendido
    if(!owner && (email == localStorage.getItem("username"))){
        description = "Vendida!";
        sellStatus = "btnSold";
    }
          return `
            <input class="btn btn-sm item1" id="${sellStatus}" value="${description}" disabled >
      </div>
    </div>
</div>`;
    }

//Funcion Artista habilitar/Deshabilitar
function getEnableArtBtnArtist(dataArt,selling,owner,email){
    let enable;

    if (selling) {enable = "Deshabilitar";}
            else {enable = "Habilitar";}

    //Si es propietario del arte y fue su creador carga el boton de habilitar compra del arte
    if(owner && (email == localStorage.getItem("username"))){
        return `
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" value="${enable} Compra" onclick="btnDeshabilitar(this)">
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" style="box-shadow: 0px 2px 5px 2px #3f3f3f;" value="Editar" onclick="">
      </div>
    </div>
</div>`;
    }
    return getArtSale(dataArt,true,owner,email);

}


//Funciones del Customer
function getBuyBtn(dataArt, selling, owner) {
    if(!selling){
        return getBtnNotEnable();
    }

    if (owner) {
        return`
        <input class="btn btn-sm btn-outline-secondary item1" id="btnSold" value="Este NFT es tuyo" disabled    >
        </div>
    </div>
</div>`;
    } else {

        return `
      <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnSell" value="Comprar" onclick="btnBuy(this,'buy')" >     
      <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnSell" style="box-shadow: 0px 2px 5px 2px #313830;" value="Añadir al Carro" onclick="btnBuy(this,'add')">        
      </div>
    </div>
</div>`;
    }
}

//Funcion customer habilitar/Deshabilitar
function getEnableArtBtnCustomer(dataArt,selling,owner){
    let enable;

    if (selling) {enable = "Deshabilitar";}
    else {enable = "Habilitar";}

    //Si es propietario del arte y fue su creador carga el boton de habilitar compra del arte
    if(owner){
        return `
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" value="${enable} Compra" onclick="btnDeshabilitar(this)">
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" style="box-shadow: 0px 2px 5px 2px #3f3f3f;" value="Editar" onclick="">
      </div>
    </div>
</div>`;
    }
    return getArtSale(dataArt,true,owner,localStorage.getItem('username'));

}

function getBtnNotEnable(){
    return`
        <input class="btn btn-sm btn-outline-secondary item1" id="btnSold" value="No esta en Venta" disabled    >
        </div>
    </div>
</div>`;
}

 const getDataCollection = async (dataCollection,enableEdit) =>   {

        var imagesCol = document.getElementById("cardcol")

        /*if (enableEdit=="artist") {
            dataCollection = await fetch(`./api/users/${localStorage.getItem("username")}/collections`).then(response => response.json());
        } else if (enableEdit=="filter") {
            dataCollection = await fetch(`./api/collections/filter?data=${params.filter}`).then(response => response.json());
        } else if (enableEdit=="general"){
            dataCollection = await fetch("./api/collections").then(response => response.json());
        }*/


        for (const dataCollection1 of dataCollection) {
            const {username, collection} = dataCollection1;

            const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());

            const urlNfts = dataCollectionNFTs.map(collectionNft => ({
                id: collectionNft.id,
                author: collectionNft.author
            }))


            if (urlNfts.length == 0 && true) {
                imagesCol.innerHTML += `
       <div class="col-md-4 card-position">
        <div class="card mb-4 shadow-sm card-dimensions" style="padding: 50%" id="modalNFTs" onclick="getDataModal('${collection.toString()}')"  data-toggle="modal" data-target=".bd-example-modal-lg">
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

                //Get number arts from the collection
                let imgsArts = "";
                for (let i = 0; i < urlNfts.length && i <= 3; i++) {
                    imgsArts += `<img class="imageCollection" width="100%" height="100%" src="${urlNfts[i]?.id}" preserveaspectratio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">`
                }

                imagesCol.innerHTML += `
       <div class="col-md-4 card-position">
    <div class="card mb-4 shadow-sm card-dimensions" id="modalNFTs" onclick="getDataModal('${collection.toString()}','${username.toString()}',${enableEdit})"  data-toggle="modal" data-target=".bd-example-modal-lg">
        <div class="imgBx collectionCatalogue">
            ${imgsArts}
        </div>
        <div class="content">
            <div class="card-body" id="item1">
                <h3 class="card-text" id="collectionCollection">Colección: ${collection}</h3>
                <p class="card-text text-muted" id="collectionAuthor">Autor: ${urlNfts[0]?.author}</p>
            </div>
        </div>
    </div>
</div>
   `;
            }
        }
    };

const getDataModal = async (collection,username,enableEdit) => {
    //Ventana emrgente modal
    var imagesModal = document.getElementById("cardsCollection");


        //llama la lista de colecciones
        // const dataCollection = await fetch(`./api/collections`).then(response => response.json());
        // const username = dataCollection.filter(data => (data.collection === collection));

        const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());
        const dataNFTs = dataCollectionNFTs.map(collectionNft => ({author: collectionNft.author}))

        imagesModal.innerHTML = `

            
            <h2 id="ofertas" class="card_tittle">Colección: ${collection} <p class="text-muted">By: ${dataNFTs[0].author}</p></h2>
            <!--Guia js-->
            <div class="card-group contenedor-social" id="socialcard">
              <section class="py-5" style="width: 100%;">
                <div class="album py-5">
                  <div class="container">
                    <div class="row" id="cardNftCatologue">
                    </div>
                  </div>
                </div>
              </section>
            </div>`;

    
/*
    let dataLikes = null;
    const listTotalLikes = await fetch("./api/arts/likes/list").then(response => response.json());
    const listTotalLikesByUser = await fetch(`./api/users/${localStorage.getItem('username')}/likes`).then(response => response.json());
    const listArtsOwner = await fetch(`./api/owners/${localStorage.getItem('username')}/arts`).then(response => response.json());
    var cardNftCatalogue =
*/

    if(localStorage.getItem("role") === "Artista"){
        //Ingresa a la opción de editar del artista
        if(enableEdit){
            getDataArts(document.getElementById("cardNftCatologue"),dataCollectionNFTs,getEnableArtBtnArtist).then(function (){
                //Borra la información del autor y colección
                document.querySelectorAll(".infoArt").forEach(el => el.remove())});
        }
        //Muestra solo las artes
        else if(!enableEdit){
            getDataArts(document.getElementById("cardNftCatologue"),dataCollectionNFTs,getArtSale).then(function (){
                //Borra la información del autor y colección
                document.querySelectorAll(".infoArt").forEach(el => el.remove())});
        }
        //Muestra las opciones del customer
    }else{
        getDataArts(document.getElementById("cardNftCatologue"),dataCollectionNFTs,getBuyBtn).then(function (){
            //Borra la información del autor y colección
            document.querySelectorAll(".infoArt").forEach(el => el.remove());
        })
    }



    /*for (const dataCollectionNFTs1 of dataCollectionNFTs) {
        const {id, title, author, price, forSale} = dataCollectionNFTs1;


        for (const dataCollectionNFTs1 of dataCollectionNFTs) {
            const {id, title, author, price, forSale} = dataCollectionNFTs1;

            let idNFT = id.toString().split("\\")[1];
            let type = "Modal";

            const artTotalLike = listTotalLikes.filter(data => (data.idImage === idNFT));

            if (artTotalLike.length != 0) {
                dataLikes = artTotalLike[0].likes;
            } else {
                dataLikes = 0;
            }

            const likeByArt = listTotalLikesByUser.filter(data => (data.idImage === idNFT));
            if (likeByArt.length != 0) {
                heartLikesStatus = likeByArt[0].likes;
            } else {
                heartLikesStatus = 0;
            }
            if (heartLikesStatus === 0) {
                heartLikesStatus = "Assets/svg/heart-unfill.svg";
            } else {
                heartLikesStatus = "Assets/svg/heart-fill.svg";
            }

            let ownerArt = listArtsOwner.filter(art => art.id === id);

            var buyButtons = "";
            var habilitar = "";

            if (window.location.toString().includes("artistAccount") && ownerArt.length != 0) {

                if (forSale) {
                    habilitar = "Deshabilitar";

                } else {
                    habilitar = "Habilitar";
                }

                buyButtons = ` 
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="${habilitar} Compra" onclick="btnDeshabilitar(this)">
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="Editar" onclick="">
            `;
            } else {


                if (forSale == true) {
                    buyButtons = ` 
 
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="Comprar" onclick="btnBuy(this,'buy')">
            <input type="submit" id='${JSON.stringify(dataCollectionNFTs1)}'  class="btn btn-sm btn-outline-secondary item1" value="Añadir al Carro" onclick="btnBuy(this,'add')">`;
                } else {
                    buyButtons = ` 
            <span class="btn btn-sm btn-outline-secondary item1 d-inline-block" tabindex="0" data-toggle="tooltip" title="El Artista deshabilitó la venta de este arte o ya fue vendida">
            No Disponible
            <span>
            `;
                }
            }

            var cardNftCatalogue = document.getElementById("cardNftCatologue");

            cardNftCatalogue.innerHTML += `
        <div class="col-md-4 card-position "> 
            <div class="  card mb-4 shadow-sm card-dimensions">
            <div class="imgBx">
                <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;" src="${id}" preserveaspectratio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
            </div>

            <div class="content card-content">
                <h4 class="card-text" id="item1">Titulo: ${title}</h4>
                <p class="text-muted" style="">Precio: $${new Intl.NumberFormat().format(price)}</p>
                <p class="text-muted" style="text-align: center;">Likes:
                    <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                        <img id="heartStatusModal${idNFT}"" src="${heartLikesStatus}" width="15px">
                    </button>
                    <span id="amountLikesModal${idNFT}" aria-valuetext="">${dataLikes}</span>
                </p>
               ${buyButtons}    
            </div>
        </div>
                    `;

    }*/
}

    const btnBuy = async (input, condition) => {

        var cantidad = localStorage.getItem('cantidadCompras');
        var data = input.id;
        var dataBuyJSON = JSON.parse(data);
        var id = dataBuyJSON.id.toString().split("\\")[1];
        let dataOwner = await fetch(`./api/owners/arts/${id}`).then(response => response.json());


        if (localStorage.getItem('username') != null || localStorage.getItem('username') != undefined) {
            if (dataOwner.username != localStorage.getItem('username')) {
                if (cantidad == null) {

                    localStorage.setItem('cantidadCompras', 1);
                    localStorage.setItem('buy1', data);
                    if (condition == 'buy') {
                        window.location.href = './account.html#shoppingCart';
                    } else {
                        document.getElementById("numCantCompras").innerHTML = `&nbsp;${localStorage.getItem('cantidadCompras')}`;
                    }
                } else {
                    let exist = false;
                    for (var i = 1; i <= cantidad && !exist; i++) {
                        if (localStorage.getItem(`buy${i}`) != data) {
                        } else {
                            exist = true;
                            if (condition == 'buy') {
                                window.location.href = "./account.html#shoppingCart";
                            }
                        }
                    }
                    if (!exist) {
                        cantidad = parseInt(cantidad) + parseInt(1);
                        localStorage.setItem(`buy${cantidad}`, data);
                        localStorage.setItem('cantidadCompras', cantidad);
                        if (condition == 'buy') {
                            window.location.href = "./account.html#shoppingCart";
                        } else {
                            document.getElementById("numCantCompras").innerHTML = `&nbsp;${localStorage.getItem('cantidadCompras')}`;
                        }
                    }

                }
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Este NFT ya es tuyo!',
                })
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Inicie Sesión para Comprar!',
            })
        }

    }

const params = new Proxy(new URLSearchParams(window.location.search), {
    get: (searchParams, prop) => searchParams.get(prop),
  });

  

const getDataArts = async (artsDiv, dataFetch, getBtnCallBack) => {

    let data = dataFetch;
    let dataLikes = null;
    let heartLikesStatus = null;

    let styleClassCardTopRankLikes = "";


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
        const { id, collection, title, author, price, forSale, email } = data1;
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

        let divClassColeccion = "";
        if(artsDiv.id === "cardNftCatologue"){
            divClassColeccion = "infoArt";
        }

        /*
        if (heartLikesStatus === 0) {
            heartLikesStatus = ;
        } else {
            heartLikesStatus = ;
        }*/

        innerhtml += `
<div class="" style="display: flex; align-items: center; justify-content: center; border-radius: 2%;"> 
    <div class=" ${styleClassCardTopRankLikes} card mb-4 shadow-sm card-dimensions" >
        <div class="imgBx">
            <img class="bd-placeholder-img card-img-top" width="100%" height="100%" style="border-radius: 3.5%;" src="${id}" preserveaspectratio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
        </div>

        <div class="content card-content">
            <h5 class="card-text" id="item1">${title}</h5>
            <p class="card-text ${divClassColeccion}" id="item1" style="font-size: small;">
            Autor: ${author}
            <br>
            Colección: ${collection}
            </p>
            <p class="text-muted" style="font-size: small;">Precio: $${new Intl.NumberFormat().format(price)}</p>
            <p class="text-muted" style="font-size: small;">Likes:
                <button class="btn-like" onclick="btnLike('${idNFT}','${type}')">
                    <img id="heartStatus${idNFT}"" src="${heartLikesStatus}" width="15px">
                </button>
                <span id="amountLikes${idNFT}" aria-valuetext="">${dataLikes}</span>
            </p>`;
        let habilitar = "";

        //
        let ownerArt = listArtsOwner.filter(art => art.id === id);
        ownerArt = (ownerArt.length != 0);

        if (localStorage.getItem("role") === "Artista") {
            innerhtml += getBtnCallBack(data1, forSale, ownerArt, email)
        } else {
            innerhtml += getBtnCallBack(data1, forSale, ownerArt)
        }
    }
    artsDiv.innerHTML += innerhtml;
}

function getArtSale(dataArt, selling, owner, email) {
    let description = "En venta"
    let sellStatus = "btnSell";

    //Muestra
    if (!selling) {
        return getBtnNotEnable();
    }

    //Si no es propietario del arte y fue su creador carga el estilo del boton vendido
    if (!owner && (email == localStorage.getItem("username"))) {
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
function getEnableArtBtnArtist(dataArt, selling, owner, email) {
    let enable;

    if (selling) { enable = "Deshabilitar"; }
    else { enable = "Habilitar"; }

    //Si es propietario del arte y fue su creador carga el boton de habilitar compra del arte
    if (owner && (email == localStorage.getItem("username"))) {
        return `
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" value="${enable} Compra" onclick="btnDeshabilitar(this)">
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" style="box-shadow: 0px 2px 5px 2px #3f3f3f;" value="Editar" onclick="editArt(this)">
      </div>
    </div>
</div>`;
    }
    return getArtSale(dataArt, true, owner, email);

}

//Funciones del Customer
function getBuyBtn(dataArt, selling, owner) {
    if (!selling) {
        return getBtnNotEnable();
    }

    if (owner) {
        return `
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
function getEnableArtBtnCustomer(dataArt, selling, owner) {
    let enable;

    if (selling) { enable = "Deshabilitar"; }
    else { enable = "Habilitar"; }

    //Si es propietario del arte y fue su creador carga el boton de habilitar compra del arte
    if (owner) {
        return `
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" value="${enable} Compra" onclick="btnDeshabilitar(this)">
            <input type="submit" id='${JSON.stringify(dataArt)}'  class="btn btn-sm btnEdit" style="box-shadow: 0px 2px 5px 2px #3f3f3f;" value="Editar" onclick="editArt(this)">
      </div>
    </div>
</div>`;
    }
    return getArtSale(dataArt, true, owner, localStorage.getItem('username'));

}

function getBtnNotEnable() {
    return `
        <input class="btn btn-sm btn-outline-secondary item1" id="btnSold" value="No esta en Venta" disabled    >
        </div>
    </div>
</div>`;
}

const getDataCollection = async (dataCollection, enableEdit) => {

    var imagesCol = document.getElementById("cardcol")


    for (const dataCollection1 of dataCollection) {
        const { username, collection } = dataCollection1;

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

const getDataModal = async (collection, username, enableEdit) => {
    //Ventana emrgente modal
    var imagesModal = document.getElementById("cardsCollection");


    //llama la lista de colecciones

    const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());
    const dataNFTs = dataCollectionNFTs.map(collectionNft => ({ author: collectionNft.author }))

    imagesModal.innerHTML = `

            
            <h2 id="ofertas" class="card_tittle">Colección: ${collection} <p class="text-muted">By: ${dataNFTs[0].author}</p></h2>
            <!--Guia js-->
            <div class="card-group contenedor-social" id="socialcard">
              <section class="py-5" style="width: 100%;">
                <div class="album py-5">
                  <div class="container">
                    <div class="catalogueIndex" id="cardNftCatologue" style="grid-template-columns: 31% 31% 31%; justify-content: space-between; row-gap: 4%;">
                    </div>
                  </div>
                </div>
              </section>
            </div>`;


    if (localStorage.getItem("role") === "Artista") {
        //Ingresa a la opción de editar del artista
        if (enableEdit) {
            getDataArts(document.getElementById("cardNftCatologue"), dataCollectionNFTs, getEnableArtBtnArtist).then(function () {
                //Borra la información del autor y colección
                document.querySelectorAll(".infoArt").forEach(el => el.remove())
            });
        }
        //Muestra solo las artes
        else if (!enableEdit) {
            getDataArts(document.getElementById("cardNftCatologue"), dataCollectionNFTs, getArtSale).then(function () {
                //Borra la información del autor y colección
                document.querySelectorAll(".infoArt").forEach(el => el.remove())
            });
        }
        //Muestra las opciones del customer
    } else {
        getDataArts(document.getElementById("cardNftCatologue"), dataCollectionNFTs, getBuyBtn).then(function () {
            //Borra la información del autor y colección
            document.querySelectorAll(".infoArt").forEach(el => el.remove());
        })
    }

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
                    top.location.href = './account.html#shoppingCart';
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
                            top.location.href = "./account.html#shoppingCart";
                        }
                    }
                }
                if (!exist) {
                    cantidad = parseInt(cantidad) + parseInt(1);
                    localStorage.setItem(`buy${cantidad}`, data);
                    localStorage.setItem('cantidadCompras', cantidad);
                    if (condition == 'buy') {
                        top.location.href = "./account.html#shoppingCart";
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

const editArt = async (input) => {


    const dataCollectionNFTs = await fetch(`./api/users/${localStorage.getItem("username")}/collections`).then(response => response.json());
    const collectionName = dataCollectionNFTs.map(collectionNft => ({ collection: collectionNft.collection }))

    var id = input.id;
    var data = JSON.parse(id);
    let title = data.title;
    var selectOptions = "";
    var selected = "";

    for (var i = 0; i < collectionName.length; i++) {
        if (collectionName[i].collection == data.collection) {
            selected = "selected"
        }
        selectOptions +=
            `<option style="font-family: 'Abel', sans-serif;" ${selected}>${collectionName[i].collection}</option>`;
    }

    var olDiv = document.getElementById('cardsCollection').innerHTML;

    document.getElementById('cardsCollection').innerHTML =
        `<div class="editDataArt" style="width: 100%;">
        <input type="hidden" id="oldDiv" >

        <div style="padding-top: 3%;  margin-left: 5%;  margin-right: 5%;">

        <div><button style="border-radius: 5px;" onclick="document.getElementById('cardsCollection').innerHTML = document.getElementById('oldDiv').value;"><i class='bx bx-arrow-back'></i></button></div>

            <h2 style="text-align: center;"></h2>


            <div class="form-group" style="width: 100%;   display: flex; justify-content: center;" >
                <img id="imgEditArt" src=${data.id}
                    alt="${data.title}" style="height: 30vh;">

            </div>
            <div class="form-group">
                <label>Título:</label>
                <input type="text" class="form-control" id="titleEditArt" placeholder="Ingrese el Nuevo Titulo...">
            </div>

            <div class="form-group">
                <label for="title">Colección:</label>
                <select name="collection" class="form-control" id="collectionSelect" aria-describedby="role">
                ${selectOptions}
                </select>
            </div>
            <div class="form-group">
                <label>Precio:</label>
                <input type="number" class="form-control" id="priceEditArt" placeholder="Ingrese el Nuevo Precio..."value=${data.price}>
            </div>

            <input id="btnEditDataArt" type="submit" class="btn btn-primary; btn-box-sign-up"
            style=" margin-top: 20px; font-family: 'Abel', sans-serif;" value="Editar">



        </div>

        <div id="imgEditModal" class="modalimgEditArt">
            <span class="close">&times;</span>
            <img class="modalimgEditArt-contentEditImg" id="img01">
            <div id="caption"></div>
        </div>
    </div>`;

    document.getElementById('titleEditArt').value = data.title;
    document.getElementById('oldDiv').value = olDiv;


    const getData = async () => {
        const dataCollectionNFTs = await fetch(`./api/users/${localStorage.getItem("username")}/collections`).then(response => response.json());
        const collectionName = dataCollectionNFTs.map(collectionNft => ({ collection: collectionNft.collection }))

        for (var i = 0; i < collectionName.length; i++) {
            document.getElementById("collectionSelect").innerHTML +=
                `<option style="font-family: 'Abel', sans-serif;">${collectionName[i].collection}</option>`;
        }
    
    }

    window.addEventListener("DOMContentLoaded", getData());

    const actualiza = async () => {


        console.log(data.id.split('\\')[1]);
    
        if (
            data.title != document.getElementById('titleEditArt').value ||
            data.collection != document.getElementById('collectionSelect').value ||
            data.price != document.getElementById('priceEditArt').value
        ) {
    
            try {
                //JSON art
                var art = {
                    "author": "null",
                    "collection": document.getElementById('collectionSelect').value,
                    "email": localStorage.getItem('username'),
                    "forSale": "null",
                    "id": data.id.split('\\')[1],
                    "price": parseFloat(document.getElementById('priceEditArt').value),
                    "title": document.getElementById('titleEditArt').value
                };
    
                art = JSON.stringify(art);
                console.log(art);
    
                //Se actualiza el arte
                await fetch(`./api/arts`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: art
                });
    
                Swal.fire('Se generó el cambio exitosamente!').then((result) =>{
                    if (result.isConfirmed){
                        location.reload();
                    }
                })
    
            } catch (err) {
                console.log(err);
    
            }
        }else{
            Swal.fire('No realizaste ningún cambio!')
        }
    }
    document.getElementById('btnEditDataArt').addEventListener('click',actualiza)



    var modal = document.getElementById('imgEditModal');


    var img = document.getElementById('imgEditArt');
    var modalImg = document.getElementById("img01");
    var captionText = document.getElementById("caption");
    img.onclick = function () {
        modal.style.display = "block";
        modalImg.src = this.src;
        captionText.innerHTML = this.alt;
    }


    var span = document.getElementsByClassName("close")[0];


    span.onclick = function () {
        modal.style.display = "none";
    }

}





const params = new Proxy(new URLSearchParams(window.location.search), {
    get: (searchParams, prop) => searchParams.get(prop),
});



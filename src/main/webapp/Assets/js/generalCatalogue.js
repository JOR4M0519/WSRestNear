var imagesDiv = document.getElementById("card");
var imagesCol = document.getElementById("cardcol")

//Obtiene información sobre el sitio al cual se quiere ingresar y se genera una respuesta
const getData = async () => {
  let data = null;
  let dataCollection = null;


  if (window.location.toString().includes("account")) {
    data = await fetch(`./api/users/user=${localStorage.getItem("username")}/collections`).then(response => response.json());
  } else {
    data = await fetch("./api/arts").then(response => response.json());
  }

  data.forEach(data => {
    // let dataLike = fetch(urlApi+`users/email=${localStorage.getItem("username")}/Likes`).then(response => response.json());

    const { id, collectionName, title, author, price, likes, email_owner } = data;
    let urlImage = "./" + id;

    // if(dataLike.find(dataLike => dataLike.pictureName === title)){
    imagesDiv.innerHTML += `
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
          <div class="content">
            <div class="card-body">
              <h3 class="card-text">Titulo: ${title}</h3>
              <p class="card-text">Autor: ${author}</p>
              <div class="card-body-price_likes">
                    <span class="text-muted">Precio: ${price}</span>
                    <span class="text-muted">Likes:<button class="btn-like" id="sumarLike"><img src="Assets/svg/heart-fill.svg" width="15px"></button>
                        <span id="amountLikes" aria-valuetext="" >${likes}</span>
                    </span>
                </div>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group btns">
                  <button type="button" class="btn btn-sm btn-outline-secondary">Comprar</button>      
                  <button type="button" class="btn btn-sm btn-outline-secondary">Añadir al carro</button>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
  `;
    /* }else{
 
     imagesDiv.innerHTML += `
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
           <div class="content">
             <div class="card-body">
               <h3 class="card-text">Titulo: ${title}</h3>
               <p class="card-text">Autor: ${author}</p>
               <div class="card-body-price_likes">
                     <span class="text-muted">Precio: ${price}</span>
                     <span class="text-muted">Likes:<button class="btn-like" id="sumarLikes"><img src="Assets/svg/heart-unfill.svg" width="15px"></button>
                         <span id="amountLikes" aria-valuetext="" >${likes}</span>
                     </span>
                 </div>
               <div class="d-flex justify-content-between align-items-center">
                 <div class="btn-group btns">
                   <button type="button" class="btn btn-sm btn-outline-secondary">Comprar</button>      
                   <button type="button" class="btn btn-sm btn-outline-secondary">Añadir al carro</button>
                 </div>
               </div>
             </div>
           </div>
         </div>
     </div>
   `;}*/
  });

  // if(window.location.toString().includes("account")){
  //   dataCollection = await fetch(`./api/users/user=${localStorage.getItem("username")}/collections`);
  // }else{
  //   dataCollection = await fetch("./api/collections");
  // }
  // let dataCollectionResult = await dataCollection.json();
  // console.log(dataCollectionResult);



  // for (var i=0;i<dataCollectionResult.length ;i++) {


  //   dataCollectionNFTs = await fetch(`./api/users/${dataCollectionResult.username[i]}/collections/${dataCollectionResult.collection[i]}/arts`);
  //   let dataCollectionNFTsResult = await dataCollectionNFTs.json();

  //   // const {id,collection2,title,author,price,likes,email_owner} = dataCollectionNFTsResult;
  //   const url_Nfts= [dataCollectionNFTsResult[0].id];

  //   console.log("Data: "+url_Nfts[0]);

  if (window.location.toString().includes("account")) {
    dataCollection = await fetch(`./api/users/user=${localStorage.getItem("username")}/collections`).then(response => response.json());
  } else {
    dataCollection = await fetch("./api/collections").then(response => response.json());
  }


  for (const dataCollection1 of dataCollection) {
    const { username, collection } = dataCollection1;

    const dataCollectionNFTs = await fetch(`./api/users/${username}/collections/${collection}/arts`).then(response => response.json());
    console.log(dataCollectionNFTs);
    const urlNfts = dataCollectionNFTs.map(collectionNft => ({ id: collectionNft.id, author: collectionNft.author }))


    // const {id,collectionName2,title,author,price,likes,email_owner} =dataCollectionNFTs;
    // const url_NFTs = [id];


    // let index =0;
    // dataCollection.forEach(dataCollection=>{
    //   url_NFTs[index++] = "./"+dataCollectionNFTs[0];
    // })

    // let dataLike = fetch(urlApi+`users/email=${localStorage.getItem("username")}/Likes`).then(response => response.json());

    // const {id,collection,author,port = [urlImage]} = dataCollection;

    // if(dataLike.find(dataLike => dataLike.pictureName === title)){
    imagesCol.innerHTML = `
       <div class="col-md-4 card-position">
    <div class="card mb-4 shadow-sm card-dimensions" data-toggle="modal" data-target=".bd-example-modal-lg">
        <div class="imgBx">
            <table>
                <tr>
                    <td>
                        <img class="collection" width="100%" height="100%" style="border-top-left-radius: 3.5%;"
                            src="${urlNfts[0]?.id}" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"
                            aria-label="Placeholder: Thumbnail">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#55595c" />
                        </img>
                    </td>
                    <td>
                        <img class="collection" width="100%" height="100%" style="border-top-right-radius: 3.5%;"
                            src="${urlNfts[1]?.id}" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"
                            aria-label="Placeholder: Thumbnail">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#55595c" />
                        </img>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img class="collection" width="100%" height="100%" style="border-bottom-left-radius: 3.5%;"
                            src="${urlNfts[2]?.id}" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"
                            aria-label="Placeholder: Thumbnail">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#55595c" />
                        </img>
                    </td>
                    <td>
                        <img class="collection" width="100%" height="100%" style="border-bottom-right-radius: 3.5%;"
                            src="${urlNfts[3]?.id}" preserveAspectRatio="xMidYMid slice" focusable="false" role="img"
                            aria-label="Placeholder: Thumbnail">
                        <title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#55595c" />
                        </img>
                    </td>
                </tr>
            </table>
        </div>
        <div class="content">
            <div class="card-body">
                <h3 class="card-text">Colección: ${collection}</h3>
                <p class="card-text text-muted">Autor: ${urlNfts[0]?.author}</p>
            </div>
        </div>
    </div>
</div>
   `;
    /* }else{

     imagesDiv.innerHTML += `
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
           <div class="content">
             <div class="card-body">
               <h3 class="card-text">Titulo: ${title}</h3>
               <p class="card-text">Autor: ${author}</p>
               <div class="card-body-price_likes">
                     <span class="text-muted">Precio: ${price}</span>
                     <span class="text-muted">Likes:<button class="btn-like" id="sumarLikes"><img src="Assets/svg/heart-unfill.svg" width="15px"></button>
                         <span id="amountLikes" aria-valuetext="" >${likes}</span>
                     </span>
                 </div>
               <div class="d-flex justify-content-between align-items-center">
                 <div class="btn-group btns">
                   <button type="button" class="btn btn-sm btn-outline-secondary">Comprar</button>
                   <button type="button" class="btn btn-sm btn-outline-secondary">Añadir al carro</button>
                 </div>
               </div>
             </div>
           </div>
         </div>
     </div>
   `;}*/
  }

  //document.getElementById('sumarLikes').addEventListener('click', btnLike);
};

window.addEventListener("DOMContentLoaded", getData());

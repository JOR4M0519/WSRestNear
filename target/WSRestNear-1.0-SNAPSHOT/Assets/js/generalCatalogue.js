var imagesDiv = document.getElementById("card");

const getData = async () => {
  let data = null;
  const urlApi = "http://localhost:8080/WSRestNear-1.0-SNAPSHOT/api/ArtNFT";
  if(window.location.toString().includes("account")){
    data = await fetch(urlApi+`/email=${localStorage.getItem("username")}`).then(response => response.json());
  }else{
    data = await fetch(urlApi).then(response => response.json());
  }

  data.forEach(data => {

    const {id,extension,title,author,price,likes,email_owner} = data;
    let urlImage = "./"+id;

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
  `;});};

window.addEventListener("DOMContentLoaded", getData());

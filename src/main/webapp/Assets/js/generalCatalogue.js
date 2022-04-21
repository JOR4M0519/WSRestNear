var imagesDiv = document.getElementById("card");

const getData = async () => {
  let data = null;

  if(window.location.toString().includes("account")){
    data = await fetch(`personalListFiles?email=${localStorage.getItem("username")}`).then(response => response.json());
  }else{
    data = await fetch("generalListFiles").then(response => response.json());
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
              <p class="text-muted">Precio: ${price}</p>
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

window.addEventListener("DOMContentLoaded", function(){
  var location = this.top.location.href.split('#')[1];
  if(location!=undefined){
  this.document.getElementById('frame').src = `./${location}.html`;
  }
});

let sidebar = document.querySelector(".sidebar");
  let closeBtn = document.querySelector("#btn");
  //let searchBtn = document.querySelector(".bx-search");

//Añade un respectivo escuchador al botón para que este pueda realizar alguna acción
  closeBtn.addEventListener("click", ()=>{
    sidebar.classList.toggle("open");
    menuBtnChange();
  });

//Añade un respectivo escuchador al botón para que este pueda realizar alguna acción
  /*searchBtn.addEventListener("click", ()=>{
    sidebar.classList.toggle("open");
    menuBtnChange(); 
  });*/

//Genera una respectiva acción dependiendo si el botón es accionado o no
  function menuBtnChange() {
   if(sidebar.classList.contains("open")){
     closeBtn.classList.replace("bx-menu", "bx-menu-alt-right");
   }else {
     closeBtn.classList.replace("bx-menu-alt-right","bx-menu");
   }
  }

/*async function loadData() {
    if (top.location.href.includes("artistAccount")) {
        const dataLikes = await fetch(`./api/owners/${localStorage.getItem("username")}/arts/likes`).then(response => response.json());
        getDataArts(document.getElementById("cardfavorites"), dataLikes, getArtSale);

    } else {
        const dataOwner = await fetch(`./api/owners/${localStorage.getItem("username")}/arts`).then(response => response.json());
       getDataArts(document.getElementById("cardOwner"), dataOwner, null);
    }
    window.addEventListener("DOMContentLoaded", this.loadData);
}*/


  
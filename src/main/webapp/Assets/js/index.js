const getArtistsData = async ()=>{
    var artistTeamDiv = document.getElementById("artistsTeam");

    var artistsData = JSON.parse(await fetch(`./api/users/data`).then(response => response.text()));


    artistsData.sort(function (a, b) {
        return a.likes-b.likes;
      });
    artistsData.reverse();

      //Toma solo los ultimos 4 artistas dependiendo de su numero de likes
    while(artistsData.length>4 ){
        artistsData.length = artistsData.length - 1;
    }


    let innerHtml = "";

    for(const artistData of artistsData){

        const {profileImage, description, name, collections, arts, likes} = artistData;
        const srcProfileImage = "profileImages\\"+profileImage;

        innerHtml += `
        <div class="col-lg-3 cardArtists">
          <div class="group-infoArtist">
            <img id="item1"  src="${srcProfileImage}"class="profileImage">
            <h3 id="item4" style="color: black; justify-self: center;"> ${name}</h3>
            <p id="item3" placeh>Collecciones: 
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-collection" viewBox="0 0 16 16">
                <path d="M2.5 3.5a.5.5 0 0 1 0-1h11a.5.5 0 0 1 0 1h-11zm2-2a.5.5 0 0 1 0-1h7a.5.5 0 0 1 0 1h-7zM0 13a1.5 1.5 0 0 0 1.5 1.5h13A1.5 1.5 0 0 0 16 13V6a1.5 1.5 0 0 0-1.5-1.5h-13A1.5 1.5 0 0 0 0 6v7zm1.5.5A.5.5 0 0 1 1 13V6a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-.5.5h-13z"/>
                </svg>
                ${collections}
            </p>

            <p>Artes: ${arts} 
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-brush" viewBox="0 0 16 16">
                <path d="M15.825.12a.5.5 0 0 1 .132.584c-1.53 3.43-4.743 8.17-7.095 10.64a6.067 6.067 0 0 1-2.373 1.534c-.018.227-.06.538-.16.868-.201.659-.667 1.479-1.708 1.74a8.118 8.118 0 0 1-3.078.132 3.659 3.659 0 0 1-.562-.135 1.382 1.382 0 0 1-.466-.247.714.714 0 0 1-.204-.288.622.622 0 0 1 .004-.443c.095-.245.316-.38.461-.452.394-.197.625-.453.867-.826.095-.144.184-.297.287-.472l.117-.198c.151-.255.326-.54.546-.848.528-.739 1.201-.925 1.746-.896.126.007.243.025.348.048.062-.172.142-.38.238-.608.261-.619.658-1.419 1.187-2.069 2.176-2.67 6.18-6.206 9.117-8.104a.5.5 0 0 1 .596.04zM4.705 11.912a1.23 1.23 0 0 0-.419-.1c-.246-.013-.573.05-.879.479-.197.275-.355.532-.5.777l-.105.177c-.106.181-.213.362-.32.528a3.39 3.39 0 0 1-.76.861c.69.112 1.736.111 2.657-.12.559-.139.843-.569.993-1.06a3.122 3.122 0 0 0 .126-.75l-.793-.792zm1.44.026c.12-.04.277-.1.458-.183a5.068 5.068 0 0 0 1.535-1.1c1.9-1.996 4.412-5.57 6.052-8.631-2.59 1.927-5.566 4.66-7.302 6.792-.442.543-.795 1.243-1.042 1.826-.121.288-.214.54-.275.72v.001l.575.575zm-4.973 3.04.007-.005a.031.031 0 0 1-.007.004zm3.582-3.043.002.001h-.002z"/>
                </svg>
            </p>

            <p>Likes: 
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark-heart-fill" viewBox="0 0 16 16">
                <path d="M2 15.5a.5.5 0 0 0 .74.439L8 13.069l5.26 2.87A.5.5 0 0 0 14 15.5V2a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v13.5zM8 4.41c1.387-1.425 4.854 1.07 0 4.277C3.146 5.48 6.613 2.986 8 4.412z"/>
                </svg>
                ${likes}
            </p>

            <p id="item4">
                ${description}
            </p>
          </div>   
        </div> `;

    }
    artistTeamDiv.innerHTML+=innerHtml;
}

const getArtDataSection = async ()=>{
    /*
    * Catalogo de Artes Resientes
    */
    const dataRecents = await fetch("./api/arts").then(response => response.json());

    dataRecents.sort(function (a, b) {
        return a.counter-b.counter;
    });
    //invierte la lista tomando los últimos creados
    dataRecents.reverse();

    //Toma solo las primeras 6 artes
    while(dataRecents.length>6 ){
        dataRecents.length = dataRecents.length - 1;
    }
    //modifica el id por la ruta de la imagen
    dataRecents.map(function(element) {
        element.id = "NFTS\\"+element.id;
    })

    /*
    * Catalogo de TopLikes
    */
    const dataTopLikes = await fetch("./api/arts/likes").then(response => response.json());


    if(localStorage.getItem("role") === "Artista"){

        getDataArts(document.getElementById("card"),dataRecents,getArtSale)
        getDataArts(document.getElementById("card_LikesRanking"), dataTopLikes, getArtSale);
    }else{
        getDataArts(document.getElementById("card"),dataRecents,getBuyBtn)
        getDataArts(document.getElementById("card_LikesRanking"), dataTopLikes, getBuyBtn);
    }

    const dataCollection = await fetch("./api/collections").then(response => response.json());
    //enableEdit False al estar en index
    getDataCollection(dataCollection,false)
}

window.addEventListener("DOMContentLoaded",
    getArtDataSection(), getArtistsData());



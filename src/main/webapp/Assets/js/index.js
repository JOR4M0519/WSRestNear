
const getArtistsData = async ()=>{
    var artistTeamDiv = document.getElementById("artistsTeam");

    var artistsData = JSON.parse(await fetch(`./api/users/data`).then(response => response.text()));
    

    artistsData.sort(function (a, b) {
        return a.likes-b.likes;
      });
      artistsData.reverse();

    let innerHtml = "";

    for(const artistData of artistsData){

        const {name, collections, arts, likes} = artistData;

        innerHtml += `
        <div class="col-lg-3 cardArtists">
          <div class="group-infoArtist">
            <img id="item1"  src="https://yt3.ggpht.com/ytc/AKedOLRYkj_Jh5coL9ljmJgpPudg6g4UzsjQPeQ1jc7L2w=s900-c-k-c0x00ffffff-no-rj"class="img-fluid rounded-circle w-50">
            <h3 id="item4" style="color: black; justify-self: center;"> ${name}</h3>
            <p id="item3">Collecciones: ${collections}</p>
            <p style="margin-left: 16%;">Artes: ${arts}</p>
            <p style="margin-left: 16%;">Likes: ${likes}</p>
            <p id="item4">
              Artista visual, videojockey, diseñador gráfico, artista y NFT artist
            </p>
          </div>   
        </div> `;
        
    }
    artistTeamDiv.innerHTML+=innerHtml;
}

const btnBuy = async (input, condition)=>{

    var cantidad = localStorage.getItem('cantidadCompras');
    var data = input.id;
    var dataBuyJSON = JSON.parse(data);
    var id = dataBuyJSON.id.toString().split("\\")[1];
    let dataOwner = await fetch(`./api/owners/arts/${id}`).then(response => response.json());


    if (localStorage.getItem('username')!=null || localStorage.getItem('username') != undefined ) {
        if (dataOwner.username != localStorage.getItem('username')) {
            if (cantidad == null) {

                localStorage.setItem('cantidadCompras', 1);
                localStorage.setItem('buy1', data);
                if (condition=='buy') {
                    window.location.href = './shoppingCart.html';
                }else {
                    document.getElementById("numCantCompras").innerHTML=`&nbsp;${localStorage.getItem('cantidadCompras')}`;
                }
            } else {
                let exist = false;
                for (var i = 1; i <= cantidad && !exist; i++) {
                    if (localStorage.getItem(`buy${i}`) != data) {
                    } else {
                        exist = true;
                        if (condition=='buy') {
                            window.location.href = "./shoppingCart.html";
                        }
                    }
                }
                if (!exist) {
                    cantidad = parseInt(cantidad) + parseInt(1);
                    localStorage.setItem(`buy${cantidad}`, data);
                    localStorage.setItem('cantidadCompras', cantidad);
                    if (condition=='buy') {
                        window.location.href = "./shoppingCart.html";
                    }else {
                        document.getElementById("numCantCompras").innerHTML=`&nbsp;${localStorage.getItem('cantidadCompras')}`;
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
    }else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Inicie Sesión para Comprar!',
        })
    }

}

window.addEventListener("DOMContentLoaded", getDataArts(document.getElementById("card")), getDataCollection(), getArtistsData());



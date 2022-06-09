divWallet = document.getElementById("walletHistory");

const getWalletHistory = async () => {
    const historyWallet = await fetch(`./api/users/${localStorage.getItem("username")}/wallet`).then(response => response.json());


    if (historyWallet.length != 0) {

        const listUsers = await fetch(`./api/users`).then(response => response.json());
        const listArts = await fetch(`./api/arts`).then(response => response.json());
        //console.log(listArts,listUsers)

        historyWallet.map(function (element) {
                let {walletType, art, origin_product, fcoins, registeredAt} = element
                console.log(registeredAt)
                registeredAt = registeredAt.toString().replace('Z', '');
                console.log(registeredAt)

                //Cargar datos en caso que sea una recarga de Fcoins
                if (walletType == "Recarga") {
                    divWallet.innerHTML +=
                        `<div class="cresumen">
                            <p class="itemLeft" style="grid-column-start: 1; grid-column-end: 3;">Recarga</p>
                
                            <p class="itemLeft">Banco</p>
                            <p>Fcoins</p>
                              
                            <p class="itemLeft">Fecha</p>
                            <p>${registeredAt}</p>
                              
                            <h3 class="itemLeft"><b> Valor </b></h3>
                            <h3><b style="color: green;" >$+${new Intl.NumberFormat().format(fcoins)} </b></h3>
                        </div>`;

                //Cargar datos en caso sea una venta o compra
                } else {
                    let type;
                    let styleMoney;
                    let sum = "";

                    const artNFT = listArts.filter(data => (data.id === art));
                    const user = listUsers.filter(data => (data.username === origin_product));

                    //Especificaciones en casos que sea venta o compra
                    if (walletType == "Venta") {
                        type = "Vendido a";
                        styleMoney = `style="color: green;"`;
                        sum = "+";
                    } else {
                        type = "Comprado a";
                        styleMoney = `style="color: red;"`;
                    }

                    divWallet.innerHTML +=
                        `<div class="cresumen">
                            <p class="itemLeft">NFT</p>
                            <p>${artNFT[0].title}</p>
                
                            <p class="itemLeft">${type}</p>
                            <p>${user[0].name} ${user[0].lastname}</p>
                              
                            <p class="itemLeft">Fecha</p>
                            <p>${registeredAt}</p>
                              
                            <h3 class="itemLeft"><b> Valor </b></h3>
                            <h3><b ${styleMoney}>$${sum} ${new Intl.NumberFormat().format(fcoins)} </b></h3>
                        </div>`;
                }
            }
        );

    //Cargar anuncio en caso que no tenga ningun registro
    } else {
        divWallet.innerHTML +=
            `<div class="cresumen">  
                <h3 class="itemLeft" style="grid-column-start: 1; grid-column-end: 3;"><b> No has realizado compras </b></h3>
            </div>`;
    }
}


window.addEventListener("DOMContentLoaded", getWalletHistory());
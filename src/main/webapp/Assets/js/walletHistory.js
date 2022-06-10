divWallet = document.getElementById("walletContent");

const getWalletHistory = async () => {
    var historyWallet = await fetch(`./api/users/${localStorage.getItem("username")}/wallet`).then(response => response.json());


    if (historyWallet.length != 0) {

        historyWallet.reverse();

        const listUsers = await fetch(`./api/users`).then(response => response.json());
        const listArts = await fetch(`./api/arts`).then(response => response.json());

        historyWallet.map(function (element) {
                let {walletType, art, originProduct, fcoins, registeredAt} = element
                registeredAt = registeredAt.toString().replace('Z', '');

                //Cargar datos en caso que sea una recarga de Fcoins
                if (walletType == "Recarga") {
                    divWallet.innerHTML +=
                        `<div class="cresumen">
                            <p class="itemLeft">Recarga</p>
                            <p>Fcoins</p>
                
                            <p class="itemLeft">Banco</p>
                            <p>${originProduct}</p>
                              
                            <p class="itemLeft">Fecha</p>
                            <p>${registeredAt}</p>
                              
                            <h5 class="itemLeft"><b> Valor </b></h5>
                            <h5><b style="color: green;" >$+${new Intl.NumberFormat().format(fcoins)} </b></h5>
                        </div>`;

                //Cargar datos en caso sea una venta o compra
                } else {
                    let type;
                    let styleMoney;
                    let sum = "";

                    const artNFT = listArts.filter(data => (data.id === art));
                    const user = listUsers.filter(data => (data.username === originProduct));

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
                              
                            <h5 class="itemLeft"><b> Valor </b></h5>
                            <h5><b ${styleMoney}>$${sum} ${new Intl.NumberFormat().format(fcoins)} </b></h5>
                        </div>`;
                }
            }
        );

    //Cargar anuncio en caso que no tenga ningun registro
    } else {
        divWallet.innerHTML +=
            `<div class="cresumen" style="border: none; margin-left: 2%">  
                <h5 class="itemLeft" style="grid-column-start: 1; grid-column-end: 3;"><b> No has realizado ninguna transacción </b></h5>
            </div>`;
    }
}


window.addEventListener("DOMContentLoaded", getWalletHistory());
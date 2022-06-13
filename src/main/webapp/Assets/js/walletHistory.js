if (localStorage.getItem('role') == 'Comprador') {
    document.getElementById('FCoins').innerHTML =
        `
        <h1 class="text">Recargar Fcoins</h1>
        <div class="info">

            <div class="card mb-3" style="width: 100%;">
                <div class="row g-0" style="margin: 0;">
                    <div class="col-md-4 bg-danger" style="background-image: linear-gradient(to bottom right,#49070f ,#BA2737 , black); border-radius: 1%; display: flex; align-items: center;">
                        <img src="Assets/img/Fcoins Icon2.png" class="img-fluid rounded-start" alt="..." style="padding: 5%; border-top-left-radius: 20%; border-bottom-left-radius: 2%;">
                    </div>

                    <div class="col-md-8">
                        <div class="card-body" style="padding-top: 6%;">
                            <h3 class="card-text">Recarga Fcoins!</h3><br>
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" id="bank" aria-label="Recipient's username" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <span class="input-group-text" id="basic-addon2">Banco</span>
                                </div>
                            </div><br>
                                <div class="input-group mb-3">
                                    <img class="input-group-text" id="basic-addon1" src="Assets/img/Fcoins_IconB.png" alt="" width="10%">
                                    <input type="text" id="cantidad" name="cantidad" class="form-control" value="0">
                                </div><br>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <button type="button" id="sumar" class="btn btn-sm btn-outline-secondary"><i class="fas fa-plus"></i></button>
                                        <button type="button" id="restar" class="btn btn-sm btn-outline-secondary"><i class="fas fa-minus"></i></button>
                                        <button type="submit" id="btnAddFCOINS" class="btn btn-sm btn-outline-secondary" style="float: right;">Cargar FCoins</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `;
}


divWallet = document.getElementById("walletContent");
var inputFcoins = document.getElementById("amountFcoins");

const getAmountFCoins = async () => {

    let response2 = await fetch(`./api/users/${localStorage.getItem("username")}/wallet/fcoins`);
    let result2 = await response2.json();
    inputFcoins.placeholder = "$" + new Intl.NumberFormat().format(result2.fcoins.toString());
    console.log(new Intl.NumberFormat().format(result2.fcoins.toString()));

}


const getWalletHistory = async () => {
    var historyWallet = await fetch(`./api/users/${localStorage.getItem("username")}/wallet`).then(response => response.json());


    if (historyWallet.length != 0) {

        historyWallet.reverse();

        const listUsers = await fetch(`./api/users`).then(response => response.json());
        const listArts = await fetch(`./api/arts`).then(response => response.json());

        historyWallet.map(function (element) {
            let { walletType, art, originProduct, fcoins, registeredAt } = element
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



window.addEventListener("DOMContentLoaded", getWalletHistory(), getAmountFCoins());
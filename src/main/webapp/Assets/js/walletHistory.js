divWallet = document.getElementById("walletContent");

const getWalletHistory = async () => {
    const historyWallet = await fetch(`./api/users/${localStorage.getItem("username")}/wallet`);

    historyWallet.map(function(element) {
        divWallet.innerHTML += 
        `<div class="cresumen">
            <p class="itemLeft">NFT</p>
            <p>${}</p>

            <p class="itemLeft">Comprado a</p>
            <p>${} Jeanpier sapohpta</p>
              
            <p class="itemLeft">Fecha</p>
            <p>${s}12/02/2022</p>
              
            <h3 class="itemLeft"><b> Valor </b></h3>
            <h3><b>$ ${new Intl.NumberFormat().format(resultBtn.fcoins)} </b></h3>
          </div>`;
        return element * 1000;
      });
}


window.addEventListener("DOMContentLoaded", getWalletHistory());
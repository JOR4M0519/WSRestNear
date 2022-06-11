const getButtonAccount = async () => {
    var botonFCoins = document.getElementById("menusbtn");
    var botonDiv = document.getElementById("dropdown-menu");
    var botonNombre = document.getElementById("nameAccount");
    var divBotones = document.getElementById("divbuttons");
    var supCompras = document.getElementById("numCantCompras");

    divBotones.style.marginLeft="86%";

    if(localStorage.getItem('cantidadCompras')>0){
        supCompras.innerHTML=`&nbsp;${localStorage.getItem('cantidadCompras')}`;
    }

    if (localStorage.getItem("username") == null || localStorage.getItem("username") == undefined) {

        botonNombre.innerHTML = `Mi Cuenta`;
        botonFCoins.innerHTML = ``;
        botonDiv.innerHTML = `
    <a class="dropdown-item " id="dropdown-item" href="./login.html"> Iniciar Sesión </a>
    <a class="dropdown-item" id="dropdown-item" href="./sign_up.html"> Crear cuenta </a>
    `;
    } else {
        let response = await fetch(`./api/users/${localStorage.getItem("username")}`);
        let result = await response.json();

        var nombre = result.name;
        botonNombre.innerHTML = `${nombre}`;
        botonDiv.innerHTML = `
         <input type="submit" class="dropdown-item" id="dropdown-item1"  value="Mi Cuenta">
         <input type="submit" class="dropdown-item " id="dropdown-item2"  value="Salir">
         
    `;
        document.getElementById("dropdown-item1").addEventListener('click',account);

        function account(){

            if(localStorage.getItem("role")=="Artista"){
                window.location.href="./account.html"
            }else if (localStorage.getItem("role")=="Comprador"){
                window.location.href="./customerAccount.html"
            }

        }
        document.getElementById("dropdown-item2").addEventListener('click',logout);

        function logout(){

            localStorage.removeItem("username");
            localStorage.removeItem("role");
            localStorage.removeItem("cantidadCompras");


            for (let i=1; localStorage.getItem(`buy${i}`) != null;i++){
                localStorage.removeItem(`buy${i}`);
            }

            window.location.href= "./index.html";

        }
        divBotones.style.marginLeft="73%";
        let responseBtn = await fetch(`./api/users/${localStorage.getItem("username")}/wallet/fcoins`);
        let resultBtn = JSON.parse(await responseBtn.text());

        if (localStorage.getItem('role')=="Comprador"){
            botonFCoins.innerHTML = `
    <div class="dropdown show" style="float: left;">
        <a class="btn btn-secondary" id="dropdown"  href="./customerAccount.html#FCoins" role="button"  aria-haspopup="true" aria-expanded="false">
            <span  id="fcoins"> FCoins: $${new Intl.NumberFormat().format(resultBtn.fcoins)} </span>
        </a>
    </div>
    `;
        }else {
            botonFCoins.innerHTML = `
    <div class="dropdown show" style="float: left;">
        <a class="btn btn-secondary" id="dropdown"  href="" role="button"  aria-haspopup="true" aria-expanded="false">
            <span  id="fcoins"> FCoins: $${new Intl.NumberFormat().format(resultBtn.fcoins)} </span>
        </a>
    </div>
    `;
        }


    }
};



window.addEventListener("DOMContentLoaded", getButtonAccount());
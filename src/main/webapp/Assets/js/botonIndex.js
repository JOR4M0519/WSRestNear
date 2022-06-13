const getButtonAccount = async () => {
    var botonFCoins = document.getElementById("menusbtn");
    var botonDiv = document.getElementById("dropdown-menu");
    var botonCart = document.getElementById('btnCart');
    var botonNombre = document.getElementById("nameAccount");
    var divBotones = document.getElementById("divbuttons");
    var supCompras = document.getElementById("numCantCompras");


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
                window.location.href="./account.html#myaccount"

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
        let responseBtn = await fetch(`./api/users/${localStorage.getItem("username")}/wallet/fcoins`);
        let resultBtn = JSON.parse(await responseBtn.text());

        if (localStorage.getItem('role')=="Comprador"){

            botonCart.innerHTML = `<button class="dropdown show btn" id="dropdown" style="float: left;">
            <a href="./account.html#shoppingCart" style="color: white;"><i class="fa fa-shopping-cart"></i><sup
                id="numCantCompras">&nbsp0</sup></a>
          </button>`;

            botonFCoins.innerHTML = `
    <div class="dropdown show" style="float: left;">
        <a class="btn btn-secondary" id="dropdown"  href="./account.html#wallet" role="button"  aria-haspopup="true" aria-expanded="false">
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
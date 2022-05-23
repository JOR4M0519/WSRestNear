const getButtonAccount = async () => {
    var botonFCoins = document.getElementById("menusbtn");
    var botonDiv = document.getElementById("dropdown-menu");
    var botonNombre = document.getElementById("nameAccount");
    var divBotones = document.getElementById("divbuttons");

    divBotones.style.marginLeft="86%";

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
               window.location.href="./artistAccount.html"
           }else if (localStorage.getItem("role")=="Comprador"){
               window.location.href="./customerAccount.html"
           }

        }
        document.getElementById("dropdown-item2").addEventListener('click',logout);

        function logout(){

            localStorage.removeItem("username");
            localStorage.removeItem("role");
            location.reload();

        }
        divBotones.style.marginLeft="79%";
            let responseBtn = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
            let resultBtn = await responseBtn.json();

            if (localStorage.getItem('role')=="Comprador"){
                botonFCoins.innerHTML = `
    <div class="dropdown show" style="float: left;">
        <a class="btn btn-secondary" id="dropdown"  href="./customerAccount.html#FCoins" role="button"  aria-haspopup="true" aria-expanded="false">
            <span  id="fcoins"> FCoins: ${resultBtn.fcoins} </span>
        </a>
    </div>
    `;
            }else {
                botonFCoins.innerHTML = `
    <div class="dropdown show" style="float: left;">
        <a class="btn btn-secondary" id="dropdown"  href="" role="button"  aria-haspopup="true" aria-expanded="false">
            <span  id="fcoins"> FCoins: ${resultBtn.fcoins} </span>
        </a>
    </div>
    `;
            }


    }
};



window.addEventListener("DOMContentLoaded", getButtonAccount());



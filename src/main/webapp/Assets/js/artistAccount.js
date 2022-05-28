const getDataAccount = async () => {


    var divFullname = document.getElementById("text-icono");
    var divName = document.getElementById("name");
    var divLastname = document.getElementById("lastname");
    var divUsername = document.getElementById("username");
    var divRole = document.getElementById("role");
    var divListFullname = document.getElementById("listFullName");
    var divListRole = document.getElementById("listRole");
    var inputFcoins = document.getElementById("amountFcoins");


    if (localStorage.getItem("username") != null || localStorage.getItem("username") != undefined) {

        let response = await fetch(`./api/users/${localStorage.getItem("username")}?role=${localStorage.getItem("role")}`);
        let result = await response.json();

        divFullname.innerHTML = `${result.name} ${result.lastname}`;
        divName.innerHTML = `${result.name}`;
        divLastname.innerHTML = `${result.lastname}`;
        divUsername.innerHTML = `${result.username}`;
        divRole.innerHTML = `${result.role}`;
        divListRole.innerHTML = `${result.role}`;
        divListFullname.innerHTML = `${result.name} ${result.lastname}`;

        if(localStorage.getItem("role")=="Comprador") {

            let response2 = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
            let result2 = await response2.json();
            inputFcoins.placeholder = "$" + new Intl.NumberFormat().format(result2.fcoins.toString());
        }
    }


};



document.getElementById("btnlog_out").addEventListener('click', logout);

function logout(){
    localStorage.removeItem("username");
    localStorage.removeItem("role");
    window.location.href= "./index.html";
}

window.addEventListener("DOMContentLoaded", getDataAccount());
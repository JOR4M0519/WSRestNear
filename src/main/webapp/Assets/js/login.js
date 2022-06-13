
//Obtiene los String necesarios para realizar un login, se los manda al Post de la API correspondiente para verificar si puede o no iniciar sesión
formElem.onsubmit = async (e) => {
    e.preventDefault();


    let user = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let role = document.getElementById("roleInput").value;


    let response = await fetch(`./api/users/${user}`);
    let result = await response.json();

    if (user==result.username && password==result.password && role==result.role){
        localStorage.setItem("username",user);
        localStorage.setItem("role", role)
        top.location.href = "./index.html";

    }else{
        swal({
            title: "Usuario Incorrecto!",
            text: "Intente Nuevamente.",
            imageUrl: '.Assets/img/error.ico'
        });
    }


}
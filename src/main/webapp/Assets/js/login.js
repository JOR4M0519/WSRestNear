//Este metodo se encarga de validar el -
formElem.onsubmit = async (e) => {
    e.preventDefault();


    let user = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let role = document.getElementById("roleInput").value;


    let response = await fetch(`./api/users/${user}?role=${role}`);
    let result = await response.json();

    if (user==result.username && password==result.password && role==result.role){
        localStorage.setItem("username",user);
        localStorage.setItem("role", role)
        window.location.href = "./index.html";

    }else{
        swal({
            title: "Usuario Incorrecto!",
            text: "Intente Nuevamente.",
            imageUrl: '.Assets/img/error.png'
        });
    }


}

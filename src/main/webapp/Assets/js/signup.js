//Este metodo se encarga de crear un usuario y validar que no exista
const form = document.querySelector("form");

form.onsubmit = async (e) => {
    e.preventDefault();

    
    let user = document.getElementById("exampleInputEmail1").value;
    let role = document.getElementById("roleInput").value;

    try {
        let responseData = await fetch(`./api/users/${user}?role=${role}`);
        let resultData = await responseData.json();

        if (resultData.username == null && resultData.role ==null ) {
            try {
                let response = await fetch("./api/users", {
                    method: 'POST',
                    body: new FormData(form)

                });
                let result = await response.json();

                localStorage.setItem("username", result.username);
                localStorage.setItem("role", result.role);
                top.location.href = "./index.html";

            } catch (err) {
                console.log(err);

            }
        }else {
            swal({
                title: "Usuario Existente!",
                text: "Intente Nuevamente.",
                imageUrl: '.Assets/img/error.ico'
            });
        }

    }catch{
        console.log("error");
    }
}



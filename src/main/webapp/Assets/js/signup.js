//Este metodo se encarga de crear un usuario y validar que no exista
const form = document.querySelector("form");

form.onsubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    let user = document.getElementById("exampleInputEmail1").value;
    let role = document.getElementById("roleInput").value;

    try {
        let responseData = await fetch(`./api/users/${user}?role=${role}`);
        let resultData = await responseData.json();


        if (resultData.username == null && resultData.role ==null ) {
            try {
                let response = await fetch("./api/users", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    body: new URLSearchParams(formData),

                });
                let result = await response.json();

                localStorage.setItem("username", result.username);
                localStorage.setItem("role", result.role);
                window.location.href = "./index.html";

            } catch (err) {
                console.log(err);

            }
        }else {
            alert("ya existe este usuario");
        }

    }catch{
        console.log("error");
    }
}



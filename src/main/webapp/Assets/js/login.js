//Obtiene los String necesarios para realizar un login, se los manda al Post de la API correspondiente para verificar si puede o no iniciar sesión
formElem.onsubmit = async (e) => {
     e.preventDefault();


   let user = document.getElementById("username").value;
   let password = document.getElementById("password").value;
    let role = document.getElementById("roleInput").value;
    console.log(role);

     let response = await fetch(`./api/users/${user}?role=${role}`);
     let result = await response.json();

     if (user==result.username && password==result.password && role==result.role){
        localStorage.setItem("username",user);
        window.location.href = "./index.jsp";

     }else{
        alert("error");
     }


 }

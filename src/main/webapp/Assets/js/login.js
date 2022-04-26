

function getData() {
    let datas = null;
    let user = document.getElementById("username").value;


    fetch( `./api/users/${user}`)
         .then(response => response.json())
         .then(data => console.log(data))
     ;



}
document.getElementById('button-user').addEventListener('click', getData);


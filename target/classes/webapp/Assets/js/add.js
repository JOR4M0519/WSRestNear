let formulario = document.getElementById("formulario");
const url = "http://localhost:3000/NEAr";
window.addEventListener("DOMContentLoaded", async () => {});
formulario.addEventListener("submit", async (esc) => {
  esc.preventDefault();
  //capturando los datos del html y los voy a enviar a mi storage.json
  let coleccion = document.getElementById("colection").value;
  let nickname = document.getElementById("nickname").value;
  let price = document.getElementById("price").value;
  let name = document.getElementById("name").value;
  let photo = document.getElementById("photo").value;

  console.log(coleccion, nickname, price, name, photo);

  // atraves de una promesa url donde se van a guardar esos datos
  let resp = await fetch(url, {
    method: "POST",
    body: JSON.stringify({
      colection: coleccion,
      nickname: nickname,
      price: price+" FCoins",
      name: name,
      photo: photo,
      likes: 0,
    }),
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
  });
  
  let data = resp.json();
  console.log(data);
  alert("¡Tu imagen ha sido agregada con éxito!");
});

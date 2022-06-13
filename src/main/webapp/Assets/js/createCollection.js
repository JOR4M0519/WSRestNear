const getName = async () => {
    document.getElementById("name").value= localStorage.getItem("username");
}


const form = document.querySelector("form");

form.onsubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData(form);

    let autor = document.getElementById("name").value;
    let collection = document.getElementById("collection").value;

        let responseData = await fetch(`./api/users/${autor}/collections/${collection}`);
        let resultData = await responseData.json();


        if (resultData.collection != collection ) {


            let response = await fetch(`./api/users/${autor}/collections` , {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams(formData),

            });
            let result = response.json();

            sessionStorage.setItem("colletion", result.collection);
            document.getElementById('frame').src= "./createNFT.html";



        }else {
            alert("Ya existe esta colección");
        }


}

window.addEventListener("DOMContentLoaded", getName());
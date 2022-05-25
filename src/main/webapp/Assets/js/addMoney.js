
const add = async () =>{

    const fcoins = JSON.stringify({ "username":localStorage.getItem('username').toString(),"fcoins": parseFloat(document.getElementById('cantidad').value)});
    console.log(fcoins);
    try {
        let response = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: fcoins

        });
        let result = await response.json();
        console.log(result)
        window.location.href = "./index.html";

    } catch (err) {
        console.log(err);


    }
}
document.getElementById('btnAddFCOINS').addEventListener('click',add);
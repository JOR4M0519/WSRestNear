
const add = async () =>{

    try {
        //JSON user
        var reloadFcoins = {  "username":localStorage.getItem('username').toString(),
            "walletType": "Recarga",
            "fcoins": parseFloat(document.getElementById('cantidad').value),
            "registeredAt": new Date(),
            "originProduct": document.getElementById('bank').value.toString()};

        reloadFcoins = JSON.stringify(reloadFcoins);

        //Se añade la recarga al historial
        await fetch(`./api/users/${localStorage.getItem("username")}/wallet`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: reloadFcoins
        });

        window.location.href = "./index.html";

    } catch (err) {
        console.log(err);

    }
}
document.getElementById('btnAddFCOINS').addEventListener('click',add);
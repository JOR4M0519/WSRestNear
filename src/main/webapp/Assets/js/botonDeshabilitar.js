
function btnDeshabilitar(btn)
{
    var text ="";
    if (btn.value=="Deshabilitar Compra") {
        text = "Deshabilitar";
    }else{
        text="Habilitar";
    }

    Swal.fire({
        title: `¿Está Seguro en ${text} la Compra?`,
        text: "",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: `Si, ${text}!`
    }).then((result) => {
        if (result.isConfirmed) {
            actualizarVenta(btn)
        }
    })

}
const actualizarVenta = async (btn) => {

    var data = btn.id;
    var art = JSON.parse(data);
    art.id = art.id.split('\\')[1];
    console.log(art);

    try {


        let response = await fetch(`./api/arts/forsale`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(art)

        });
        let result = await response.json();

        result.id = "NFTS\\"+result.id;
        btn.id = JSON.stringify(result);
        document.getElementById(data).id=JSON.stringify(result);

        if (btn.value=="Deshabilitar Compra"){
            btn.value="Habilitar Compra";
        }else {
            btn.value="Deshabilitar Compra";
        }

    } catch (err) {
        console.log(err);

    }




}

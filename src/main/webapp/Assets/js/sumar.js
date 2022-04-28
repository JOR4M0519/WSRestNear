document.getElementById('sumar').addEventListener('click', sumar);
document.getElementById('restar').addEventListener('click', restar);

//sumar de 100 en 100
    function sumar(){
        var inputCantidad = document.getElementById("cantidad");
        var cantidad = inputCantidad.value;
        inputCantidad.value = parseInt(cantidad) + parseInt(100);
    }

//restar de 100 en 100
    function restar(){
        var inputCantidad = document.getElementById("cantidad");
        var cantidad = parseInt(inputCantidad.value);

        if (cantidad>=100) {
        inputCantidad.value = cantidad - parseInt(100);
        }
    }



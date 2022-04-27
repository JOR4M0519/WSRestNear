document.getElementById('sumar').addEventListener('click', sumar);
document.getElementById('restar').addEventListener('click', restar);


    function sumar(){
        try {
            var inputCantidad = document.getElementById("cantidad");
            var cantidad = inputCantidad.value;
            inputCantidad.value = parseInt(cantidad) + parseInt(100);
        }catch {
            inputCantidad.value = 100;
        }
    }

    function restar(){

        var inputCantidad = document.getElementById("cantidad");
        var cantidad = parseInt(inputCantidad.value);

        if (cantidad>=100) {
        inputCantidad.value = cantidad - parseInt(100);
        }
    }



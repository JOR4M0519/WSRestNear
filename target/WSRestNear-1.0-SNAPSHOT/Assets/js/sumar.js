document.getElementById('sumar').addEventListener('click', sumar);
document.getElementById('restar').addEventListener('click', restar);
document.getElementById('sumarLikes').addEventListener('click', sumar);
document.getElementById('restarLikes').addEventListener('click', restar);

    function sumar(){
        var inputCantidad = document.getElementById("cantidad");
        var cantidad = inputCantidad.value;
        inputCantidad.value = parseInt(cantidad) + parseInt(100);
    }

    function restar(){

        var inputCantidad = document.getElementById("cantidad");
        var cantidad = parseInt(inputCantidad.value);

        if (cantidad>=100) {
        inputCantidad.value = cantidad - parseInt(100);
        }
    }

    function sumarLikes(){
        var inputCantidad = document.getElementById("cantidadLikes");
        var cantidad = inputCantidad.value;
        document.
        inputCantidad.value = parseInt(cantidad) + parseInt(1);
    }

    function restarLikes(){

        var inputCantidad = document.getElementById("cantidadLikes");
        var cantidad = parseInt(inputCantidad.value);

        if (cantidad>=1) {
            inputCantidad.value = cantidad - parseInt(1);
        }
    }


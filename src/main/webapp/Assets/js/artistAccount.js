const changeUserPassword = async () => {

    var oldPassword = document.getElementById('oldPassword').value;
    var newPassword = document.getElementById('newPassword').value;

    let response1 = await fetch(`./api/users/${localStorage.getItem('username')}`);
    let result1 = await response1.json();

    if (result1.password == oldPassword) {

        const username = JSON.stringify({ "username": localStorage.getItem('username').toString(), "password": newPassword, "name": "null", "lastname": "null", "role": "null", "profileImage": "null", "description": "null" });

        let response = await fetch(`./api/users/password`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: username

        });
        let result = await response.json();
        console.log(result)
        Swal.fire('Cambio Exitoso!').then((result) => {
            if (result.isConfirmed) {
                location.reload();
            }
        })

    } else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'No Coincide la Contraseña, Intente de Nuevo!',
        });
    }

}

document.getElementById('updatePasswordForm').addEventListener('click', function () {

    document.getElementById('newPasswordDiv').innerHTML = `
    
    <table style="margin-bottom: 3%; border-collapse: separate; border-spacing: 20px 15px;">
                <tbody>
                    <tr>
                        <td><span>Anterior Contraseña:</span></td>
                        <td><input id="oldPassword" type="password"></td>
                    </tr>
                    
                    <tr>
                        <td><span>Nueva Contraseña:</span></td>
                        <td><input id="newPassword" type="password"></td>
                    </tr>
                    <tr>
                        <td><input type="submit" id="updatePassword" onClick="changeUserPassword()" class="btn btn-primary; btn-box-sign-up" value="Confirmar">
</td>
                        
                    </tr>
                </tbody>
            </table>
            `;
});



function chargeBtn() {
    document.getElementById('btnUpdateFoto').style.visibility = "visible";
}

$('#edit').click(

    function(){

        var lastname = "";
        var name = "";
        var description = "";

        if (document.getElementById('btnStatus').value == "edit") {
            document.getElementById('edit').innerHTML = `<i id="logoSave" style="color: #BA2737;" class='bx bxs-save'></i>`;
            document.getElementById('resumeDescripcionPersonal').contentEditable = "true";
            document.getElementById('lastname').contentEditable = "true";
            document.getElementById('name').contentEditable = "true";
            document.getElementById('profileImage').className = "profileImageEdit";
            document.getElementById('middle').innerHTML = `<div class="textImg">Editar Foto</div>`;
            document.getElementById('file-input').disabled = false;
            document.getElementById('btnStatus').value = "save";

            lastname = document.getElementById('lastname').innerHTML;
            name = document.getElementById('name').innerHTML;
            description = document.getElementById('resumeDescripcionPersonal').innerHTML;
        } else {

            document.getElementById('edit').innerHTML = `<i id="logoEdit" style="color: #BA2737;" class='bx bxs-edit-alt'></i>`;
            document.getElementById('resumeDescripcionPersonal').contentEditable = "false";
            document.getElementById('lastname').contentEditable = "false";
            document.getElementById('name').contentEditable = "false";
            document.getElementById('role').contentEditable = "false";
            document.getElementById('username').contentEditable = "false";
            document.getElementById('profileImage').className = "profileImage";
            document.getElementById('middle').innerHTML = "";
            document.getElementById('file-input').disabled = true;
            document.getElementById('btnUpdateFoto').style.visibility = "hidden";
            document.getElementById('btnStatus').value = "edit";

            if (

                lastname != document.getElementById('lastname').innerHTML ||
                name != document.getElementById('name').innerHTML ||
                description != document.getElementById('resumeDescripcionPersonal').innerHTML

                
            ) {

                
                funcion();
                
            }
        }

    }

);


const funcion = async()=>{
   
   
    var lastname = document.getElementById('lastname').innerHTML;
    var name = document.getElementById('name').innerHTML;
    var description = document.getElementById('resumeDescripcionPersonal').innerHTML;

    const username = JSON.stringify({ "username": localStorage.getItem('username').toString(), "password": "null", "name": name, "lastname": lastname, "role": "null", "profileImage": "null", "description": description });


                let response = await fetch(`./api/users/update`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: username

                });
                let result = await response.json();
                console.log(result)
                Swal.fire('Cambio Exitoso!').then((result) => {
                    if (result.isConfirmed) {
                        location.reload();
                    }
                })
}

$(document).ready(function () {

    $(document).on('change', 'input[type=file]', function (e) {
        var TmpPath = URL.createObjectURL(e.target.files[0]);
        $('span').html(TmpPath);
        $('#profileImage').attr('src', TmpPath);
    });

});

const form = document.querySelector("form");

form.onsubmit = async (e) => {
    e.preventDefault();


    let fileName = document.getElementById("fileName").value;


    try {
        await fetch("./api/users/photo", {
            method: 'PUT',
            body: new FormData(form)

        });


    } catch (err) {
        console.log(err);

    }

}


const getDataAccount = async () => {



    var divFullname = document.getElementById("text-icono");
    var divName = document.getElementById("name");
    var divLastname = document.getElementById("lastname");
    var divUsername = document.getElementById("username");
    var divRole = document.getElementById("role");
    // var divListFullname = document.getElementById("listFullName");
    // var divListRole = document.getElementById("listRole");
    var inputFcoins = document.getElementById("amountFcoins");
    var imgProfileImage = document.getElementById("profileImage");





    if (localStorage.getItem("username") != null || localStorage.getItem("username") != undefined) {

        let response = await fetch(`./api/users/${localStorage.getItem("username")}?role=${localStorage.getItem("role")}`);
        let result = await response.json();

        divFullname.innerHTML = `${result.name} ${result.lastname}`;
        divName.innerHTML = `${result.name}`;
        divLastname.innerHTML = `${result.lastname}`;
        divUsername.innerHTML = `${result.username}`;
        divRole.innerHTML = `${result.role}`;
        // divListRole.innerHTML = `${result.role}`;
        imgProfileImage.src = "profileImages/" + result.profileImage;
        document.getElementById('fileName').value = `${result.profileImage}`;

        if (localStorage.getItem("role") == "Comprador") {

            let response2 = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins`);
            let result2 = await response2.json();
            inputFcoins.placeholder = "$" + new Intl.NumberFormat().format(result2.fcoins.toString());
        } else {
            var textDescription = document.getElementById("resumeDescripcionPersonal");
            textDescription.innerHTML = `${result.description}`;

        }
    }


};

// const editUser = async () => {
//     if (localStorage.getItem("role") == "Artista") {

//         var btnEditDescription = document.getElementById("editDescription");

//         btnEditDescription.addEventListener("click", async () => {
//             var description = document.getElementById("resumeDescripcionPersonal");
//             description = description.textContent;


//             await fetch(`./api/users/${localStorage.getItem("username")}/edit`, {
//                 method: "PUT",
//                 headers: {
//                     "Content-Type": "application/json",
//                 },
//                 body: description

//             });

//         })
//     }
// }

// document.getElementById("btnlog_out").addEventListener('click', logout);

function logout() {
    localStorage.removeItem("username");
    localStorage.removeItem("role");
    window.location.href = "./index.html";
}





window.addEventListener("DOMContentLoaded", getDataAccount());
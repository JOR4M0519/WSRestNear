<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Near</title>
    <link rel="shortcut icon" href="Assets/img/logo.ico" />
      <!-- BOOTSTRAP CSS -->
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
      integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
      integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
      <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Abel&display=swap" rel="stylesheet">
    <!-- CUSTOM CSS -->
    <link rel="stylesheet" href="Assets/Styles/login.css">
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

<div class="login-page-wrapper">

    <div class="contenedor-login">
    <a href="index.jsp"><img src="Assets\img\LogoLogin.png" width="35%"></a>
        
    <div class="contenedor-form-login">  
        <h1 style="margin-bottom: 15%; text-align: center;">Inicia Sesión</h1>
        <form action="./login" method="post">
          
        <div class="form-group" >
          <span for="name" >Correo:</span>
          <input type="email" class="form-control" id="username" name="username" required>
        </div>  
          
        <div class="form-group">
          <span for="name" >Contraseña:</span>
          <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <div class="form-group" >
            <label for="roleInput">Rol</label>
            <select name = "role" class="form-control" id="roleInput" aria-describedby="role" required>
              <option>Artista</option>
              <option>Comprador</option>
            </select>
          </div>
  
        <button type="submit" class="btn btn-primary; btn-box-sign-up" style="margin-left: 35%;" >Ingresar</button>
      </form>
    </div>
</div>
</div>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="Assets/js/sumarLikes.js"></script>
<link rel="stylesheet" href="alert/dist/sweetalert.css">
<script type="text/javascript">

    var status = document.getElementById("status").value;
    if(status=="failed"){

        swal({
            title: "Usuario Incorrecto!",
            text: "Intente Nuevamente.",
            imageUrl: '.Assets/img/error.png'
        });
    }

</script>
</body>
</html>
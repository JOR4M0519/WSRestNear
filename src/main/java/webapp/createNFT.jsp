<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Crear</title>
    <link rel="shortcut icon" href="Assets/img/logo.ico">
      <!-- BOOTSTRAP CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
  integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<!-- FONT AWESOME -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
  integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Abel&display=swap" rel="stylesheet">
<!-- CUSTOM CSS -->
<link rel="stylesheet" href="Assets/Styles/main.css">

</head>
<body style="background-color: #BA2737;">
  <div >

    <div style="float: left;"><img src="Assets/img/createNFT.png" width="103.1%"></div>
    

    <div class="box-logoc">
    <a href="index.jsp" ><img style="height: 15vh;" src="Assets/img/logoNear2.png"  ></a>
    </div>
    
    <div class="box-create">  
      <h2 >Crea tu NFT</h2>

      <form action="./uploadNft" method="post" enctype="multipart/form-data" >
      <% 
        String author = request.getParameter("username");
      %>
        
      <div class="form-group" >
        <label for="name" >Author:</label>
        <input class="form-control" style="pointer-events:none;" id="name" name="author" value="<%=author %>">
      </div>  
        
      <div class="form-group" >
        <label for="name" >Titulo:</label>
        <input class="form-control" id="name" placeholder="Titulo" name="titulo" required>
      </div>

      <div class="form-group" style="font-family: 'Abel', sans-serif;">
        <label for="name">Precio:</label>
        <input class="form-control" id="name" placeholder="Precio" name="precio" required>
      </div>

      <div class="form-group">
        <label for="name">Subir NFT</label>
      </div>

     <div class="custom-file">
        <label>Subir archivo</label>
        <input type="file" class="custom-file-input" id="customFile" name="file" >
        <label class="custom-file-label" style="font-family: 'Abel', sans-serif;" for="customFile" >Elegir Archivo</label>
      </div>
          <input type="hidden" name="username" value="<%=author%>" >
      <button type="submit" class="btn btn-primary; btn-box-sign-up" >Subir NFT</button>
    </form>
     
  </div>
</body>
</html>
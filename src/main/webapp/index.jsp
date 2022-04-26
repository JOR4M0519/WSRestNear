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
  <!-- FONT AWESOME -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
        integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
  <!-- CUSTOM CSS -->
  <link rel="stylesheet" href="Assets/Styles/main.css">

</head>

<body>
<input type="hidden" id="username" value="<%= request.getParameter("username")%>">
<%
  String data ="";
  String name ="";
  String role="";
  String fcoinsButton="";
  String fcoins = "";
  String percent ="85%";


  if( request.getAttribute("name") != null) {
    name = String.valueOf(request.getAttribute("name"));
    role = String.valueOf(request.getAttribute("role"));
    fcoins = String.valueOf(request.getAttribute("fcoins"));

    if (role.equals("Comprador")) {

      percent = "79%";
      fcoinsButton = "<div class=\"dropdown show\" style=\"float: left;\"><a class=\"btn btn-secondary\" id=\"dropdown\"  href=\"#\" role=\"button\"  aria-haspopup=\"true\" aria-expanded=\"false\"><span  id=\"fcoins\"> FCoins: $" + fcoins + " </span></a></div>";
    }
    data = "<form action=\"./account\" method=\"post\" name=\"myaccount\"> <input type=\"hidden\" name=\"role\" value=\""+role+"\"> <input type=\"hidden\" id=\"usernameData\" name=\"usernameData\"> <input class=\"dropdown-item\" id=\"dropdown-item\" type=\"submit\" value=\"Mi cuenta\"> </form> <form action=\"./account\" method=\"get\"> <input type=\"hidden\" name=\"usernameData\"> <input class=\"dropdown-item \" id=\"dropdown-item\" type=\"submit\" value=\"Salir\"> </form>";
  }else{
    name ="Mi Cuenta";
    data = "<a class=\"dropdown-item \" id=\"dropdown-item\" href=\"./login.jsp\"> Iniciar Sesión </a><a class=\"dropdown-item\" id=\"dropdown-item\" href=\"./sign_up.jsp\"> Crear cuenta </a>";
  }
%>
<!-- NAVIGATION -->
<div class=" fixed-top" style="background-color: #BA2737 ;">

  <div style="display: flex; margin-left: <%=percent%>; padding-bottom: 0.5%; padding-top: 0.2%;">

    <%=fcoinsButton%>
    <div class="dropdown show" style="float: left;">
      <a class="btn btn-secondary dropdown-toggle" id="dropdown"  href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
          <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
          <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
        </svg>
        <span  id="nameAccount"> <%= name %> </span>
      </a>


      <div class="dropdown-menu" id="dropdown-menu" aria-labelledby="dropdownMenuLink">
        <%=data%>
      </div>
    </div>

  </div>


  <nav class="navbar" style=" display: inline-block; width: 100%; background-color: #BA2737;" >
    <div class="input-group">

      <a class="navbar-brand" href="#">
        <img src="Assets/img/logoNear2.png" class="logo">
      </a>

      <input class="container-input-search" type="search" id="form1" class="form-control" placeholder="Buscar arte" >
      <button type="button" style="background-color: #ffffff2c" class="btn btn-primary" >
        <i class="fas fa-search"></i>
      </button>

      <div class="navbar navbar-expand-lg" id="navbarNav">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a id="nav-a" class="nav-link" href="#info">Info</a>
          </li>
          <li class="nav-item">
            <a id="nav-a" class="nav-link" href="#Artist">Artistas</a>
          </li>
          <li class="nav-item">
            <a id="nav-a"  class="nav-link" href="#Contact">Contáctenos</a>
          </li>
        </ul>
      </div>

  </nav>
</div>

<!-- HEADER -->
<header class="main-header" style="margin-top: 7%;">

  <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
      <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
      <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
      <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img class="d-block w-100" src="Assets/img/Banner_Index2.png" alt="First slide">
      </div>
      <div class="carousel-item">
        <img class="d-block w-100" src="Assets/img/Banner_Index.png" alt="Second slide">
      </div>
      <div class="carousel-item">
        <img class="d-block w-100" src="Assets/img/banner_index3.png" alt="Third slide">
      </div>
    </div>
    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>



</header>

<!-- FEATURES -->

<section class="py-5">
  <div class="container">
    <div class="row">
      <div class="col-md-3">
        <div class="card text-center border-primary">
          <div class="card-body">
            <table>
              <tr>
                <td>
                  <img
                          src="https://http2.mlstatic.com/resources/frontend/homes-korriban/assets/images/payments/credit-card.svg"
                          alt="Metodos de pago">
                </td>
                <td>
                  <h6>Paga a cuotas</h6>
                  <p class="tarjetas">
                    Ver más
                  </p>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-center border-primary">
          <div class="card-body text-center">
            <table>
              <tr>
                <td>
                  <img src="Assets/img/cryptos.png" alt="Metodos de pago" style="width:30px; margin: 10px;">
                </td>
                <td>
                  <h6>Paga con Cryptos</h6>
                  <p class="tarjetas">
                    Ver más
                  </p>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-center border-primary">
          <div class="card-body">
            <table>
              <tr>
                <td>
                  <img
                          src="https://http2.mlstatic.com/resources/frontend/homes-korriban/assets/images/payments/transfer.svg"
                          alt="Metodos de pago">
                </td>
                <td>
                  <h6 class="payment">Paga en bancos</h6>
                  <p class="tarjetas">
                    Ver más
                  </p>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card text-center border-primary">
          <div class="card-body text-center">
            <table>
              <tr>
                <td>
                  <img
                          src="https://http2.mlstatic.com/resources/frontend/homes-korriban/assets/images/payments/view-more.svg"
                          alt="Metodos de pago">
                </td>
                <td>
                  <h6>Otros métodos</h6>
                  <p class="tarjetas">
                    Ver más
                  </p>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- ABOUT -->
<section class="m5 text-center " id="info" style="background-color: #e4e9f77d">
  <div class="container">
    <div class="row">
      <div class="m-5">
        <h3>¿Que son los NFT's?</h3>
        <p>
          Los Tokens no fungibles (NFT) por sus siglas en ingles, son tokens criptográficos que representa algo único.
          Los tókenes no fungibles no son, por tanto, mutuamente intercambiables.
        </p>
      </div>
    </div>
  </div>
</section>


<section class="py-5" style="background-color: #E4E9F7">
  <div class="cards">
    <p id="ofertas" class="card_tittle">Catálogo:</p>
    <!--Guia js-->
    <div class="card-group contenedor-social" id="socialcard">
      <section class="py-5">

        <div class="album py-5">
          <div class="container">

            <div class="row" id="card">

            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</section>


<!-- TEAM -->
<section class="text-center team">
  <div class="container p-5">
    <h1 class="text-center text-white" id="Artist">Artistas</h1>
    <p class="text-white">
      Artistas del momento
    </p>
    <div class="row">
      <!-- USER TEAM -->
      <div class="col-lg-3">
        <div class="card">
          <div class="card-body">
            <img src="https://upload.wikimedia.org/wikipedia/commons/d/d0/Mike_Winkelmann.png"
                 class="img-fluid rounded-circle w-50">
            <h3>Mike Winkelmann</h3>
            <p>
              Artista visual, videojockey, diseñador gráfico, artista y NFT artist
            </p>
            <div class="d-flex flex-row justify-content-center">
            </div>
          </div>
        </div>
      </div>

      <div class="col-lg-3">
        <div class="card">
          <div class="card-body">
            <img
                    src="https://yt3.ggpht.com/ytc/AKedOLRYkj_Jh5coL9ljmJgpPudg6g4UzsjQPeQ1jc7L2w=s900-c-k-c0x00ffffff-no-rj"
                    class="img-fluid rounded-circle w-50">
            <h3>3LAU</h3>
            <p>
              DJ, Productor musical y NFT artist.
            </p>
          </div>
        </div>
      </div>

      <div class="col-lg-3">
        <div class="card">
          <div class="card-body">
            <img src="https://miro.medium.com/max/1200/1*zfkFCW52ity5kH9SeKd2XQ.jpeg"
                 class="img-fluid rounded-circle w-50">
            <h3>José Delbo</h3>
            <p>
              Artista de comics y NFT artist
            </p>
          </div>
        </div>
      </div>

      <div class="col-lg-3">
        <div class="card">
          <div class="card-body">
            <img
                    src="https://images.squarespace-cdn.com/content/v1/53ad9fcae4b0f941aa4e511f/1622666810733-8XLUH8KEQ13Y6IXNK73A/Sarah+Zucker+2021+headshot.jpg"
                    class="img-fluid rounded-circle w-50">
            <h3>Sarah Zucker</h3>
            <p>
              Artista visual, diseñadora grafica, artista digital y NFT artist.
            </p>
          </div>
        </div>
      </div>

    </div>
  </div>
</section>

<!-- CONTACT -->
<section class="bg-light py-5">
  <div class="container">
    <div class="row">
      <div class="col-lg-9" id="Contact">
        <h3>Contáctanos</h3>
        <p>
          ¿Tienes alguna pregunta, sugerencia o queja? <br>
          Consígnala aquí nosotros estaremos atendiendo tu reclamo.
        </p>
        <form name="hi" action="">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <i class="fas fa-user input-group-text"></i>
            </div>
            <input type="text" class="form-control"  name="holaa" placeholder="Nombre" aria-label="Username"
                   aria-describedby="basic-addon1">
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <i class="fas fa-envelope input-group-text"></i>
            </div>
            <input type="text" class="form-control" placeholder="Correo" aria-label="Username"
                   aria-describedby="basic-addon1">
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <i class="fas fa-pencil-alt input-group-text"></i>
            </div>
            <textarea name="" cols="30" rows="10" placeholder="Mensaje" class="form-control"></textarea>
          </div>
          <button type="submit" class="btn btn-primary btn-block">Enviar</button>
        </form>
      </div>
      <div class="col-lg-3 align-self-center">
        <a href="#">
          <img src="Assets/img/logoNear1.png" style=" width: 140%;">
        </a>
      </div>
    </div>
  </div>
</section>

<footer>
  <div class="container p-3">
    <div class="row text-center text-white">
      <div class="col ml-auto">
        <p>Copyright &copy; 2022 <br>
          Kevin García <br>
          Laura Mateus <br>
          Santiago Prieto <br>
          Jeanpier Ramos
        </p>
      </div>
    </div>
  </div>
</footer>

<script type="text/javascript">

  var username = document.getElementById("username").value;
  localStorage.setItem('username', username);

  var Myelement = document.forms['myaccount']['usernameData'];
  console.log(Myelement.value);
  var user = localStorage.getItem('username');
  Myelement.setAttribute('value',user);
  console.log(Myelement.value);

</script>

<!-- BOOTSTRAP SCRIPTS -->
<script src="Assets/js/generalCatalogue.js"></script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>
<script src="Assets/js/sumarLikes.js"></script>
</body>

</html>
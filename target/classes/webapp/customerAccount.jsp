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

    <!-- Boxicons CDN Link -->
    <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%
    String username = request.getParameter("usernameData");
    String name = String.valueOf(request.getAttribute("name"));
    String lastname = String.valueOf(request.getAttribute("lastname"));
    String role = String.valueOf(request.getAttribute("role"));
    String FCoins = String.valueOf(request.getAttribute("FCoins"));
    String password = String.valueOf(request.getAttribute("password"));

    String data = "<input type=\"hidden\" name=\"role\" value=\""+role+"\"> <input type=\"hidden\" id=\"username\"name=\"username\" value=\""+username+"\"> <input type=\"hidden\" id=\"password\"name=\"password\" value=\""+password+"\">";
%>


<div class="sidebar">
    <div class="logo-details">
        <form action="./login" method="post">
            <%=data%>
            <button type="submit" style="border: none; background: transparent; border-color: transparent; outline: none">
                <img class="logo icon" src="Assets\img\logoNear3.png">
            </button>
            <i class='bx bx-menu' id="btn" ></i>
        </form>
    </div>
    <ul class="nav-list">
        <li>
            <i class='bx bx-search' ></i>
            <input type="text" placeholder="Buscar...">
            <span class="tooltip">Buscar</span>
        </li>
        <li>
            <a href="#Cuenta">
                <i class='bx bx-user' ></i>
                <span class="links_name">Cuenta</span>
            </a>
            <span class="tooltip">Cuenta</span>
        </li>
        <li>
            <a href="#FCoins">
                <i class='bx bx-coin'></i>
                <span class="links_name">FCoins</span>
            </a>
            <span class="tooltip">FCoins</span>
        </li>
        <li>
            <span class="tooltip">Files</span>
        </li>
        <li>
            <a href="#Compras">
                <i class='bx bx-cart-alt' ></i>
                <span class="links_name">Compras</span>
            </a>
            <span class="tooltip">Compras</span>
        </li>
        <li>
            <a href="#Favoritos">
                <i class='bx bx-heart' ></i>
                <span class="links_name">Favoritos</span>
            </a>
            <span class="tooltip">Favoritos</span>
        </li>
        <li>
            <a href="#Configuraciones">
                <i class='bx bx-cog' ></i>
                <span class="links_name">Configuraciones</span>
            </a>
            <span class="tooltip">Configuraciones</span>
        </li>
        <li class="profile">
            <div class="profile-details">
                <!--<img src="profile.jpg" alt="profileImg">-->
                <div class="name_job">
                    <div class="name"><%=name+" "+lastname%></div>
                    <div class="job"><%=role%></div>
                </div>
            </div>
            <a href="index.jsp"><i class='bx bx-log-out' id="log_out" ></i></a>
        </li>
    </ul>
</div>

<section class="home-section">
    <section class="contenedor" id="Cuenta">

        <h1 class="text">Tu cuenta</h1>
        <div class="info">

            <section>
                <figure>
                    <img id="icon" src="Assets\img\avatar_driver_people_person_profile_user_icon_123374.png">
                </figure>
                <div id="text-icono"><%=name+" "+lastname%></div>
            </section>

            <section id="informacion-personal">

                <div id="Derecho">

                    <div class="titulos-info">Nombre</div>
                    <div class="conten-info"><%=name%></div>

                    <div class="titulos-info" >Correo</div>
                    <div class="conten-info" id="email"><%=username%></div>

                </div>

                <div id="izquierdo">

                    <div class="titulos-info" >Apellido</div>
                    <div class="conten-info" style="margin-bottom: 8.7%;"><%=lastname%></div>

                    <div class="titulos-info" >Rol</div>
                    <div class="conten-info"><%=role%></div>

                </div>

            </section>

        </div>

    </section>
    <section class="contenedor" id="FCoins">
        <h1 class="text">Fcoins</h1>
        <div class="info">
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm" id="fcoins">
                    <div class="imgBx">
                        <img class="bd-placeholder-img card-img-top" width="100%" height="100%" src="Assets/img/1494.jpg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"/></img>
                    </div>
                    <div class="coin">
                        <img src="Assets/img/Fcoins Icon2.png" alt="" style="width: 40%;">
                    </div>
                    <div class="card-body">
                        <h3 class="card-text">Recarga Fcoins!</h3>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="<%=FCoins%>" aria-label="Recipient's username" aria-describedby="basic-addon2" disabled>
                            <div class="input-group-append">
                                <span class="input-group-text" id="basic-addon2">Fcoins</span>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">$</span>
                            </div>
                            <form action="./money" method="post">
                                <input type="hidden" name="username" value="<%=username%>">
                                <input type="hidden" name="role" value="Comprador">
                                <input type="text" id="cantidad" name="cantidad" class="form-control" value="0">
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="btn-group btns">
                                <button type="button" id="sumar" class="btn btn-sm btn-outline-secondary"><i class="fas fa-plus"></i></button>
                                <button type="button" id="restar" class="btn btn-sm btn-outline-secondary"><i class="fas fa-minus"></i></button>
                                <input style="float: right" type="submit" value="Cargar FCoins">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>

    <section class="contenedor" id="Compras">

        <h1 class="text">Compras</h1>
        <div class="info">No has realizado compras!</div>

    </section>

    <section class="contenedor" id="Favoritos">

        <h1 class="text">Favoritos</h1>
        <div class="info">Pronto Habilitaresmos Esta Función!</div>
        <div class="info" style="width: 100%; height: 100%;"> </div>

    </section>

    <section class="contenedor" id="Configuraciones">

        <h1 class="text">Configuraciones</h1>
        <div class="info">Pronto Habilitaresmos Esta Función!</div>
        <div class="info" style="width: 100%; height: 100%;"> </div>



    </section>

    <section class="contenedor" >
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
    </section>
</section>

<script src="Assets/js/sumar.js"></script>
<script src="Assets/js/account.js"></script>
<script src="Assets/js/functions.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>


<script src="Assets/js/generalCatalogue.js"></script>
<script src="Assets/js/account.js"></script>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <title>AgenHotel</title>
</head>

<body>
    <div id="containner">
   
            <div id="Div_Tp">
                
                <h1>AgenHotel</h1>

                <nav>
                    <a href="#">Início</a>
                    <a href="${pageContext.request.contextPath}/hospedes">Hóspedes</a>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                    <a href="${pageContext.request.contextPath}/cadastro">Cadastro</a>
                </nav>

        </div>

        <main>

            <span class="destaque">HOSPEDAGEM INTELIGENTE</span>
            <h2>Sua estadia começa aqui.</h2>

         <p>
            Organize hóspedes e reservas em uma experiência simples, segura e moderna.
         </p>

         <a class="botao" href="${pageContext.request.contextPath}/login">Acessar o sistema</a>

         </main>

        <footer>
            <p>&copy; 2026 AgenHotel</p>
        </footer>

    </div>

</body>

</html>

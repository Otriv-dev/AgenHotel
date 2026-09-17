<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - AgenHotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<main class="acesso">
    <section class="apresentacao">
        <a class="marca" href="${pageContext.request.contextPath}/">AgenHotel</a>
        <span class="etiqueta">NOVO ACESSO</span>
        <h1>Faça parte da equipe.</h1>
        <p>Crie seu acesso para utilizar as ferramentas internas do AgenHotel.</p>
    </section>

    <section class="cartao">
        <h2>Criar conta</h2>
        <p class="subtitulo">Preencha seus dados de acesso.</p>
        <c:if test="${not empty erro}"><div class="aviso erro"><c:out value="${erro}" /></div></c:if>

        <form action="${pageContext.request.contextPath}/cadastro" method="post">
            <div class="form-group">
                <label for="nome">Nome completo</label>
                <input type="text" id="nome" name="nome" value="${usuario.nome}" required>
            </div>
            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" value="${usuario.email}" required>
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" minlength="6" required>
            </div>
            <button type="submit">Finalizar cadastro</button>
        </form>
        <a class="link-cadastro" href="${pageContext.request.contextPath}/login">Já tenho uma conta</a>
    </section>
</main>
</body>
</html>

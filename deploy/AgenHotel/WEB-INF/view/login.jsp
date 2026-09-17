<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - AgenHotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<main class="acesso">
    <section class="apresentacao">
        <a class="marca" href="${pageContext.request.contextPath}/">AgenHotel</a>
        <span class="etiqueta">ACESSO SEGURO</span>
        <h1>Bem-vindo de volta.</h1>
        <p>Entre para gerenciar os hóspedes e manter a operação do hotel organizada.</p>
    </section>

    <section class="cartao">
        <h2>Entrar</h2>
        <p class="subtitulo">Use seu e-mail e sua senha.</p>

        <c:if test="${not empty mensagem}"><div class="aviso sucesso"><c:out value="${mensagem}" /></div></c:if>
        <c:if test="${not empty erro}"><div class="aviso erro"><c:out value="${erro}" /></div></c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" value="${email}" placeholder="voce@email.com" required autofocus>
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" placeholder="Sua senha" required>
            </div>
            <button type="submit">Acessar painel</button>
        </form>
        <p class="rodape-form">Ainda não possui uma conta?</p>
        <a class="link-cadastro" href="${pageContext.request.contextPath}/cadastro">Criar cadastro</a>
    </section>
</main>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de hospede - AgenHotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hospedes.css">
</head>
<body>
<header class="topo">
    <div class="topo-conteudo">
        <h1>AgenHotel</h1>
        <nav>
            <span><c:out value="${sessionScope.usuarioLogado.nome}" /></span>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </nav>
    </div>
</header>
<main class="conteudo">
    <h2>${hospede.id == 0 ? 'Novo hospede' : 'Editar hospede'}</h2>
    <c:if test="${not empty erro}"><p class="erro"><c:out value="${erro}" /></p></c:if>
    <form method="post" action="${pageContext.request.contextPath}/hospedes">
        <input type="hidden" name="id" value="${hospede.id == 0 ? '' : hospede.id}">
        <div class="campo">
            <label for="nome">Nome completo</label>
            <input type="text" id="nome" name="nome" value="${hospede.nome}" required>
        </div>
        <div class="campo">
            <label for="email">E-mail</label>
            <input type="email" id="email" name="email" value="${hospede.email}" required>
        </div>
        <div class="campo">
            <label for="telefone">Telefone</label>
            <input type="text" id="telefone" name="telefone" value="${hospede.telefone}">
        </div>
        <button type="submit">Salvar</button>
        <a class="botao botao-secundario" href="${pageContext.request.contextPath}/hospedes">Cancelar</a>
    </form>
</main>
</body>
</html>

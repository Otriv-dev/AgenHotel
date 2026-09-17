<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hospedes - AgenHotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hospedes.css">
</head>
<body>
<header class="topo">
    <div class="topo-conteudo">
        <h1>AgenHotel</h1>
        <nav>
            <span>Olá, <c:out value="${sessionScope.usuarioLogado.nome}" /></span>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </nav>
    </div>
</header>
<main class="conteudo">
    <div class="titulo-linha">
        <h2>Hospedes cadastrados</h2>
        <a class="botao" href="${pageContext.request.contextPath}/hospedes?acao=novo">Novo hospede</a>
    </div>
    <c:choose>
        <c:when test="${empty hospedes}"><p>Nenhum hospede cadastrado.</p></c:when>
        <c:otherwise>
            <table>
                <thead><tr><th>Nome</th><th>E-mail</th><th>Telefone</th><th>Acoes</th></tr></thead>
                <tbody>
                <c:forEach var="hospede" items="${hospedes}">
                    <tr>
                        <td><c:out value="${hospede.nome}" /></td>
                        <td><c:out value="${hospede.email}" /></td>
                        <td><c:out value="${hospede.telefone}" /></td>
                        <td class="acoes">
                            <a class="botao botao-secundario" href="${pageContext.request.contextPath}/hospedes?acao=editar&id=${hospede.id}">Editar</a>
                            <a class="botao botao-perigo" href="${pageContext.request.contextPath}/hospedes?acao=excluir&id=${hospede.id}" onclick="return confirm('Deseja excluir este hospede?');">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>

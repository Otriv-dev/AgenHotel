# AgenHotel

Projeto da disciplina de Aplicacoes para Internet desenvolvido com Java, Maven,
JSP, Servlet e MySQL. A funcionalidade de hospedes foi organizada no padrao MVC,
com as camadas DAO e Service separadas.

## Estrutura do projeto

- `Model/Hospede.java`: representa os dados do hospede.
- `Dao/HospedeDAO.java`: executa o CRUD no MySQL.
- `Service/HospedeService.java`: concentra as validacoes e regras.
- `Controller/HospedeServlet.java`: recebe as requisicoes e escolhe a view.
- `WEB-INF/view/hospedes`: telas de listagem e formulario.
- `css`: estilos das paginas.

## Como executar

Requisitos: Java 21, Maven e Docker.

1. Gere a aplicacao com `mvn clean package`.
2. Execute `docker compose up --force-recreate`.
3. Abra `http://localhost:8080/AgenHotel/`.

O Maven copia o arquivo `AgenHotel.war` para `deploy`. O Docker monta esse mesmo
arquivo no Tomcat. Essa configuracao evita que uma copia antiga das paginas seja
exibida.

## CRUD demonstrado

Acesse `http://localhost:8080/AgenHotel/hospedes` para cadastrar, listar, editar e
excluir hospedes.

O sistema tambem possui:

- lista de quartos com situacao livre, reservada ou ocupada;
- mapa visual de quartos com acesso direto a nova reserva;
- reservas com bloqueio de conflito de datas por quarto;
- operacoes de check-in, check-out e cancelamento;
- pesquisa de hospedes, quartos e reservas;
- dados ficticios para demonstracao: usuarios, hospedes, quartos e reservas.

## Login

O cadastro cria um usuario no MySQL. Depois do login, o sistema guarda o usuario
na sessao e libera o CRUD de hospedes. Sem uma sessao valida, o filtro redireciona
automaticamente para a tela de login.

Usuario inicial para demonstracao:

- E-mail: `admin@agenhotel.com`
- Senha: `123456`

Outros usuarios ficticios usam a mesma senha:

- `mariana@agenhotel.com`
- `carlos@agenhotel.com`

O banco inicial tambem inclui seis hospedes, cinco quartos e quatro reservas de
exemplo. Assim e possivel testar imediatamente quartos livres, reservados,
ocupados, check-in e check-out.

## Diferenca entre Quartos e Reservas

- **Quartos** e o mapa atual do hotel. Mostra numero, tipo, capacidade, diaria e
  situacao. O botao `Reservar este quarto` abre o formulario com ele selecionado.
- **Reservas** e a agenda de estadias. Nela sao criadas as reservas e realizados
  check-in, check-out e cancelamento.

Um quarto ocupado hoje ainda pode ser reservado para outra data. O sistema usa
as datas de entrada e saida para impedir somente os periodos que se sobrepoem.

Se o banco ja tiver sido criado por uma versao anterior, o novo `init.sql` nao sera
executado novamente pelo MySQL. Durante o desenvolvimento, use
`docker compose down -v` uma unica vez para recriar o banco. Esse comando apaga os
dados que ja estiverem cadastrados.

Se quiser preservar os dados existentes, aplique o script manualmente:

```powershell
Get-Content -Raw .\init.sql | docker exec -i agenhotel-mysql mysql -uagenhotel_user -pagenhotel123 agenhotel
```

## Quando uma alteracao de CSS nao aparecer

Execute novamente `mvn clean package` e recrie o Tomcat com
`docker compose up --force-recreate`. No navegador, confirme que a URL comeca com
`/AgenHotel/`. Nao use `/view/cadastro.jsp` sem o contexto da aplicacao.

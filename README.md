<h1>🎵 SpotiFEI</h1>
SpotiFEI é um projeto desenvolvido por um aluno da FEI (Fundação Educacional Inaciana Padre Sabóia de Medeiros) com o objetivo de simular, de forma didática e simplificada, as funcionalidades básicas do Spotify. A aplicação permite que usuários interajam com suas músicas favoritas através de uma interface personalizada, criada com fins educacionais e voltada para o aprendizado prático de Java e banco de dados.

<h2>🎯 1. Objetivo</h2>
O objetivo principal do SpotiFEI é proporcionar uma experiência prática de desenvolvimento em Java, com foco em programação orientada a objetos (POO), uso do framework gráfico JFrame e integração com um banco de dados relacional (PostgreSQL). A ideia é transformar conceitos teóricos em prática concreta, através de uma aplicação funcional e interativa.

<h2>🚀 2. Funcionalidades</h2>
O projeto conta com diversas funcionalidades, como:

🔐 Autenticação de usuários (login e registro)

❤️ Curtir músicas

➕ Adicionar músicas à playlist

ℹ️ Exibição de informações da faixa (nome, artista, gênero)

🔍 Pesquisa de músicas por nome ou artista

Essas funcionalidades simulam o uso real de uma plataforma de streaming, mesmo que em formato textual.

<h2>🛠️ 3. Tecnologias Utilizadas</h2>
Java: linguagem principal da aplicação.

JFrame: biblioteca gráfica para construção da interface do usuário.

PostgreSQL: banco de dados relacional para persistência de dados.

Apache NetBeans: IDE utilizada no desenvolvimento.

<h2>📦 4. Instalação</h2>
Clone o repositório:

```bash
git clone https://github.com/hNicolini/SpotiFEI.git
```
Importe o projeto na IDE Apache NetBeans.

Certifique-se de que o PostgreSQL está instalado e o banco de dados configurado conforme os scripts do projeto.

Execute a aplicação pela classe principal (View.Login.java).

<h2>📄 5. Licença</h2>
Este projeto está licenciado sob a MIT License.

<h2>🔍 6. Objetivos Aprofundados</h2> <p>O projeto tem como base a ideia de criar uma aplicação intuitiva e funcional, mesmo para usuários sem conhecimento técnico. Ele simula as principais ações de um player de música, com foco na usabilidade.</p> <p>Do ponto de vista técnico, o principal objetivo foi **consolidar o conhecimento em Java orientado a objetos** e **entender o uso de bancos de dados relacionais**. A persistência de dados com PostgreSQL permitiu salvar informações dos usuários mesmo após o fechamento da aplicação, o que torna o projeto mais próximo de um sistema real.</p>

<h2>🧠 7. Metodologia / Desenvolvimento</h2> <p>Para o desenvolvimento do projeto, utilizei a IDE **Apache NetBeans**, que facilitou a criação da interface gráfica com JFrame. Apesar de algumas dificuldades iniciais, como a integração com o GitHub e um design menos intuitivo em comparação ao VS Code, a IDE se mostrou eficiente em detectar e corrigir erros.</p> <p>A arquitetura da aplicação foi baseada no padrão **MVC (Model-View-Controller)**:</p>
Model: representa os dados do sistema e regras de negócio (ex: classes Musica, Usuario, Playlist);

View: é a camada responsável pela interface gráfica;

Controller: gerencia a lógica da aplicação, interligando as ações do usuário à atualização dos dados e da interface.</p>

<p>Esse padrão facilitou a manutenção do código e melhorou a organização do projeto, permitindo separar responsabilidades e evitar códigos repetitivos.</p>
<h2>📚 8. Processo de Aprendizado</h2> <p>O início do projeto foi desafiador, especialmente por ainda estar aprendendo os conceitos de orientação a objetos. Para superar isso, comecei desenvolvendo o sistema todo via **terminal**, sem interface gráfica. Essa abordagem me permitiu focar primeiro na lógica e estrutura de dados.</p> <p>Posteriormente, adaptei o código para incluir a interface com o JFrame, refinando a usabilidade. Durante o processo, me preocupei em nomear as classes e métodos de forma clara e intuitiva, para facilitar a leitura e manutenção por parte de outros desenvolvedores.</p> <p>Entre os maiores aprendizados estão:</p>
O uso de classes reutilizáveis e herança;

Conexão Java com banco de dados PostgreSQL via JDBC;

Separação de responsabilidades com MVC;

Manipulação de eventos gráficos com JFrame;

Controle de versões com Git e GitHub.</p>

<h2>🔧 9. Possíveis Melhorias Futuras</h2>
Adição de faixas de áudio reais usando bibliotecas de reprodução de som;

Melhoria no design da interface com bibliotecas de terceiros (ex: JavaFX);

Implementação de um sistema de recomendações baseado nas músicas curtidas;

Interface de login com recuperação de senha;

Deploy como aplicação web ou mobile em versões futuras.

<h2>💬 10. Considerações Finais</h2> <p>O projeto SpotiFEI foi um grande passo no meu aprendizado em programação, especialmente por unir lógica de programação, banco de dados e interface gráfica em um único sistema funcional. Apesar das dificuldades encontradas no caminho, pude aprender não apenas sobre código, mas sobre **planejamento, organização de software** e a importância da documentação.</p> <p>Esse projeto representa não só uma entrega acadêmica, mas também o início de uma jornada como desenvolvedor. Espero que possa servir de inspiração e aprendizado para outros estudantes que também estão começando.</p>

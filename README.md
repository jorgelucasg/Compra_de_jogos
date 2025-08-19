[README.txt](https://github.com/user-attachments/files/21859416/README.txt)
# Loja de Games em Java

Este projeto é uma aplicação de desktop em Java que simula uma loja de games. Ele permite que um cliente visualize um catálogo de jogos, compre jogos (individualmente, o máximo possível com o saldo atual, ou todos os jogos se houver saldo suficiente), e gerencie seu saldo (adicionar e retirar).

## Funcionalidades

*   **Catálogo de Jogos:** Exibe uma lista de jogos disponíveis com nome, preço, categoria e classificação etária.
*   **Compra de Jogos:**
    *   Comprar um jogo selecionado.
    *   Comprar o maior número possível de jogos com o saldo atual (estratégia gulosa, priorizando os mais baratos).
    *   Comprar todos os jogos disponíveis se o saldo for suficiente.
*   **Gerenciamento de Saldo:**
    *   Adicionar saldo à conta do cliente.
    *   Retirar saldo da conta do cliente.
*   **Remoção de Jogos Comprados:** Permite "remover" um jogo da lista de comprados, adicionando seu valor de volta ao saldo do cliente.
*   **Interface Gráfica (GUI):** Desenvolvida com Swing para uma interação intuitiva.

## Estrutura do Projeto

O projeto é composto por três classes principais:

*   **`Game.java`**: Representa um jogo com atributos como nome, preço, categoria e classificação etária. Inclui métodos para acessar e modificar esses atributos, além de um método para exibir informações formatadas do jogo.
*   **`Cliente.java`**: Representa um cliente da loja com nome, email, saldo e uma lista de jogos comprados. Contém a lógica para comprar jogos, adicionar/retirar saldo e a funcionalidade de "comprar o máximo" de jogos.
*   **`LojaGamesGUI.java`**: A classe principal da aplicação, responsável por construir a interface gráfica do usuário (GUI) usando Swing e integrar as funcionalidades das classes `Game` e `Cliente`.

## Como Compilar e Executar

Para compilar e executar este projeto, você precisará ter o Java Development Kit (JDK) instalado em sua máquina.

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd comprasjogos
    ```

2.  **Compile os arquivos Java:**
    ```bash
    javac comprasjogos/*.java
    ```

3.  **Execute a aplicação GUI:**
    ```bash
    java comprasjogos.LojaGamesGUI
    ```

    Ou, para executar a versão de console com testes básicos:
    ```bash
    java comprasjogos.Main
    ```

## Requisitos

*   Java 8 ou superior

## Exemplo de Uso (GUI)

Ao executar `LojaGamesGUI`, uma janela será exibida com:

*   Informações do cliente (nome e saldo atual).
*   Uma lista de "Games disponíveis" à esquerda.
*   Botões de ação no centro ("Comprar selecionado", "Comprar o máximo", "Remover comprado", "Comprar Todos").
*   Uma lista de "Games comprados" à direita.
*   Campos para adicionar ou retirar saldo.

Você pode interagir com a aplicação selecionando jogos, clicando nos botões de ação e inserindo valores para gerenciar o saldo.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests para melhorias, correções de bugs ou novas funcionalidades.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator; // Importar para ordenação

public class LojaGamesGUI extends JFrame {
    private JList<Game> listaGames;
    private JList<Game> listaComprados;
    private JLabel labelSaldo;
    private Cliente clienteAtual;
    private DefaultListModel<Game> modeloDisponiveis;
    private DefaultListModel<Game> modeloComprados;

    // Componentes para adicionar/retirar saldo
    private JTextField campoValorSaldo;
    private JButton btnAdicionarSaldo;
    private JButton btnRetirarSaldo;

    private static final Locale PT_BR = new Locale("pt", "BR");
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(PT_BR);

    public LojaGamesGUI() {
        // Janela
        setTitle("Loja de Games - Java OOP");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Cliente
        clienteAtual = new Cliente("Lucas", "lucas@example.com", new BigDecimal("200.00"));

        // Componentes
        inicializarComponentes();
        criarGamesIniciais();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Topo: info do cliente e controles de saldo
        JPanel painelTopo = new JPanel(new BorderLayout());

        JPanel painelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JLabel labelCliente = new JLabel("Cliente: " + clienteAtual.getNome());
        labelSaldo = new JLabel("Saldo: " + MOEDA.format(clienteAtual.getSaldo()));
        painelCliente.add(labelCliente);
        painelCliente.add(labelSaldo);
        painelTopo.add(painelCliente, BorderLayout.WEST);

        JPanel painelControleSaldo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        campoValorSaldo = new JTextField(8);
        btnAdicionarSaldo = new JButton("Adicionar Saldo");
        btnRetirarSaldo = new JButton("Retirar Saldo");

        btnAdicionarSaldo.addActionListener(e -> adicionarSaldo());
        btnRetirarSaldo.addActionListener(e -> retirarSaldo());

        painelControleSaldo.add(new JLabel("Valor:"));
        painelControleSaldo.add(campoValorSaldo);
        painelControleSaldo.add(btnAdicionarSaldo);
        painelControleSaldo.add(btnRetirarSaldo);
        painelTopo.add(painelControleSaldo, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // Esquerda: lista de disponíveis
        modeloDisponiveis = new DefaultListModel<>();
        listaGames = new JList<>(modeloDisponiveis);
        listaGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaGames);
        scrollLista.setPreferredSize(new Dimension(380, 420));
        JPanel painelEsquerda = new JPanel(new BorderLayout(5, 5));
        painelEsquerda.add(new JLabel("Games disponíveis:"), BorderLayout.NORTH);
        painelEsquerda.add(scrollLista, BorderLayout.CENTER);
        add(painelEsquerda, BorderLayout.WEST);

        // Centro: botões de ação
        JButton btnComprar = new JButton("Comprar selecionado");
        btnComprar.addActionListener(e -> comprarGameSelecionado());

        JButton btnComprarMax = new JButton("Comprar o máximo");
        btnComprarMax.addActionListener(e -> comprarMaximo());

        JButton btnRemoverComprado = new JButton("Remover comprado");
        btnRemoverComprado.addActionListener(e -> removerGameComprado());

        JButton btnComprarTodos = new JButton("Comprar Todos");
        btnComprarTodos.addActionListener(e -> comprarTodos());

        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.add(Box.createVerticalStrut(60));
        painelCentro.add(btnComprar);
        painelCentro.add(Box.createVerticalStrut(20));
        painelCentro.add(btnComprarMax);
        painelCentro.add(Box.createVerticalStrut(20));
        painelCentro.add(btnRemoverComprado);
        painelCentro.add(Box.createVerticalStrut(20));
        painelCentro.add(btnComprarTodos);
        add(painelCentro, BorderLayout.CENTER);

        // Direita: lista de comprados
        modeloComprados = new DefaultListModel<>();
        listaComprados = new JList<>(modeloComprados);
        JScrollPane scrollComprados = new JScrollPane(listaComprados);
        scrollComprados.setPreferredSize(new Dimension(380, 420));
        JPanel painelDireita = new JPanel(new BorderLayout(5, 5));
        painelDireita.add(new JLabel("Games comprados:"), BorderLayout.NORTH);
        painelDireita.add(scrollComprados, BorderLayout.CENTER);
        add(painelDireita, BorderLayout.EAST);

        // Dica: duplo clique para comprar
        listaGames.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) comprarGameSelecionado();
            }
        });
    }

    private void criarGamesIniciais() {
        List<Game> iniciais = Arrays.asList(
                new Game("Minecraft", 89.90, "Aventura", 10),
                new Game("FIFA 2023", 199.90, "Esporte", 0),
                new Game("The Sims 4", 129.90, "Simulação", 12),
                new Game("Grand Theft Auto V", 149.90, "Ação", 18),
                new Game("Fortnite", 0.0, "Battle Royale", 12),
                new Game("Hades", 79.90, "Roguelike", 14),
                new Game("Stardew Valley", 24.99, "Simulação", 0),
                new Game("Celeste", 36.90, "Plataforma", 10),
                new Game("Among Us", 19.99, "Party", 7),
                new Game("Cyberpunk 2077", 149.99, "RPG", 18),
                new Game("Valorant", 0.00, "FPS", 14),
                new Game("Rocket League", 39.99, "Esporte", 3),
                new Game("Call of Duty", 339.99, "FPS", 18)
        );
        for (Game g : iniciais) {
            modeloDisponiveis.addElement(g);
        }
    }

    private void comprarGameSelecionado() {
        Game gameSelecionado = listaGames.getSelectedValue();
        if (gameSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um game para comprar.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Verifica se o jogo já foi comprado
        if (modeloComprados.contains(gameSelecionado)) {
            JOptionPane.showMessageDialog(this, "Você já possui este jogo.",
                    "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean ok = clienteAtual.comprarGame(gameSelecionado);
        if (ok) {
            modeloComprados.addElement(gameSelecionado);
            modeloDisponiveis.removeElement(gameSelecionado); // Remove dos disponíveis
            atualizarSaldo();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Saldo insuficiente para comprar " + gameSelecionado.getNome() +
                            ". Saldo atual: " + MOEDA.format(clienteAtual.getSaldo()),
                    "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void comprarMaximo() {
        // Cria uma lista temporária dos jogos disponíveis para evitar ConcurrentModificationException
        java.util.List<Game> disponiveisParaCompra = new java.util.ArrayList<>();
        for (int i = 0; i < modeloDisponiveis.size(); i++) {
            disponiveisParaCompra.add(modeloDisponiveis.getElementAt(i));
        }

        // Ordena os jogos disponíveis por preço (do menor para o maior)
        disponiveisParaCompra.sort(Comparator.comparing(Game::getPreco));

        // Tenta comprar o máximo de jogos possível
        for (Game g : disponiveisParaCompra) {
            // Verifica se o jogo ainda está disponível e se o cliente tem saldo
            if (modeloDisponiveis.contains(g) && clienteAtual.getSaldo().compareTo(g.getPreco()) >= 0) {
                boolean comprado = clienteAtual.comprarGame(g);
                if (comprado) {
                    modeloComprados.addElement(g);
                    modeloDisponiveis.removeElement(g);
                }
            }
        }
        atualizarSaldo();
        JOptionPane.showMessageDialog(this, "Tentativa de comprar o máximo de jogos concluída.",
                "Compra Máxima", JOptionPane.INFORMATION_MESSAGE);
    }

    private void comprarTodos() {
        // Cria uma lista temporária dos jogos disponíveis
        java.util.List<Game> todosDisponiveis = new java.util.ArrayList<>();
        for (int i = 0; i < modeloDisponiveis.size(); i++) {
            todosDisponiveis.add(modeloDisponiveis.getElementAt(i));
        }

        BigDecimal custoTotal = BigDecimal.ZERO;
        for (Game g : todosDisponiveis) {
            custoTotal = custoTotal.add(g.getPreco());
        }

        if (clienteAtual.getSaldo().compareTo(custoTotal) < 0) {
            JOptionPane.showMessageDialog(this,
                    "Saldo insuficiente para comprar todos os jogos. Custo total: " + MOEDA.format(custoTotal) +
                            ". Saldo atual: " + MOEDA.format(clienteAtual.getSaldo()),
                    "Saldo Insuficiente", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int jogosComprados = 0;
        for (Game g : todosDisponiveis) {
            // Verifica se o jogo ainda está disponível e se o cliente tem saldo
            if (modeloDisponiveis.contains(g) && clienteAtual.getSaldo().compareTo(g.getPreco()) >= 0) {
                boolean comprado = clienteAtual.comprarGame(g);
                if (comprado) {
                    modeloComprados.addElement(g);
                    modeloDisponiveis.removeElement(g);
                    jogosComprados++;
                }
            }
        }
        atualizarSaldo();
        JOptionPane.showMessageDialog(this, jogosComprados + " jogos foram comprados.",
                "Compra de Todos", JOptionPane.INFORMATION_MESSAGE);
    }


    private void removerGameComprado() {
        Game gameSelecionado = listaComprados.getSelectedValue();
        if (gameSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um game para remover da sua lista de comprados.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lógica para "remover" o jogo (simulando uma devolução ou venda)
        // Neste exemplo, vamos adicionar o valor do jogo de volta ao saldo do cliente
        // e movê-lo de volta para a lista de disponíveis.
        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente remover '" + gameSelecionado.getNome() + "'? O valor será adicionado ao seu saldo.",
                "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            clienteAtual.adicionarSaldo(gameSelecionado.getPreco()); // Adiciona o valor do jogo de volta
            modeloComprados.removeElement(gameSelecionado);
            modeloDisponiveis.addElement(gameSelecionado); // Retorna para a lista de disponíveis
            atualizarSaldo();
            JOptionPane.showMessageDialog(this, gameSelecionado.getNome() + " foi removido e o valor adicionado ao seu saldo.",
                    "Remoção Concluída", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void adicionarSaldo() {
        try {
            BigDecimal valor = new BigDecimal(campoValorSaldo.getText());
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "O valor a adicionar deve ser positivo.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            clienteAtual.adicionarSaldo(valor);
            atualizarSaldo();
            campoValorSaldo.setText(""); // Limpa o campo
            JOptionPane.showMessageDialog(this, MOEDA.format(valor) + " adicionado ao saldo.",
                    "Saldo Atualizado", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor numérico válido.",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retirarSaldo() {
        try {
            BigDecimal valor = new BigDecimal(campoValorSaldo.getText());
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "O valor a retirar deve ser positivo.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (clienteAtual.getSaldo().compareTo(valor) < 0) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente para retirar este valor.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            clienteAtual.retirarSaldo(valor);
            atualizarSaldo();
            campoValorSaldo.setText(""); // Limpa o campo
            JOptionPane.showMessageDialog(this, MOEDA.format(valor) + " retirado do saldo.",
                    "Saldo Atualizado", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor numérico válido.",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarSaldo() {
        labelSaldo.setText("Saldo: " + MOEDA.format(clienteAtual.getSaldo()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LojaGamesGUI::new);
    }
}

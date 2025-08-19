import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Cliente {
    private final String nome;
    private final String email;
    private BigDecimal saldo;
    private final List<Game> gamesComprados;

    private static final Locale PT_BR = new Locale("pt", "BR");
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(PT_BR);

    public Cliente(String nome, String email, BigDecimal saldoInicial) {
        this.nome = nome;
        this.email = email;
        this.saldo = saldoInicial;
        this.gamesComprados = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public BigDecimal getSaldo() { return saldo; }
    public List<Game> getGamesComprados() {
        return Collections.unmodifiableList(gamesComprados);
    }

    public boolean comprarGame(Game game) {
        if (game == null) return false;
        if (saldo.compareTo(game.getPreco()) >= 0) {
            // Verifica se o jogo já foi comprado antes de adicionar
            if (!gamesComprados.contains(game)) {
                gamesComprados.add(game);
                saldo = saldo.subtract(game.getPreco());
                System.out.println(nome + " comprou: " + game.getNome() +
                        " por " + MOEDA.format(game.getPreco()));
                return true;
            } else {
                System.out.println(nome + " já possui: " + game.getNome());
                return false; // Já possui o jogo
            }
        } else {
            System.out.println(nome + " não tem saldo suficiente para: " + game.getNome());
            return false;
        }
    }

    /** Compra o MAIOR número possível de games dado o saldo atual (guloso por preço). */
    public void comprarMaximo(List<Game> disponiveis) {
        if (disponiveis == null || disponiveis.isEmpty()) return;
        List<Game> ordenados = new ArrayList<>(disponiveis);
        // Ordena por preço para a estratégia gulosa (comprar os mais baratos primeiro)
        ordenados.sort(Comparator.comparing(Game::getPreco));

        for (Game g : ordenados) {
            // Verifica se o jogo já foi comprado antes de tentar comprar
            if (!gamesComprados.contains(g)) {
                if (saldo.compareTo(g.getPreco()) >= 0) {
                    comprarGame(g); // Chama o método comprarGame que já atualiza o saldo e a lista
                }
            }
        }
    }

    // Novo método para adicionar saldo
    public void adicionarSaldo(BigDecimal valor) {
        if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0) {
            this.saldo = this.saldo.add(valor);
            System.out.println(nome + " adicionou " + MOEDA.format(valor) + ". Novo saldo: " + MOEDA.format(this.saldo));
        }
    }

    // Novo método para retirar saldo
    public boolean retirarSaldo(BigDecimal valor) {
        if (valor != null && valor.compareTo(BigDecimal.ZERO) > 0) {
            if (this.saldo.compareTo(valor) >= 0) {
                this.saldo = this.saldo.subtract(valor);
                System.out.println(nome + " retirou " + MOEDA.format(valor) + ". Novo saldo: " + MOEDA.format(this.saldo));
                return true;
            } else {
                System.out.println(nome + " não tem saldo suficiente para retirar " + MOEDA.format(valor));
                return false;
            }
        }
        return false;
    }
}

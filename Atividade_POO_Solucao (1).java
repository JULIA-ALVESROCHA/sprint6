import java.util.ArrayList;
import java.util.List;

/**
 * Solução do roteiro GCVA — Loja de Consoles.
 * Partes 1 a 5: encapsulamento, construtores, interface + composição,
 * herança apropriada e polimorfismo/OCP.
 */

// ===================== PARTES 1 e 2 =====================
// Resultado das etapas 1 e 2 sobre a classe original.
// A partir da Parte 3 ela é substituída pelas classes específicas,
// então fica aqui apenas como registro da evolução.
class Console {
    private final String nome;
    private final String tipo;
    private final double preco;

    public Console(String nome, String tipo, double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public double getPreco() { return preco; }
}

// ===================== PARTE 3 — Interface e Composição =====================
interface IConsole {
    void ligar();
    double calcularPreco();
    String getNome();
}

class DadosConsole {
    private final String nome;
    private final double precoBase;

    public DadosConsole(String nome, double precoBase) {
        if (precoBase < 0) {
            throw new IllegalArgumentException("Preço base não pode ser negativo.");
        }
        this.nome = nome;
        this.precoBase = precoBase;
    }

    public String getNome() { return nome; }
    public double getPrecoBase() { return precoBase; }
}

class Nintendo implements IConsole {
    private final DadosConsole dados;

    public Nintendo(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    @Override
    public void ligar() {
        System.out.println("Nintendo ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.10;
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }
}

class Playstation implements IConsole {
    // protected para que PlaystationPortatil consiga acessar (Parte 4).
    protected final DadosConsole dados;

    public Playstation(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    @Override
    public void ligar() {
        System.out.println("Playstation ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.20;
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }
}

// ===================== PARTE 4 — Herança apropriada =====================
class PlaystationPortatil extends Playstation {

    public PlaystationPortatil(String nome, double precoBase) {
        super(nome, precoBase);
    }

    @Override
    public void ligar() {
        System.out.println("Playstation Portátil ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.15;
    }
}

// ===================== PARTE 5 — Extensibilidade =====================
class Xbox implements IConsole {
    private final DadosConsole dados;

    public Xbox(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    @Override
    public void ligar() {
        System.out.println("Xbox ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * 1.18;
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }
}

// ===================== PARTE 5 — Loja polimórfica =====================
class Loja {

    public void venderConsole(IConsole console) {
        console.ligar();
        System.out.printf("%s -> Preço final: R$ %.2f%n",
                console.getNome(), console.calcularPreco());
    }

    public void venderVarios(List<IConsole> consoles) {
        for (IConsole console : consoles) {
            venderConsole(console);
        }
    }

    public double calcularFaturamentoTotal(List<IConsole> consoles) {
        double total = 0;
        for (IConsole console : consoles) {
            total += console.calcularPreco();
        }
        return total;
    }
}

public class Atividade_POO_Solucao {

    public static void main(String[] args) {

        Loja loja = new Loja();

        List<IConsole> consoles = new ArrayList<>();
        consoles.add(new Nintendo("Nintendo Switch", 2000));
        consoles.add(new Playstation("Playstation 5", 3000));
        consoles.add(new PlaystationPortatil("Playstation Portátil", 2500));

        System.out.println("=== Venda inicial ===");
        loja.venderVarios(consoles);
        System.out.printf("Faturamento total: R$ %.2f%n%n",
                loja.calcularFaturamentoTotal(consoles));

        // Nenhuma linha de Loja foi alterada para suportar o Xbox.
        consoles.add(new Xbox("Xbox Series X", 2800));

        System.out.println("=== Venda com o novo console ===");
        loja.venderVarios(consoles);
        System.out.printf("Faturamento total: R$ %.2f%n",
                loja.calcularFaturamentoTotal(consoles));
    }
}

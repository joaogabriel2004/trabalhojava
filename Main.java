import java.util.*;

class Livro {
    private static int proximoId = 1;

    int id;
    String titulo;
    int nota; // de 0 a 5, -1 se não avaliado
    String descricao;
    boolean lido;

    Livro(String titulo) {
        this.id = proximoId++;
        this.titulo = titulo;
        this.nota = -1;
        this.descricao = "";
        this.lido = false;
    }

    void marcarComoLido(int nota, String descricao) {
        this.lido = true;
        this.nota = nota;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s%s", id, titulo,
                lido ? String.format(" | Nota: %d | %s", nota, descricao.isEmpty() ? "Sem descrição" : descricao) : " [Desejo]");
    }
}

class Biblioteca {
    List<Livro> livros = new ArrayList<>();

    boolean tituloExiste(String titulo) {
        return livros.stream().anyMatch(l -> l.titulo.equalsIgnoreCase(titulo));
    }

    void adicionarLivro(String titulo) {
        if (tituloExiste(titulo)) {
            System.out.println("Esse livro já está cadastrado.");
            return;
        }
        livros.add(new Livro(titulo));
    }

    void marcarComoLido(int id, int nota, String descricao) {
        Livro livro = encontrarPorId(id);
        if (livro != null && !livro.lido) {
            livro.marcarComoLido(nota, descricao);
        } else {
            System.out.println("Livro não encontrado ou já está marcado como lido.");
        }
    }

    Livro encontrarPorId(int id) {
        for (Livro l : livros) {
            if (l.id == id) return l;
        }
        return null;
    }

    void exibirDesejos() {
        System.out.println("📚 Lista de Desejos:");
        livros.stream().filter(l -> !l.lido).forEach(System.out::println);
    }

    void exibirLidos() {
        System.out.println("✅ Livros Lidos:");
        livros.stream().filter(l -> l.lido).forEach(System.out::println);
    }

    void editarLivro(int id, String novoTitulo, int novaNota, String novaDescricao) {
        Livro livro = encontrarPorId(id);
        if (livro != null) {
            livro.titulo = novoTitulo;
            if (livro.lido) {
                livro.nota = novaNota;
                livro.descricao = novaDescricao;
            }
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    void removerLivro(int id) {
        livros.removeIf(l -> l.id == id);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        int opcao;

        do {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Adicionar livro à lista de desejos");
            System.out.println("2. Marcar livro como lido");
            System.out.println("3. Visualizar lista de desejos");
            System.out.println("4. Visualizar livros lidos");
            System.out.println("5. Editar livro por ID");
            System.out.println("6. Remover livro por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Título do livro: ");
                    String titulo = sc.nextLine();
                    biblioteca.adicionarLivro(titulo);
                    break;
                case 2:
                    System.out.print("ID do livro a marcar como lido: ");
                    int idLido = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nota (0 a 5): ");
                    int nota = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Descrição (opcional): ");
                    String desc = sc.nextLine();
                    biblioteca.marcarComoLido(idLido, nota, desc);
                    break;
                case 3:
                    biblioteca.exibirDesejos();
                    break;
                case 4:
                    biblioteca.exibirLidos();
                    break;
                case 5:
                    System.out.print("ID do livro a editar: ");
                    int idEdit = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Novo título: ");
                    String novoTitulo = sc.nextLine();
                    System.out.print("Nova nota (-1 se não alterar): ");
                    int novaNota = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nova descrição (deixe vazio se não alterar): ");
                    String novaDesc = sc.nextLine();
                    biblioteca.editarLivro(idEdit, novoTitulo, novaNota, novaDesc);
                    break;
                case 6:
                    System.out.print("ID do livro a remover: ");
                    int idRemove = sc.nextInt();
                    biblioteca.removerLivro(idRemove);
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        sc.close();
    }
}

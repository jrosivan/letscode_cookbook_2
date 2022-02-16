package br.com.elpis.cookbook.view;

import br.com.elpis.cookbook.controller.Catalogo;
import br.com.elpis.cookbook.domain.Receita;

import java.util.Locale;
import java.util.Scanner;

public class CatalogoView {

    private Catalogo controller;
    private Receita ative;
    private int currentIndex;

    public CatalogoView(Catalogo controller) {
        this.controller = controller;
        currentIndex = -1;
        next();
    }

    public void find() {
        // capturar nome da receita
        // procurar no catálogo se já existe;
        System.out.println("Entre com o NOME ou NÚMERO da receita para localizar (V-Voltar) ");
        Scanner scanner = new Scanner(System.in);
        String opcao;

        do {
            opcao = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
            if (!opcao.equals("V")) {

                // entrou com o NÚMERO da Receita
                if (opcao.chars().allMatch(Character::isDigit)) {
                    int numeroReceita = Integer.parseInt(opcao);
                    if (numeroReceita -1 < 0 || numeroReceita > controller.getTotal()) {
                        System.out.printf("Número da receita [%d] é inválido!\n", numeroReceita);
                    } else {
                        Receita receita = controller.getReceita(numeroReceita-1);
                        if (receita != null) {
                            ative = receita;
                            break;
                        } else {
                            System.out.printf("Receita nº [%d] não localizada!\n", numeroReceita);
                        }
                    }
                } else {  // entrou com o NOME da Receita
                    Receita receita = controller.getReceita(opcao);
                    if (receita != null) {
                        ative = receita;
                        break;
                    } else {
                        System.out.printf("Receita de nome [%d] não localizada!\n", opcao);
                    }

                }

            } else break;
        } while(true);

    }


    public void view() { // desenha a tela....
        // se NÃO houver receita ativa: mostrar mensagem;
        // se  houver receita:
        //    monta o layout da tela com os dados da receita;
        //    exibe o layout montado;
        // exibe o menu de opções

        do {

            String tela = "";
            if (ative == null) {
                tela = "Nenhuma receita encontrada!";
            } else {
                tela = "  # ".concat(ative.toString());
            }

            System.out.println(tela);

        } while (showMenu());

    }

    private boolean showMenu() {
        System.out.println("┌".concat("─".repeat(100)));
        System.out.println("│  + = Adicionar");
        if (ative != null) System.out.println("│  - = Remover");
        if (controller.getTotal() > 0) {
            System.out.println("│  P = Próxima");
            System.out.println("│  A = Anterior");
            System.out.println("│  L = Localizar");
        }
        System.out.println("│  X = Sair");
        System.out.println("└".concat("─".repeat(100)));

        Scanner scanner = new Scanner(System.in);
        String opcao;
        opcao = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "+":
                add();
                break;
            case "-":
                if (ative != null) del();
                break;
            case "P":
                if (ative != null) next();
                break;
            case "A":
                if (ative != null) previous();
                break;
            case "L":
                if (controller.getTotal() > 0) find();
                break;
            case "X":
                System.out.println("Obrigado por usar o App [Pangeia-Cake]!!");
                return false;
            default:
                System.out.println("Opção Inválida!\n");
        }
        return true;
    }


    public void next() {
        // se estiver com a receita ativa, ativa a próxima;
        // se NÃO estiver com uma receita ativa, ativa a primeira receita;
        currentIndex = (currentIndex >= controller.getTotal() - 1) ? 0 : ++currentIndex;
        ative = controller.getReceita(currentIndex);
    }

    public void previous() {
        // se estiver com a receita ativa, ativa a anterior;
        // se NÃO estiver com uma receita ativa, ativa a última receita;
        currentIndex = (currentIndex == 0) ? controller.getTotal() - 1 : --currentIndex;
        ative = controller.getReceita(currentIndex);
    }

    public void del() {
        //Se NÃO estiver com uma receita ativa, mostra mensagem.
        //Se estiver com uma receita ativa, confirma a operação.
        //Se confirmar, solicita ao Catalogo apagar a receita.
        System.out.println("Você deseja realmente APAGAR a receita " + ative.getNome() + "?\nS - Sim   N - Não");
        Scanner scanner = new Scanner(System.in);
        String opcao;
        do {
            opcao = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
            if (opcao.equals("S")) {
                controller.del(ative.getNome());
                --currentIndex;
                next();
                break;
            } else if (opcao.equals("N")) {
                break;
            } else {
                System.out.println("Opção inválida!!!");
            }
        } while (true);

    }

    public void add() {
        // capturar nome da receita;
        // procurar no catálogo se já existe;
        // Se encontra, mostrar mensagem;
        // Se não encontrar continua:
        //   capturar dados da nova receita;
        //   criar nova receita;
        //   passa a receita para o Catalogo adicionar;
        // Torna a nova receita ativa;
    }

    public void edit() {
        // se NÃO estiver com a receita ativa, mostra mensagem;
        // se estiver com a receita ativa, abrir tela de edição;
    }


}

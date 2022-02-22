package br.com.elpis.cookbook.view;
import br.com.elpis.cookbook.controller.Catalogo;
import br.com.elpis.cookbook.domain.Receita;
import br.com.elpis.cookbook.enums.Categoria;

import java.lang.reflect.Method;
import java.util.Arrays;

public class CatalogoView {

    private Catalogo controller;
    private Receita ative;
    private int currentIndex;

    public CatalogoView(Catalogo controller) {
        this.controller = controller;
        currentIndex = -1;
        next();
    }

    public void view() {
        do {
            //Exibe receita ativa...
            new ReceitaView(ative).fullView(System.out);
            //Exibe o menu de opções.
        } while (showMenu());
    }

    private boolean showMenu() {

        String[][] menuOptions = new String[7][3];  // quantidade dos itens ['menu']['metodo/resposta']...

        ConsoleUtils.addOptionsMenu(menuOptions, "+ : Adicionar", "+", "add");
        if (ative != null) {
            ConsoleUtils.addOptionsMenu(menuOptions, "- : Remover", "-", "del");
        }
        if (controller.getTotal() > 0) {
            ConsoleUtils.addOptionsMenu(menuOptions, "P : Próxima", "P", "next");
            ConsoleUtils.addOptionsMenu(menuOptions, "A : Anterior", "A", "previous");
            ConsoleUtils.addOptionsMenu(menuOptions, "L : Localizar", "L", "find");
            ConsoleUtils.addOptionsMenu(menuOptions, "E : Editar", "E", "edit");
        }
        ConsoleUtils.addOptionsMenu(menuOptions, "X : [Sair]", "X", "exit");

        String option = ConsoleUtils.getUserOption(ConsoleUtils.montaMenu(menuOptions), menuOptions, "MENU PRINCIPAL - Escolha uma opção");

        ConsoleUtils.callByName(this, option);

        return ( !option.equals("exit") );
    }

    public void exit() { System.out.println("\nObrigado por usar o App [Pangeia-Cake]®!!"); }

    public void next() {
        currentIndex = (currentIndex >= controller.getTotal() - 1) ? 0 : ++currentIndex;
        ative = controller.getReceita(currentIndex);
    }

    public void previous() {
        currentIndex = (currentIndex <= 0) ? controller.getTotal() - 1 : --currentIndex;
        ative = controller.getReceita(currentIndex);
    }

    public void del() {
        String option = ConsoleUtils.getUserOption("Confirma APAGAR a receita: " + ative.getNome() + " [S=Sim  N=Não]? ", ConsoleUtils.MENU_OPTIONS_YESNO, "");
        if (option.equalsIgnoreCase("S")) {
            controller.del(ative.getNome());
            --currentIndex;
            next();
        }
    }

    public void find() {
        String option = ConsoleUtils.getUserInput("Entre com o NOME ou NÚMERO da receita para localizar: ");
        if (option.trim().isBlank()) return;
        if (option.chars().allMatch(Character::isDigit)) { // entrou com o NÚMERO da Receita
            ative = controller.getReceita(Integer.parseInt(option) - 1);
        } else {  // entrou com o NOME da Receita
            ative = controller.getReceita(option);
        }
            currentIndex = -1;
    }

    public void add() {  // OK!
        // capturar nome da receita;
        String name = ConsoleUtils.getUserInput("Entre com o NOME da nova receita: ");
        if (name.trim().isBlank()) return;
        // verifica se já existe;
        Receita other = controller.getReceita(name);
        if (other != null) {
            String option = ConsoleUtils.getUserOption("Receita já existe! Deseja visualizar [S=Sim  N=Não]?  ", ConsoleUtils.MENU_OPTIONS_YESNO, "");
            if (option.equalsIgnoreCase("S")) {
                ative = other;
            }
        } else {
            // selecionar Categoria:
            int index  = ConsoleUtils.menuEnums( Arrays.stream(Categoria.values()).map(Enum::name).toArray(String[]::new), "Selecione a NOVA Categoria");
            if (index == ConsoleUtils.RETURN_VALUE_EXIT) return;
            // criar nova receita;
            other = new Receita(name, Categoria.values()[index-1]);
            // passa a receita para o Catalogo adicionar;
            controller.add(other);
            // torna a nova receita ativa;
            ative = other;
            currentIndex = -1;
        }
    }

    public void edit() {
        Receita nova = new EditReceitaView(ative, controller).edit(); // clone..
        if (nova != null) {
            controller.del(ative.getNome());
            controller.add(nova);
            //Torna a nova receita a ativa.
            ative = nova;
            currentIndex = 0;
        }
    }


}

package br.com.elpis.cookbook.view;

import br.com.elpis.cookbook.controller.Catalogo;
import br.com.elpis.cookbook.domain.Ingrediente;
import br.com.elpis.cookbook.domain.Receita;
import br.com.elpis.cookbook.domain.Rendimento;
import br.com.elpis.cookbook.enums.Categoria;
import br.com.elpis.cookbook.enums.tipoMedida;
import br.com.elpis.cookbook.enums.tipoRendimento;

import java.util.Arrays;
import java.util.List;

public class EditReceitaView {

    private Catalogo controller;
    private Receita receita;

    public EditReceitaView(Receita receita, Catalogo controller) {
        this.receita = new Receita(receita);
        this.controller = controller;
    }

    public Receita edit() {
        Receita original = new Receita(receita);
        do {
            new ReceitaView(receita).fullView(System.out); //Exibe receita ativa...
        } while (showMenu());
        if (!receita.equals(original)) {
            String option = ConsoleUtils.getUserOption("Gravar Alterações? [S=Sim  N=Não]  ", ConsoleUtils.MENU_OPTIONS_YESNO, "");
            if (option.equalsIgnoreCase("S")) {
                return receita;
            }
        }
        return null;
    }

    private boolean showMenu() {

        String[][] menuOptions = new String[7][3];  // quantidade dos itens ['menu']['metodo/resposta']...

        ConsoleUtils.addOptionsMenu(menuOptions, "N : Editar NOME da Receita", "N", "editarNome");
        ConsoleUtils.addOptionsMenu(menuOptions, "C : Editar CATEGORIA da Receita", "C", "editarCategoria");
        ConsoleUtils.addOptionsMenu(menuOptions, "T : Editar TEMPO DE PREPARO da Receita", "T", "editarTempoPreparo");
        ConsoleUtils.addOptionsMenu(menuOptions, "R : Editar RENDIMENTO da Receita", "R", "editarRendimento");
        ConsoleUtils.addOptionsMenu(menuOptions, "I : Editar INGREDIENTES da Receita", "I", "menuIngredientes");
        ConsoleUtils.addOptionsMenu(menuOptions, "M : Editar MODO DE PREPARO da Receita", "M", "menuModoPreparo");
        ConsoleUtils.addOptionsMenu(menuOptions, "X : [Voltar ao Menu Anterior]", "X", "voltar");

        String option = ConsoleUtils.getUserOption(ConsoleUtils.montaMenu(menuOptions), menuOptions, "EDITAR RECEITA  - Selecione uma Opção");

        ConsoleUtils.callByName(this, option);

        return ( !option.equals("voltar") );

    }

    public void voltar() {}

    public void editarNome() {
        System.out.printf("Nome anterior: [%s]\n", receita.getNome());
        String name = ConsoleUtils.getUserInput("Entre com o NOVO NOME da receita: ");
        if (name.trim().isBlank()) return;
        // verificar se já existe:
        Receita other = controller.getReceita(name);
        if (other != null) {
            String option = ConsoleUtils.getUserOption("Receita já existe! Deseja visualizar [S=Sim  N=Não]?  ", ConsoleUtils.MENU_OPTIONS_YESNO, "");
            if (option.equalsIgnoreCase("S")) {
                receita = other;
            }
        } else {
            receita.setNome(name);
        }
    }

    public void editarCategoria() {  //OK!
        new ReceitaView(receita).preparoView(System.out);
        int index = ConsoleUtils.menuEnums( Arrays.stream(Categoria.values()).map(Enum::name).toArray(String[]::new), "Entre com a NOVA Categoria");
        if (index == ConsoleUtils.RETURN_VALUE_EXIT ) return;
        receita.setCategoria(Categoria.values()[index-1]);
    }

    public void editarTempoPreparo() {  //OK!
        new ReceitaView(receita).tempoPreparoView(System.out);
        double tempoPreparo = ConsoleUtils.getUserValueDouble("Entre com o NOVO TEMPO DE PREPARO da receita: ");
        if (tempoPreparo != ConsoleUtils.RETURN_VALUE_BLANK  && tempoPreparo != ConsoleUtils.RETURN_VALUE_ERROR) {
            receita.setTempoPreparo(tempoPreparo);
        } else System.out.println("   ### Tempo de preparo INVÁLIDO!");
    }

    public void editarRendimento() {  // OK!
        new ReceitaView(receita).rendimentoView(System.out);
        int index = ConsoleUtils.menuEnums( Arrays.stream(tipoRendimento.values()).map(Enum::name).toArray(String[]::new),"Entre com o NOVO Tipo de Rendimento");
        if (index == ConsoleUtils.RETURN_VALUE_EXIT ) return;
        int rendimentoMinimo = ConsoleUtils.getUserValueInt("Entre com o Rendimento Mínimo da receita: ");
        int rendimentoMaximo = ConsoleUtils.getUserValueInt("Entre com o Rendimento Máximo da receita [ENTER para repetir o Mínimo]: ");
        if (rendimentoMaximo == ConsoleUtils.RETURN_VALUE_BLANK) {
            rendimentoMaximo = rendimentoMinimo;
        }
        if (rendimentoMaximo != ConsoleUtils.RETURN_VALUE_ERROR && rendimentoMinimo != ConsoleUtils.RETURN_VALUE_ERROR) {
            receita.setRendimento(new Rendimento(rendimentoMinimo, rendimentoMaximo, tipoRendimento.values()[index-1]));
        } else System.out.println("   ### Rendimento(s) INVÁLIDO(s)!");
    }

    public void menuIngredientes() {  //OK!
        String option;
        do {
            new ReceitaView(receita).ingredientesView(System.out);
            String[][] menuOptions = new String[4][3];  // quantidade dos itens ['menu']['metodo/resposta']...
            ConsoleUtils.addOptionsMenu(menuOptions, "+ : Adicionar Ingrediente", "+", "addIngrediente");
            if (receita.getIngredientes().size() > 0) {
                ConsoleUtils.addOptionsMenu(menuOptions, "- : Remover Ingrediente", "-", "delIngrediente");
                ConsoleUtils.addOptionsMenu(menuOptions, "E : Editar Ingrediente", "E", "editIngrediente");
            }
            ConsoleUtils.addOptionsMenu(menuOptions, "X : [Voltar ao Menu Anterior]", "X", "voltar");
            option = ConsoleUtils.getUserOption(ConsoleUtils.montaMenu(menuOptions), menuOptions, "EDITAR INGREDIENTE - Selecione uma Opção");
            ConsoleUtils.callByName(this, option);
        } while ( !option.equals("voltar") );

    }

    public void addIngrediente() {  // OK!
        Ingrediente ingrediente = getUserIngrediente();
        if (ingrediente != null) {
            int index = ConsoleUtils.getUserValueInt("Qual a posição do novo Ingrediente? [ENTER para adicionar no final]");
            if (index == ConsoleUtils.RETURN_VALUE_BLANK) {
                receita.addIngrediente(ingrediente);
            } else {
                if (index != ConsoleUtils.RETURN_VALUE_ERROR) {
                     receita.addIngrediente( index-1, ingrediente);
                 } else System.out.println("   ### Posição INVÁLIDA!");
            }
        }
    }

    public void delIngrediente() {  // OK!
        int index = ConsoleUtils.getUserValueInt("Qual a posição do Ingrediente para Remover?");
        if (index != ConsoleUtils.RETURN_VALUE_BLANK && index != ConsoleUtils.RETURN_VALUE_ERROR && index <= receita.getIngredientes().size()) {
           receita.delIngrediente( index-1);
        } else System.out.println("   ### Posição INVÁLIDA!");
    }

    public void editIngrediente() {  // OK!
        int index = ConsoleUtils.getUserValueInt("Qual a posição do Ingrediente para Editar?");
        if (index != ConsoleUtils.RETURN_VALUE_BLANK && index != ConsoleUtils.RETURN_VALUE_ERROR && index <= receita.getIngredientes().size()) {
            Ingrediente ingrediente = getUserIngrediente();
            if (ingrediente != null ) {
                receita.delIngrediente(index - 1);
                receita.addIngrediente(index-1, ingrediente);
            }
        } else System.out.println("   ### Posição INVÁLIDA!");
    }

    public Ingrediente getUserIngrediente() {  //
        Ingrediente ingrediente = null;
        String nameIngrediente = ConsoleUtils.getUserInput("Entre com o NOME do novo Ingrediente: ");
        if (!nameIngrediente.isBlank()) {
            double quantidade = ConsoleUtils.getUserValueInt("Entre com o QUANTIDADE do Ingrediente: ");
            if (quantidade != ConsoleUtils.RETURN_VALUE_BLANK && quantidade != ConsoleUtils.RETURN_VALUE_ERROR && quantidade > 0) {
                int index = ConsoleUtils.menuEnums( Arrays.stream(tipoMedida.values()).map(Enum::name).toArray(String[]::new),"Entre com o TIPO de Medida");
                if (index != ConsoleUtils.RETURN_VALUE_EXIT) {
                    ingrediente = new Ingrediente(nameIngrediente, Double.valueOf(quantidade),  tipoMedida.values()[ index-1 ] );
                }
            }
        }
        if (ingrediente == null) {
            System.out.println("   ### Ingrediente INVÁLIDO!");
        }
        return ingrediente;
    }


    public void menuModoPreparo() {
        String option;
        do {
            new ReceitaView(receita).preparoView(System.out);
            String[][] menuOptions = new String[4][3];  // quantidade dos itens ['menu']['metodo/resposta']...
            ConsoleUtils.addOptionsMenu(menuOptions, "+ : Adicionar Preparo", "+", "addPreparo");
            if (receita.getPreparo().size() > 0) {
                ConsoleUtils.addOptionsMenu(menuOptions, "- : Remover preparo", "-", "delPreparo");
                ConsoleUtils.addOptionsMenu(menuOptions, "E : Editar Preparo", "E", "editPreparo");
            }
            ConsoleUtils.addOptionsMenu(menuOptions, "X : [Voltar ao Menu Anterior]", "X", "voltar");
            option = ConsoleUtils.getUserOption(ConsoleUtils.montaMenu(menuOptions), menuOptions, "EDITAR PREPARO - Selecione uma Opção");
            ConsoleUtils.callByName(this, option);
        } while ( !option.equals("voltar") );
    }

    public void addPreparo() {
        String preparo;
        List<String> modoPreparo = receita.getPreparo();
        do {
            preparo = ConsoleUtils.getUserInput("Modo de preparo ".concat(String.valueOf(modoPreparo.size()+1)).concat(": [ENTER para finalizar]"));
            if (!preparo.trim().isBlank()) {
                modoPreparo.add(preparo);
            }
        } while ( !preparo.equals("") );
    }

    public void delPreparo() {
        List<String> modoPreparo = receita.getPreparo();
        int index = ConsoleUtils.getUserValueInt("Qual a posição do Preparo para Remover?");
        if (index != ConsoleUtils.RETURN_VALUE_BLANK && index != ConsoleUtils.RETURN_VALUE_ERROR && index <= modoPreparo.size()) {
            modoPreparo.remove(index-1);
        } else System.out.println("   ### Posição INVÁLIDA!");
    }
    public void editPreparo() {
        List<String> modoPreparo = receita.getPreparo();
        int index = ConsoleUtils.getUserValueInt("Qual a posição do Preparo para Editar?");
        if (index != ConsoleUtils.RETURN_VALUE_BLANK && index != ConsoleUtils.RETURN_VALUE_ERROR && index <= modoPreparo.size()) {
            String preparo = ConsoleUtils.getUserInput("Modo de preparo ".concat(String.valueOf(index)).concat(": [ENTER para finalizar]"));
            if (!preparo.trim().isBlank()) {
                modoPreparo.remove(index-1);
                modoPreparo.add(index-1, preparo);
            }
        } else System.out.println("   ### Posição INVÁLIDA!");
    }

}

package br.com.elpis.cookbook.view;

import br.com.elpis.cookbook.enums.Categoria;

import java.lang.reflect.Method;
import java.util.Scanner;

public class ConsoleUtils {

    public static final String[][] MENU_OPTIONS_YESNO =
            { {"S=Sim", "S", "S"},
              {"N=Não", "N", "N"} };

    private static final String INVALID_OPTION_MSG = "Invalid option. Please try again!";
    public static final int RETURN_VALUE_BLANK = -1;
    public static final int RETURN_VALUE_ERROR = -999;
    public static final int RETURN_VALUE_EXIT  = -888;


    // Reflections - Executar o método da classe OBJ pelo nome.. generalizando a exception:
    // # TO-DO:
    // 1-tratar para aceitar parâmetros;
    // 2-fazer único edit para "Simples" e outro para "Listas";
    public static void callByName(Object obj, String funcName) {
        try {
            obj.getClass().getDeclaredMethod(funcName).invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // adicionar items e opções ao menu[][]:
    public static void addOptionsMenu(String[][] menuOptions, String item, String option, String metodo) {
        for (int i = 0; i < menuOptions.length; i++) {
            if (menuOptions[i][0] == null) {
                menuOptions[i][0] = item;
                menuOptions[i][1] = option;
                menuOptions[i][2] = metodo;
                break;
            }
        }
    }

    public static String montaMenu(String[][] menuOptions) {
        String tela = "┌".concat("─".repeat(100)).concat("┐\n");
        for (int i = 0; i < menuOptions.length; i++) {
            if (menuOptions[i][0] != null) {
                tela += "│ " + menuOptions[i][0]
                        .concat(" ".repeat(100 - menuOptions[i][0].length() - 1))
                        .concat("│\n");
            }
        }
        tela += "└".concat("─".repeat(100)).concat("┘");
        return tela;
    }

    // monta a tela, aguarda escolha do usuário e retorna opção (método/reposta):
    public static String getUserOption(String message, String[][] menuOptions, String question) {
        System.out.println(message);
        String methodName;
        do {
            String option = getUserInput(question);
            methodName = isValid(option, menuOptions);
            if (methodName.equals("")) {
                System.out.printf("[%s] ", INVALID_OPTION_MSG);
            }
        } while (methodName.equals(""));
        return (methodName);
    }

    public static String getUserInput(String question) {
        System.out.printf(question.concat("  # : "));
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    // Valida selecao do usuário, retorna metodo referenciado:
    private static String isValid(String option, String[][] menuOptions) {
        for (String[] v : menuOptions) {
            if (v[0] != null && v[1].equalsIgnoreCase(option)) {
                return v[2];
            }
        }
        return "";
    }

    public static int menuEnums(String[] itens, String question) {
        String[][] menuOptions = new String[itens.length + 1][3];  // quantidade de Itens + VOLTAR;
        for (int i = 0; i < itens.length; i++) {
            addOptionsMenu(menuOptions, i + 1 + " - " + itens[i], String.valueOf(i + 1), String.valueOf(i + 1));
        }
        addOptionsMenu(menuOptions, "X : [Voltar ao Menu Anterior]", "X", String.valueOf(RETURN_VALUE_EXIT) );
        return Integer.valueOf(ConsoleUtils.getUserOption(ConsoleUtils.montaMenu(menuOptions), menuOptions, question));
    }

    // return -1 if BLANK; -999 if error:
    public static double getUserValueDouble(String question) {
        String value = ConsoleUtils.getUserInput(question);
        if (value.isBlank()) {
            return RETURN_VALUE_BLANK;
        } else {
            try {
                return Double.valueOf(value);
            } catch (Exception e) {
                return RETURN_VALUE_ERROR;
            }
        }
    }

    public static int getUserValueInt(String question) {
        return (int)getUserValueDouble(question);
    }


}

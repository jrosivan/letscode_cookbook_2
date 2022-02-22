package br.com.elpis.cookbook.view;

import br.com.elpis.cookbook.domain.Ingrediente;
import br.com.elpis.cookbook.domain.Receita;

import java.io.PrintStream;
import java.util.List;

public class ReceitaView {

    private Receita receita;

    public ReceitaView(Receita receita) {
        this.receita = receita;
    }

    public void fullView(PrintStream out) {
        if (receita == null) {
            out.printf("%n  # %s%n", "Nenhuma receita encontrada!");
        } else {
            headerView(out);
            categoriaView(out);
            tempoPreparoView(out);
            rendimentoView(out);
            ingredientesView(out);
            preparoView(out);
        }
    }

    public void categoriaView(PrintStream out) {
        out.printf("\ta) Categoria: %s%n", receita.getCategoria().name());
    }

    public void tempoPreparoView(PrintStream out) {
        out.printf("\tb) Tempo de preparo: %s minutos %n", receita.getTempoPreparo());
    }

    public void rendimentoView(PrintStream out) {
        if (receita.getRendimento() != null) {
            if (receita.getRendimento().getMinimo() != receita.getRendimento().getMaximo()) {
                out.printf("\tc) Rendimento: de %s à %s %s%n", receita.getRendimento().getMinimo(), receita.getRendimento().getMaximo(), receita.getRendimento().getTipo().name());
            } else {
                out.printf("\tc) Rendimento: %s %s%n", receita.getRendimento().getMinimo(), receita.getRendimento().getTipo().name());
            }
        } else {
            out.printf("\tc) Rendimento: [%s]%n", "Indefinido");
        }
    }


    public void headerView(PrintStream out) {
        out.printf("%s%n", "═".repeat(100));
        out.printf(" [### %s ###]%n", receita.getNome());
        out.printf("%s%n", "═".repeat(100));
    }

    public void ingredientesView(PrintStream out) {
        out.printf("\t%s%n", "d) Ingredientes --");
        if (receita.getIngredientes() == null || receita.getIngredientes().isEmpty()) {
            out.printf("\t\t%s%n", "» Nenhum ingrediente encontrado!");
        } else {
            List<Ingrediente> ingredientes = receita.getIngredientes();
            for (int i = 0; i < ingredientes.size(); i++) {
                out.printf("\t\t%d - %s %s de %s%n", i+1, ingredientes.get(i).getQuantidade(), ingredientes.get(i).getTipo().name(), ingredientes.get(i).getNome());
            }
        }
    }

    public void preparoView(PrintStream out) {
        out.printf("\t%s%n", "e) Modo de preparo --");
        if (receita.getPreparo() == null || receita.getPreparo().isEmpty()) {
            out.printf("\t\t%s%n", "» Nenhum preparo encontrado!");
        } else {
            List<String> modoPreparo = receita.getPreparo();
            for (int i = 0; i < modoPreparo.size() ; i++) {
                out.printf("\t\t%d - %s%n", i+1, modoPreparo.get(i));
            }
        }
    }


}

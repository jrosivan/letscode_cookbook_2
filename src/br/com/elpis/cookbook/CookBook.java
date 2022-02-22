package br.com.elpis.cookbook;

import br.com.elpis.cookbook.controller.Catalogo;
import br.com.elpis.cookbook.domain.Receita;
import br.com.elpis.cookbook.enums.Categoria;
import br.com.elpis.cookbook.view.CatalogoView;

public class CookBook {

    public static void main(String[] args) {

        //String[] a = Arrays.stream(Categoria.values()).map(Enum::name).toArray(String[]::new);

        Catalogo catalogo = new Catalogo();
        catalogo.add(new Receita("Cookies da Lara - 01", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara - 02", Categoria.SALGADO));
        catalogo.add(new Receita("Cookies da Lara - 03", Categoria.BEBIDA));
        catalogo.add(new Receita("Cookies da Lara - 04", Categoria.BEBIDA));
        CatalogoView view = new CatalogoView(catalogo);
        view.view();

    }

}


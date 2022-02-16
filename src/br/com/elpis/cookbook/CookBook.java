package br.com.elpis.cookbook;

// classe de entrada.. MAIN.. pode ser chamada de APP!

import br.com.elpis.cookbook.controller.Catalogo;
import br.com.elpis.cookbook.domain.Receita;
import br.com.elpis.cookbook.enums.Categoria;
import br.com.elpis.cookbook.view.CatalogoView;

public class CookBook {

    public static void main(String[] args) {

        Catalogo catalogo = new Catalogo();
        catalogo.add(new Receita("Cookies da Lara - 01", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara - 02", Categoria.SALGADO));
        catalogo.add(new Receita("Cookies da Lara - 03", Categoria.BEBIDA));
        CatalogoView view = new CatalogoView(catalogo);
        view.view();

    }

}


/**
 *  FINALIZAR ATÉ QUINTA 17/02/2022!!!
 *
 *
 */


package br.com.elpis.cookbook.controller;

import br.com.elpis.cookbook.domain.Receita;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// esta classe é como se fosse um CONTROLLER....
public class Catalogo {

    private List<Receita> receitas = new ArrayList<>();

    public void add(Receita receita) {
        if (receita == null) throw new IllegalArgumentException();
        receitas.add(receita);
    }

    public void del(String nomeReceita) {
        Receita receita = getReceita(nomeReceita);
        if (receita != null){
          receitas.remove(receita);
        }
    }

    public Receita getReceita(String nomeReceita) {
        if (nomeReceita == null || nomeReceita.isBlank()) return null;//throw new IllegalArgumentException();
        for (Receita receita: receitas) {
            if (receita.getNome().equalsIgnoreCase(nomeReceita)) {
                return receita;
            }
        }
        return null;
    }

    public Receita getReceita(int numeroReceita) { // usuário não começa do ZERO!
        if(numeroReceita < 0 || numeroReceita >= receitas.size()) return null;//throw new IllegalArgumentException();
        return receitas.get(numeroReceita);
    }

    public Receita getRandom() {
        int index = new Random().nextInt(receitas.size());
        return receitas.get(index);
    }

    public int getTotal(){
        return receitas.size();
    }

}

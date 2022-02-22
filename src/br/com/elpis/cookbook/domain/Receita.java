package br.com.elpis.cookbook.domain;

import br.com.elpis.cookbook.enums.Categoria;

import java.util.ArrayList;
import java.util.List;

public class Receita {
    private String nome;
    private Categoria categoria;
    private double tempoPreparo;
    private Rendimento rendimento;
    private List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
    private List<String> preparo = new ArrayList<String>();

    public Receita(String nome, Categoria categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public Receita(Receita origem) {
        this.nome = origem.nome;
        this.categoria = origem.categoria;
        this.tempoPreparo = origem.tempoPreparo;
        this.rendimento = origem.rendimento;
        if (origem.ingredientes != null) {
            this.ingredientes = new ArrayList<>(origem.ingredientes);
        }
        if (origem.preparo != null) {
            this.preparo = new ArrayList<>(origem.preparo);
        }
    }

    // gets...
    public String getNome() { return nome; }
    public Categoria getCategoria() {
        return categoria;
    }
    public double getTempoPreparo() {
        return tempoPreparo;
    }
    public Rendimento getRendimento() { return rendimento; }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }
    public List<String> getPreparo() {
        return preparo;
    }

    // sets...
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public void setTempoPreparo(double tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }
    public void setRendimento(Rendimento rendimento) {
        this.rendimento = rendimento;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        this.ingredientes.add(ingrediente);
    }

    public void addIngrediente(int index, Ingrediente ingrediente) {
        this.ingredientes.add(index, ingrediente);
    }

    public void delIngrediente(int index) {
        this.ingredientes.remove(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receita receita = (Receita) o;
        if (tempoPreparo != receita.getTempoPreparo()) return false;
        if (rendimento != null && !rendimento.equals(receita.getTempoPreparo() )) return false;
        if (!ingredientes.equals(receita.getIngredientes())) return false;
        if (!preparo.equals(receita.getPreparo())) return false;
        return nome != null ? nome.equals(receita.nome) : receita.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "br.com.elpis.cookbook.domain.Receita{" +
                "nome='" + nome + '\'' +
                ", categoria=" + categoria +
                ", tempoPreparo=" + tempoPreparo +
                ", rendimento=" + rendimento +
                ", ingredientes=" + ingredientes +
                ", preparo=" + preparo +
                '}';
    }

}

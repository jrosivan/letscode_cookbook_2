package br.com.elpis.cookbook.domain;

import br.com.elpis.cookbook.enums.Categoria;

import java.util.List;

public class Receita {
    private String nome;
    private Categoria categoria;
    private double tempoPreparo;
    private Rendimento rendimento;
    private List<Ingrediente> ingredientes;
    private List<String> preparo;

    public Receita(String nome, Categoria categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    // gets...
    public String getNome() {
        return nome;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public double getTempoPreparo() {
        return tempoPreparo;
    }
    public Rendimento getRendimento() {
        return rendimento;
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receita receita = (Receita) o;

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

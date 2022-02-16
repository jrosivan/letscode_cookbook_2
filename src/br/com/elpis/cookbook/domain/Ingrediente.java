package br.com.elpis.cookbook.domain;

import br.com.elpis.cookbook.enums.tipoMedida;

public class Ingrediente {
    private String nome;
    private double quantidade;
    private tipoMedida tipo;

    public Ingrediente(String nome, double quantidade, tipoMedida tipo) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public tipoMedida getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingrediente that = (Ingrediente) o;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public String toString() {
        return "br.com.elpis.cookbook.domain.Ingrediente{" +
                "nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", tipo=" + tipo +
                '}';
    }

}

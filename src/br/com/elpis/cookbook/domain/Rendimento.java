package br.com.elpis.cookbook.domain;

import br.com.elpis.cookbook.enums.tipoRendimento;

public class Rendimento {
    private int minimo;
    private int maximo;
    private tipoRendimento tipo;

    public Rendimento(int minimo, int maximo, tipoRendimento tipoRendimento) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.tipo = tipoRendimento;
    }

    public Rendimento(int quantidade, tipoRendimento tipoRendimento) {
        this.maximo = this.minimo = quantidade;
        this.tipo = tipoRendimento;
    }

    public int getMinimo() {
        return minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public tipoRendimento getTipoRendimento() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rendimento that = (Rendimento) o;

        if (minimo != that.minimo) return false;
        if (maximo != that.maximo) return false;
        return tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        int result = minimo;
        result = 31 * result + maximo;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "br.com.elpis.cookbook.domain.Rendimento{" +
                "minimo=" + minimo +
                ", maximo=" + maximo +
                ", tipo=" + tipo +
                '}';
    }

}

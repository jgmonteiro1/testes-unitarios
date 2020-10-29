package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {

    private Filme filme;

    private FilmeBuilder(){

    }

    public static FilmeBuilder filmeBuilder(){
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setNome("Filme 1");
        builder.filme.setPrecoLocacao(5.0);
        builder.filme.setEstoque(10);
        return  builder;
    }

    public Filme now(){
        return filme;
    }

    public FilmeBuilder semEstoque(){
        filme.setEstoque(0);
        return this;
    }
}

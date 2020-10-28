package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void setup(){
        service = new LocacaoService();
    }

    @Test
    public void valorDaLocacaoTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);

        //ação
       Locacao locacao = service.alugarFilme(usuario, filme);

        //resultado
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));

    }

    @Test
    public void dataLocacaoTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);

        //ação
       Locacao locacao = service.alugarFilme(usuario, filme);

        //resultado
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }

    @Test
    public void dataRetornoTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);

        //Ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        //resultado
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void filmeSemEstoqueTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 0, 5.0);

        //Ação
        service.alugarFilme(usuario, filme);

    }

    @Test
    public void usuarioVazioTest() throws FilmeSemEstoqueException{
        //cenario
        Filme filme = new Filme("Django Livre", 5, 5.0);

        //ação
        try {
            service.alugarFilme(null, filme);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    @Test
    public void filmeExisteTest() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario("João");

        expectedException.expect(LocadoraException.class);

        //acao
        service.alugarFilme(usuario, null);
    }
}
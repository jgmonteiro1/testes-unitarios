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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 10, 5.0));

        //ação
       Locacao locacao = service.alugarFilme(usuario, filmes);

        //resultado
        assertThat(locacao.getValor(), is(equalTo(5.0)));

    }

    @Test
    public void dataLocacaoTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 10, 5.0));

        //ação
       Locacao locacao = service.alugarFilme(usuario, filmes);

        //resultado
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }

    @Test
    public void dataRetornoTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 10, 5.0));

        //Ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //resultado
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void filmeSemEstoqueTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 0, 5.0));

        //Ação
        service.alugarFilme(usuario, filmes);

    }

    @Test
    public void usuarioVazioTest() throws FilmeSemEstoqueException{
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 0, 5.0));

        //ação
        try {
            service.alugarFilme(null, filmes);
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

    @Test
    public void deveAplicarDesconto25Test() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 10, 4.0), new Filme("Spirit o corcel indomável", 5, 4.0), new Filme("Testes: Um grito de socorro", 1, 4.0));
        Usuario usuario = new Usuario("Teste");

        //ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificação
        assertThat(locacao.getValor(), is(11.0));

    }

    @Test
    public void deveAplicarDesconto50Test() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 10, 4.0), new Filme("Spirit o corcel indomável", 5, 4.0), new Filme("Testes: Um grito de socorro", 1, 4.0), new Filme("Filme 4", 1, 4.0));
        Usuario usuario = new Usuario("Teste");

        //ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificação
        //4+4+3+2
        assertThat(locacao.getValor(), is(13.0));

    }

    @Test
    public void deveAplicarDesconto75Test() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 10, 4.0), new Filme("Spirit o corcel indomável", 5, 4.0), new Filme("Testes: Um grito de socorro", 1, 4.0), new Filme("Filme 4", 1, 4.0), new Filme("Filme 5", 1, 4.0));
        Usuario usuario = new Usuario("Teste");

        //ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificação
        //4+4+3+2+1
        assertThat(locacao.getValor(), is(14.0));

    }

    @Test
    public void deveAplicarDesconto100Test() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Django Livre", 10, 4.0), new Filme("Spirit o corcel indomável", 5, 4.0), new Filme("Testes: Um grito de socorro", 1, 4.0), new Filme("Filme 4", 1, 4.0), new Filme("Filme 5", 1, 4.0), new Filme("Filme 6", 1, 4.0));
        Usuario usuario = new Usuario("Teste");

        //ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificação
        //4+4+3+2+1+0
        assertThat(locacao.getValor(), is(14.0));

    }

    @Test
    public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Joao");
        List<Filme> filmes = Arrays.asList(new Filme("Filme1", 1, 1.0));

        //ação
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificação
        boolean  segundaFeira = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        assertTrue(segundaFeira);
    }
}
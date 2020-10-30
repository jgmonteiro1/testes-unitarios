package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.dao.LocacaoDAOFake;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.matchers.DiaSemanaMatcher;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import sun.util.resources.cldr.wae.CalendarData_wae_CH;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.filmeBuilder;
import static br.ce.wcaquino.builders.UsuarioBuilder.usuarioBuilder;
import static br.ce.wcaquino.matchers.MatchersProprios.caiEm;
import static br.ce.wcaquino.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    private LocacaoService service;

    private LocacaoDAO locacaoDAO;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();



    @Before
    public void setup(){
        service = new LocacaoService();
        //Passando um objeto que está implementando a interface locacaoDAO
        locacaoDAO = new LocacaoDAOFake();
        //Injeção desse locacaoDAO
        service.setLocacaoDAO(locacaoDAO);
    }

    @Test
    public void valorDaLocacaoTest() throws Exception {
        Assume.assumeFalse  (DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        //cenario
        Usuario usuario = usuarioBuilder().now();
        List<Filme> filmes = Arrays.asList(filmeBuilder().now());

        //ação
       Locacao locacao = service.alugarFilme(usuario, filmes);

        //resultado
        assertThat(locacao.getValor(), is(equalTo(5.0)));

    }

    @Test
    public void dataLocacaoTest() throws Exception {
        //cenario
        Usuario usuario = usuarioBuilder().now();
        List<Filme> filmes = Arrays.asList(filmeBuilder().now());

        //ação
       Locacao locacao = service.alugarFilme(usuario, filmes);

        //resultado
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }

    @Test
    public void dataRetornoTest() throws Exception {
        //cenario
        Usuario usuario = usuarioBuilder().now();
        List<Filme> filmes = Arrays.asList(filmeBuilder().now());

        //Ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //resultado
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void filmeSemEstoqueTest() throws Exception {
        //cenario
        Usuario usuario = usuarioBuilder().now();
        List<Filme> filmes = Arrays.asList(filmeBuilder().semEstoque().now());

        //Ação
        service.alugarFilme(usuario, filmes);

    }

    @Test
    public void usuarioVazioTest() throws FilmeSemEstoqueException{
        //cenario
        List<Filme> filmes = Arrays.asList(filmeBuilder().now());

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
        Usuario usuario = usuarioBuilder().now();

        expectedException.expect(LocadoraException.class);

        //acao
        service.alugarFilme(usuario, null);
    }



    @Test//Esse método só funciona no sábado.
    public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        Usuario usuario = usuarioBuilder().now();
        List<Filme> filmes = Arrays.asList(filmeBuilder().now());

        //ação
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificação
        boolean  segundaFeira = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        //assertTrue(segundaFeira);
        assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
    }
}
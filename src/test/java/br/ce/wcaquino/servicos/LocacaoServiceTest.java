package br.ce.wcaquino.servicos;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.LocacaoBuilder;
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
import org.mockito.Mockito;
import sun.util.resources.cldr.wae.CalendarData_wae_CH;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.filmeBuilder;
import static br.ce.wcaquino.builders.UsuarioBuilder.usuarioBuilder;
import static br.ce.wcaquino.matchers.MatchersProprios.caiEm;
import static br.ce.wcaquino.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    private LocacaoService service;

    private LocacaoDAO locacaoDAO;

    private SPCService spcService;

    private EmailService emailService;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();



    @Before
    public void setup(){
        service = new LocacaoService();
        //Passando um objeto que está implementando a interface locacaoDAO
        locacaoDAO = Mockito.mock(LocacaoDAO.class);
        //Injeção desse locacaoDAO
        service.setLocacaoDAO(locacaoDAO);
        //Criando uma instância mockada
        spcService = Mockito.mock(SPCService.class);
        //injetando a instância no serviço
        service.setSpcService(spcService);

        emailService = Mockito.mock(EmailService.class);
        service.setEmailService(emailService);

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

    @Test
    public void naoDeveAlugarFilmeParaNegativado() throws FilmeSemEstoqueException, LocadoraException {
        //cenário
        Usuario usuario = usuarioBuilder().now();
        List<Filme> filmes = Arrays.asList(filmeBuilder().now());
        Mockito.when(spcService.possuiNegativacao(usuario)).thenReturn(true);
       //resultadoesperado
        expectedException.expect(LocadoraException.class);
        expectedException.expectMessage("Usuário negativado");

        //acap
        service.alugarFilme(usuario, filmes);

    }

    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas(){
        //cenario
        Usuario usuario = UsuarioBuilder.usuarioBuilder().now();
        List<Locacao> locacaoes = Arrays.asList(LocacaoBuilder.umLocacao().comUsuario(usuario).comDataRetorno(DataUtils.obterDataComDiferencaDias(-2)).agora());
        //Gravando expectativa
        Mockito.when(locacaoDAO.obterLocacaoPendentes()).thenReturn(locacaoes);
        //acao
        service.notificarAtrasos();
        //verificação
        Mockito.verify(emailService).notificaAtraso(usuario);
    }
}
package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
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

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void valorDaLocacaoTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);
        LocacaoService service = new LocacaoService();

        //ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        //resultado
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));

    }

    @Test
    public void dataLocacaoTest() throws Exception{
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);
        LocacaoService service = new LocacaoService();

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
        LocacaoService service = new LocacaoService();

       //Ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        //resultado
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

    }

    @Test
    public void filmeSemEstoqueTest() throws Exception {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 0, 5.0);
        LocacaoService service = new LocacaoService();

        //Deve ser declarada antes da execução da ação
        expectedException.expect(Exception.class);

        //Ação
        service.alugarFilme(usuario, filme);

    }


}
package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Test
    public void valorDaLocacaoTest() {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);
        LocacaoService service = new LocacaoService();
        Locacao locacao;
        try {
            //ação
            locacao = service.alugarFilme(usuario, filme);
            error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void dataLocacaoTest() {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);
        LocacaoService service = new LocacaoService();
        Locacao locacao;
        try {
            //ação
            locacao = service.alugarFilme(usuario, filme);
            //resultado
            assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void dataRetornoTest() {
        //cenario
        Usuario usuario = new Usuario("João");
        Filme filme = new Filme("Django Livre", 10, 5.0);
        LocacaoService service = new LocacaoService();
        Locacao locacao;
        try {
            //ação
            locacao = service.alugarFilme(usuario, filme);
            //resultado
            assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
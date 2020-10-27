package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

	@Test
	public void valorDaLocacaoTest(){
		//cenario
		Usuario usuario = new Usuario("João");
		Filme filme = new Filme("Django Livre", 10, 5.0);
		LocacaoService service = new LocacaoService();
		//ação
		Locacao locacao = service.alugarFilme(usuario,filme);
		//resultado
		assertThat(locacao.getValor(), is(equalTo(5.0)));
	}

	@Test
	public void dataLocacaoTest(){
		//cenario
		Usuario usuario = new Usuario("João");
		Filme filme = new Filme("Django Livre", 10, 5.0);
		LocacaoService service = new LocacaoService();
		//ação
		Locacao locacao = service.alugarFilme(usuario,filme);
		//resultado
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
	}

	@Test
	public void dataRetornoTest(){
		//cenario
		Usuario usuario = new Usuario("João");
		Filme filme = new Filme("Django Livre", 10, 5.0);
		LocacaoService service = new LocacaoService();
		//ação
		Locacao locacao = service.alugarFilme(usuario,filme);
		//resultado
		assertThat(isMesmaData( locacao.getDataRetorno(), obterDataComDiferencaDias(1)),is(true));
	}
}
package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

public class LocacaoServiceTest {
	@Test
	public void teste(){
		//cenario
		Usuario usuario = new Usuario("João");
		Filme filme = new Filme("Django Livre", 10, 5.0);
		LocacaoService service = new LocacaoService();
		//ação
		Locacao locacao = service.alugarFilme(usuario,filme);
		//resultado
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		Assert.assertEquals(5.0,locacao.getValor(), 0.01);
	}
}
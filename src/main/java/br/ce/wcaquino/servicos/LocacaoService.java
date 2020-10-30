package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import org.junit.Assert;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.Test;

public class LocacaoService  {

	private LocacaoDAO locacaoDAO;
	private SPCService spcService;
	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException  {

		if (usuario == null) {
			throw new LocadoraException("Usuário vazio");
		}

		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme vazio");
		}

		for(Filme filme : filmes){
			if(filme.getEstoque() == 0){
				throw  new FilmeSemEstoqueException("Filme sem estoque");
			}
		}

		if(spcService.possuiNegativacao(usuario)){
			throw new LocadoraException("Usuário negativado");
		}


		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0.0;

		for(int i = 0; i<filmes.size(); i++){
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			switch (i){
				case 2: valorFilme = valorFilme * 0.75; break;
				case 3: valorFilme = valorFilme * 0.50; break;
				case 4: valorFilme = valorFilme * 0.25; break;
				case 5: valorFilme = 0d; break;
			}

			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)){
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		locacaoDAO.salvar(locacao);
		
		return locacao;
	}

	public void notificarAtrasos(){
		List<Locacao> locacoes = locacaoDAO.obterLocacaoPendentes();
		for(Locacao locacao : locacoes){
			emailService.notificaAtraso(locacao.getUsuario());
		}
	}

	//Passando externamente uma instância do dao para minha classe de serviço/injeção de dependência
	public void setLocacaoDAO(LocacaoDAO locacaoDAO){
		this.locacaoDAO = locacaoDAO;
	}
	public void setSpcService(SPCService spcService){this.spcService = spcService;}
	public void setEmailService(EmailService emailService){this.emailService = emailService;}
}
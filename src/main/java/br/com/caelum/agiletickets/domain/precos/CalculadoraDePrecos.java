package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA)
				|| sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			// quando estiver acabando os ingressos...
			preco = preco = calculaPreco(sessao, 0.10, 0.05);
		} else if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			preco = calculaPreco(sessao, 0.10, 0.50);
			calculaPrecoComDuracaoMaior(sessao);
		} else if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = calculaPreco(sessao, 0.20, 0.50);
			calculaPrecoComDuracaoMaior(sessao);
		} else {
			// nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static int ingressoDisponivel(Sessao sessao) {
		return sessao.getTotalIngressos() - sessao.getIngressosReservados();
	}

	private static double totalDeIngressoPorSessao(Sessao sessao) {
		return sessao.getTotalIngressos().doubleValue();
	}

	private static double percentageDisponivel(Sessao sessao) {
		return ingressoDisponivel(sessao) / totalDeIngressoPorSessao(sessao);
	}

	private static BigDecimal obtemPreco(Sessao sessao, double valor) {
		return sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(valor)));
	}

	private static BigDecimal calculaPreco(Sessao sessao, double valorPercentual, double percentualDoIf) {
		if (percentageDisponivel(sessao) <= percentualDoIf) {
			return obtemPreco(sessao, valorPercentual);
		} else {
			return sessao.getPreco();
		}
	}

	private static BigDecimal calculaPrecoComDuracaoMaior(Sessao sessao) {
		BigDecimal preco = null;
		return preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
	}
}

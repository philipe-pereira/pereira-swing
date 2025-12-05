package br.com.pereiraeng.swing.mecabay;

import java.io.File;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Change {
	public enum TipoAlteracao {
		TEXTO_VALOR_1(String.class), TEXTO_VALOR_2(String.class), IMAGE_1(
				File.class), IMAGE_2(File.class), ACTION(String.class), AH(
				AlinHrz.class), AV(AlinVrt.class), FONTE(Fonte.class), SIZE(
				Integer.class), B(Boolean.class), I(Boolean.class), LINHAS(
				Integer.class), COLUNAS(Integer.class), EDICAO(Boolean.class), SELECTED(
				Boolean.class), ORIENT(Orientacao.class), VALOR_MIN(
				Integer.class), VALOR_MAX(Integer.class), STEP(Integer.class);
		public Class<?> classeValor;

		private TipoAlteracao(Class<?> classeValor) {
			this.classeValor = classeValor;
		}
	}

	public enum Fonte {
		DIALOG("Dialog"), DIALOG_INPUT("DialogInput"), MONOSPACED("Monospaced"), SERIF(
				"Serif"), SANSSERIF("SansSerif");
		public String name;

		private Fonte(String name) {
			this.name = name;
		}
	}

	public enum Orientacao {
		HORIZONTAL(JSeparator.HORIZONTAL), VERTICAL(JSeparator.VERTICAL);
		public int direcao;

		private Orientacao(int direcao) {
			this.direcao = direcao;
		}
	}

	public enum AlinHrz {
		ESQUERDA(SwingConstants.LEFT), DIREITA(SwingConstants.RIGHT), CENTRO(
				SwingConstants.CENTER);
		public int alinhamento;

		private AlinHrz(int alinhamento) {
			this.alinhamento = alinhamento;
		}
	}

	public enum AlinVrt {
		CENTRO(SwingConstants.CENTER), CIMA(SwingConstants.TOP), BAIXO(
				SwingConstants.BOTTOM);
		public int alinhamento;

		private AlinVrt(int alinhamento) {
			this.alinhamento = alinhamento;
		}
	}

	private TipoAlteracao alteracao;
	private Object valor;

	public Change(TipoAlteracao alteracao, Object object) {
		this.alteracao = alteracao;
		if (object != null)
			this.valor = object;
		else
			this.valor = getParDefault(alteracao);
	}

	public Object getType() {
		return this.alteracao;
	}

	public Object getValue() {
		return this.valor;
	}

	public String toString() {
		return alteracao.name() + ": " + valor;
	}

	public boolean equals(Object object) {
		if (object != null) {
			if (object instanceof Change) {
				Change p = (Change) object;
				return this.alteracao.equals(p.alteracao);
			} else
				return false;
		} else
			return false;
	}

	public String getXML() {
		String out = "<" + this.alteracao.name().toLowerCase() + ">"
				+ this.valor + "</" + this.alteracao.name().toLowerCase()
				+ ">\n";
		return out;
	}

	public static Object getParDefault(TipoAlteracao propriedade) {
		switch (propriedade) {
		case TEXTO_VALOR_1:
		case TEXTO_VALOR_2:
		case ACTION:
			return "";
		case STEP:
			return 1;
		case VALOR_MIN:
			return 0;
		case VALOR_MAX:
			return 1;
		case SIZE:
			return 12;
		case LINHAS:
			return 1;
		case COLUNAS:
			return 1;
		case B:
			return false;
		case EDICAO:
			return false;
		case I:
			return false;
		case SELECTED:
			return false;
		case AH:
			return AlinHrz.ESQUERDA;
		case AV:
			return AlinVrt.CIMA;
		case ORIENT:
			return Orientacao.HORIZONTAL;
		case FONTE:
			return Fonte.SANSSERIF;
		case IMAGE_1:
		case IMAGE_2:
			return new File("/");
		default:
			return null;
		}
	}
}
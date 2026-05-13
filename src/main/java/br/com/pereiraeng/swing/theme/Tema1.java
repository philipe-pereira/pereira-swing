package br.com.pereiraeng.swing.theme;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class Tema1 extends DefaultMetalTheme {

	private Color[] naoSei;

	public Tema1() {
		naoSei = new Color[10];
		for (int i = 1; i <= 10; i++) {
			naoSei[i - 1] = Color.MAGENTA;
		}
	}

	/**********************************************************************************************/

	/********************/
	/* CORES DOS TEXTOS */
	/********************/

	// COR DOS TEXTOS DOS BUTÕES, TABELAS, ABAS, MAS NÃO DOS DO MENU
	private Color a = Color.WHITE;

	// COR DO TEXTO QUANDO SELECIONADO
	private Color h = Color.BLACK;

	// COR DO TEXTO DA BARRA DE MENU
	private Color i = Color.WHITE;

	// COR DO TEXTO DA BARRA DE MENU QUANDO O MENU E SELECIONADO
	private Color l = Color.GREEN;

	// COR DOS TEXTOS DOS LABELS (QUANDO NAO ESPECIFICADO)
	private Color r = Color.WHITE;

	// COR DO TEXTO QUE VOCE DIGITA
	private Color t = Color.WHITE;

	// COR DO TEXTO DOS BUTÕES DESATIVADOS
	private Color x = Color.GRAY;

	/**********************/
	/* CORES DAS SELECCAO */
	/**********************/

	// COR DA SELECAO DOS TEXTOS, DAS CELULAS DA TABELA E DAS LISTAS
	private Color s = Color.WHITE;

	// POR ENQUANTO EU SEI QUE ISSO CONTROLA A COR DA OPCAO SELECIONADA DO
	// JCOMBOBOX E (SEI LA PORQUE...) A COR DE FUNDO DA BARRA DO JSCROLL
	private Color o = Color.DARK_GRAY;

	/********************/
	/* CORES DOS FUNDOS */
	/********************/

	// PRINCIPAL - PANELS, BUTÕES
	private Color b = Color.BLACK;

	// FUNDO DA BARRA DE MENU
	private Color j = Color.BLACK;

	// FUNDO DO BUTÃO DO MENU QUANDO SELECIONADO
	private Color k = Color.GRAY;

	// COR DE FUNDO DAS CAIXAS DE TEXTO E TABELAS
	private Color u = new Color(0, 0, 128);

	/***********************/
	/* CORES DOS CONTORNOS */
	/***********************/

	// DETALHES DA SOMBRA DO PRINCIPAL
	private Color c = Color.DARK_GRAY;
	private Color d = Color.GRAY;

	// FOCUS - A CAIXA QUE ENVOLVE O QUE VAI SER SELECIONADO
	private Color g = new Color(0, 0, 128);

	// CONTORNOS DA CAIXAS DO MENU
	private Color m = Color.DARK_GRAY;
	private Color n = Color.GRAY;

	// JSEPARATOR
	private Color p = Color.DARK_GRAY;
	private Color q = Color.GRAY;

	/****************/
	/* OUTRAS CORES */
	/****************/

	// POR ENQUANTO EU SEI QUE ISSO CONTROLA A COR DA SETA DO JCOMBO
	private Color e = Color.RED;

	// LINHA QUE SEPARA O MENU BAR, BUTOES PRESSIONADOS, ABAS NAO ABERTAS,
	// CONTORNOS DAS CAIXAS DE TEXTO, SEPARADOR DAS CELULAS DA TABELA,
	// ENVOLTORIO DOS BOTÕES E RADIO BUTTONS DESATIVADOS
	private Color f = Color.ORANGE;

	// FUNDO DOS TIPS, DETALHES DA BARRA DE SCROLL E COR PRINCIPAL DOS ICONES DO
	// JCHOOSEFILE E JTREE
	private Color v = Color.WHITE;

	// TEXTO DOS TIPS E COR SECUNDARIA DOS ICONES DO JCHOOSEFILE E JTREE
	private Color w = Color.BLACK;

	/**********************************************************************************************/

	// COR DOS TEXTOS DOS BUTÕES, TABELAS, ABAS, MAS NÃO DOS DO MENU
	public ColorUIResource getControlTextColor() {
		return new ColorUIResource(a);
	}

	public ColorUIResource getDesktopColor() {
		return new ColorUIResource(naoSei[0]);
	}

	// COR DO TEXTO DOS BUTÕES INATIVADOS
	public ColorUIResource getInactiveControlTextColor() {
		return new ColorUIResource(x);
	}

	public ColorUIResource getMenuDisabledForeground() {
		return new ColorUIResource(naoSei[1]);
	}

	public String getName() {
		return "Thème nocturne";
	}

	public ColorUIResource getAcceleratorForeground() {
		return new ColorUIResource(naoSei[2]);
	}

	public ColorUIResource getAcceleratorSelectedForeground() {
		return new ColorUIResource(naoSei[3]);
	}

	// PRINCIPAL - PANELS, BUTÕES
	public ColorUIResource getControl() {
		return new ColorUIResource(b);
	}

	// DETALHES DA SOMBRA DO PRINCIPAL
	public ColorUIResource getControlDarkShadow() {
		return new ColorUIResource(c);
	}

	// PRIMO DO OUTRO DETALHE DA SOMBRA
	public ColorUIResource getControlHighlight() {
		return new ColorUIResource(d);
	}

	public ColorUIResource getControlDisabled() {
		return new ColorUIResource(naoSei[4]);
	}

	// POR ENQUANTO EU SEI QUE ISSO CONTROLA A COR DA SETA DO JCOMBO
	public ColorUIResource getControlInfo() {
		return new ColorUIResource(e);
	}

	// LINHA QUE SEPARA O MENU BAR, BUTOES PRESSIONADOS, ABAS NAO ABERTAS,
	// CONTORNOS DAS CAIXAS DE TEXTO, SEPARADOR DAS CELULAS DA TABELA
	public ColorUIResource getControlShadow() {
		return new ColorUIResource(f);
	}

	// FOCUS - A CAIXA QUE ENVOLVE O QUE VAI SER SELECIONADO
	public ColorUIResource getFocusColor() {
		return new ColorUIResource(g);
	}

	// COR DO TEXTO QUANDO SELECIONADO
	public ColorUIResource getHighlightedTextColor() {
		return new ColorUIResource(h);
	}

	public ColorUIResource getInactiveSystemTextColor() {
		return new ColorUIResource(naoSei[5]);
	}

	// FUNDO DA BARRA DE MENU
	public ColorUIResource getMenuBackground() {
		return new ColorUIResource(j);
	}

	// COR DO TEXTO DA BARRA DE MENU
	public ColorUIResource getMenuForeground() {
		return new ColorUIResource(i);
	}

	// FUNDO DO BUTÃO DO MENU QUANDO SELECIONADO
	public ColorUIResource getMenuSelectedBackground() {
		return new ColorUIResource(k);
	}

	// COR DO TEXTO DA BARRA DE MENU QUANDO O MENU E SELECIONADO
	public ColorUIResource getMenuSelectedForeground() {
		return new ColorUIResource(l);
	}

	// CONTORNOS DA CAIXAS DO MENU...
	public ColorUIResource getPrimaryControlDarkShadow() {
		return new ColorUIResource(m);
	}

	// ... E PRIMO
	public ColorUIResource getPrimaryControlHighlight() {
		return new ColorUIResource(n);
	}

	// FUNDO DOS TIPS, DETALHES DA BARRA DE SCROLL E COR PRINCIPAL DOS ICONES DO
	// JCHOOSEFILE E JTREE
	public ColorUIResource getPrimaryControl() {
		return new ColorUIResource(v);
	}

	// TEXTO DOS TIPS E COR SECUNDARIA DOS ICONES DO JCHOOSEFILE E JTREE
	public ColorUIResource getPrimaryControlInfo() {
		return new ColorUIResource(w);
	}

	// SELECIONADO DO JCOMBO BOX
	public ColorUIResource getPrimaryControlShadow() {
		return new ColorUIResource(o);
	}

	// JSeparator...
	public ColorUIResource getSeparatorBackground() {
		return new ColorUIResource(p);
	}

	// ...e o primo dele
	public ColorUIResource getSeparatorForeground() {
		return new ColorUIResource(q);
	}

	// QUANDO VOCE NAO ESPECIFICA A COR DOS TEXTOS DOS LABELS, ESSA E COR QUE
	// FICA
	public ColorUIResource getSystemTextColor() {
		return new ColorUIResource(r);
	}

	// COR DA SELECAO DOS TEXTOS
	public ColorUIResource getTextHighlightColor() {
		return new ColorUIResource(s);
	}

	// COR DO TEXTO QUE VOCE DIGITA
	public ColorUIResource getUserTextColor() {
		return new ColorUIResource(t);
	}

	// COR DE FUNDO DAS CAIXAS DE TEXTO E TABELAS
	public ColorUIResource getWindowBackground() {
		return new ColorUIResource(u);
	}

	public ColorUIResource getWindowTitleBackground() {
		return new ColorUIResource(naoSei[6]);
	}

	public ColorUIResource getWindowTitleForeground() {
		return new ColorUIResource(naoSei[7]);
	}

	public ColorUIResource getWindowTitleInactiveBackground() {
		return new ColorUIResource(naoSei[8]);
	}

	public ColorUIResource getWindowTitleInactiveForeground() {
		return new ColorUIResource(naoSei[9]);
	}
}
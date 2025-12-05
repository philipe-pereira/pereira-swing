package br.com.pereiraeng.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * Componente do tipo <code>JPanel</code> com o gerenciador de layout
 * <code>GridBagLayout</code>, de modo que os componentes são inseridos numa
 * grade, cada um com uma posição, uma largura e uma altura
 * 
 * @author Philipe Pereira
 * 
 */
public class Grade extends JPanel {
	private static final long serialVersionUID = 1L;

	protected GridBagLayout layout;
	protected GridBagConstraints constraints;

	public transient int xMax = 0;
	public transient int yMax = 0;

	/**
	 * Construtor do componente <code>Grade</cdoe>
	 */
	public Grade() {
		this.setLayout();
	}

	/**
	 * Construtor do componente <code>Grade</code> com o qual estabelece-se as
	 * distâncias entre as linhas e colunas
	 * 
	 * @param cima
	 *            distância superior de uma linha com outra
	 * @param direita
	 *            distância pela direita de uma coluna com outra
	 * @param baixo
	 *            distância inferior de uma linha com outra
	 * @param esquerda
	 *            distância pela esquerda de uma coluna com outra
	 */
	public Grade(int cima, int direita, int baixo, int esquerda) {
		this.setLayout();
		this.setSeparation(cima, direita, baixo, esquerda);
	}

	/**
	 * Função que estabelece as distâncias entre as linhas e colunas da grade
	 * 
	 * @param cima
	 *            distância superior de uma linha com outra
	 * @param direita
	 *            distância pela direita de uma coluna com outra
	 * @param baixo
	 *            distância inferior de uma linha com outra
	 * @param esquerda
	 *            distância pela esquerda de uma coluna com outra
	 */
	public void setSeparation(int cima, int direita, int baixo, int esquerda) {
		this.constraints.insets = new Insets(cima, direita, baixo, esquerda);
	}

	/**
	 * Função que cria o gerenciador de layout nas condições padrão
	 */
	private void setLayout() {
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.PAGE_START;

		super.setLayout(layout);
	}

	/**
	 * Função que adiciona um componente a esta grade na posição e com a altura
	 * e largura indicadas
	 * 
	 * @param component
	 *            componente a ser inserido na grade
	 * @param column
	 *            coordenada horizontal do componente
	 * @param row
	 *            coordenada vertical do componente
	 * @param width
	 *            largura do componente
	 * @param height
	 *            altura do componente
	 */
	public void add(Component component, int column, int row, int width, int height) {
		this.add(component, column, row, width, height, GridBagConstraints.BOTH, 0, 0);
	}

	/**
	 * Função que adiciona um componente a esta grade na posição e com a altura
	 * e largura indicadas, definindo uma política de redimensionamento caso
	 * haja espaço sobrando
	 * 
	 * @param component
	 *            componente a ser inserido na grade
	 * @param column
	 *            coordenada horizontal do componente
	 * @param row
	 *            coordenada vertical do componente
	 * @param width
	 *            largura do componente
	 * @param height
	 *            altura do componente
	 * @param fill
	 *            política de redimensionamento
	 */
	public void add(Component component, int column, int row, int width, int height, int fill) {
		this.add(component, column, row, width, height, fill, 0, 0);
	}

	/**
	 * Função que adiciona um componente a esta grade na posição e com a altura
	 * e largura indicadas, definindo o quanto o componente pode expandir numa
	 * dada direção caso haja espaço sobrando
	 * 
	 * @param component
	 *            componente a ser inserido na grade
	 * @param column
	 *            coordenada horizontal do componente
	 * @param row
	 *            coordenada vertical do componente
	 * @param width
	 *            largura do componente
	 * @param height
	 *            altura do componente
	 * @param extraWidth
	 *            espaço extra na direção X
	 * @param extraHeight
	 *            espaço extra na direção Y
	 */
	public void add(Component component, int column, int row, int width, int height, int extraWidth, int extraHeight) {
		this.add(component, column, row, width, height, GridBagConstraints.BOTH, extraWidth, extraHeight);
	}

	/**
	 * Função que adiciona um componente a esta grade na posição e com a altura
	 * e largura indicadas, definindo uma política de redimensionamento e o
	 * quanto o componente pode expandir numa dada direção caso haja espaço
	 * sobrando
	 * 
	 * @param component
	 *            componente a ser inserido na grade
	 * @param column
	 *            coordenada horizontal do componente
	 * @param row
	 *            coordenada vertical do componente
	 * @param width
	 *            largura do componente
	 * @param height
	 *            altura do componente
	 * @param fill
	 *            política de redimensionamento
	 * @param extraWidth
	 *            espaço extra na direção X
	 * @param extraHeight
	 *            espaço extra na direção Y
	 */
	public void add(Component component, int column, int row, int width, int height, int fill, int extraWidth,
			int extraHeight) {
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;

		constraints.weightx = extraWidth;
		constraints.weighty = extraHeight;

		constraints.fill = fill;

		this.xMax = (this.xMax > column + width ? this.xMax : column + width);
		this.yMax = (this.yMax > row + height ? this.yMax : row + height);

		layout.setConstraints(component, constraints);
		add(component);
	}

	/**
	 * Função que adiciona um componente a esta grade na posição e com a altura
	 * e largura indicadas
	 * 
	 * @param panel
	 *            painel onde o componente será adicionado
	 * @param component
	 *            componente a ser inserido na grade
	 * @param column
	 *            coordenada horizontal do componente
	 * @param row
	 *            coordenada vertical do componente
	 * @param width
	 *            largura do componente
	 * @param height
	 *            altura do componente
	 */
	public static void add(JPanel panel, Component component, int column, int row, int width, int height) {
		GridBagLayout layout = null;

		// verifica se o gerenciador de layout é mesmo o GridBag
		if (panel.getLayout() instanceof GridBagLayout)
			layout = (GridBagLayout) panel.getLayout();
		else
			panel.setLayout(layout = new GridBagLayout());

		// cria o objeto com as informações sobre onde o componente deve ficar e
		// ser dimensionado
		GridBagConstraints defaultGBL = new GridBagConstraints();

		GridBagConstraints constraints = new GridBagConstraints(column, row, width, height, defaultGBL.weightx,
				defaultGBL.weighty, GridBagConstraints.PAGE_START, defaultGBL.fill, defaultGBL.insets, defaultGBL.ipadx,
				defaultGBL.ipady);

		// adiciona o componente ao painel
		layout.setConstraints(component, constraints);
		panel.add(component);
	}
}
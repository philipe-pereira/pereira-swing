package br.com.pereiraeng.swing.input.mtz;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.pereiraeng.core.collections.ArrayUtils;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico que permite a inserção e edição de matrizes de
 * número decimais
 * 
 * @author Philipe PEREIRA
 *
 */
public class MatrizInput extends Grade implements Input<double[][]>, DocumentListener, FocusListener {
	private static final long serialVersionUID = 1L;

	private int rows, columns, cellWidth;

	/**
	 * se <code>true</code> para as caixas de entrada serem editáveis,
	 * <code>false</code> se não
	 */
	private transient boolean editable;

	/**
	 * se <code>true</code>, considera-se que a matriz é simétrica, impedindo-se que
	 * se faça edições nas células acima da diagonal
	 */
	private boolean sim;

	/**
	 * Construtor do objeto gráfico de edição de matrizes
	 * 
	 * @param rows      número de linhas
	 * @param columns   número de colunas
	 * @param cellWidth largula da célula (em número de caracteres)
	 * 
	 */
	public MatrizInput(int rows, int columns, int cellWidth) {
		this(rows, columns, cellWidth, false);
	}

	/**
	 * Construtor do objeto gráfico de edição de matrizes
	 *
	 * @param m         array de duas dimensões
	 * @param cellWidth largula da célula (em número de caracteres)
	 */
	public MatrizInput(double[][] m, int cellWidth) {
		this(m, cellWidth, checkSim(m));
	}

	/**
	 * Construtor do objeto gráfico de edição de matrizes
	 *
	 * @param m         array de duas dimensões
	 * @param cellWidth largula da célula (em número de caracteres)
	 * @param sim       se <code>true</code>, considera-se que a matriz é simétrica
	 *                  (i.e. impede-se que se faça edições nas células acima da
	 *                  diagonal)
	 */
	public MatrizInput(double[][] m, int cellWidth, boolean sim) {
		this(m.length, m[m.length - 1].length, cellWidth, sim);
		set(m);
	}

	/**
	 * Construtor do objeto gráfico de edição de matrizes
	 * 
	 * @param rows      número de linhas
	 * @param columns   número de colunas
	 * @param cellWidth largula da célula (em número de caracteres)
	 * @param sim       se <code>true</code>, considera-se que a matriz é simétrica
	 *                  (i.e. impede-se que se faça edições nas células acima da
	 *                  diagonal)
	 */
	public MatrizInput(int rows, int columns, int cellWidth, boolean sim) {
		this.rows = rows;
		this.columns = columns;
		this.cellWidth = cellWidth;
		this.sim = sim && (getRowsCount() == getColumnsCount());
		this.editable = true;

		Parenteses p = new Parenteses(true);
		p.setRows(rows);
		JLabel l = new JLabel(p);
		add(l, 0, 0, 1, 100);

		p = new Parenteses(false);
		p.setRows(rows);
		l = new JLabel(p);
		add(l, 100, 0, 1, 100);

		this.createCells();
	}

	private void createCells() {
		for (int r = 0; r < getRowsCount(); r++) {
			for (int c = 0; c < getColumnsCount(); c++) {
				JTextField t = new JTextField(cellWidth);
				add(t, c + 1, r, 1, 1);

				if (this.sim && c > r) {
					// se a matriz for necessariamente simétrica, as células
					// acima da diagonal não são editáveis e não recebe os
					// listeners
					t.setEditable(false);
				} else {
					t.setEditable(this.editable);

					t.addFocusListener(this);

					// as coordenadas são dadas na forma 'linha';'coluna'
					t.getDocument().putProperty("name", String.format("%d;%d", r, c));
					t.getDocument().addDocumentListener(this);
				}
			}
		}
	}

	private JTextField getTextField(int r, int c) {
		if (r >= getRowsCount() || c >= getColumnsCount())
			return null;
		return (JTextField) this.getComponent(2 + getColumnsCount() * r + c);
	}

	public void setRows(int rows) {
		changeOrder(rows, getColumnsCount());
	}

	public void setColumns(int columns) {
		changeOrder(getRowsCount(), columns);
	}

	/**
	 * Função que retorna o valor máximo entre o número de colunas e o número de
	 * linhas
	 * 
	 * @return o maior número entre o número de colunas e o número de linhas
	 */
	public int getOrder() {
		return Math.max(getColumnsCount(), getRowsCount());
	}

	/**
	 * Função que altera o tamanho da matriz a ser editada
	 * 
	 * @param newRows    novo número de linhas
	 * @param newColumns novo número de colunas
	 */
	public void changeOrder(int newRows, int newColumns) {
		double[][] oldValues = get();
		// remover células antigas
		int t = getRowsCount() * getColumnsCount();
		for (int i = 0; i < t; i++)
			this.remove(this.getComponent(2));

		// setar novos valores
		boolean b = this.rows != newRows;
		this.rows = newRows;
		if (b) {
			((Parenteses) ((JLabel) this.getComponent(0)).getIcon()).setRows(this.rows);
			((Parenteses) ((JLabel) this.getComponent(1)).getIcon()).setRows(this.rows);
		}
		this.columns = newColumns;

		// novas células
		this.createCells();
		this.validate();
		this.repaint();

		double[][] newValues = new double[getRowsCount()][getColumnsCount()];
		for (int i = 0; i < Math.min(oldValues.length, newValues.length); i++)
			for (int j = 0; j < Math.min(oldValues[i].length, newValues[i].length); j++)
				newValues[i][j] = oldValues[i][j];
		set(newValues);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ---------- getter and setters ----------

	public int getRowsCount() {
		return rows;
	}

	public int getColumnsCount() {
		return columns;
	}

	/**
	 * Função que estabelece se as células da matriz podem ser editadas ou não
	 * 
	 * @param b se <code>true</code> então a matriz pode ser editada,
	 *          <code>false</code> senão
	 */
	public void setEditable(boolean b) {
		if (b ^ editable) {
			// se for diferente, altera
			this.editable = b;
			for (int r = 0; r < getRowsCount(); r++)
				for (int c = 0; c < getColumnsCount(); c++)
					getTextField(r, c).setEditable(this.editable && !(this.sim && c > r));
		}
	}

	/**
	 * <p>
	 * Essa matriz é somente uma cópia daquela que foi
	 * {@link MatrizInput#set(double[][]) inserida}, de modo que as alterações nela
	 * não incidirão sobre a matriz editada (não se o usuário não quiser; caso ela
	 * seja editada, notificações serão enviadas através do {@link #listener}).
	 * </p>
	 * 
	 * <p>
	 * Manter essa cópia é interessante nos casos em que o usuário inseriu uma
	 * informação não válida nas caixas de texto e quero voltar com o valor antigo
	 * (ver {@link #focusLost(FocusEvent)}).
	 * </p>
	 */
	private transient double[][] matEdi;

	@Override
	public double[][] get() {
		return ArrayUtils.copyOf(matEdi);
	}

	@Override
	public void set(double[][] mat) {
		if (mat != null) {
			if (mat.length == getRowsCount() && mat[mat.length - 1].length == getColumnsCount()) {
				// aceite-se somente matrizes com o tamanho que o input é capaz
				// de aceitar
				matEdi = ArrayUtils.copyOf(mat);
				refresh();
			}
		} else {
			// se a matriz for nula, deve-se limpar os campos... mas o editor
			// não pode nunca ter um objeto mat null (isso pois o objeto é quem
			// guarda a informações de quantas linhas e colunas tem esse editor)
			matEdi = new double[getRowsCount()][getColumnsCount()];
			refresh();
		}
	}

	public void clear() {
		set(null);
	}

	private void refresh() {
		for (int r = 0; r < getRowsCount(); r++)
			for (int c = 0; c < getColumnsCount(); c++)
				refreshTextField(r, c);
	}

	/**
	 * Função que atualiza a caixa de texto sem acionar os escutadores associados
	 * 
	 * @param r linha da célula atualizada
	 * @param c coluna da célula atualizada
	 */
	private void refreshTextField(int r, int c) {
		this.innerChange = true;
		(getTextField(r, c)).setText(String.format("%.3g", c < matEdi[r].length ? matEdi[r][c] : matEdi[c][r]));
		this.innerChange = false;
	}

	// --------------------------- LISTENER ---------------------------

	private transient ChangeListener listener;

	public void addChangeListener(ChangeListener listener) {
		this.listener = listener;
	}

	private transient boolean editing = false, innerChange = true;

	@Override
	public void changedUpdate(DocumentEvent event) {
		// Plain text components do not fire these events
	}

	@Override
	public void insertUpdate(DocumentEvent event) {
		if (!innerChange)
			update(event);
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		if (!innerChange)
			update(event);
	}

	private void update(DocumentEvent event) {
		this.editing = true;

		// procurar as coordenadas da caixa de texto
		String coord = (String) event.getDocument().getProperty("name");
		int[] c = getCoordinates(coord);
		JTextField tf = getTextField(c[0], c[1]);

		try {
			// a medida que a caixa de texto vai sendo preenchida, já vai
			// alterando os valores no vetor de double's
			this.matEdi[c[0]][c[1]] = Double.parseDouble(tf.getText().replace(',', '.'));

			// se a matriz for obrigatoriamente simétrica, já se escreve um
			// valor idêntico na matriz triangular superior
			if (this.sim && c[0] != c[1]) {
				if (c[0] < this.matEdi[c[1]].length)
					this.matEdi[c[1]][c[0]] = this.matEdi[c[0]][c[1]];
				refreshTextField(c[1], c[0]);
			}

			if (this.listener != null)
				this.listener.stateChanged(new ChangeEvent(this));
		} catch (NumberFormatException e) {
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		this.innerChange = true;
		JTextField tf = (JTextField) event.getSource();
		tf.selectAll();
		this.innerChange = false;
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (this.editing) {
			JTextField tf = (JTextField) event.getSource();
			try {
				// se o valor que foi inserido ao longo do processo de edição
				// for válido, não acontece nada (pois o vetor de double's já
				// vinha sendo alterado)
				Double.parseDouble(tf.getText().replace(',', '.'));
				tf.setCaretPosition(0);
			} catch (NumberFormatException e) {
				// se o valor inserido não é válido, retorna a mostrar o último
				// valor que foi validado
				int[] c = getCoordinates((String) tf.getDocument().getProperty("name"));
				this.refreshTextField(c[0], c[1]);
			}
			this.editing = false;
		}
	}

	// ------------------------ AUXILIARES ------------------------

	/**
	 * Função que indica o par de coordenadas da caixa de texto a partir do nome
	 * desta
	 * 
	 * @param docName nome da caixa de texto
	 * @return vetor com duas posições indicando as coordenadas da caixa de texto na
	 *         matriz
	 */
	private static int[] getCoordinates(String docName) {
		String[] d = docName.split(";");
		return new int[] { Integer.parseInt(d[0]), Integer.parseInt(d[1]) };
	}

	/**
	 * Função que verifica se uma matriz é simétrica (a característica de um vetor
	 * de vetor deve ter para representar uma matriz simétrica é a de que ele possua
	 * em cada linha o número de colunas igual ao da sua posição mais 1, sendo
	 * portanto uma matriz triangular; supõe-se assim que a parte triangular
	 * superior seja igual a inferior)
	 * 
	 * @param m matriz
	 * @return <code>true</code> se for triangular inferior, <code>false</code> se
	 *         não
	 */
	private static boolean checkSim(double[][] m) {
		for (int r = 0; r < m.length; r++)
			if (m[r].length != r + 1)
				return false;
		return true;
	}
}
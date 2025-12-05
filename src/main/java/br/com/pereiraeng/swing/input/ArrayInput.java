package br.com.pereiraeng.swing.input;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.mtz.MatrizInput;

/**
 * Classe do objeto gráfico que permite a inserção e edição de vetores de número
 * decimais
 * 
 * @author Philipe PEREIRA
 *
 */
public class ArrayInput extends Grade implements Input<float[]>, DocumentListener, FocusListener {
	private static final long serialVersionUID = 1L;

	private int rows, cellWidth;

	/**
	 * se <code>true</code> para as caixas de entrada serem editáveis,
	 * <code>false</code> se não
	 */
	private transient boolean editable;

	/**
	 * Construtor do objeto gráfico de edição de vetores
	 * 
	 * @param m         array
	 * @param cellWidth largula da célula (em número de caracteres)
	 */
	public ArrayInput(float[] m, int cellWidth) {
		this(m.length, cellWidth);
		set(m);
	}

	/**
	 * Construtor do objeto gráfico de edição de vetores
	 * 
	 * @param rows      número de posições do vetor
	 * @param cellWidth largula da célula (em número de caracteres)
	 */
	public ArrayInput(int rows, int cellWidth) {
		this.rows = rows;
		this.cellWidth = cellWidth;
		this.editable = true;

		this.createCells();
	}

	private void createCells() {
		for (int r = 0; r < getRowsCount(); r++) {
			JTextField t = new JTextField(cellWidth);
			add(t, 0, r, 1, 1);

			t.setEditable(this.editable);

			t.addFocusListener(this);

			// as coordenadas são dadas na forma 'linha';'coluna'
			t.getDocument().putProperty("name", String.valueOf(r));
			t.getDocument().addDocumentListener(this);
		}
	}

	private JTextField getTextField(int r) {
		if (r >= getRowsCount())
			return null;
		return (JTextField) this.getComponent(r);
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
				getTextField(r).setEditable(this.editable);
		}
	}

	/**
	 * Função que altera o tamanho da matriz a ser editada
	 * 
	 * @param newRows    novo número de linhas
	 * @param newColumns novo número de colunas
	 */
	public void changeOrder(int newRows) {
		// remover células antigas
		int t = getRowsCount();
		for (int i = 0; i < t; i++)
			this.remove(this.getComponent(0));

		// novas células
		this.createCells();
		this.validate();
		this.repaint();
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// ---------- getter and setters ----------

	public int getRowsCount() {
		return rows;
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
	private transient float[] matEdi;

	@Override
	public float[] get() {
		return Arrays.copyOf(matEdi, matEdi.length);
	}

	@Override
	public void set(float[] mat) {
		if (mat != null) {
			if (mat.length == getRowsCount()) {
				// aceite-se somente matrizes com o tamanho que o input é capaz
				// de aceitar
				matEdi = Arrays.copyOf(mat, mat.length);
				refresh();
			}
		} else {
			// se a matriz for nula, deve-se limpar os campos... mas o editor
			// não pode nunca ter um objeto mat null (isso pois o objeto é quem
			// guarda a informações de quantas linhas e colunas tem esse editor)
			matEdi = new float[getRowsCount()];
			refresh();
		}
	}

	public void clear() {
		set(null);
	}

	private void refresh() {
		for (int r = 0; r < getRowsCount(); r++)
			refreshTextField(r);
	}

	/**
	 * Função que atualiza a caixa de texto sem acionar os escutadores associados
	 * 
	 * @param r linha da célula atualizada
	 */
	private void refreshTextField(int r) {
		this.innerChange = true;
		getTextField(r).setText(String.format("%.3g", matEdi[r]));
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
		int c = Integer.parseInt((String) event.getDocument().getProperty("name"));
		JTextField tf = getTextField(c);

		try {
			// a medida que a caixa de texto vai sendo preenchida, já vai
			// alterando os valores no vetor de double's
			this.matEdi[c] = Float.parseFloat(tf.getText().replace(',', '.'));

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
				int c = Integer.parseInt((String) tf.getDocument().getProperty("name"));
				this.refreshTextField(c);
			}
			this.editing = false;
		}
	}
}

package br.com.pereiraeng.swing.input;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.pereiraeng.core.ReflectionUtils;
import br.com.pereiraeng.swing.table.ResizableTable;

/**
 * Classe dos objetos gráfico que representam uma tabela com duas colunas para
 * inserção manual de tabelas de dispersão
 * 
 * @author Philipe PEREIRA
 *
 */
public class MapInput<K, V> extends ResizableTable implements Input<Map<K, V>>, ActionListener {
	private static final long serialVersionUID = 1L;

	private Class<?> class1, class2;

	/**
	 * Construtor do objeto gráfico editor de tabelas de dispersão
	 * 
	 * @param class1 classe dos objetos chave
	 * @param class2 classe dos objetos valor
	 */
	public MapInput(Class<?> class1, Class<?> class2) {
		this(false, class1, class2);
	}

	/**
	 * Construtor do objeto gráfico editor de tabelas de dispersão
	 * 
	 * @param paste  <code>true</code> para incluir um botão 'colar' (este botão, ao
	 *               ser clicado, gera um evento que será ouvido pelo
	 *               {@link ActionListener} adicionados à esta tabela e com o
	 *               comando {@link ResizableTable#PASTE})
	 * @param class1 classe dos objetos chave
	 * @param class2 classe dos objetos valor
	 */
	public MapInput(boolean paste, Class<?> class1, Class<?> class2) {
		this(paste, new HashMap<K, V>());
		this.class1 = class1;
		this.class2 = class2;
	}

	/**
	 * Construtor do objeto gráfico editor de tabelas de dispersão
	 * 
	 * @param map tabela a ser editada
	 */
	public MapInput(Map<K, V> map) {
		this(false, map);
	}

	public MapInput(boolean paste, Map<K, V> map) {
		super(new String[] { "Entrada", "Saída" }, paste);
		if (map.size() != 0) {
			Entry<?, ?> entry = map.entrySet().iterator().next();
			this.class1 = entry.getKey().getClass();
			this.class2 = entry.getValue().getClass();
		}
		this.set(map);
	}

	@Override
	protected void insertRow(int row) {
		Object[] emptyRow = ReflectionUtils.getNulls(class1, class2);
		super.insertRow(row, emptyRow);
	}

	// ------------------------- INPUT -------------------------

	@Override
	public void set(Map<K, V> map) {
		if (map == null) // clear
			setRowCount(0);
		else {
			// ajusta o tamanho da tabela
			setRowCount(map.size());
			// repassa para a interface gráfica
			int r = 0;
			for (Entry<?, ?> e : map.entrySet()) {
				setValueAt(e.getKey(), r, 0);
				setValueAt(e.getValue(), r, 1);
				r++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> get() {
		// LinkedHashMap pois, se é para manter a ordem com que os elementos foram
		// inseridos, OK; senão, embaralha depois, mas não agora...
		Map<K, V> newMap = new LinkedHashMap<>();
		// unload table
		int rows = super.getRowCount();
		for (int r = 0; r < rows; r++) {
			Object i = super.getValueAt(r, 0);
			Object o = super.getValueAt(r, 1);
			newMap.put((K) i, (V) o);
		}
		return newMap;
	}

	@Override
	public Component getComponent() {
		return this;
	}
}
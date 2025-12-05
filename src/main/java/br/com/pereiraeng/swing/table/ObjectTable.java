package br.com.pereiraeng.swing.table;

import java.awt.Component;

import br.com.pereiraeng.core.StringUtils;
import br.com.pereiraeng.core.split.Property;
import br.com.pereiraeng.core.split.Splitable;
import br.com.pereiraeng.core.split.Spliter;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico de uma tabela com uma única coluna que exibe em cada
 * linha os diferentes campos de um {@link Splitable objeto particionável}
 * 
 * 
 * @author Philipe Pereira
 * 
 */
public class ObjectTable extends OneColumnTable implements Input<Splitable> {
	private static final long serialVersionUID = 1L;

	private Splitable object;

	public ObjectTable(Splitable object) {
		super(getFieldsDefault(object));
	}

	// --------------------- INPUT ---------------------

	/**
	 * Função que estabelece qual objeto está sendo exibido na tabela
	 * 
	 * @param object
	 *            objeto a ser exibido
	 */
	@Override
	public void set(Splitable object) {
		this.object = object;
		fillTableFromObjectData();
	}

	/**
	 * Função que retorna o objeto que está sendo exibido
	 * 
	 * @return objeto exibido
	 */
	@Override
	public Splitable get() {
		rebuildObjectFromTableData();
		return object;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	// --------------------- SPLITABLE ---------------------

	private void fillTableFromObjectData() {
		Property[] fields = Spliter.getFields(object);
		for (int i = 0; i < fields.length; i++)
			super.set(i, fields[i].getValue());
	}

	private void rebuildObjectFromTableData() {
		for (int i = 0; i < super.modelRowHeader.size(); i++) {
			Object o = super.model.getValueAt(i, 0);
			Property p = new Property(StringUtils.removeSpace(super.modelRowHeader.get(i)), o, o.getClass(), true);
			Spliter.setFields(object, p);
		}
	}

	/**
	 * Função que a partir do {@link Splitable objeto} gera uma matriz com duas
	 * linhas, uma com os nomes dos campos do objeto, a segunda com seus
	 * valores. Este método é utilizado para se obter o argumento do
	 * {@link OneColumnTable#OneColumnTable(Object[][]) construtor especial} da
	 * tabela de uma coluna.
	 * 
	 * @param object
	 *            objeto cujos campos serão utilizados para preencher a matriz
	 * @return matriz com duas linhas
	 */
	private static Object[][] getFieldsDefault(Splitable object) {
		Property[] fields = Spliter.getFields(object);

		Object[][] out = new Object[][] { new String[fields.length], new Object[fields.length] };

		for (int i = 0; i < fields.length; i++) {
			out[0][i] = fields[i].getEditionName();
			out[1][i] = fields[i].getValue();
		}

		return out;
	}
}
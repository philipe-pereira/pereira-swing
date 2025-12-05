package br.com.pereiraeng.swing.table;

import java.awt.Component;

import br.com.pereiraeng.core.StringUtils;
import br.com.pereiraeng.core.split.Property;
import br.com.pereiraeng.core.split.Splitable;
import br.com.pereiraeng.core.split.Spliter;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico de uma tabela que exibe em cada linha um
 * {@link Splitable objeto particionável}, sendo que cada coluna mostra um campo
 * 
 * @author Philipe PEREIRA
 * 
 */
public class ObjectsTable extends EditableTable implements Input<Splitable[]> {
	private static final long serialVersionUID = 1L;

	/**
	 * Lista dos objetos exibidos na tabela
	 */
	private Splitable[] objects;

	/**
	 * Construtor da tabela de objetos
	 * 
	 * @param objects        lista de objetos a serem exibidos na tabela
	 * @param size           dimensões da tabela
	 * @param rowHeight      altura da linha
	 * @param columnMaxWidth largura máxima das colunas
	 * @param modelListener  listener de alterações na tabela
	 */
	public ObjectsTable(Splitable[] objects) {
		this.set(objects);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void set(Splitable[] objects) {
		this.objects = objects;
		this.setTableContent();
	}

	private void setTableContent() {
		// o cabeçalho da linhas é a numeração
		String[] rows = new String[this.objects.length];
		for (int i = 0; i < this.objects.length; i++)
			rows[i] = String.valueOf(i + 1);

		String[] cols = Spliter.getFieldsName(this.objects[0]);

		// uma lista de célula para cada linha/objeto
		Object[][] cells = new Object[this.objects.length][cols.length];

		// para cada objeto
		for (int i = 0; i < this.objects.length; i++) {
			Property[] fields = Spliter.getFields(this.objects[i]);

			// lista de variáveis do objeto
			Object[] objs = new Object[fields.length];
			for (int j = 0; j < fields.length; j++)
				objs[i] = fields[i].getValue();

			cells[i] = objs;
		}

		super.setDataVector(cells, cols);
		super.setRowIdentifiers(rows);
	}

	@Override
	public Splitable[] get() {
		this.rebuildObjectFromTableData();
		return this.objects;
	}

	private void rebuildObjectFromTableData() {
		for (int i = 0; i < this.objects.length; i++) {
			for (int j = 0; j < super.model.getColumnCount(); j++) {
				Object o = super.model.getValueAt(i, j);
				Property p = new Property(StringUtils.removeSpace(super.model.getColumnName(j)), o, o.getClass(), true);
				Spliter.setFields(objects[i], p);
			}
		}
	}
}

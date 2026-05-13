package br.com.pereiraeng.swing.table;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.pereiraeng.core.ReflectionUtils;
import br.com.pereiraeng.swing.SwingUtils;

/**
 * Classe do objeto gráfico de uma {@link JTable tabela} em que é possível usar
 * novos comandos a partir do teclado:
 * 
 * <ul>
 * <li>o comando <code>Ctrl+X</code> para recortar dados da tabela (i.e.,
 * removê-los da tabela e enviá-los para a área de transferência);</i>
 * <li>o comando <code>Ctrl+V</code> para colar dados na tabela (i.e., inserir
 * na tabela dados tabulados existentes na área de transferência);</i>
 * <li>o comando <code>Delete</code> para apagar mais de uma linha</i>
 * </ul>
 * 
 * Vale lembrar que o comando <code>Ctrl+C</code> (copiar para a área de
 * transferência) é herdado de {@link JTable tabela}, não precisando ser
 * reimplementado.
 * 
 * @author Philipe PEREIRA
 *
 */
public class AdvTable extends JTable implements KeyListener {
	private static final long serialVersionUID = 1L;

	public AdvTable() {
		super();
		addKeyListener(this);
	}

	public AdvTable(TableModel dm) {
		super(dm);
		addKeyListener(this);
	}

	// ----------------- KEY LISTENING -----------------

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) { // se apertar CTRL
			if (e.getKeyChar() == 24) { // se apertar X (recortar)
				int[] rows = this.getSelectedRows();
				TableModel tm = this.getModel();

				StringBuilder builder = new StringBuilder();
				int cols = this.getColumnModel().getColumnCount();
				for (int i = 0; i < rows.length; i++) {
					for (int j = 0; j < cols; j++) {
						builder.append(tm.getValueAt(rows[i], j));
						if (j != cols - 1)
							builder.append("\t");
					}
					builder.append("\n");
				}
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(builder.toString()), null);

				delete(tm, rows);
			} else if (e.getKeyChar() == 22) // se apertar V (colar)
				AdvTable.paste(this.getSelectedRow(), 0, SwingUtils.getClipBoardContent(SwingUtils.getWindow(this)), false,
						this.getModel(), null, false);
		} else if (e.getKeyChar() == 127) { // se apertar o DEL
			if (isEditing())
				getCellEditor().stopCellEditing();
			int[] rows = this.getSelectedRows();
			TableModel tm = this.getModel();
			delete(tm, rows);
		}
	}

	private void delete(TableModel tm, int[] rows) {
		for (int c = 0; c < tm.getColumnCount(); c++) {
			Object o = null;
			if (tm instanceof AbstractTableModel)
				o = ReflectionUtils.getNull(((AbstractTableModel) tm).getColumnClass(c));
			for (int i = 0; i < rows.length; i++)
				tm.setValueAt(o, rows[i], c);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// ----------------- AUXILIARES -----------------

	/**
	 * Função que cola o conteúdo da área de trabalho na tabela
	 * 
	 * @param rowOffset    índice da linha onde deve-se começar a colar
	 * @param columnOffset índice da coluna onde deve-se começar a colar
	 * @param content      conteúdo da área de trabalho
	 * @param resizeRows   <code>true</code>, para mudar o número de linhas (se o
	 *                     modelo de tabela permitir isso); <code>false</code> para
	 *                     manter o número de linhas
	 * @param tableModel   modela da tabela
	 * @param rowHeader    cabeçalho das linhas (pode ser <code>null</code>, caso
	 *                     não houver)
	 * @param d            <code>true</code> para {@link Double#valueOf(String)
	 *                     converter} o texto em um número decimal;
	 *                     <code>false</code> para manter a sequência de caracteres
	 */
	public static void paste(int rowOffset, int columnOffset, String content, boolean resizeRows, TableModel tableModel,
			DefaultListModel<String> rowHeader, boolean d) {
		if (content != null) {
			// cabeçalho das linhas
			String[] rows = content.split("\n");

			int lastRow = rowOffset + rows.length;
			if (resizeRows && tableModel instanceof DefaultTableModel) {
				((DefaultTableModel) tableModel).setRowCount(lastRow);
				if (rowHeader != null) {
					while (rowHeader.size() < lastRow)
						rowHeader.addElement(String.valueOf(rowHeader.size() + 1));
					while (rowHeader.size() > lastRow)
						rowHeader.removeElementAt(rowHeader.size() - 1);
				}
			} else
				lastRow = Math.min(lastRow, tableModel.getRowCount());

			// conteúdo das tabelas
			int rowContent = 0;
			for (int row = rowOffset; row < lastRow; row++) {
				String[] columns = rows[rowContent++].split("\t");
				int columnContent = 0;

				int lastColumn = columnOffset + columns.length;
				lastColumn = Math.min(lastColumn, tableModel.getColumnCount());
				for (int column = columnOffset; column < lastColumn; column++) {
					String cell = columns[columnContent++];
					Object obj = null;
					if (d) {
						try {
							obj = Double.valueOf(cell.replace(',', '.'));
						} catch (NumberFormatException e) {
						}
					} else
						obj = cell;
					tableModel.setValueAt(obj, row, column);
				}
			}
		}
	}
}
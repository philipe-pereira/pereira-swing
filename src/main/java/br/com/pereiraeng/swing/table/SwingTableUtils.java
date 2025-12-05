package br.com.pereiraeng.swing.table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.table.AbstractTableModel;

public class SwingTableUtils {

	/**
	 * Função que exporta o conteúdo do {@link AbstractTableModel modelo de uma
	 * tabela} para um arquivo CSV
	 * 
	 * @param file       arquivo a ser criado
	 * @param tableModel modelo de uma tabela
	 */
	public static void exportCSV(File file, AbstractTableModel tableModel) {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

			int columnCount = tableModel.getColumnCount();
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				if (columnIndex > 0)
					randomAccessFile.writeBytes(";");
				randomAccessFile.writeBytes(String.format("%s", tableModel.getColumnName(columnIndex)));
			}
			randomAccessFile.writeBytes("\r\n");

			int rowCount = tableModel.getRowCount();
			for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					if (columnIndex > 0)
						randomAccessFile.writeBytes(";");
					randomAccessFile.writeBytes(String.format("%s", tableModel.getValueAt(rowIndex, columnIndex)));
				}
				randomAccessFile.writeBytes("\r\n");
			}
			randomAccessFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

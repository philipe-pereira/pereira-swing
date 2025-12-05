package br.com.pereiraeng.swing.longtask;

/**
 * Interface das classes que devem ser notificadas pelo final da execução de um
 * processo longo
 * 
 * @author Philipe PEREIRA
 *
 */
public interface Notifier {

	public enum CalcStatus {
		OK, MEMO, OCUP, POST
	}

	/**
	 * Função que é chamada na classe notificada para se indicar que um processo
	 * longo terminou
	 * 
	 * @param source
	 * @param status
	 *            <ul start="0">
	 *            <li>{@link CalcStatus#OK OK};</i>
	 *            <li>{@link CalcStatus#MEMO memória};</i>
	 *            <li>{@link CalcStatus#OCUP ocupado};</i>
	 *            <li>{@link CalcStatus#POST pós-cálculo}.</i>
	 *            </ul>
	 */
	public void taskDone(LongTask<?> source, CalcStatus status);
}

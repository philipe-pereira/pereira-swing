package br.com.pereiraeng.swing.longtask;

/**
 * Interface das classes que contém um rotina cuja execução leva muito tempo,
 * sendo assim necessário criar uma linha de execução paralela e permitir a
 * interação com o usuário
 * 
 * @author Philipe PEREIRA
 *
 * @param <K>
 *            classe do objeto obtido ao final da rotina de longa duração
 */
public interface LongTask<K> {

	/**
	 * Função que é chamada ao se dar o {@link LongTaskManager#execute() comando
	 * de início} de processamento
	 * 
	 * @param ltm
	 *            objeto que coordena a interação entre a execução de uma tarefa
	 *            de longa duração e a atualização dos objetos gráficos que
	 *            mostram ao usuário em que estado está a execução da rotina
	 * @return objeto obtido ao final da rotina de longa duração
	 */
	public K run(LongTaskManager<K> ltm);

	/**
	 * Função que é chamada ao final da execução
	 * 
	 * @param k
	 *            objeto obtido ao final da rotina de longa duração
	 */
	public void afterRun(K k);
}

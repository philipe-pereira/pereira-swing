package br.com.pereiraeng.swing.longtask;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

/**
 * Classe dos objeto que coordenam a <strong>execução</strong> de uma tarefa
 * longa duração e sua a <strong>interação</strong> com o usuário através da
 * atualização dos objetos gráficos que lhe mostram o estado em que se encontra
 * 
 * @author Philipe PEREIRA
 *
 * @param <K> classe do objeto que é retornado após o cômputo
 */
public class LongTaskManager<K> extends SwingWorker<K, String> implements PropertyChangeListener {

	private JProgressBar pb;

	private JTextComponent text;

	/**
	 * <code>true</code> toca-se um som de alerta ao final da execução da rotina
	 */
	private boolean sound = false;

	/**
	 * <code>true</code> a barra de progresso aparece no começo da execução do
	 * programa e se esconde ao final
	 */
	private boolean autoHide = false;

	/**
	 * 
	 */
	private LongTask<K> longRun;

	/**
	 * Construtor da objeto coordenador da tarefa longa
	 * 
	 * @param pb      barra de progresso
	 * @param longRun interface da classe que contem a rotina a ser executada
	 */
	public LongTaskManager(JProgressBar pb, LongTask<K> longRun) {
		this(pb, null, longRun);
	}

	/**
	 * Construtor da objeto coordenador da tarefa longa
	 * 
	 * @param pb      barra de progresso
	 * @param text    caixa de texto para onde serão enviadas mensagens sobre o
	 *                andamento da execução
	 * @param longRun interface da classe que contem a rotina a ser executada
	 */
	public LongTaskManager(JProgressBar pb, JTextComponent text, LongTask<K> longRun) {
		this.pb = pb;
		this.longRun = longRun;
		this.text = text;

		super.addPropertyChangeListener(this);
	}

	// --------------------------- SETTERS ---------------------------------

	/**
	 * Função que estabelece se haverá um beep sonoro ao final da execução da tarefa
	 * 
	 * @param sound <code>true</code> toca-se um som de alerta ao final da execução
	 *              da rotina
	 */
	public void setSound(boolean sound) {
		this.sound = sound;
	}

	/**
	 * Função que estabelece se a barra de progresso aparece e desaparece a cada
	 * execução da tarefa
	 * 
	 * @param autoHide <code>true</code> a barra de progresso aparece no começo da
	 *                 execução do programa e se esconde ao final
	 */
	public void setAutoHide(boolean autoHide) {
		this.autoHide = autoHide;
	}

	// ----------- Função que coordenam a execução da tarefa longa -----------
	@Override
	protected K doInBackground() throws Exception {
		if (autoHide)
			pb.setVisible(true);
		return longRun.run(this);
	}

	@Override
	protected void done() {
		if (pb != null) {
			pb.setIndeterminate(false);
			pb.setValue(0);
			if (autoHide)
				pb.setVisible(false);
		}
		if (sound)
			Toolkit.getDefaultToolkit().beep();
		try {
			longRun.afterRun(this.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	// -- Funções que recebem dados sobre o andamento da execução da rotina --

	/**
	 * Função através da qual envia-se da rotina em execução para o
	 * {@link SwingWorker} o ponto em que está o processo
	 * 
	 * @param progress inteiro que representa em que ponto está o processo, em
	 *                 termos percentuais (-1 para indeterminado, -2 para fim)
	 */
	public void reportProgress(int progress) {
		boolean det = progress >= 0;
		if (pb != null) {
			pb.setStringPainted(det);
			pb.setIndeterminate(progress == -1);
		}
		if (det)
			super.setProgress(progress);
	}

	/**
	 * Função através da qual envia-se da rotina em execução para o
	 * {@link SwingWorker} mensagens intermediárias sobre o processo
	 * 
	 * @param results mensagens sobre o processo
	 */
	public void reportResults(String... results) {
		super.publish(results);
	}

	// ---- Funções que modificam os objetos gráficos (barra de ------
	// -- progresso, caixa de texto) à medida que a execução avança --

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if ("progress".equals(event.getPropertyName()) && pb != null)
			pb.setValue((Integer) event.getNewValue());
	}

	@Override
	protected void process(List<String> chunks) {
		if (text != null)
			for (String c : chunks)
				text.setText(text.getText() + c);
	}
}

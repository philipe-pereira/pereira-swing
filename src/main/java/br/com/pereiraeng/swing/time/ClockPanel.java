package br.com.pereiraeng.swing.time;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import br.com.pereiraeng.core.TimeRefresh;
import br.com.pereiraeng.math.DuplaV;
import br.com.pereiraeng.swing.Leaf;

/**
 * <p>
 * Classe do objeto gráfico que representa um relógio, que indica as horas,
 * minutos e segundos.
 * </p>
 * 
 * <p>
 * Há a possibilidade de se adicionar {@link TimeListener escutadores} que são
 * disparados {@link ClockPanel#addAlarm(int, String) periodicamente}. Isso se
 * deve ao fato de que a classe implementa a interface {@link TimeRefresh}, de
 * modo que as atualizações gráficas sejam acompanhados da execução de uma
 * função chamada periodicamente
 * </p>
 * 
 * @author Philipe PEREIRA
 *
 */
public class ClockPanel extends Leaf implements TimeRefresh, AncestorListener {
	private static final long serialVersionUID = 1L;

	private boolean autoTurnOff = true;

	private final String format;

	public ClockPanel() {
		this(Color.WHITE, 150, 39);
	}

	public ClockPanel(Color back, int width, int height) {
		this(back, width, height, "%tT");
	}

	public ClockPanel(Color back, int width, int height, String format) {
		super(back, width, height, true);
		this.format = format;
		super.enableTimeMotor();
		this.setFont(new Font("Arial", Font.BOLD, 30));
		this.addAncestorListener(this);
	}

	public void setAutoTurnOff(boolean autoTurnOff) {
		this.autoTurnOff = autoTurnOff;
	}

	public void setStart(Calendar start) {
		super.tm.setOffset((int) (start.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()));
	}

	@Override
	public void fireTimeRefresh() {
		// alarme periódico
		if (alarms != null)
			for (Entry<DuplaV, String> e : alarms.entrySet()) {
				DuplaV p = e.getKey();
				int periodo = p.get1();
				int delay = p.get2();
				if (super.tm.getT() % periodo == delay)
					fireTimePerformed(new TimeEvent(this, super.tm.getNow(), e.getValue()));
			}
	}

	@Override
	public int getUnit() {
		return Calendar.SECOND;
	}

	@Override
	public int getWindow() {
		return Calendar.DAY_OF_MONTH;
	}

	// ------------------------------- GRAPHICS -------------------------------

	@Override
	protected void draw(Graphics2D g) {
		g.drawString(String.format(format, super.tm.getNow()), this.getSize().width / 2 - 60, 30);
	}

	// ------------------------------- LISTENER -------------------------------

	@Override
	public void ancestorAdded(AncestorEvent e) {
	}

	@Override
	public void ancestorMoved(AncestorEvent e) {
	}

	@Override
	public void ancestorRemoved(AncestorEvent e) {
		if (autoTurnOff)
			super.stop();
	}

	// ----------------------------- TIME LISTENER -----------------------------

	/**
	 * Tabela de dispersão que associa para cada par de inteiros (o período e o
	 * delay) o comando de {@link TimeEvent}
	 */
	private Map<DuplaV, String> alarms;

	/**
	 * Função que estabele o tempo, em segundos, entre a execução da função
	 * {@link TimeListener#timeElapsed(TimeEvent)}
	 * 
	 * @param alarm   tempo, em segundos
	 * @param command A string that may specify a command (possibly one of several)
	 *                associated with the event
	 */
	public void addAlarm(int alarm, String command) {
		this.addAlarm(new DuplaV(alarm, 0), command);
	}

	/**
	 * Função que estabele o tempo, em segundos, entre a execução da função
	 * {@link TimeListener#timeElapsed(TimeEvent)}
	 * 
	 * @param alarm   vetor com duas coordenadas, a primeira indicando o tempo, em
	 *                segundos e a segunda o atraso proposital
	 * @param command A string that may specify a command (possibly one of several)
	 *                associated with the event
	 */
	public void addAlarm(DuplaV alarm, String command) {
		if (this.alarms == null)
			this.alarms = new HashMap<>();
		if (alarm.get1() > 0)
			this.alarms.put(alarm, command);
	}

	/**
	 * Função que adiciona o listener do tempo neste componente
	 * 
	 * @param listener listener
	 */
	public void addTimeListener(TimeListener listener) {
		if (listener != null)
			super.listenerList.add(TimeListener.class, listener);
	}

	protected void fireTimePerformed(TimeEvent event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying those that are interested in
		// this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TimeListener.class) {
				TimeListener tl = (TimeListener) listeners[i + 1];
				tl.timeElapsed(event);
			}
		}
	}
}
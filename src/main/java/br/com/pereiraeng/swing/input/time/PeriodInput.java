package br.com.pereiraeng.swing.input.time;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.pereiraeng.core.TimeUtils;
import br.com.pereiraeng.swing.Grade;
import br.com.pereiraeng.swing.input.Input;

/**
 * Classe do objeto gráfico que permite inclusão de duas datas que definem uma
 * faixa de tempo
 * 
 * @author Philipe PEREIRA
 *
 */
public class PeriodInput extends Grade implements ChangeListener, Input<Calendar[]>, ActionListener {
	private static final long serialVersionUID = 1L;

	private Calendar min;

	private Calendar max;

	private TimeInput begin, end;

	private JSpinner month;

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo. Os valores mínimo
	 * e máximo são estabelecidos como sendo o primeiro dia do ano 1900 e do ano
	 * 2100, respectivamente.
	 * 
	 * @param enableMonthSelection se <code>true</code> o painel de inserção
	 *                             incluíra um indicador de mês (para seleção de um
	 *                             mês inteiro exato)
	 */
	public PeriodInput(boolean enableMonthSelection) {
		this(new GregorianCalendar(1900, 0, 0), new GregorianCalendar(2100, 0, 0), enableMonthSelection);
	}

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo
	 * 
	 * @param min data mínima que pode ser inserida no objeto
	 * @param max data máxima que pode ser inserida no objeto
	 */
	public PeriodInput(Calendar min, Calendar max) {
		this(min, max, false);
	}

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo
	 * 
	 * @param min                  data mínima que pode ser inserida no objeto
	 * @param max                  data máxima que pode ser inserida no objeto
	 * @param enableMonthSelection se <code>true</code> o painel de inserção
	 *                             incluíra um indicador de mês (para seleção de um
	 *                             mês inteiro exato)
	 */
	public PeriodInput(Calendar min, Calendar max, boolean enableMonthSelection) {
		this(min, max, enableMonthSelection, 1);
	}

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo
	 * 
	 * @param min                  data mínima que pode ser inserida no objeto
	 * @param max                  data máxima que pode ser inserida no objeto
	 * @param enableMonthSelection se <code>true</code> o painel de inserção
	 *                             incluíra um indicador de mês (para seleção de um
	 *                             mês inteiro exato)
	 * @param disp
	 *                             <ol start="0">
	 *                             <li>elementos dispostos na horizontal;</i>
	 *                             <li>elementos dispostos na vertical;</i>
	 *                             <li>elementos dispostos de forma retangular (só
	 *                             vale para modo mensal).</i>
	 *                             </ol>
	 */
	public PeriodInput(Calendar min, Calendar max, boolean enableMonthSelection, int disp) {
		this(min, max, min, max, enableMonthSelection, disp);
	}

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo
	 * 
	 * @param min                  data mínima que pode ser inserida no objeto
	 * @param max                  data máxima que pode ser inserida no objeto
	 * @param enableMonthSelection se <code>true</code> o painel de inserção
	 *                             incluíra um indicador de mês (para seleção de um
	 *                             mês inteiro exato)
	 * @param disp
	 *                             <ol start="0">
	 *                             <li>elementos dispostos na horizontal;</i>
	 *                             <li>elementos dispostos na vertical;</i>
	 *                             <li>elementos dispostos de forma retangular (só
	 *                             vale para modo mensal).</i>
	 *                             </ol>
	 * @param showHour             se <code>true</code> o objeto gráfico exibe o
	 *                             selecionador de horas, senão este é ignorado (nos
	 *                             casos em que tanto faz a hora, em que se quer
	 *                             escolher apenas os dias)
	 */
	public PeriodInput(Calendar min, Calendar max, boolean enableMonthSelection, int disp, boolean showHour) {
		this(min, max, min, max, enableMonthSelection, disp, showHour);
	}

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo
	 * 
	 * @param min                  data mínima que pode ser inserida no objeto
	 * @param max                  data máxima que pode ser inserida no objeto
	 * @param begin                data inferior inicial indicado no painel
	 * @param end                  data superior inicial indicado no painel
	 * @param enableMonthSelection se <code>true</code> o painel de inserção
	 *                             incluíra um indicador de mês (para seleção de um
	 *                             mês inteiro exato)
	 */
	public PeriodInput(Calendar min, Calendar max, Calendar begin, Calendar end, boolean enableMonthSelection) {
		this(min, max, begin, end, enableMonthSelection, 1);
	}

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo
	 * 
	 * @param min                  data mínima que pode ser inserida no objeto
	 * @param max                  data máxima que pode ser inserida no objeto
	 * @param begin                data inferior inicial indicado no painel
	 * @param end                  data superior inicial indicado no painel
	 * @param enableMonthSelection se <code>true</code> o painel de inserção
	 *                             incluíra um indicador de mês (para seleção de um
	 *                             mês inteiro exato)
	 * @param disp
	 *                             <ol start="0">
	 *                             <li>elementos dispostos na horizontal;</i>
	 *                             <li>elementos dispostos na vertical;</i>
	 *                             <li>elementos dispostos de forma retangular (só
	 *                             vale para modo mensal).</i>
	 *                             </ol>
	 */
	public PeriodInput(Calendar min, Calendar max, Calendar begin, Calendar end, boolean enableMonthSelection,
			int disp) {
		this(min, max, begin, end, enableMonthSelection, disp, true);
	}

	/**
	 * Construtor do painel gráfico de seleção de faixa de tempo
	 * 
	 * @param min                  data mínima que pode ser inserida no objeto
	 * @param max                  data máxima que pode ser inserida no objeto
	 * @param begin                data inferior inicial indicado no painel
	 * @param end                  data superior inicial indicado no painel
	 * @param enableMonthSelection se <code>true</code> o painel de inserção
	 *                             incluíra um indicador de mês (para seleção de um
	 *                             mês inteiro exato)
	 * @param disp
	 *                             <ol start="0">
	 *                             <li>elementos dispostos na horizontal;</i>
	 *                             <li>elementos dispostos na vertical;</i>
	 *                             <li>elementos dispostos de forma retangular (só
	 *                             vale para modo mensal).</i>
	 *                             </ol>
	 * @param showHour             se <code>true</code> o objeto gráfico exibe os
	 *                             selecionadores de horas, <code>false</code> só
	 *                             aparece os selecionadores de dias
	 */
	public PeriodInput(Calendar min, Calendar max, Calendar begin, Calendar end, boolean enableMonthSelection, int disp,
			boolean showHour) {
		this.min = min == null ? Calendar.getInstance() : min;
		if (begin == null)
			begin = this.min;

		this.max = max == null ? Calendar.getInstance() : max;
		if (end == null)
			end = this.max;

		// -------------------- selecionador de mês -------------------------

		// habilitar seleção de mês
		JCheckBox cb = new JCheckBox("Seleciona mês", false); // inicialmente, invisível
		cb.setVisible(enableMonthSelection);
		cb.addActionListener(this);
		add(cb, 0, 0, 1, 1);

		// seleção do mês
		this.month = new JSpinner(new SpinnerDateModel(this.max.getTime(), TimeUtils.truncateMonth(this.min).getTime(),
				this.max.getTime(), Calendar.MONTH));
		this.month.setEditor(new JSpinner.DateEditor(month, "MMMM/yyyy"));
		this.month.addChangeListener(this);
		this.month.setVisible(false); // inicialmente, invisível
		add(this.month, disp == 0 ? 1 : 0, disp == 0 ? 0 : 1, 1, 1);

		// -------------------- inicial e final -------------------------

		// data inicial
		if (!showHour) {
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
		}

		this.begin = new TimeInput(begin, showHour);
		this.begin.addChangeListener(this);
		add(this.begin, disp == 0 ? 2 : (disp == 1 ? 0 : 1), disp == 0 ? 0 : (disp == 1 ? 2 : 0), 1, 1);

		// data final
		if (!showHour) {
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}

		this.end = new TimeInput(end, showHour);
		this.end.addChangeListener(this);
		add(this.end, disp == 0 ? 3 : (disp == 1 ? 0 : 1), disp == 0 ? 0 : (disp == 1 ? 3 : 1), 1, 1);
	}

	public void setMin(Calendar min) {
		if (min == null)
			return;
		this.min = min;

		innerChange = true;

		Calendar c = this.begin.get();
		if (c.before(this.min))
			this.begin.set(this.min);

		c = this.end.get();
		if (c.before(this.min))
			this.end.set(this.max);

		((SpinnerDateModel) month.getModel()).setStart(TimeUtils.truncateMonth(this.min).getTime());

		innerChange = false;
	}

	public void setMax(Calendar max) {
		if (max == null)
			return;
		this.max = max;

		innerChange = true;

		Calendar c = this.end.get();
		if (c.after(this.max))
			this.end.set(this.max);

		c = this.begin.get();
		if (c.after(this.max))
			this.begin.set(this.min);

		((SpinnerDateModel) month.getModel()).setEnd(this.max.getTime());
		((SpinnerDateModel) month.getModel()).setValue(this.max.getTime());

		innerChange = false;
	}

	/**
	 * Função que indica se este objeto gráfico deve operar permanentemente em modo
	 * seleção de meses
	 * 
	 * @param monthSelection <code>true</code> para permitir somente a seleção de
	 *                       meses
	 */
	public void setMonthSelection(boolean monthSelection) {
		if (this.month != null) {
			if ((monthSelection && !this.month.isVisible()) || (!monthSelection && this.month.isVisible())) {
				changeMensal();
				((JCheckBox) getComponent(0)).setVisible(!monthSelection);
				stateChanged(new ChangeEvent(this.month)); // atualiza limites
			}
		}
	}

	/**
	 * Função que mostra ou esconde o selecionador de mês
	 */
	private void changeMensal() {
		// altera a visibilidade
		boolean m = !this.month.isVisible();
		this.month.setVisible(m);
		// ativa ou desativa ou butões
		this.begin.setEnabled(!m);
		this.end.setEnabled(!m);
	}

	@Override
	public void set(Calendar[] k) {
		if (k[0].after(k[1]))
			throw new IllegalArgumentException("O período inicial não pode vir depois do final.");
		boolean order = k[0].before(end.get());
		if (order) {
			this.begin.set(k[0]);
			this.end.set(k[1]);
		} else {
			this.end.set(k[1]);
			this.begin.set(k[0]);
		}
	}

	/**
	 * Função que estabelece o período atual exibido no objeto gráfico em função dos
	 * valores limítrofes. Determina-se se o período começará no valor mínimo ou
	 * terminará no máximo e o período máximo
	 * 
	 * @param start     <code>true</code> se o período começar no valor mínimo
	 *                  <code>false</code> se ele terminar no valor máximo
	 * @param windowMax comprimento máximo da janela
	 * @param tu        unidade de medida do tempo
	 */
	public void set(boolean start, int windowMax, TimeUnit tu) {
		int period = (int) tu.convert(this.max.getTimeInMillis() - this.min.getTimeInMillis(), TimeUnit.MILLISECONDS);
		if (period < windowMax)
			set(new Calendar[] { min, max });
		else {
			Calendar c = (Calendar) (start ? min : max).clone();
			c.add(Calendar.MINUTE, (int) TimeUnit.MINUTES.convert(start ? windowMax : -windowMax, tu));
			set(start ? new Calendar[] { min, c } : new Calendar[] { c, max });
		}
	}

	@Override
	public Calendar[] get() {
		return new Calendar[] { this.begin.get(), this.end.get() };
	}

	/**
	 * Função que retorna os valores mínimo e máximo que o período pode assumir
	 * 
	 * @return vetor com duas posições, uma indicando o máximo e outra o mínimo
	 */
	public Calendar[] getLimits() {
		return new Calendar[] { min, max };
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void setEnabled(boolean enabled) {
		((JCheckBox) getComponent(0)).setEnabled(enabled);
		month.setEnabled(enabled);
		if (!this.month.isVisible()) {
			begin.setEnabled(enabled);
			end.setEnabled(enabled);
		}
	}

	@Override
	public boolean isEnabled() {
		return ((JCheckBox) getComponent(0)).isEnabled();
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (begin != null) {
			((JCheckBox) getComponent(0)).setFont(font);
			month.setFont(font);
			begin.setFont(font);
			end.setFont(font);
		}
	}

	// --------------- LISTENER ----------------

	private transient ChangeListener listener;

	private transient boolean innerChange = false;

	public void addChangeListener(ChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (!innerChange) {
			Object src = e.getSource();
			if (this.begin.equals(src)) {
				// o valor inferior não pode ser menor que o mínimo
				Calendar newBeginValue = this.begin.get();
				if (newBeginValue.before(min))
					this.begin.set(min);

				// o valor inferior não pode ser maior que o superior
				Calendar end = this.end.get();
				if (newBeginValue.after(end))
					this.begin.set(end);

			} else if (this.end.equals(src)) {
				// o valor superior não pode ser maior que o máximo
				Calendar newEndValue = this.end.get();
				if (newEndValue.after(max))
					this.end.set(max);

				// o valor superior não pode ser menor que o inferior
				Calendar begin = this.begin.get();
				if (newEndValue.before(begin))
					this.end.set(begin);

			} else if (this.month.equals(src)) {
				Calendar m = TimeUtils.date2Calendar((Date) this.month.getModel().getValue());
				Calendar[] cs = TimeUtils.getMonthRange(m);
				this.set(cs);
			}
			if (listener != null)
				listener.stateChanged(new ChangeEvent(this));
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.changeMensal();
	}
}

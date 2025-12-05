package br.com.pereiraeng.swing.input;

/**
 * Classe abstrata do objeto gráfico com uma caixa de texto na qual será
 * digitada uma sequência de caracteres que será convertida em um {@link Double
 * número decimal de precisão dupla}
 * 
 * @author Philipe PEREIRA
 *
 */
public class DoubleInput extends NumberInput<Double> {
	private static final long serialVersionUID = 1L;

	protected int precision = 0;

	/**
	 * Construtor do objeto gráfico que permite a edição de número decimal de
	 * precisão dupla
	 * 
	 * @param value     número decimal de precisão simples que será mostrado num
	 *                  primeiro momento
	 * @param precision número de digitos em que o número será exibido no editor
	 */
	public DoubleInput(Float value, int precision) {
		this((double) value, precision);
	}

	/**
	 * Construtor do objeto gráfico que permite a edição de número decimal de
	 * precisão dupla
	 * 
	 * @param value     número decimal de precisão dupla que será mostrado num
	 *                  primeiro momento
	 * @param precision número de digitos em que o número será exibido no editor
	 */
	public DoubleInput(Double value, int precision) {
		this(precision);
		this.set(value);
	}

	/**
	 * Construtor do objeto gráfico que permite a edição de número decimal de
	 * precisão dupla
	 * 
	 * @param precision número de digitos em que o número será exibido no editor
	 */
	public DoubleInput(int precision) {
		this.precision = precision;
		super.setColumns(precision + 1);
	}

	public void set(float value) {
		super.set((double) value);
	}

	// ---------------------------- PARSEABLE ----------------------------

	@Override
	protected Double getValue() {
		return super.number.doubleValue();
	}

	@Override
	protected String value2string(Double value) {
		return this.precision > 0 ? String.format("%." + this.precision + "g", value)
				: String.valueOf(Math.round(value));
	}
}
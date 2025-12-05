package br.com.pereiraeng.swing.input;

import java.awt.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import br.com.pereiraeng.swing.Grade;

public class IPinput extends Grade implements Input<byte[]> {
	private static final long serialVersionUID = -1512451896622894686L;

	private static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	private JLabel ipLabel;
	private JFormattedTextField ip;
	private JSpinner ipEnd;

	private JSpinner port;

	/**
	 * 
	 * @param port0 número default da porta
	 */
	public IPinput(int port0) {
		String ipv4 = null;
		try {
			ipv4 = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		add(ipLabel = new JLabel("IPv4"), 0, 0, 1, 1);
		ip = new JFormattedTextField();
		ipLabel.setLabelFor(ip);
		ip.setFormatterFactory(new DefaultFormatterFactory(new IPverifier()));
		ip.setText(ipv4);
		add(ip, 1, 0, 1, 1);

		JLabel l = new JLabel("Port");
		add(l, 0, 1, 1, 1);
		port = new JSpinner(new SpinnerNumberModel(port0, 0, 65536, 1));
		l.setLabelFor(port);
		add(port, 1, 1, 1, 1);
	}

	/**
	 * 
	 * @param preffix sequência de caracteres na forma XXX.YYY.ZZZ. que indica o
	 *                prefixo de todos os IP
	 * @param ip0     três últimos digitos do IP
	 * @param port0   número default da porta
	 */
	public IPinput(String preffix, int ip0, int port0) {
		add(ipLabel = new JLabel("IPv4: " + preffix), 0, 0, 2, 1);
		ipEnd = new JSpinner(new SpinnerNumberModel(ip0, 0, 255, 1));
		add(ipEnd, 2, 0, 1, 1);

		add(new JLabel("Port"), 0, 1, 1, 1);
		add(port = new JSpinner(new SpinnerNumberModel(port0, 0, 65536, 1)), 1, 1, 2, 1);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	public String getIP() {
		if (ipEnd == null)
			return ip.getText();
		else
			return ipLabel.getText().substring(6) + String.valueOf(this.ipEnd.getValue());
	}

	public int getPort() {
		return (int) port.getValue();
	}

	private static final Pattern PATTERN = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");

	public class IPverifier extends DefaultFormatter {
		private static final long serialVersionUID = 335311451292335460L;

		@Override
		public Object stringToValue(String text) throws ParseException {
			Matcher matcher = PATTERN.matcher(text);
			if (matcher.matches()) {
				return super.stringToValue(text);
			} else
				return "   .   .   .   ";
		}
	}

	@Override
	public void set(byte[] k) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] get() {
		// TODO Auto-generated method stub
		return null;
	}
}

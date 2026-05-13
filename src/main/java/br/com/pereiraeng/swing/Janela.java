package br.com.pereiraeng.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import br.com.pereiraeng.swing.interfaces.LnF;
import br.com.pereiraeng.swing.longtask.SplashAdapter;
import br.com.pereiraeng.swing.longtask.Splashable;

/**
 * Classe que extende a {@link JFrame}, adicionando-lhe funções que facilitam
 * seu manuseio
 * 
 * @author Philipe Pereira
 * 
 */
public class Janela extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final Dimension MIN_DIM = new Dimension(1024, 768);

	private static final Map<String, Integer> BARS_HEIGHT;

	static {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("Windows 7", 40);
		map.put("Windows 8.1", 40);
		map.put("Windows Vista", 30);
		map.put("Windows 10", 38);
		map.put("Windows 11", 120);
		BARS_HEIGHT = Collections.unmodifiableMap(map);
	}

	/**
	 * Contrutor da classe com o qual se cria uma janela sem título, com o tamanho
	 * da tela e posicionada no canto da desta.
	 */
	public Janela() {
		this("");
	}

	/**
	 * Contrutor da classe com o qual se cria uma janela com um dado título,
	 * ocupando todo o espaço da tela
	 * 
	 * @param title
	 *            título da janela a ser exibido na barra superior
	 */
	public Janela(String title) {
		this(title, new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height - BARS_HEIGHT.get(System.getProperty("os.name"))),
				new Point(0, 0));
	}

	/**
	 * Contrutor da classe com o qual se cria uma janela com um dado título e um
	 * dado tamanho, posicionada no centro da tela.
	 * 
	 * @param title
	 *            título da janela a ser exibido na barra superior
	 * @param size
	 *            objeto <code>Dimension</code> com as dimensões, em pixels, da
	 *            janela (se for nulo)
	 */
	public Janela(String title, Dimension size) {
		super(title);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		{
			String s = System.getProperty("os.name");
			int bar = 40;
			Integer b = BARS_HEIGHT.get(s);
			if (b != null)
				bar = b;
			else
				System.err.println("O altura da barra de menu para o sistema " + s + " não foi cadastrada");
			screen.height -= bar;
		}

		int x = 0, y = 0;
		if (size == null)
			size = new Dimension(screen.width, screen.height);
		else {
			if (screen.width > size.width)
				x = (screen.width - size.width) / 2;
			else
				size.width = screen.width;
			if (screen.height > size.height)
				y = (screen.height - size.height) / 2;
			else
				size.height = screen.height;
		}

		super.setSize(size);
		super.setLocation(x, y);
	}

	/**
	 * Contrutor da classe com o qual se cria uma janela com um dado título e um
	 * dado tamanho, posicionada numa dada coordenada a partir do canto da tela.
	 * 
	 * @param title
	 *            título da janela a ser exibido na barra superior
	 * @param size
	 *            objeto <code>Dimension</code> com as dimensões, em pixels, da
	 *            janela
	 * @param position
	 *            coordenadas, em pixels, indicando onde será posicionada a tela.
	 */
	public Janela(String title, Dimension size, Point position) {
		super(title);
		super.setSize(size);
		super.setLocation(position);
	}

	/**
	 * Contrutor da classe com o qual se cria uma janela com um dado título,
	 * centrada na tela e com as dimensões dadas como porcentagem do tamanho desta.
	 * 
	 * @param title
	 *            título da janela a ser exibido na barra superior
	 * @param perc
	 *            número indicando o tamanho relativo entre a largura da janela e a
	 *            da tela e a altura da janela e a da tela
	 */
	public Janela(String title, float perc) {
		this(title, perc, perc);
	}

	/**
	 * Contrutor da classe com o qual se cria uma janela com um dado título,
	 * centrada na tela e com as dimensões dadas como porcentagem do tamanho desta.
	 * 
	 * @param title
	 *            título da janela a ser exibido na barra superior
	 * @param width
	 *            número indicando o tamanho relativo entre a largura da janela e a
	 *            da tela
	 * @param height
	 *            número indicando o tamanho relativo entre a altura da janela e a
	 *            da tela
	 */
	public Janela(String title, float width, float height) {
		super(title);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		{
			String s = System.getProperty("os.name");
			int bar = 40;
			Integer b = BARS_HEIGHT.get(s);
			if (b != null)
				bar = b;
			else
				System.err.println("O altura da barra de menu para o sistema " + s + " não foi cadastrada");
			screen.height -= bar;
		}

		Dimension size = new Dimension((int) (width * screen.width), (int) (height * screen.height));

		super.setSize(size);
		super.setLocation((screen.width - size.width) / 2, (screen.height - size.height) / 2);
	}

	/**
	 * Contrutor da classe com o qual se cria uma janela com um dado título,
	 * dimensões dadas como porcentagem do tamanho desta e posicionada com relação
	 * ao centro
	 * 
	 * @param title
	 *            título da janela a ser exibido na barra superior
	 * @param width
	 *            número indicando o tamanho relativo entre a largura da janela e a
	 *            da tela
	 * @param height
	 *            número indicando o tamanho relativo entre a altura da janela e a
	 *            da tela
	 * @param x
	 *            posição horizontal relativa ao centro onde a janela será
	 *            posicionada (-1f para a janela ser posicionada à esquerda, 1f para
	 *            ser posicionada à direita)
	 * @param y
	 *            posição vertical relativa ao centro onde a janela será posicionada
	 *            (-1f para a janela ser posicionada abaixo, 1f para ser posicionada
	 *            acima)
	 */
	public Janela(String title, float width, float height, float x, float y) {
		super(title);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		{
			String s = System.getProperty("os.name");
			int bar = 40;
			Integer b = BARS_HEIGHT.get(s);
			if (b != null)
				bar = b;
			else
				System.err.println("O altura da barra de menu para o sistema " + s + " não foi cadastrada");
			screen.height -= bar;
		}

		Dimension size = new Dimension((int) (width * screen.width), (int) (height * screen.height));

		super.setSize(size);
		super.setLocation((int) ((screen.width - size.width) * (x / 2 + 0.5f)),
				(int) ((screen.height - size.height) * (-y / 2 + 0.5f)));
	}

	/**
	 * Função que faz aparecer a janela. Esta, por sua vez, poderá ser
	 * redimensionada ou não, em função do parâmetro <code>resizable</code>.
	 * 
	 * @param resizable
	 *            se <code>true</code>, a janela poderá ser redimensionada pelo
	 *            usuário
	 */
	public void showFrame(boolean resizable) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(resizable);
		if (resizable)
			this.setMinimumSize(this.getSize());
		this.setVisible(true);
	}

	/**
	 * Função que altera os padrões de coloração da janela e dos itens nela contidos
	 * 
	 * @param mt
	 *            classe contendo os padrões de cores
	 */
	public void changeLookAndFell(MetalTheme mt) {
		changeLookAndFell(0, mt);
	}

	/**
	 * Função que altera os padrões de coloração da janela e dos itens nela contidos
	 * 
	 * @param lnf
	 *            <ol start="0">
	 *            <li>Metal;</i>
	 *            <li>System;</i>
	 *            <li>Motif;</i>
	 *            <li>GTK;</i>
	 *            <li>e demais: um dos estiver instalados.</i>
	 *            </ol>
	 */
	public void changeLookAndFell(int lnf) {
		changeLookAndFell(lnf, null);
	}

	private void changeLookAndFell(int lnf, MetalTheme mt) {
		String lookAndFeel = null;

		switch (lnf) {
		case 0:
			// ------------------- metal -------------------
			lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			// lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
			if (mt != null)
				MetalLookAndFeel.setCurrentTheme(mt);
			break;
		case 1:
			// ------------------- system -------------------
			lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			break;
		case 2:
			// ------------------- motif -------------------
			lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
			break;
		case 3:
			// ------------------- GTK -------------------
			lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
			break;
		default:
			// ------------------- todas as opções -------------------
			int opt = lnf - 4;
			LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
			if (opt < infos.length) {
				lookAndFeel = infos[opt].getClassName();
			} else {
				System.err.println("Unexpected value specified");
				lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			}
			break;
		}

		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
			System.err.println("Couldn't find class for specified look and feel:" + lookAndFeel);
			System.err.println("Did you include the L&F library in the class path?");
			System.err.println("Using the default look and feel.");
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println("Can't use the specified look and feel (" + lookAndFeel + ") on this platform.");
			System.err.println("Using the default look and feel.");
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");
			System.err.println("Using the default look and feel.");
			e.printStackTrace();
		}
	}

	/**
	 * Função que carrega em uma nova janela independente a aplicação. Assim como na
	 * função {@link Subzone#loadingApp(CEA, String...)}, as funções são chamadas na
	 * seguinte ordem:
	 * 
	 * <ul>
	 * <li>{@link App#getTitle()};</i>
	 * <li>{@link App#getSize()};</i>
	 * <li>{@link Splashable#setSplash()} (se aplicável);</i>
	 * <li>{@link App#build(java.awt.Component)};</i>
	 * <li>{@link App#open(String)} (para cada um dos elementos do vetor
	 * {@code args});</i>
	 * <li>{@link App#start()};</i>
	 * <li>{@link App#isResizable()}.</i>
	 * </ul>
	 * 
	 * @param app
	 *            objeto que representa o aplicativo
	 * @param args
	 *            sequência de caracteres para a abertura de arquivos
	 */
	public static <K> Janela startAlone(App app, String... args) {
		Janela janela = new Janela(app.getTitle(), app.getWindowSize());

		if (app instanceof LnF)
			janela.changeLookAndFell(((LnF) app).getTheme());

		janela.addWindowListener(new WindowCloser(app));

		SplashAdapter sa = null;
		if (app instanceof Splashable) {
			SplashScreen splash = SplashScreen.getSplashScreen();
			if (splash == null)
				System.err.println("SplashScreen.getSplashScreen() returned null");
			sa = new SplashAdapter(splash);
			((Splashable) app).setSplash(sa);
			sa.update("parte gráfica");
		}

		app.build(janela);

		for (String arg : args)
			app.open(arg);
		app.start();

		if (sa != null)
			sa.close();

		janela.showFrame(app.isResizable());

		return janela;
	}
}
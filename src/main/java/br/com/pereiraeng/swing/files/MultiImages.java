package br.com.pereiraeng.swing.files;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MultiImages extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;

	// dados

	private URL[] images;

	// parte gráfica

	private JSlider slider;

	private ImageScreen imageScreen;

	public MultiImages() {
		super(new BorderLayout());

		imageScreen = new ImageScreen();
		imageScreen.setPreferredSize(new Dimension(570, 520));
		this.add(imageScreen, BorderLayout.CENTER);

		this.add(slider = new JSlider(SwingConstants.HORIZONTAL, 1, 1, 1), BorderLayout.SOUTH);
		slider.addChangeListener(this);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
	}

	public void setImage(File file) {
		if (file.isDirectory()) {
			this.setImages(file.listFiles());
		} else {
			this.setImages(new File[] { file });
		}
	}

	public void setImages(File[] files) {
		this.setImages(null, files);
	}

	public void setImages(URL context, File[] files) {
		URL[] urls = new URL[files.length];
		for (int i = 0; i < files.length; i++) {
			try {
				if (context == null)
					urls[i] = files[i].toURI().toURL();
				else
					urls[i] = new URL(context, files[i].getPath());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		setImages(urls);
	}

	public void setImages(URL[] urls) {
		this.images = urls;

		this.slider.setEnabled(urls != null ? urls.length > 1 : false);
		if (this.slider.isEnabled()) {
			this.slider.setMaximum(images.length);
			this.slider.setValue(1);
		}

		if (images != null)
			this.imageScreen.setImage(images[0]);
		else
			this.imageScreen.clearImage();
	}

	public void clear() {
		setImages((URL[]) null);
	}

	// -------------------------- LISTENER --------------------------

	@Override
	public void stateChanged(ChangeEvent event) {
		int i = slider.getValue();
		if (i >= 0 && images != null)
			imageScreen.setImage(images[i - 1]);
	}
}

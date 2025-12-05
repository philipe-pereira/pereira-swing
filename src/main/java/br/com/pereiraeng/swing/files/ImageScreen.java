package br.com.pereiraeng.swing.files;

import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import br.com.pereiraeng.swing.SwingUtils;

public class ImageScreen extends JScrollPane implements MouseWheelListener {
	private static final long serialVersionUID = 1L;

	private JLabel label;

	// ----------------------

	private transient Image currentImage;

	private transient int currentHeight, currentWidth;

	// ----------------------

	private double zoomLevel = 1.;

	private static final double ZOOM_STEP = 0.1;

	public ImageScreen() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		super.setViewportView(label = new JLabel());

		label.addMouseWheelListener(this);
	}
	
	public void setImage(File file){
		try {
			this.setImage(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void setImage(URL image) {
		this.zoomLevel = 1.;

		ImageIcon ii = new ImageIcon(image);

		this.currentImage = ii.getImage();
		this.currentHeight = ii.getIconHeight();
		this.currentWidth = ii.getIconWidth();
		this.label.setIcon(ii);

	}

	public void clearImage() {
		this.currentImage = null;
		this.currentHeight = -1;
		this.currentWidth = -1;
		this.label.setIcon(null);
	}

	// -------------------------- LISTENER --------------------------

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomLevel -= ZOOM_STEP * e.getWheelRotation();
		int newWidth = (int) (currentWidth * zoomLevel);
		int newHeight = (int) (currentHeight * zoomLevel);

		if (newWidth > 0 && newHeight > 0) {
			label.setIcon(new ImageIcon(SwingUtils.getScaledImage(currentImage,
					newWidth, newHeight)));
			label.repaint();
		}
	}
}

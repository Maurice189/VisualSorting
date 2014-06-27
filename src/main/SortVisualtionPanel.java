package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import main.Statics.COMPONENT_TITLE;

/**
 * @author maurice
 * 
 * 
 * 
 * */

public class SortVisualtionPanel extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;
	private static final int preferredGapSize = 3;
	private static Color backgroundColor = Color.white;
	private static int gapSize = 3, marginTop = 20;
	private static int margin = 7;
	private static final int offsetY = 30;

	private TitledBorder leftBorder;
	private BufferedImage buffer;
	private Graphics2D gbuffer;
	private int width, height, refWidth, refHeight;
	private int elements[], lstIndex1 = -1, lstIndex2 = -1;
	private int lstInsert = -1;

	public SortVisualtionPanel(Controller controller, String selectedSort,
			int width, int height) {

		this.width = width;
		this.height = height;

		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setFont(Statics.getDefaultFont(14f));
		gbuffer.setBackground(SortVisualtionPanel.backgroundColor);
	
		leftBorder = BorderFactory.createTitledBorder("");
		leftBorder.setTitleJustification(TitledBorder.ABOVE_TOP);
		setInfo(selectedSort,0);

		
		addComponentListener(this);
		setBorder(leftBorder);

		manageButtons(controller);
	}
	
	private void manageButtons(Controller controller){
		
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.insets = new Insets(-7, 0, 0, 2);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		JButton remove = new JButton();
		remove.addActionListener(controller);
		remove.setActionCommand(Statics.REMOVE_SORT);
		remove.setIcon(new ImageIcon(Statics.class.getResource("/resources/delete_visualsort_1.png")));
		remove.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/delete_visualsort_rollover_1.png")));
		remove.setBorder(BorderFactory.createEmptyBorder());
		remove.setPreferredSize(new Dimension(16,16));
		
		JButton info = new JButton();
		info.addActionListener(controller);
		info.setActionCommand(Statics.INFO);
		info.setIcon(new ImageIcon(Statics.class.getResource("/resources/info_visualsort_1.png")));
		info.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/info_visualsort_rollover_1.png")));
		info.setBorder(BorderFactory.createEmptyBorder());
		info.setPreferredSize(new Dimension(16,16));
		GridBagConstraints gbc2 = (GridBagConstraints) gbc.clone();
		gbc2.gridx = 0;
		gbc2.weightx = 1;
		gbc2.weighty = 1;
		
		add(remove,gbc);
		add(info,gbc2);
	}

	public void setInfo(String info) {
		leftBorder.setTitle(info);
	}
	
	public void setInfo(String algoname,int iterations) {
		
		String info = 
		algoname.concat(" - ").concat(String.valueOf(iterations)).concat(" ").concat(
		Statics.getNamebyXml(COMPONENT_TITLE.ITERATIONS));
		
		leftBorder.setTitle(info);
	}

	public static void setBackgroundColor(Color color) {
		SortVisualtionPanel.backgroundColor = color;
	}

	// FIXME: make static
	public void setElements(int elements[]) {

		this.elements = elements;
		int max = 1;

		for (int i = 0; i < elements.length; i++) {
			if (elements[i] > max)
				max = elements[i];
		}

		refHeight = (height - offsetY - SortVisualtionPanel.marginTop) / max;
		refWidth = (width - (elements.length * SortVisualtionPanel.preferredGapSize))
				/ elements.length;

		if (refHeight <= 0)
			refHeight = 1;
		if (refWidth <= 0){
			
			
			double newBorder = ((elements.length-width)/((double)(-1)*elements.length));
			if(newBorder > 0) SortVisualtionPanel.gapSize = (int) newBorder;
			else SortVisualtionPanel.gapSize = 1;
			refWidth = 1;
			
		}
		
		else SortVisualtionPanel.gapSize = 3;
			

		SortVisualtionPanel.margin = (width - (elements.length * (refWidth + SortVisualtionPanel.gapSize))) / 2;
		drawElements();

	}

	public void updatePanelSize() {

		int max = 1;

		width = this.getWidth();
		height = this.getHeight();

		buffer = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setBackground(SortVisualtionPanel.backgroundColor);
		gbuffer.setFont(Statics.getDefaultFont(14f));

		for (int i = 0; i < elements.length; i++) {
			if (elements[i] > max)
				max = elements[i];
		}

		refHeight = (height - offsetY - SortVisualtionPanel.marginTop) / max;
		refWidth = (width - (elements.length * SortVisualtionPanel.preferredGapSize))
				/ elements.length;

		if (refHeight <= 0)
			refHeight = 1;
		if (refWidth <= 0){
			
			double newBorder = ((elements.length-width)/((double)(-1)*elements.length));
			if(newBorder > 0) SortVisualtionPanel.gapSize = (int) newBorder;
			else SortVisualtionPanel.gapSize = 1;
			refWidth = 1;
			
		}
		
		else SortVisualtionPanel.gapSize = 3;

		SortVisualtionPanel.margin = (width - (elements.length * (refWidth + SortVisualtionPanel.gapSize))) / 2;
		drawElements();

	}

	public void drawElements() {

		gbuffer.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());

		gbuffer.setColor(Color.GRAY);

		for (int i = 0; i < elements.length; i++) {

			gbuffer.drawRect((i * (refWidth + SortVisualtionPanel.gapSize))
					+ SortVisualtionPanel.margin,
					(height - (refHeight * elements[i])) - offsetY, refWidth,
					refHeight * elements[i]);
			gbuffer.fillRect((i * (refWidth + SortVisualtionPanel.gapSize))
					+ SortVisualtionPanel.margin,
					(height - (refHeight * elements[i])) - offsetY, refWidth,
					refHeight * elements[i]);

		}

		repaint();
	}

	public void visualInsert(int c, int value) {

		if (lstInsert >= 0) {
			gbuffer.setColor(Color.GRAY);
			gbuffer.drawRect(
					(lstInsert * (refWidth + SortVisualtionPanel.gapSize))
							+ SortVisualtionPanel.margin,
					(height - (refHeight * elements[lstInsert])) - offsetY,
					refWidth, refHeight * elements[lstInsert]);
			gbuffer.fillRect(
					(lstInsert * (refWidth + SortVisualtionPanel.gapSize))
							+ SortVisualtionPanel.margin,
					(height - (refHeight * elements[lstInsert])) - offsetY,
					refWidth, refHeight * elements[lstInsert]);

		}

		gbuffer.setColor(SortVisualtionPanel.backgroundColor);
		gbuffer.drawRect((c * (refWidth + SortVisualtionPanel.gapSize))
				+ SortVisualtionPanel.margin,
				(height - (refHeight * elements[c])) - offsetY, refWidth,
				refHeight * elements[c]);
		gbuffer.fillRect((c * (refWidth + SortVisualtionPanel.gapSize))
				+ SortVisualtionPanel.margin,
				(height - (refHeight * elements[c])) - offsetY, refWidth,
				refHeight * elements[c]);

		gbuffer.setColor(Color.GREEN);
		gbuffer.drawRect((c * (refWidth + SortVisualtionPanel.gapSize))
				+ SortVisualtionPanel.margin, (height - (refHeight * value))
				- offsetY, refWidth, refHeight * value);
		gbuffer.fillRect((c * (refWidth + SortVisualtionPanel.gapSize))
				+ SortVisualtionPanel.margin, (height - (refHeight * value))
				- offsetY, refWidth, refHeight * value);

		lstInsert = c;

		repaint();

	}

	// FIXME
	public void visualCmp(int c1, int c2, boolean changed) {

		// long t = System.currentTimeMillis();
		int x1, x2, y1, y2, h1, h2;

		// nur unteren bereich loeschen
		gbuffer.clearRect(0, height - offsetY, width, height);

		if (lstIndex1 >= 0 && lstIndex2 >= 0) {

			x1 = (lstIndex1 * (refWidth + SortVisualtionPanel.gapSize))
					+ SortVisualtionPanel.margin;
			x2 = (lstIndex2 * (refWidth + SortVisualtionPanel.gapSize))
					+ SortVisualtionPanel.margin;
			y1 = (height - (refHeight * elements[lstIndex1])) - offsetY;
			y2 = (height - (refHeight * elements[lstIndex2])) - offsetY;
			h1 = refHeight * elements[lstIndex1];
			h2 = refHeight * elements[lstIndex2];

			gbuffer.setColor(Color.GRAY);
			// gbuffer.setClip(r1);
			gbuffer.drawRect(x1, y1, refWidth, h1);
			gbuffer.fillRect(x1, y1, refWidth, h1);

			// gbuffer.setClip(r2);
			gbuffer.drawRect(x2, y2, refWidth, h2);
			gbuffer.fillRect(x2, y2, refWidth, h2);

		}

		x1 = (c1 * (refWidth + SortVisualtionPanel.gapSize))
				+ SortVisualtionPanel.margin;
		x2 = (c2 * (refWidth + SortVisualtionPanel.gapSize))
				+ SortVisualtionPanel.margin;
		y1 = (height - (refHeight * elements[c1])) - offsetY;
		y2 = (height - (refHeight * elements[c2])) - offsetY;
		h1 = refHeight * elements[c1];
		h2 = refHeight * elements[c2];

		if (changed) {

			gbuffer.setColor(SortVisualtionPanel.backgroundColor);
			gbuffer.drawRect(x1, y2, refWidth, h2);
			gbuffer.fillRect(x1, y2, refWidth, h2);

			gbuffer.drawRect(x2, y1, refWidth, h1);
			gbuffer.fillRect(x2, y1, refWidth, h1);

			signalExchangedElements(c1, c2, refWidth);
		}

		gbuffer.setColor(Color.RED);
		gbuffer.drawRect(x1, y1, refWidth, h1);
		gbuffer.fillRect(x1, y1, refWidth, h1);

		gbuffer.drawRect(x2, y2, refWidth, h2);
		gbuffer.fillRect(x2, y2, refWidth, h2);

		lstIndex1 = c1;
		lstIndex2 = c2;
		// System.out.println("TIME: "+(System.currentTimeMillis()-t));
		repaint();

	}

	private void signalExchangedElements(int i1, int i2, int refWidth) {

		int x1 = (int) (i1 * (refWidth + SortVisualtionPanel.gapSize) + (refWidth * 0.5))
				+ SortVisualtionPanel.margin;
		int x2 = (int) (i2 * (refWidth + SortVisualtionPanel.gapSize) + (refWidth * 0.5))
				+ SortVisualtionPanel.margin;

		gbuffer.setColor(Color.BLUE);
		gbuffer.setStroke(new BasicStroke(1));
		gbuffer.drawLine(x1, height - offsetY + 5, x1,
				(int) (height - (offsetY * 0.70)));
		gbuffer.drawLine(x2, height - offsetY + 5, x2,
				(int) (height - (offsetY * 0.70)));
		gbuffer.drawLine(x1, (int) (height - (offsetY * 0.70)), x2,
				(int) (height - (offsetY * 0.70)));

	}

	public void flashing() {

		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gbuffer.setRenderingHints(rh);

		this.setOpaque(false);

		float b = 0f;
		for (int bi = 0; bi < 20; bi++) {

			if (bi > 9)
				b -= 0.015f;
			else
				b += 0.015f;

			gbuffer.setBackground(new Color(0f, 0f, 0f, b));
			gbuffer.clearRect(4, 20, width-8, height - 40);

			gbuffer.setColor(Color.GRAY);
			for (int i = 0; i < elements.length; i++) {

				gbuffer.drawRect((i * (refWidth + SortVisualtionPanel.gapSize))
						+ SortVisualtionPanel.margin,
						(height - (refHeight * elements[i])) - offsetY,
						refWidth, refHeight * elements[i]);
				gbuffer.fillRect((i * (refWidth + SortVisualtionPanel.gapSize))
						+ SortVisualtionPanel.margin,
						(height - (refHeight * elements[i])) - offsetY,
						refWidth, refHeight * elements[i]);

			}

			if (bi < 19) {
				gbuffer.setColor(Color.WHITE);
				gbuffer.setFont(Statics.getDefaultFont(26f));
				gbuffer.drawString("Finished", (int) (width * 0.46), height >> 1);
			}
			repaint();
			try {
				Thread.currentThread();
				Thread.sleep(37);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}

		}

		// TODO: antianalaysing ausschalten
		repaint();

		rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		gbuffer.setRenderingHints(rh);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(buffer, 0, 0, width, height, this);

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		updatePanelSize();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}

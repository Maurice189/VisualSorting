package main;


/*
Visualsorting
Copyright (C) 2014  Maurice Koch

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import algorithms.Sort;

/**
 * 
 * This class is used to display the animation in a panel.
 * The animation is based on bars with different heights. Each bar is representing 
 * a different value in the sorting list.
 * 
 * @author maurice
 * @version BETA
 * @category graphics
 * 
 * 
 **/

public class SortVisualisationPanel extends JPanel {

	private static Color backgroundColor = Color.white;
	private static final int preferredGapSize = 3,offsetY = 20;
	private static int width,height,refWidth,refHeight,margin = 7,gapSize = 3, marginTop = 25;
	
	private BufferedImage buffer;
	private Graphics2D gbuffer;
	private int elements[], lstIndex1 = -1, lstIndex2 = -1,lstInsert = -1,lstPivot = -1;

	public SortVisualisationPanel(ActionListener listener, String selectedSort,
			int width, int height) {

		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setFont(Window.getComponentFont(12f));
		gbuffer.setBackground(SortVisualisationPanel.backgroundColor);

	}
	
	public SortVisualisationPanel(
			int width, int height) {

		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setFont(Window.getComponentFont(12f));
		gbuffer.setBackground(SortVisualisationPanel.backgroundColor);

	}

	public void drawElements() {

		gbuffer.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());

		gbuffer.setColor(Color.GRAY);

		for (int i = 0; i < elements.length; i++) {

			gbuffer.drawRect((i * (refWidth + SortVisualisationPanel.gapSize))
					+ SortVisualisationPanel.margin,
					(height - (refHeight * elements[i])) - offsetY, refWidth,
					refHeight * elements[i]);
			gbuffer.fillRect((i * (refWidth + SortVisualisationPanel.gapSize))
					+ SortVisualisationPanel.margin,
					(height - (refHeight * elements[i])) - offsetY, refWidth,
					refHeight * elements[i]);

		}

		repaint();
	}
	
	public void visualPivot(int pivotIndex){
		
		
		
			int x = (pivotIndex * (refWidth + SortVisualisationPanel.gapSize))
				+ SortVisualisationPanel.margin;

			int y = (height - (refHeight * elements[pivotIndex])) - offsetY;
			int h = refHeight * elements[pivotIndex];
		
			gbuffer.setColor(Color.CYAN);
			gbuffer.drawRect(x, y, refWidth, h);
			gbuffer.fillRect(x, y, refWidth, h);
		
			if(lstPivot != -1 && lstPivot != pivotIndex){
				x = (lstPivot * (refWidth + SortVisualisationPanel.gapSize))
					+ SortVisualisationPanel.margin;

				y = (height - (refHeight * elements[lstPivot])) - offsetY;
				h = refHeight * elements[lstPivot];
		
				gbuffer.setColor(Color.GRAY);
				gbuffer.drawRect(x, y, refWidth, h);
				gbuffer.fillRect(x, y, refWidth, h);
			}
			
			
			lstPivot = pivotIndex;
		
		
		
	}

	public void visualInsert(int c, int value) {

		if (lstInsert >= 0) {
			gbuffer.setColor(Color.GRAY);
			gbuffer.drawRect(
					(lstInsert * (refWidth + SortVisualisationPanel.gapSize))
							+ SortVisualisationPanel.margin,
					(height - (refHeight * elements[lstInsert])) - offsetY,
					refWidth, refHeight * elements[lstInsert]);
			gbuffer.fillRect(
					(lstInsert * (refWidth + SortVisualisationPanel.gapSize))
							+ SortVisualisationPanel.margin,
					(height - (refHeight * elements[lstInsert])) - offsetY,
					refWidth, refHeight * elements[lstInsert]);

		}

		gbuffer.setColor(SortVisualisationPanel.backgroundColor);
		gbuffer.drawRect((c * (refWidth + SortVisualisationPanel.gapSize))
				+ SortVisualisationPanel.margin,
				(height - (refHeight * elements[c])) - offsetY, refWidth,
				refHeight * elements[c]);
		gbuffer.fillRect((c * (refWidth + SortVisualisationPanel.gapSize))
				+ SortVisualisationPanel.margin,
				(height - (refHeight * elements[c])) - offsetY, refWidth,
				refHeight * elements[c]);

		gbuffer.setColor(Color.GREEN);
		gbuffer.drawRect((c * (refWidth + SortVisualisationPanel.gapSize))
				+ SortVisualisationPanel.margin, (height - (refHeight * value))
				- offsetY, refWidth, refHeight * value);
		gbuffer.fillRect((c * (refWidth + SortVisualisationPanel.gapSize))
				+ SortVisualisationPanel.margin, (height - (refHeight * value))
				- offsetY, refWidth, refHeight * value);

		lstInsert = c;

		repaint();

	}

	public void visualCmp(int c1, int c2, boolean changed) {

		// long t = System.currentTimeMillis();
		int x1, x2, y1, y2, h1, h2;

		// nur unteren bereich loeschen
		gbuffer.clearRect(0, height - offsetY, width, height);

		if (lstIndex1 >= 0 && lstIndex2 >= 0) {

			x1 = (lstIndex1 * (refWidth + SortVisualisationPanel.gapSize))
					+ SortVisualisationPanel.margin;
			x2 = (lstIndex2 * (refWidth + SortVisualisationPanel.gapSize))
					+ SortVisualisationPanel.margin;
			y1 = (height - (refHeight * elements[lstIndex1])) - offsetY;
			y2 = (height - (refHeight * elements[lstIndex2])) - offsetY;
			h1 = refHeight * elements[lstIndex1];
			h2 = refHeight * elements[lstIndex2];

			gbuffer.setColor(Color.GRAY);
			gbuffer.drawRect(x1, y1, refWidth, h1);
			gbuffer.fillRect(x1, y1, refWidth, h1);

			gbuffer.drawRect(x2, y2, refWidth, h2);
			gbuffer.fillRect(x2, y2, refWidth, h2);

		}

		x1 = (c1 * (refWidth + SortVisualisationPanel.gapSize))
				+ SortVisualisationPanel.margin;
		x2 = (c2 * (refWidth + SortVisualisationPanel.gapSize))
				+ SortVisualisationPanel.margin;
		y1 = (height - (refHeight * elements[c1])) - offsetY;
		y2 = (height - (refHeight * elements[c2])) - offsetY;
		h1 = refHeight * elements[c1];
		h2 = refHeight * elements[c2];

		if (changed) {

			gbuffer.setColor(SortVisualisationPanel.backgroundColor);
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

		int x1 = (int) (i1 * (refWidth + SortVisualisationPanel.gapSize) + (refWidth * 0.5))
				+ SortVisualisationPanel.margin;
		int x2 = (int) (i2 * (refWidth + SortVisualisationPanel.gapSize) + (refWidth * 0.5))
				+ SortVisualisationPanel.margin;

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

				gbuffer.drawRect((i * (refWidth + SortVisualisationPanel.gapSize))
						+ SortVisualisationPanel.margin,
						(height - (refHeight * elements[i])) - offsetY,
						refWidth, refHeight * elements[i]);
				gbuffer.fillRect((i * (refWidth + SortVisualisationPanel.gapSize))
						+ SortVisualisationPanel.margin,
						(height - (refHeight * elements[i])) - offsetY,
						refWidth, refHeight * elements[i]);

			}

			if (bi < 19) {
				gbuffer.setColor(Color.WHITE);
				gbuffer.setFont(Window.getComponentFont(26f));
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

		repaint();

		rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		gbuffer.setRenderingHints(rh);

	}

	public void updatePanelSize() {


		buffer = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setBackground(SortVisualisationPanel.backgroundColor);
		gbuffer.setFont(Window.getComponentFont(14f));
		drawElements();

	}
	
	public void setElements(int elements[]) {

		this.elements = elements;
		drawElements();

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(buffer, 0, 0, width, height, this);

	}
	
	public static void updateBarSize(){
		
		int elements[] = Sort.getElements();

		refHeight = (height - offsetY - SortVisualisationPanel.marginTop) / MathFunc.getMax(elements);
		refWidth = (width - (elements.length * SortVisualisationPanel.preferredGapSize))
				/ elements.length;

		if (refHeight <= 0)
			refHeight = 1;
		if (refWidth <= 0){
			
			double newBorder = ((elements.length-width)/((double)(-1)*elements.length));
			if(newBorder > 0) SortVisualisationPanel.gapSize = (int) newBorder;
			else SortVisualisationPanel.gapSize = 1;
			refWidth = 1;
		}
		
		else SortVisualisationPanel.gapSize = 3;

		SortVisualisationPanel.margin = (width - (elements.length * (refWidth + SortVisualisationPanel.gapSize))) / 2;
	}
	
	public static void updateSize(int width,int height){

		SortVisualisationPanel.width = width;
		SortVisualisationPanel.height = height;
		updateBarSize();
		
	}

	public static void setBackgroundColor(Color color) {
		SortVisualisationPanel.backgroundColor = color;
	}




	
	
	

}
